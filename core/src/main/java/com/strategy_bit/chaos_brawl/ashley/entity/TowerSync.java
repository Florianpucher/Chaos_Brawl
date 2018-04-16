package com.strategy_bit.chaos_brawl.ashley.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.components.BuildingComponent;
import com.strategy_bit.chaos_brawl.ashley.components.NewCombatComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TeamGameObjectComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TextureComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.managers.AssetManager;


public class TowerSync extends Entity {

    public TowerSync(Vector2 position){
        TransformComponent transformComponent = new TransformComponent();
        transformComponent.setPosition(position);
        TextureComponent textureComponent = new TextureComponent();
        textureComponent.setTexture(AssetManager.getInstance().TowerSkin);
        NewCombatComponent newCombatComponent=new NewCombatComponent(100000.0,13,1,20,true);
        TeamGameObjectComponent teamGameObjectComponent = new TeamGameObjectComponent(30000.0,1);
        add(transformComponent);
        add(textureComponent);
        add(newCombatComponent);
        add(teamGameObjectComponent);
        add(new BuildingComponent(true));
    }
}
