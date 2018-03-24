package com.strategy_bit.chaos_brawl.ashley.systems;


import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.ashley.components.CombatComponent;
import com.strategy_bit.chaos_brawl.ashley.components.MovementComponent;
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
        CombatComponent combatComponent=mCombatComponent.get(entity);
        if (combatComponent!=null){
            if(combatComponent.isEngagedInCombat()){
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

        // position = position + (velocity * deltaTime)
        transform.setPosition(VectorMath.add(position, VectorMath.scl(movementComponent.getVelocity(), Gdx.graphics.getDeltaTime())));
        /*if(movementComponent.isDeleteOnTargetReached()){
            if(VectorMath.distance(targetLocation,position)<1){
                getEngine().removeEntity(entity);
            }
        }*/

    }

    private void updateTarget(MovementComponent movementComponent, Vector2 position){
        Vector2 targetLocation=movementComponent.getTargetLocation();
        if(VectorMath.distance(targetLocation,position)<1.0){

            movementComponent.popCurTarget();
            return;
        }else if (movementComponent.hasNoPath()){
            movementComponent.setPath(calculatePathTo(targetLocation));
        }
    }
    private Array<Vector2> calculatePathTo(Vector2 dest){
        Array<Vector2> path=new Array<Vector2>();
        //TODO: find path
        path.add(dest);
        return path;
    }
}
