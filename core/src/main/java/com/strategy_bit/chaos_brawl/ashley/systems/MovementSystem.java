package com.strategy_bit.chaos_brawl.ashley.systems;


import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.components.MovementComponent;
import com.strategy_bit.chaos_brawl.ashley.components.CombatComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.util.VectorMath;


/**
 * system for moving entities
 * <br>
 * handled components
 * <ul>
 *     <li>MovementComponent</li>
 *     <li>TransformComponent</li>
 * </ul>
 *
 * @author AIsopp
 * @version 1.0
 * @since 16.03.2018
 */

public class MovementSystem extends IteratingSystem {

    protected ComponentMapper<TransformComponent> mTransformComponent;
    protected ComponentMapper<MovementComponent> mMovementComponent;
    protected ComponentMapper<CombatComponent> mCombatComponent;

    public MovementSystem() {
        super(Family.all(TransformComponent.class, MovementComponent.class).get());
        mTransformComponent = ComponentMapper.getFor(TransformComponent.class);
        mMovementComponent = ComponentMapper.getFor(MovementComponent.class);
        mCombatComponent=ComponentMapper.getFor(CombatComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        //TODO moved this code part to somewhere else
        CombatComponent combatComponent=mCombatComponent.get(entity);
        if (combatComponent!=null){
            //Unit is attacking
            if(combatComponent.isEngagedInCombat()){
                //stop moving
                return;
            }
        }
        TransformComponent transform = mTransformComponent.get(entity);
        MovementComponent movementComponent = mMovementComponent.get(entity);
        // get actual position of entity
        Vector2 position = transform.getPosition();

        updateTarget(movementComponent,position);
        //get target position of entity
        Vector2 targetLocation = movementComponent.getTargetLocation();

        float speed = movementComponent.getSpeed();
        //TODO maybe velocity is not needed to be a field of movementComponent
        // velocity = normalVector(targetLocation - position) * speed
        Vector2 velocity = VectorMath.scl(VectorMath.nor(VectorMath.sub(targetLocation, position)), speed);
        movementComponent.setVelocity(velocity);
        float angle = getRotation(velocity);
        transform.setRotation(angle);
        //TODO Florian rotate entities here try to find out the direction of velocity
        // position = position + (velocity * deltaTime)
        transform.setPosition(VectorMath.add(position, VectorMath.scl(movementComponent.getVelocity(), Gdx.graphics.getDeltaTime())));

    }

    public float getRotation(Vector2 v){
        float angle = (float) (Math.atan2(v.y, v.x)) + (float) (Math.PI/2.0);
        return angle;
    }

    private void updateTarget(MovementComponent movementComponent, Vector2 position){
        Vector2 targetLocation=movementComponent.getTargetLocation();
        if(VectorMath.distance(targetLocation,position)<0.2){
            //System.out.println("POP");
            movementComponent.popCurTarget();
            return;
        }/*else if (movementComponent.hasNoPath()){
            movementComponent.setPath(calculatePathTo(position, targetLocation));
        }*/
    }
    /*public Array<Vector2> calculatePathTo(Vector2 position, Vector2 dest){
        Array<Vector2> path=new Array<Vector2>();
        path = Pathfinder.findPath(position, dest);
        //path.add(dest);
        return path;
    }*/
}
