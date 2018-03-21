package com.strategy_bit.chaos_brawl.ashley.systems;


import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.components.MovementComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.util.VectorMath;

/**
 * system for moving entities
 *
 * @author AIsopp
 * @version 1.0
 * @since 16.03.2018
 */

public class MovementSystem extends IteratingSystem {

    protected ComponentMapper<TransformComponent> mTransformComponent;
    protected ComponentMapper<MovementComponent> mMovementComponent;

    public MovementSystem() {
        super(Family.all(TransformComponent.class, MovementComponent.class).get());
        mTransformComponent = ComponentMapper.getFor(TransformComponent.class);
        mMovementComponent = ComponentMapper.getFor(MovementComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transform = mTransformComponent.get(entity);
        MovementComponent movementComponent = mMovementComponent.get(entity);
        Vector2 position = transform.getPosition();

        Vector2 targetLocation = movementComponent.getTargetLocation();
        float speed = movementComponent.getSpeed();
        // velocity = normal(targetLocation - position) * speed
        Vector2 velocity = VectorMath.scl(VectorMath.nor(VectorMath.sub(targetLocation, position)), speed);
        movementComponent.setVelocity(velocity);

        // position = position + (velocity * deltaTime)
        transform.setPosition(VectorMath.add(position, VectorMath.scl(movementComponent.getVelocity(), Gdx.graphics.getDeltaTime())));

    }
}
