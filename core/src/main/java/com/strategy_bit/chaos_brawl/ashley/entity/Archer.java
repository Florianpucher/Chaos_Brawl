package com.strategy_bit.chaos_brawl.ashley.entity;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.components.MovementComponent;
import com.strategy_bit.chaos_brawl.ashley.components.CombatComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TeamGameObjectComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TextureComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.ashley.engine.MyEngine;
import com.strategy_bit.chaos_brawl.managers.AssetManager;

/**
 * test entity
 *
 * @author AIsopp
 * @version 1.0
 * @since 15.03.2018
 */
public class Archer extends Entity {
    public Archer(Vector2 position, int teamId) {
        TransformComponent transformComponent = MyEngine.getInstance().createComponent(TransformComponent.class);
        transformComponent.setPosition(position);
        TextureComponent textureComponent =MyEngine.getInstance().createComponent(TextureComponent.class);
        textureComponent.setTexture(AssetManager.getInstance().archerSkin);
        MovementComponent movementComponent =MyEngine.getInstance().createComponent(MovementComponent.class);
        movementComponent.setEverything(5,transformComponent);
        CombatComponent combatComponent = MyEngine.getInstance().createComponent(CombatComponent.class);
        combatComponent.setRadiusAndAttackRadiusAndAttackSpeedAndAttackDamageAndRanged(10000.0,3,2,5,true);
        TeamGameObjectComponent teamGameObjectComponent =MyEngine.getInstance().createComponent(TeamGameObjectComponent.class);
        teamGameObjectComponent.setEverything(50.0,teamId);
        add(transformComponent);
        add(textureComponent);
        add(movementComponent);
        add(combatComponent);
        add(teamGameObjectComponent);
    }
}
