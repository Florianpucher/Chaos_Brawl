package com.strategy_bit.chaos_brawl.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.strategy_bit.chaos_brawl.ashley.components.ExplosionComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TeamGameObjectComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.ashley.entity.Explosion;


/**
 * @author AIsopp
 * @version 1.0
 * @since 16.04.2018
 */
public class DeleteSystem extends IteratingSystem {

    protected ComponentMapper<TeamGameObjectComponent> mTeamGameObjectComponent;
    protected ComponentMapper<ExplosionComponent> explosionComponentComponentMapper;
    private ComponentMapper<TransformComponent> transformMapper;

    private Engine engine;
    private Camera camera;

    public DeleteSystem(Camera camera) {
        super(Family.all(TeamGameObjectComponent.class).get());
        mTeamGameObjectComponent = ComponentMapper.getFor(TeamGameObjectComponent.class);
        explosionComponentComponentMapper = ComponentMapper.getFor(ExplosionComponent.class);
        transformMapper = ComponentMapper.getFor(TransformComponent.class);
        this.camera = camera;
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
        TeamGameObjectComponent component = mTeamGameObjectComponent.get(entity);
        if (component.getHitPoints() <= 0.0) {
            ExplosionComponent explosionComponent = explosionComponentComponentMapper.get(entity);

            // Has explosion component
            // Play explosion

            if (explosionComponent != null) {
                // Get Position of object here
                TransformComponent transform = transformMapper.get(entity);

                // and give it to the Explosion entity
                engine.addEntity(new Explosion(transform.getPosition()));


                // explosionComponent.explode();
            }
            engine.removeEntity(entity);
        }
    }
}
