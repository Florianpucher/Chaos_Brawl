package com.strategy_bit.chaos_brawl.ashley.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.strategy_bit.chaos_brawl.ashley.components.BulletComponent;
import com.strategy_bit.chaos_brawl.ashley.components.MovementComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TextureComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.ashley.engine.MyEngine;
import com.strategy_bit.chaos_brawl.managers.AssetManager;

/**
 * Created by A_329_09 on 22/03/2018.
 */

public class Projectile extends Entity implements Pool.Poolable {
    public Projectile(Vector2 start,long target,float damage) {
        TransformComponent transformComponent = MyEngine.getInstance().createComponent(TransformComponent.class);
        transformComponent.setPosition(start);
        TextureComponent textureComponent = MyEngine.getInstance().createComponent(TextureComponent.class);
        textureComponent.setTexture(AssetManager.getInstance().projectileSkin);
        MovementComponent movementComponent =MyEngine.getInstance().createComponent(MovementComponent.class);
        movementComponent.setEverything(10,transformComponent);
        add(transformComponent);
        add(textureComponent);
        add(movementComponent);
        BulletComponent bulletComponent= MyEngine.getInstance().createComponent(BulletComponent.class);
        bulletComponent.setDeleteWhenTargetIsReachedAndTargetIdAndDamage(true,target,damage);
        add(bulletComponent);
    }

    public Projectile() {
        TransformComponent transformComponent = MyEngine.getInstance().createComponent(TransformComponent.class);
        TextureComponent textureComponent = MyEngine.getInstance().createComponent(TextureComponent.class);
        textureComponent.setTexture(AssetManager.getInstance().projectileSkin);
        MovementComponent movementComponent =MyEngine.getInstance().createComponent(MovementComponent.class);
        movementComponent.setEverything(10,transformComponent);
        add(transformComponent);
        add(textureComponent);
        add(movementComponent);
        BulletComponent bulletComponent= MyEngine.getInstance().createComponent(BulletComponent.class);
        add(bulletComponent);
    }

    public void setEverything(Vector2 start,long target,float damage) {
        this.getComponent(TransformComponent.class).setPosition(start);
        this.getComponent(BulletComponent.class).setDeleteWhenTargetIsReachedAndTargetIdAndDamage(true,target,damage);
    }

    @Override
    public void reset() {

    }
}
