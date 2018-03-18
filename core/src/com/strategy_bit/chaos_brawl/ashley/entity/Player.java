package com.strategy_bit.chaos_brawl.ashley.entity;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.components.MovementComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TextureComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;

/**
 * @author AIsopp
 * @version 1.0
 * @since 15.03.2018
 */
public class Player extends Entity {

    public Player() {
        TransformComponent transformComponent = new TransformComponent();
        transformComponent.setPosition(new Vector2(5,7.5f));
        TextureComponent textureComponent = new TextureComponent();
        textureComponent.setTexture(new TextureRegion(new Texture("character.png")));
        MovementComponent movementComponent = new MovementComponent(5,transformComponent);
        add(transformComponent);
        add(textureComponent);
        add(movementComponent);
    }
}
