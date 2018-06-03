package com.strategy_bit.chaos_brawl.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.strategy_bit.chaos_brawl.ashley.components.BulletComponent;

/**
 * @author AIsopp
 * @version 1.0
 * @since 24.03.2018
 */

public class BulletDeleteSystem extends IteratingSystem {
    private ComponentMapper<BulletComponent> mBulletComponent;

    public BulletDeleteSystem() {
        super(Family.all(BulletComponent.class).get());
        mBulletComponent = ComponentMapper.getFor(BulletComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {


        BulletComponent bulletComponent = mBulletComponent.get(entity);
        if (bulletComponent.isDelete()){
            getEngine().removeEntity(entity);
        }
    }



}
