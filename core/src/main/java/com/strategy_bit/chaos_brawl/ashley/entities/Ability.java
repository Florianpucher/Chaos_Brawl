package com.strategy_bit.chaos_brawl.ashley.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.components.AbilityComponent;
import com.strategy_bit.chaos_brawl.ashley.components.BoundaryComponent;
import com.strategy_bit.chaos_brawl.ashley.components.CombatComponent;
import com.strategy_bit.chaos_brawl.ashley.components.ExplosionComponent;
import com.strategy_bit.chaos_brawl.ashley.components.MovementComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TeamGameObjectComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TextureComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.ashley.components.UpgradeComponent;
import com.strategy_bit.chaos_brawl.ashley.engine.MyEngine;
import com.strategy_bit.chaos_brawl.config.AbilityConfig;
import com.strategy_bit.chaos_brawl.config.UnitConfig;

import static com.strategy_bit.chaos_brawl.config.WorldSettings.PIXELS_TO_METRES;

public class Ability {
    public static void setComponents(Entity entity, AbilityConfig abilityConfig, int teamId, Vector2 position) {
        MyEngine engine=MyEngine.getInstance();
        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        transformComponent.setPosition(position);

        TextureComponent textureComponent =engine.createComponent(TextureComponent.class);
        textureComponent.setTexture(abilityConfig.getSkin());

        Vector2 size = new Vector2((textureComponent.getTexture().getRegionWidth() * PIXELS_TO_METRES),(textureComponent.getTexture().getRegionHeight() * PIXELS_TO_METRES));
        BoundaryComponent boundaryComponent = engine.createComponent(BoundaryComponent.class);
        boundaryComponent.setSizeAndTransformComponent(size,transformComponent);

        AbilityComponent abilityComponent=engine.createComponent(AbilityComponent.class);
        abilityComponent.setAbilityId(abilityConfig.getAbilityId());
        abilityComponent.setTeamId(teamId);
        entity.add(abilityComponent);

        entity.add(boundaryComponent);
        entity.add(transformComponent);
        entity.add(textureComponent);
    }

    private Ability(){

    }
}
