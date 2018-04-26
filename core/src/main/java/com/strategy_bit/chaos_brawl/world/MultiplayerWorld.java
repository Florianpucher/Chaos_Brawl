package com.strategy_bit.chaos_brawl.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.ashley.components.MovementComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TeamGameObjectComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.ashley.engine.MyEngine;
import com.strategy_bit.chaos_brawl.ashley.entity.Archer;
import com.strategy_bit.chaos_brawl.ashley.entity.PlayerClone;
import com.strategy_bit.chaos_brawl.ashley.systems.BulletSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.CombatSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.DeleteSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.MovementSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.RenderSystem;
import com.strategy_bit.chaos_brawl.network.BrawlMultiplayer;
import com.strategy_bit.chaos_brawl.network.Server.BrawlServer;
import com.strategy_bit.chaos_brawl.network.Server.BrawlServerImpl;
import com.strategy_bit.chaos_brawl.network.messages.Request.EntitySpawnMessage;
import com.strategy_bit.chaos_brawl.player_input_output.OtherPlayerController;
import com.strategy_bit.chaos_brawl.player_input_output.PawnController;
import com.strategy_bit.chaos_brawl.types.UnitType;

public class MultiplayerWorld extends World implements MultiplayerInputHandler{

    private boolean isServer;
    private OtherPlayerController serverController;

    public MultiplayerWorld(boolean isServer) {
        this.isServer = isServer;
    }

    public void setServerController(OtherPlayerController serverController){
        this.serverController = serverController;
    }

    @Override
    public void render() {
        if(isServer){
            if(System.currentTimeMillis() - resourceTimeStamp > 1){
                for (PawnController controller :
                        playerControllers) {
                    controller.tick();
                    resourceTimeStamp = System.currentTimeMillis();
                }
            }
        }
        engine.update(Gdx.graphics.getDeltaTime());
    }

    @Override
    protected void createEngine() {
        engine = new MyEngine(units);
        //Add some logic
        /*if(isServer){
            engine.addSystem(new DeleteSystem());
            engine.addSystem(new CombatSystem());
        }*/
        //engine.addSystem(new DeleteSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new BulletSystem());
        engine.addSystem(new CombatSystem());
        //Renderer should be the last system to add
        RenderSystem renderSystem = new RenderSystem();
        engine.addSystem(renderSystem);
        camera = renderSystem.getCamera();
    }

    @Override
    public void createEntityWorldCoordinates(Vector2 worldCoordinates, UnitType entityType, int teamID) {
        //super.createEntityWorldCoordinates(worldCoordinates, entityType, teamID);
        if(isServer){
            Entity entity = spawner.createNewUnit(entityType,teamID,worldCoordinates);
            PawnController spawnerController = playerControllers[teamID];
            Array<Vector2> path = gdxPathFinder.calculatePath(entity.getComponent(TransformComponent.class).getPosition(),
                    bases[spawnerController.getCurrentTargetTeam()].getComponent(TransformComponent.class).getPosition());
            createEntity(entity);
            for (PawnController controller :
                    playerControllers) {
                controller.notifyAboutSpawning(worldCoordinates, entityType,teamID);
                controller.notifyAboutMoving(lastID-1, path);
            }

        }else {
            serverController.notifyAboutSpawning(worldCoordinates, entityType,teamID);
        }
    }

    @Override
    public void createEntityLocal(Vector2 worldCoordinates, UnitType entityType, int teamID) {
        Entity entity = spawner.createNewUnit(entityType,teamID,worldCoordinates);
        createEntity(entity);
    }

    @Override
    public void moveEntityLocal(long unitID, Array<Vector2> wayPoints) {
        Entity unit = units.get(unitID);
        unit.getComponent(MovementComponent.class).setPath(wayPoints);
    }

    @Override
    public void deleteUnitLocal(long unitID) {
        Entity unit = units.get(unitID);
        engine.removeEntity(unit);
    }

    @Override
    public void unitAttackLocal(long attackerID, long victimID) {
        //TODO synchronize attacking
    }
}
