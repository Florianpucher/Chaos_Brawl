package com.strategy_bit.chaos_brawl.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.strategy_bit.chaos_brawl.ashley.components.ExplosionComponent;
import com.strategy_bit.chaos_brawl.ashley.components.MovementComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TeamGameObjectComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.ashley.entity.Explosion;
import com.strategy_bit.chaos_brawl.ashley.entity.Smoke;
import com.strategy_bit.chaos_brawl.managers.AssetManager;


/**
 * @author AIsopp
 * @version 1.0
 * @since 16.04.2018
 */
public class DeleteSystem extends IteratingSystem {

    protected ComponentMapper<TeamGameObjectComponent> mTeamGameObjectComponent;
    protected ComponentMapper<ExplosionComponent> explosionComponentComponentMapper;
    protected ComponentMapper<TransformComponent> transformComponentMapper;
    protected ComponentMapper<MovementComponent> movementComponentMapper;

    private Engine engine;
    private Camera camera;

    public DeleteSystem(Camera camera) {
        super(Family.all(TeamGameObjectComponent.class).get());
        mTeamGameObjectComponent = ComponentMapper.getFor(TeamGameObjectComponent.class);
        explosionComponentComponentMapper = ComponentMapper.getFor(ExplosionComponent.class);
        transformComponentMapper = ComponentMapper.getFor(TransformComponent.class);
        movementComponentMapper = ComponentMapper.getFor(MovementComponent.class);
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
            MovementComponent movementComponent = movementComponentMapper.get(entity);

            // Has explosion component
            // Play explosion

            if (explosionComponent != null && movementComponent == null) {  // true =  building
                // Get Position of object here
                TransformComponent transform = transformComponentMapper.get(entity);

                // and give it to the Explosion entity
                engine.addEntity(new Explosion(transform.getPosition()));
                AssetManager.getInstance().explosionSound.play(1f);
                explosionComponent.explode();

            } else if (explosionComponent != null && movementComponent != null) {  // true =  unit
                // Get Position of object here
                TransformComponent transform = transformComponentMapper.get(entity);

                // and give it to the Smoke entity
                engine.addEntity(new Smoke(transform.getPosition()));
                //AssetManager.getInstance().smokeSound.play(1f);    // there is no smokeSound atm, do we need any?
                explosionComponent.death();

            }

            engine.removeEntity(entity);

        }
    }

}
