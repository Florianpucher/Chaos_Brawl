package com.strategy_bit.chaos_brawl.ashley.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.components.BulletComponent;
import com.strategy_bit.chaos_brawl.ashley.components.MovementComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TextureComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.managers.AssetManager;


public class Fireball extends Entity {
    public Fireball (Vector2 start,long target,float damage) {
        TransformComponent transformComponent = new TransformComponent();
        transformComponent.setPosition(start);
        TextureComponent textureComponent = new TextureComponent();
        textureComponent.setTexture(AssetManager.getInstance().fireballSkin);
        MovementComponent movementComponent = new MovementComponent(5,transformComponent);
        add(transformComponent);
        add(textureComponent);
        add(movementComponent);
        add(new BulletComponent(true,target,damage));
    }
}
