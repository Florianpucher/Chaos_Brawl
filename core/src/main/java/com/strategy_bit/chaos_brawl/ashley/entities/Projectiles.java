package com.strategy_bit.chaos_brawl.ashley.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.components.BulletComponent;
import com.strategy_bit.chaos_brawl.ashley.components.MovementComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TextureComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.ashley.engine.MyEngine;
import com.strategy_bit.chaos_brawl.config.UnitConfig;

public class Projectiles {
    public static void setComponents(Entity entity, UnitConfig unitConfig, Vector2 start, long target, float damage, MyEngine engine) {

        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        transformComponent.setPosition(start);

        TextureComponent textureComponent = engine.createComponent(TextureComponent.class);
        textureComponent.setTexture(unitConfig.getSkin());

        MovementComponent movementComponent = engine.createComponent(MovementComponent.class);
        movementComponent.setEverything(unitConfig.getSpeed(), transformComponent);

        BulletComponent bulletComponent= engine.createComponent(BulletComponent.class);
        bulletComponent.setDeleteWhenTargetIsReachedAndTargetIdAndDamage(true,target,damage);

        entity.add(bulletComponent);
        entity.add(movementComponent);
        entity.add(transformComponent);
        entity.add(textureComponent);
    }

    private Projectiles(){

    }
}
