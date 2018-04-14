package com.strategy_bit.chaos_brawl.ashley.entity;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.components.CombatComponent;
import com.strategy_bit.chaos_brawl.ashley.components.MovementComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TextureComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;

import com.strategy_bit.chaos_brawl.managers.AssetManager;

/**
 * test entity
 *
 * @author AIsopp
 * @version 1.0
 * @since 15.03.2018
 */
public class Player extends Entity {

    public Player() {
        TransformComponent transformComponent = new TransformComponent();
        transformComponent.setPosition(new Vector2(5,7.5f));
        TextureComponent textureComponent = new TextureComponent();
        textureComponent.setTexture(AssetManager.getInstance().playerSkin);
        MovementComponent movementComponent = new MovementComponent(5,transformComponent);
        CombatComponent combatComponent=new CombatComponent(10000.0,2,2,5,0,true);
        add(transformComponent);
        add(textureComponent);
        add(movementComponent);
        add(combatComponent);
    }
    public Player(Vector2 position, int teamId) {
        TransformComponent transformComponent = new TransformComponent();
        transformComponent.setPosition(position);
        TextureComponent textureComponent = new TextureComponent();
        textureComponent.setTexture(AssetManager.getInstance().playerSkin);
        MovementComponent movementComponent = new MovementComponent(5,transformComponent);
        CombatComponent combatComponent=new CombatComponent(10000.0,2,2,5,teamId,true);
        add(transformComponent);
        add(textureComponent);
        add(movementComponent);
        add(combatComponent);
    }
}
