package com.strategy_bit.chaos_brawl.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.components.CombatComponent;
import com.badlogic.gdx.utils.Queue;
import com.strategy_bit.chaos_brawl.ashley.components.MovementComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TeamGameObjectComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.ashley.entities.Unit;
import com.strategy_bit.chaos_brawl.config.UnitConfig;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.world.InputHandler;

public class UpgradeSystem extends IteratingSystem {

    private ComponentMapper<TeamGameObjectComponent> teamGameObjectComponentMapper;
    private ComponentMapper<TransformComponent> transformComponentMapper;
    private ComponentMapper<MovementComponent> movementComponentMapper;
    private ComponentMapper<CombatComponent> combatComponentMapper;

    private double hitPoints;
    private int teamID;
    private Vector2 position;
    private float rotation;
    private double attackRadius;
    private Queue<Vector2> path;
    private Vector2 targetLocation;

    private Engine engine;

    InputHandler inputHandler;


    public UpgradeSystem(InputHandler inputHandler) {
        super(Family.all(TeamGameObjectComponent.class).get());
        teamGameObjectComponentMapper = ComponentMapper.getFor(TeamGameObjectComponent.class);
        transformComponentMapper = ComponentMapper.getFor((TransformComponent.class));
        movementComponentMapper = ComponentMapper.getFor((MovementComponent.class));
        combatComponentMapper = ComponentMapper.getFor((CombatComponent.class));
        this.inputHandler=inputHandler;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        UpgradeToNextTier(entity);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        this.engine = engine;
    }

    public void UpgradeToNextTier(Entity entity) {
        TeamGameObjectComponent teComponent = teamGameObjectComponentMapper.get(entity);

        if (teComponent.getUnitType() == 1 || teComponent.getUnitType() == 11) {
            CombatComponent cComponent = combatComponentMapper.get(entity);
            TransformComponent trComponent = transformComponentMapper.get(entity);
            MovementComponent mComponent = movementComponentMapper.get(entity);

            teamID = teComponent.getTeamId();
            attackRadius = cComponent.getAttackRadius();
            position = trComponent.getPosition();

            Entity entityNew = new Entity();

            if (mComponent != null) {                               // entity is a t1 unit

                hitPoints = teComponent.getHitPoints();
                rotation = trComponent.getRotation();
                path = mComponent.getPath();
                targetLocation = mComponent.getTargetLocation();

                UnitConfig config = AssetManager.getInstance().unitManager.unitConfigHashMap.get(3);

               Unit.setComponents(entityNew, config, teamID, position);
               entityNew.getComponent(TeamGameObjectComponent.class).setHitPoints(hitPoints);
               entityNew.getComponent(MovementComponent.class).setPath(path);


            } else {                                                // entity is a t1 tower

                UnitConfig config = AssetManager.getInstance().unitManager.unitConfigHashMap.get(8);
                Unit.setComponents(entityNew, config, teamID, position);

            }
            engine.removeEntity(entity);
            inputHandler.upgradeEntityInternal(entityNew);
        }

    }
}