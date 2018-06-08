package com.strategy_bit.chaos_brawl.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.strategy_bit.chaos_brawl.ashley.components.ExplosionComponent;
import com.strategy_bit.chaos_brawl.ashley.components.MovementComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TeamGameObjectComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.ashley.entities.Particle;
import com.strategy_bit.chaos_brawl.managers.AssetManager;


/**
 * @author AIsopp
 * @version 1.0
 * @since 16.04.2018
 */
public class DeleteSystem extends IteratingSystem {

    private ComponentMapper<TeamGameObjectComponent> teamGameObjectComponentMapper;
    private ComponentMapper<ExplosionComponent> explosionComponentComponentMapper;
    private ComponentMapper<TransformComponent> transformComponentMapper;
    private ComponentMapper<MovementComponent> movementComponentMapper;

    private Engine engine;

    public DeleteSystem() {
        super(Family.all(TeamGameObjectComponent.class).get());
        teamGameObjectComponentMapper = ComponentMapper.getFor(TeamGameObjectComponent.class);
        explosionComponentComponentMapper = ComponentMapper.getFor(ExplosionComponent.class);
        transformComponentMapper = ComponentMapper.getFor(TransformComponent.class);
        movementComponentMapper = ComponentMapper.getFor(MovementComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        removeEntity(entity);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        this.engine = engine;
    }

    public void removeEntity(Entity entity) {
        TeamGameObjectComponent component = teamGameObjectComponentMapper.get(entity);
        if (component.getHitPoints() <= 0.0) {
            ExplosionComponent explosionComponent = explosionComponentComponentMapper.get(entity);
            MovementComponent movementComponent = movementComponentMapper.get(entity);

            if (explosionComponent != null && movementComponent == null) {  // true =  building

                TransformComponent transform = transformComponentMapper.get(entity);
                engine.addEntity(new Particle(transform.getPosition(), "explosion"));
                    AssetManager.getInstance().explosionSound.play(1f);

            } else if (explosionComponent != null) {  // true =  unit

                TransformComponent transform = transformComponentMapper.get(entity);
                engine.addEntity(new Particle(transform.getPosition(), "smoke"));

            }

            engine.removeEntity(entity);

        }
    }

}
