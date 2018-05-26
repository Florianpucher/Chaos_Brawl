package com.strategy_bit.chaos_brawl.ashley.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.components.CombatComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TeamGameObjectComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TextureComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.ashley.engine.MyEngine;
import com.strategy_bit.chaos_brawl.managers.AssetManager;


public class Tower {

    public static void setComponents(Entity entity,Vector2 position, int teamID){
        TransformComponent transformComponent = MyEngine.getInstance().createComponent(TransformComponent.class);
        transformComponent.setPosition(position);
        TextureComponent textureComponent = MyEngine.getInstance().createComponent(TextureComponent.class);
        textureComponent.setTexture(AssetManager.getInstance().ballistaTowerSkin);
        CombatComponent combatComponent = MyEngine.getInstance().createComponent(CombatComponent.class);
        combatComponent.setRadiusAndAttackRadiusAndAttackSpeedAndAttackDamageAndRanged(100000.0,4,1,20,true,false);
        TeamGameObjectComponent teamGameObjectComponent =MyEngine.getInstance().createComponent(TeamGameObjectComponent.class);
        teamGameObjectComponent.setEverything(50.0,teamID);

        entity.add(textureComponent);
        entity.add(transformComponent);
        entity.add(combatComponent);
        entity.add(teamGameObjectComponent);
        // add(new BuildingComponent(true));
    }

}
