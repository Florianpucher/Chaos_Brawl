package com.strategy_bit.chaos_brawl.ashley.entity;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.components.MovementComponent;
import com.strategy_bit.chaos_brawl.ashley.components.NewCombatComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TeamGameObjectComponent;
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
public class PlayerClone extends Entity {

    public PlayerClone(Vector2 position) {
        TransformComponent transformComponent = new TransformComponent();
        transformComponent.setPosition(position);
        TextureComponent textureComponent = new TextureComponent();
        textureComponent.setTexture(AssetManager.getInstance().playerSkin);
        MovementComponent movementComponent = new MovementComponent(5,transformComponent);
        NewCombatComponent newCombatComponent= new NewCombatComponent(10.0,0,1,0,true);
        TeamGameObjectComponent teamGameObjectComponent = new TeamGameObjectComponent(10.0,6);
        add(transformComponent);
        add(textureComponent);
        add(movementComponent);
        add(newCombatComponent);
        add(teamGameObjectComponent);
    }
    public PlayerClone(Vector2 position,int teamId) {
        TransformComponent transformComponent = new TransformComponent();
        transformComponent.setPosition(position);
        TextureComponent textureComponent = new TextureComponent();
        textureComponent.setTexture(AssetManager.getInstance().playerSkin);
        MovementComponent movementComponent = new MovementComponent(5,transformComponent);
        //CombatComponent combatComponent=new CombatComponent(100.0,0,1,0,teamId,true);
        TeamGameObjectComponent teamGameObjectComponent = new TeamGameObjectComponent(10,teamId);
        add(transformComponent);
        add(textureComponent);
        add(movementComponent);
        add(teamGameObjectComponent);
        //add(combatComponent);
    }
}
