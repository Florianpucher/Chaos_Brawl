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
    public static void setComponents(Entity entity, UnitConfig unitConfig, Vector2 start, long target, float damage) {

        TransformComponent transformComponent = MyEngine.getInstance().createComponent(TransformComponent.class);
        transformComponent.setPosition(start);

        TextureComponent textureComponent =MyEngine.getInstance().createComponent(TextureComponent.class);
        textureComponent.setTexture(unitConfig.getSkin());

        MovementComponent movementComponent = MyEngine.getInstance().createComponent(MovementComponent.class);
        movementComponent.setEverything(unitConfig.getSpeed(), transformComponent);

        BulletComponent bulletComponent= MyEngine.getInstance().createComponent(BulletComponent.class);
        bulletComponent.setDeleteWhenTargetIsReachedAndTargetIdAndDamage(true,target,damage);

        entity.add(bulletComponent);
        entity.add(movementComponent);
        entity.add(transformComponent);
        entity.add(textureComponent);
    }

    private Projectiles(){

    }
}
