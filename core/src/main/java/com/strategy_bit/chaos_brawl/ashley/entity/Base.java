package com.strategy_bit.chaos_brawl.ashley.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.components.BuildingComponent;
import com.strategy_bit.chaos_brawl.ashley.components.CombatComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TextureComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.managers.AssetManager;


public class Base extends Entity {

    public Base(){
        TransformComponent transformComponent = new TransformComponent();
        transformComponent.setPosition(new Vector2(20,20));
        TextureComponent textureComponent = new TextureComponent();
        textureComponent.setTexture(AssetManager.getInstance().wallSkin);
        //CombatComponent combatComponent=new CombatComponent(200000.0,12,1,30,0,true);
        add(transformComponent);
        add(textureComponent);
        //add(combatComponent);
        add(new BuildingComponent(true));
    }
}
