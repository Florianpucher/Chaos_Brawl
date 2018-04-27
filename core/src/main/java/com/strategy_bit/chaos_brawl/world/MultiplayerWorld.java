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
    private BrawlMultiplayer multiplayer;
    private DeleteSystem deleteSystem;

    public MultiplayerWorld(boolean isServer, BrawlMultiplayer multiplayer, int players) {
        super(1, players);
        this.isServer = isServer;
        this.multiplayer = multiplayer;
        if(!isServer){
            engine.removeSystem(deleteSystem);
        }else{
            engine.setInputHandler(this);
        }
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
            multiplayer.sendTick();
        }
        engine.update(Gdx.graphics.getDeltaTime());
    }




    @Override
    protected void createEngine() {
        engine = new MyEngine(units);
        //Add some logic
        deleteSystem = new DeleteSystem();
        engine.addSystem(deleteSystem);
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
            long id = lastID;
            createEntityMultiPlayer(entity, id);
            lastID++;
            multiplayer.sendEntitySpawnMsg(worldCoordinates, entityType,teamID, id);
            if(entity.getComponent(MovementComponent.class) != null){
                Array<Vector2> path = gdxPathFinder.calculatePath(entity.getComponent(TransformComponent.class).getPosition(),
                        bases[spawnerController.getCurrentTargetTeam()].getComponent(TransformComponent.class).getPosition());
                entity.getComponent(MovementComponent.class).setPath(path);

                multiplayer.sendEntityMovingMessage(id, path);
            }
        }else {
            multiplayer.sendEntitySpawnMsg(worldCoordinates, entityType,teamID, -1);
        }
    }



    public void createEntityMultiPlayer(Entity entity, long unitID){
        engine.addEntity(entity);
        units.put(unitID, entity);
    }

    @Override
    public void createEntityLocal(Vector2 worldCoordinates, UnitType entityType, int teamID, long unitID) {
        Entity entity = spawner.createNewUnit(entityType,teamID,worldCoordinates);
        createEntityMultiPlayer(entity, unitID);
        //createEntity(entity);
    }

    @Override
    public void moveEntityLocal(long unitID, Array<Vector2> wayPoints) {
        Entity unit = units.get(unitID);
        unit.getComponent(MovementComponent.class).setPath(wayPoints);
    }

    @Override
    public void deleteUnitLocal(long unitID) {
        if(isServer){
            multiplayer.sendEntityDeleteMsg(unitID);
        }else{
            Entity unit = units.get(unitID);
            engine.removeEntity(unit);
        }

    }

    @Override
    public void unitAttackLocal(long attackerID, long victimID) {
        //TODO synchronize attacking
    }

    @Override
    public void getTick() {
        for (PawnController controller :
             playerControllers) {
            if(controller != null){
                controller.tick();
            }
        }
    }
}
