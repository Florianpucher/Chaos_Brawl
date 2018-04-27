package com.strategy_bit.chaos_brawl.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.strategy_bit.chaos_brawl.ashley.components.TeamGameObjectComponent;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


/**
 * @author AIsopp
 * @version 1.0
 * @since 16.04.2018
 */
public class DeleteSystem extends IteratingSystem {
    
    protected ComponentMapper<TeamGameObjectComponent> mTeamGameObjectComponent;
    public DeleteSystem() {
        super(Family.all(TeamGameObjectComponent.class).get());
        mTeamGameObjectComponent = ComponentMapper.getFor(TeamGameObjectComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TeamGameObjectComponent component = mTeamGameObjectComponent.get(entity);
        if(component.getHitPoints() <= 0.0){
            getEngine().removeEntity(entity);
        }
    }
}
