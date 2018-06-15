package com.strategy_bit.chaos_brawl.ashley.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.components.BoundaryComponent;
import com.strategy_bit.chaos_brawl.ashley.components.CombatComponent;
import com.strategy_bit.chaos_brawl.ashley.components.ExplosionComponent;
import com.strategy_bit.chaos_brawl.ashley.components.MovementComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TeamGameObjectComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TextureComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.ashley.components.UpgradeComponent;
import com.strategy_bit.chaos_brawl.ashley.engine.MyEngine;
import com.strategy_bit.chaos_brawl.config.UnitConfig;

import static com.strategy_bit.chaos_brawl.config.WorldSettings.PIXELS_TO_METRES;

public class Unit {
    public static void setComponents(Entity entity, UnitConfig unitConfig, int teamId, Vector2 position, MyEngine engine) {

        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        transformComponent.setPosition(position);

        TextureComponent textureComponent = engine.createComponent(TextureComponent.class);
        textureComponent.setTexture(unitConfig.getSkin());

        if (unitConfig.hasMovementComponent()) {
            MovementComponent movementComponent = engine.createComponent(MovementComponent.class);
            movementComponent.setEverything(unitConfig.getSpeed(), transformComponent);
            entity.add(movementComponent);
        }

        CombatComponent combatComponent = engine.createComponent(CombatComponent.class);
        combatComponent.setEverything(unitConfig.getAttackRadius(),unitConfig.getAttackSpeed(),unitConfig.getAttackDamage(),unitConfig.isRanged(),unitConfig.isRangedAttackType());

        TeamGameObjectComponent teamGameObjectComponent = engine.createComponent(TeamGameObjectComponent.class);
        teamGameObjectComponent.setEverything(unitConfig.getHitPoints(),teamId);
        teamGameObjectComponent.setUnitType(unitConfig.getUnitType());
        teamGameObjectComponent.setUnitId(unitConfig.getUnitId());

        if (unitConfig.hasBoundaryComponent()){
            Vector2 size = new Vector2((textureComponent.getTexture().getRegionWidth() * PIXELS_TO_METRES),(textureComponent.getTexture().getRegionHeight() * PIXELS_TO_METRES));
            BoundaryComponent boundaryComponent = engine.createComponent(BoundaryComponent.class);
            boundaryComponent.setSizeAndTransformComponent(size,transformComponent);
            entity.add(boundaryComponent);
        }

        if (unitConfig.hasExplosionComponent()){
            ExplosionComponent explosionComponent = new ExplosionComponent();
            entity.add(explosionComponent);
        }

        if (unitConfig.hasUpgradeComponent()){
            UpgradeComponent upgradeComponent = new UpgradeComponent();
            entity.add(upgradeComponent);
        }

        entity.add(transformComponent);
        entity.add(textureComponent);
        entity.add(combatComponent);
        entity.add(teamGameObjectComponent);
    }

    private Unit(){

    }
}
