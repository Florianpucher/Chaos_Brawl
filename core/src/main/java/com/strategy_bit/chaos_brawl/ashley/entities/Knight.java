package com.strategy_bit.chaos_brawl.ashley.entities;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.components.ExplosionComponent;
import com.strategy_bit.chaos_brawl.ashley.components.MovementComponent;
import com.strategy_bit.chaos_brawl.ashley.components.CombatComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TeamGameObjectComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TextureComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.ashley.components.UpgradeComponent;
import com.strategy_bit.chaos_brawl.ashley.engine.MyEngine;
import com.strategy_bit.chaos_brawl.managers.AssetManager;

/**
 * test entity
 *
 * @author Engin92
 * @version 1.0
 * @since 29.05.18
 */
public class Knight {
    public static void setComponents(Entity entity,Vector2 position, int teamId) {

        TransformComponent transformComponent = MyEngine.getInstance().createComponent(TransformComponent.class);
        transformComponent.setPosition(position);

        TextureComponent textureComponent = MyEngine.getInstance().createComponent(TextureComponent.class);
        textureComponent.setTexture(AssetManager.getInstance().skins.get("knightSkin"));

        MovementComponent movementComponent =MyEngine.getInstance().createComponent(MovementComponent.class);
        movementComponent.setEverything(3,transformComponent);

        CombatComponent combatComponent = MyEngine.getInstance().createComponent(CombatComponent.class);
        combatComponent.setEverything(1,1,5,false,false);

        TeamGameObjectComponent teamGameObjectComponent =MyEngine.getInstance().createComponent(TeamGameObjectComponent.class);
        teamGameObjectComponent.setEverything(120.0,teamId);

        ExplosionComponent explosionComponent = new ExplosionComponent();
        UpgradeComponent upgradeComponent = new UpgradeComponent();

        entity.add(upgradeComponent);
        entity.add(explosionComponent);
        entity.add(transformComponent);
        entity.add(textureComponent);
        entity.add(movementComponent);
        entity.add(combatComponent);
        entity.add(teamGameObjectComponent);
    }
}