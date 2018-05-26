package com.strategy_bit.chaos_brawl.ashley.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.components.BoundaryComponent;
import com.strategy_bit.chaos_brawl.ashley.components.CombatComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TeamGameObjectComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TextureComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.ashley.engine.MyEngine;
import com.strategy_bit.chaos_brawl.managers.AssetManager;

import static com.strategy_bit.chaos_brawl.config.WorldSettings.PIXELS_TO_METRES;


public class Base {
    public static void setComponents(Entity entity,Vector2 position, int teamID){
        TransformComponent transformComponent = MyEngine.getInstance().createComponent(TransformComponent.class);
        transformComponent.setPosition(position);
        TextureComponent textureComponent = MyEngine.getInstance().createComponent(TextureComponent.class);
        textureComponent.setTexture(AssetManager.getInstance().mainTowerSkin);
        CombatComponent combatComponent = MyEngine.getInstance().createComponent(CombatComponent.class);
        combatComponent.setRadiusAndAttackRadiusAndAttackSpeedAndAttackDamageAndRanged(100000.0,4,1,20,true,false);
        TeamGameObjectComponent teamGameObjectComponent =MyEngine.getInstance().createComponent(TeamGameObjectComponent.class);
        teamGameObjectComponent.setEverything(1000.0,teamID);

        Vector2 size = new Vector2((textureComponent.getTexture().getRegionWidth() * PIXELS_TO_METRES),(textureComponent.getTexture().getRegionHeight() * PIXELS_TO_METRES));

        BoundaryComponent boundaryComponent = MyEngine.getInstance().createComponent(BoundaryComponent.class);
        boundaryComponent.setSizeAndTransformComponent(size,transformComponent);
        entity.add(boundaryComponent);
        entity.add(textureComponent);
        entity.add(transformComponent);
        entity.add(combatComponent);
        entity.add(teamGameObjectComponent);
        // add(new BuildingComponent(true));
    }
}
