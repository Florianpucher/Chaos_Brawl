package com.strategy_bit.chaos_brawl.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;
import com.strategy_bit.chaos_brawl.ashley.components.MovementComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TeamGameObjectComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.ashley.components.UpgradeComponent;
import com.strategy_bit.chaos_brawl.ashley.engine.MyEngine;
import com.strategy_bit.chaos_brawl.ashley.entities.Particle;
import com.strategy_bit.chaos_brawl.ashley.entities.Unit;
import com.strategy_bit.chaos_brawl.config.UnitConfig;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.world.InputHandler;

import java.util.Iterator;
import java.util.Map;


public class UpgradeSystem extends IteratingSystem {

    private ComponentMapper<TeamGameObjectComponent> teamGameObjectComponentMapper;
    private ComponentMapper<TransformComponent> transformComponentMapper;
    private ComponentMapper<MovementComponent> movementComponentMapper;
    private ComponentMapper<UpgradeComponent> upgradeComponentMapper;


    UnitConfig config;

    private Engine engine;

    InputHandler inputHandler;


    public UpgradeSystem(InputHandler inputHandler) {
        super(Family.all(TeamGameObjectComponent.class).get());
        teamGameObjectComponentMapper = ComponentMapper.getFor(TeamGameObjectComponent.class);
        transformComponentMapper = ComponentMapper.getFor((TransformComponent.class));
        movementComponentMapper = ComponentMapper.getFor((MovementComponent.class));
        upgradeComponentMapper = ComponentMapper.getFor((UpgradeComponent.class));
        this.inputHandler = inputHandler;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // needs to be empty
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        this.engine = engine;
    }

    public Entity upgradeToNextTier(Entity entity, Iterator<Map.Entry<Long, Entity>> iterator) {
        TeamGameObjectComponent teComponent = teamGameObjectComponentMapper.get(entity);

        int entityID;
        int teamID;
        float hitPoints;
        Vector2 position;
        Queue<Vector2> path;

        if ((teComponent.getUnitType() == 1 || teComponent.getUnitType() == 11)) {

            TransformComponent trComponent = transformComponentMapper.get(entity);
            MovementComponent mComponent = movementComponentMapper.get(entity);
            UpgradeComponent uComponent = upgradeComponentMapper.get(entity);

            entityID = teComponent.getUnitId();
            teamID = teComponent.getTeamId();
            position = trComponent.getPosition();

            Entity entityNew = new Entity();

            if ((mComponent != null)) {                     // entity is a t1 unit

                hitPoints = teComponent.getHitPoints();
                path = mComponent.getPath();

                uComponent.setUnitID(entityID);

                config = AssetManager.getInstance().unitManager.unitConfigHashMap.get(uComponent.getUnitID());

                Unit.setComponents(entityNew, config, teamID, position, (MyEngine) engine);
                entityNew.getComponent(TeamGameObjectComponent.class).setHitPoints(hitPoints);
                entityNew.getComponent(MovementComponent.class).setPath(path);

            } else {                                     // entity is a t1 tower

                uComponent.setTowerID(entityID);

                config = AssetManager.getInstance().unitManager.unitConfigHashMap.get(uComponent.getTowerID());

                Unit.setComponents(entityNew, config, teamID, position, (MyEngine) engine);


            }
            engine.removeEntity(entity);
            engine.addEntity(new Particle(trComponent.getPosition(), "star"));
            inputHandler.upgradeEntityInternal(entityNew, config.getId());
            iterator.remove();
            return entityNew;


        }
        return null;

    }

}