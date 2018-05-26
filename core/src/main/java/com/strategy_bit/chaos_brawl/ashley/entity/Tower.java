package com.strategy_bit.chaos_brawl.ashley.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.components.CombatComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TeamGameObjectComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TextureComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.ashley.components.ExplosionComponent;
import com.strategy_bit.chaos_brawl.managers.AssetManager;


public class Tower extends Entity {

    public Tower(Vector2 position, int teamID){
        TransformComponent transformComponent = new TransformComponent();
        transformComponent.setPosition(position);
        TextureComponent textureComponent = new TextureComponent();
        textureComponent.setTexture(AssetManager.getInstance().ballistaTowerSkin);
        CombatComponent combatComponent =new CombatComponent(100000.0,4,1,20,true, false);
        TeamGameObjectComponent teamGameObjectComponent = new TeamGameObjectComponent(50.0,teamID);

        ExplosionComponent explosionComponent = new ExplosionComponent();

        add(textureComponent);
        add(transformComponent);
        add(combatComponent);
        add(teamGameObjectComponent);
        add(explosionComponent);
    }

}
