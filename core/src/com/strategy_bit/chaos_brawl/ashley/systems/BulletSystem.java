package com.strategy_bit.chaos_brawl.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.components.BulletComponent;
import com.strategy_bit.chaos_brawl.ashley.components.MovementComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TextureComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.util.VectorMath;

/**
 * @author AIsopp
 * @version 1.0
 * @since 24.03.2018
 */

public class BulletSystem extends IteratingSystem {
    protected ComponentMapper<MovementComponent> mMovementComponent;
    protected ComponentMapper<BulletComponent> mBulletComponent;
    protected ComponentMapper<TransformComponent> mTransformComponent;

    public BulletSystem() {
        super(Family.all(BulletComponent.class, TransformComponent.class, MovementComponent.class).get());
        mMovementComponent = ComponentMapper.getFor(MovementComponent.class);
        mBulletComponent = ComponentMapper.getFor(BulletComponent.class);
        mTransformComponent = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        MovementComponent movementComponent = mMovementComponent.get(entity);
        TransformComponent transformComponent = mTransformComponent.get(entity);
        BulletComponent bulletComponent = mBulletComponent.get(entity);
        Vector2 targetLocation = movementComponent.getTargetLocation();
        Vector2 position = transformComponent.getPosition();
        if(bulletComponent.isDeleteWhenTargetIsReached()&&VectorMath.distance(targetLocation,position)<1){
            getEngine().removeEntity(entity);
        }
    }
}
