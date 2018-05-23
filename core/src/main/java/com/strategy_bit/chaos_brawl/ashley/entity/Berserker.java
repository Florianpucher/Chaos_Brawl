package com.strategy_bit.chaos_brawl.ashley.entity;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.components.ExplosionComponent;
import com.strategy_bit.chaos_brawl.ashley.components.MovementComponent;
import com.strategy_bit.chaos_brawl.ashley.components.CombatComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TeamGameObjectComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TextureComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.ashley.components.UpgradeComponent;
import com.strategy_bit.chaos_brawl.managers.AssetManager;

// upgraded Swordfighter
public class Berserker extends Entity {
    public Berserker(Vector2 position, int teamId) {

        TransformComponent transformComponent = new TransformComponent();
        transformComponent.setPosition(position);

        TextureComponent textureComponent = new TextureComponent();
        textureComponent.setTexture(AssetManager.getInstance().berserkerSkin);

        MovementComponent movementComponent = new MovementComponent(5,transformComponent);
        CombatComponent combatComponent = new CombatComponent(10000.0,1,2.5,9,false);
        TeamGameObjectComponent teamGameObjectComponent = new TeamGameObjectComponent(95.0,teamId);
        ExplosionComponent explosionComponent = new ExplosionComponent();

        add(explosionComponent);
        add(transformComponent);
        add(textureComponent);
        add(movementComponent);
        add(combatComponent);
        add(teamGameObjectComponent);
    }
}
