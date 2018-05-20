package com.strategy_bit.chaos_brawl.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.strategy_bit.chaos_brawl.ashley.components.ExplosionComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TeamGameObjectComponent;


/**
 * @author AIsopp
 * @version 1.0
 * @since 16.04.2018
 */
public class DeleteSystem extends IteratingSystem {
    
    protected ComponentMapper<TeamGameObjectComponent> mTeamGameObjectComponent;
    protected ComponentMapper<ExplosionComponent> explosionComponentComponentMapper;

    public DeleteSystem() {
        super(Family.all(TeamGameObjectComponent.class).get());
        mTeamGameObjectComponent = ComponentMapper.getFor(TeamGameObjectComponent.class);
        explosionComponentComponentMapper = ComponentMapper.getFor(ExplosionComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        removeEntity(entity);
        }


    public void removeEntity(Entity entity){
        TeamGameObjectComponent component = mTeamGameObjectComponent.get(entity);
        if(component.getHitPoints() <= 0.0){
            ExplosionComponent explosionComponent = explosionComponentComponentMapper.get(entity);

            // Has explosion component
            // Play explosion

            if(explosionComponent != null){
                explosionComponent.explode();
            }
            getEngine().removeEntity(entity);
        }
    }
}
