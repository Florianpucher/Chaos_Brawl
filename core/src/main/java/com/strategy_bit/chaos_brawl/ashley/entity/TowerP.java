package com.strategy_bit.chaos_brawl.ashley.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.components.BuildingComponent;
import com.strategy_bit.chaos_brawl.ashley.components.NewCombatComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TeamGameObjectComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TextureComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.managers.AssetManager;


public class TowerP extends Entity {

    public TowerP(Vector2 position){
        TransformComponent transformComponent = new TransformComponent();
        transformComponent.setPosition(position);
        TextureComponent textureComponent = new TextureComponent();
        textureComponent.setTexture(AssetManager.getInstance().ballistaTowerSkin);
        NewCombatComponent newCombatComponent=new NewCombatComponent(100000.0,4,1,20,true);
        TeamGameObjectComponent teamGameObjectComponent = new TeamGameObjectComponent(50.0,9);

        add(transformComponent);
        add(textureComponent);
        add(newCombatComponent);
        add(teamGameObjectComponent);
        //add(new BuildingComponent(true));
    }
}
