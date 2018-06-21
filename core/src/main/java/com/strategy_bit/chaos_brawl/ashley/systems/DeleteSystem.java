package com.strategy_bit.chaos_brawl.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.strategy_bit.chaos_brawl.ashley.components.BaseComponent;
import com.strategy_bit.chaos_brawl.ashley.components.ExplosionComponent;
import com.strategy_bit.chaos_brawl.ashley.components.MovementComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TeamGameObjectComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.ashley.entities.Particle;
import com.strategy_bit.chaos_brawl.managers.SoundManager;
import com.strategy_bit.chaos_brawl.world.MultiplayerInputHandler;

import java.util.Iterator;
import java.util.Map;


/**
 * system for deleting units if their health points <= 0
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
    private Map<Long, Entity> units;
    private MultiplayerInputHandler inputHandler;

    public DeleteSystem(Map<Long, Entity> units) {
        super(Family.all(TeamGameObjectComponent.class).get());
        this.units = units;
        teamGameObjectComponentMapper = ComponentMapper.getFor(TeamGameObjectComponent.class);
        explosionComponentComponentMapper = ComponentMapper.getFor(ExplosionComponent.class);
        transformComponentMapper = ComponentMapper.getFor(TransformComponent.class);
        movementComponentMapper = ComponentMapper.getFor(MovementComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if(removeEntity(entity)){
            Iterator<Map.Entry<Long,Entity>> iterator = units.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<Long,Entity> entry = iterator.next();
                if(entry.getValue() == entity){
                    if(inputHandler != null){
                        inputHandler.deleteUnitLocal(entry.getKey());
                    }
                    iterator.remove();
                    break;
                }
            }
        }
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        this.engine = engine;
    }

    public boolean removeEntity(Entity entity) {
        TeamGameObjectComponent teamGameObjectComponent = teamGameObjectComponentMapper.get(entity);
        if (teamGameObjectComponent.getHitPoints() <= 0.0) {
            BaseComponent baseComponent=entity.getComponent(BaseComponent.class);
            if (baseComponent!=null){
                baseComponent.setDestroyed(true);
            }
            ExplosionComponent explosionComponent = explosionComponentComponentMapper.get(entity);
            MovementComponent movementComponent = movementComponentMapper.get(entity);

            // Has explosion component
            // Play explosion

            if (movementComponent == null && teamGameObjectComponent.getUnitId() != 18 && teamGameObjectComponent.getUnitId() != 19) {  // true =  building
                // Get Position of object here
                TransformComponent transform = transformComponentMapper.get(entity);

                // and give it to the Explosion entity
                engine.addEntity(new Particle(transform.getPosition(), "explosion"));
                SoundManager.getInstance().playSound("explosionSound");
            } else if (explosionComponent != null) {  // true =  unit
                // Get Position of object here
                TransformComponent transform = transformComponentMapper.get(entity);

                // and give it to the Smoke entity
                engine.addEntity(new Particle(transform.getPosition(), "smoke"));

            }

            engine.removeEntity(entity);
            return true;
        }
        return false;
    }

    /**
     * if set it will invoke the {@link com.strategy_bit.chaos_brawl.world.MultiplayerInputHandler#deleteUnitLocal(long)}
     * method during the {@link #processEntity(Entity, float)} method if an unit dies
     *
     * @param inputHandler sets a multiplayer input handler
     */
    public void setInputHandler(MultiplayerInputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }
}
