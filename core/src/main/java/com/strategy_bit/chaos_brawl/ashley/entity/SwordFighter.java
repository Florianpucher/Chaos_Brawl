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

/**
 * test entity
 *
 * @author Engin92
 * @version 1.0
 * @since 29.05.18
 */
public class SwordFighter extends Entity {
    public SwordFighter(Vector2 position, int teamId) {

        TransformComponent transformComponent = new TransformComponent();
        transformComponent.setPosition(position);

        TextureComponent textureComponent = new TextureComponent();
        textureComponent.setTexture(AssetManager.getInstance().swordFighterSkin);

        MovementComponent movementComponent = new MovementComponent(5,transformComponent);
        CombatComponent combatComponent = new CombatComponent(10000.0,1,2.5,7,false, false);
        TeamGameObjectComponent teamGameObjectComponent = new TeamGameObjectComponent(75.0,teamId);
        ExplosionComponent explosionComponent = new ExplosionComponent();
        UpgradeComponent upgradeComponent = new UpgradeComponent();

        add(upgradeComponent);
        add(explosionComponent);
        add(transformComponent);
        add(textureComponent);
        add(movementComponent);
        add(combatComponent);
        add(teamGameObjectComponent);
    }
}
