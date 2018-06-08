package com.strategy_bit.chaos_brawl.ashley.systems;


import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.components.CombatComponent;
import com.strategy_bit.chaos_brawl.ashley.components.MovementComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.util.VectorMath;


/**
 * system for moving entities
 * <br>
 * handled components
 * <ul>
 * <li>MovementComponent</li>
 * <li>TransformComponent</li>
 * </ul>
 *
 * @author AIsopp
 * @version 1.0
 * @since 16.03.2018
 */

public class MovementSystem extends IteratingSystem {

    private ComponentMapper<TransformComponent> mTransformComponent;
    private ComponentMapper<MovementComponent> mMovementComponent;
    private ComponentMapper<CombatComponent> mCombatComponent;

    public MovementSystem() {
        super(Family.all(TransformComponent.class, MovementComponent.class).get());
        mTransformComponent = ComponentMapper.getFor(TransformComponent.class);
        mMovementComponent = ComponentMapper.getFor(MovementComponent.class);
        mCombatComponent = ComponentMapper.getFor(CombatComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CombatComponent combatComponent = mCombatComponent.get(entity);
        if (combatComponent != null && combatComponent.isAttacking()) {
            return;
        }
        TransformComponent transform = mTransformComponent.get(entity);
        MovementComponent movementComponent = mMovementComponent.get(entity);
        // get actual position of entity
        Vector2 position = transform.getPosition();

        updateTarget(movementComponent, position);
        //get target position of entity
        Vector2 targetLocation = movementComponent.getTargetLocation();

        float speed = movementComponent.getSpeed();
        // velocity = normalVector(targetLocation - position) * speed
        Vector2 velocity = VectorMath.scl(VectorMath.nor(VectorMath.sub(targetLocation, position)), speed);
        float angle = getRotation(velocity);
        transform.setRotation(angle);

        // position = position + (velocity * deltaTime)
        transform.setPosition(VectorMath.add(position, VectorMath.scl(velocity, Gdx.graphics.getDeltaTime())));

    }

    public float getRotation(Vector2 v) {
        return (float) (Math.atan2(v.y, v.x)) + (float) (Math.PI / 2.0);
    }

    private void updateTarget(MovementComponent movementComponent, Vector2 position) {
        Vector2 targetLocation = movementComponent.getTargetLocation();
        if (VectorMath.distance(targetLocation, position) < 0.2) {
            movementComponent.popCurTarget();
        }
    }
}
