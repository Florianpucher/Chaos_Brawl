package com.strategy_bit.chaos_brawl.ashley.entities;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.components.CombatComponent;
import com.strategy_bit.chaos_brawl.ashley.components.ExplosionComponent;
import com.strategy_bit.chaos_brawl.ashley.components.MovementComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TeamGameObjectComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TextureComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.ashley.engine.MyEngine;
import com.strategy_bit.chaos_brawl.managers.AssetManager;

// upgraded Knight
public class Templar {
    public static void setComponents(Entity entity,Vector2 position, int teamId) {

        MyEngine engine=MyEngine.getInstance();

        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        transformComponent.setPosition(position);

        TextureComponent textureComponent = engine.createComponent(TextureComponent.class);
        textureComponent.setTexture(AssetManager.getInstance().skins.get("templarSkin"));

        MovementComponent movementComponent = engine.createComponent(MovementComponent.class);
        movementComponent.setEverything(3,transformComponent);

        CombatComponent combatComponent = engine.createComponent(CombatComponent.class);
        combatComponent.setEverything(1,1,6,false, false);

        TeamGameObjectComponent teamGameObjectComponent = engine.createComponent(TeamGameObjectComponent.class);
        teamGameObjectComponent.setEverything(150.0,teamId);

        ExplosionComponent explosionComponent = engine.createComponent(ExplosionComponent.class);

        entity.add(explosionComponent);
        entity.add(transformComponent);
        entity.add(textureComponent);
        entity.add(movementComponent);
        entity.add(combatComponent);
        entity.add(teamGameObjectComponent);
    }
}