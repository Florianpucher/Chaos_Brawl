package com.strategy_bit.chaos_brawl.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.ashley.components.MovementComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TeamGameObjectComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.network.BrawlMultiplayer;
import com.strategy_bit.chaos_brawl.player_input_output.PawnController;
import com.strategy_bit.chaos_brawl.player_input_output.PlayerController;

public class MultiplayerWorld extends World implements MultiplayerInputHandler{

    private BrawlMultiplayer multiplayer;
    private boolean isInitialized = false;

    public MultiplayerWorld() {
        super();
    }

    public MultiplayerWorld(BrawlMultiplayer multiplayer, int players, int map) {
        super(map, players);

        this.multiplayer = multiplayer;
        if(!multiplayer.isHost()){
            engine.removeSystem(deleteSystem);
        }else{
            engine.setInputHandler(this);
        }
    }

    @Override
    public void render() {
        if(multiplayer.isHost()){
            updateResources();

        }
        engine.update(Gdx.graphics.getDeltaTime());
        //Check if bases are already initialized
        if(!isInitialized){
            boolean basesAreUp = true;
            for (Entity base :
                    bases) {
                if(base == null){
                    basesAreUp = false;
                    break;
                }
            }
            if(basesAreUp){
                for (PawnController cont :
                        playerControllers) {
                    if (cont instanceof PlayerController){
                        updateMarker(cont.getCurrentTargetTeam());
                    }
                }
                isInitialized = true;
            }
        }else{
            checkWinningLosing();
        }

    }

    @Override
    protected void updateResources() {
        if(System.currentTimeMillis() - resourceTimeStamp > 1){
            for (PawnController controller :
                    playerControllers) {
                controller.tick();

            }
            resourceTimeStamp = System.currentTimeMillis();
            multiplayer.sendTick();
        }
    }


    public void createEntityWorldCoordinates(Vector2 worldCoordinates, int unitId, int teamID) {
                if(multiplayer.isHost()){

            PawnController spawnerController = playerControllers[teamID];
            long id = lastID;
            Entity entity = createEntityInternal(unitId, id,worldCoordinates, teamID);
            lastID++;
            multiplayer.sendEntitySpawnMsg(worldCoordinates, unitId,teamID, id);
            if(entity.getComponent(MovementComponent.class) != null){
                Array<Vector2> path = gdxPathFinder.calculatePath(entity.getComponent(TransformComponent.class).getPosition(),
                        bases[spawnerController.getCurrentTargetTeam()].getComponent(TransformComponent.class).getPosition());
                entity.getComponent(MovementComponent.class).setPath(path);

                multiplayer.sendEntityMovingMessage(id, path);
            }
        }else {
            multiplayer.sendEntitySpawnMsg(worldCoordinates, unitId,teamID, -1);
        }
    }

    @Override
    public void createEntityLocal(Vector2 worldCoordinates, int unitId, int teamID, long unitID) {
        createEntityInternal(unitId, unitID, worldCoordinates, teamID);

    }

    @Override
    public void moveEntityLocal(long unitID, Array<Vector2> wayPoints) {
        Entity unit = units.get(unitID);
        unit.getComponent(MovementComponent.class).setPath(wayPoints);
    }

    @Override
    public void deleteUnitLocal(long unitID) {
        if(multiplayer.isHost()){
            multiplayer.sendEntityDeleteMsg(unitID);
        }else{
            Entity unit = units.get(unitID);
            if(unit==null){
                return;
            }
            unit.getComponent(TeamGameObjectComponent.class).setHitPoints(0.0f);
            deleteSystem.removeEntity(unit);
        }

    }

    @Override
    public void unitAttackLocal(long attackerID, long victimID) {
        //TODO synchronize attacking
    }

    @Override
    protected int checkIfClickHappensOnBase(Vector2 targetLocation, PawnController controller) {
        int baseIndex = super.checkIfClickHappensOnBase(targetLocation, controller);
        if(!multiplayer.isHost() && baseIndex >= 0){
            multiplayer.sendNewTargetMsg(controller.getTeamID(), baseIndex);
        }
        return baseIndex;
    }

    @Override
    public void playerChangesTarget(int playerIndex, int targetIndex) {
        playerControllers[playerIndex].setCurrentTargetTeam(targetIndex);
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
