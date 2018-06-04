package com.strategy_bit.chaos_brawl.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.components.BulletComponent;
import com.strategy_bit.chaos_brawl.ashley.components.MovementComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TeamGameObjectComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TextureComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.ashley.engine.MyEngine;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.util.VectorMath;
import com.strategy_bit.chaos_brawl.world.World;

import java.util.HashMap;

/**
 * @author AIsopp
 * @version 1.0
 * @since 24.03.2018
 */

public class BulletSystem extends IteratingSystem {
    protected ComponentMapper<MovementComponent> mMovementComponent;
    protected ComponentMapper<BulletComponent> mBulletComponent;
    protected ComponentMapper<TransformComponent> mTransformComponent;
    private static final float TARGET_RADIUS = 0.3f;
    private World world;

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
        Vector2 newTargetLocation = world.getUnitPosition(bulletComponent.getTargetId());
        updateTargetLocation(newTargetLocation, movementComponent);
        Vector2 targetLocation = movementComponent.getTargetLocation();
        Vector2 position = transformComponent.getPosition();
        if (newTargetLocation == null && VectorMath.distance(targetLocation, position) < TARGET_RADIUS) {
            //target died already
            bulletComponent.setDelete(true);
        } else if (bulletComponent.isDeleteWhenTargetIsReached() && VectorMath.distance(targetLocation, position) < TARGET_RADIUS) {
            TeamGameObjectComponent enemyTeamGameObjectComponent = world.getUnit(bulletComponent.getTargetId()).getComponent(TeamGameObjectComponent.class);
            enemyTeamGameObjectComponent.setHitPoints(enemyTeamGameObjectComponent.getHitPoints() - bulletComponent.getDamage());
            bulletComponent.setDelete(true);
            if (AssetManager.getInstance().getPlayable()){
                AssetManager.getInstance().hitArrow.play(1f);
            }
        }
    }

    public void addWorld(World world) {
        this.world = world;
    }

    private void updateTargetLocation(Vector2 newLocation, MovementComponent movementComponent) {
        if (newLocation != null) {
            movementComponent.setTargetLocation(newLocation);
        }
    }
}
