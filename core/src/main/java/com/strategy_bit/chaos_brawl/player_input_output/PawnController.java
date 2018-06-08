package com.strategy_bit.chaos_brawl.player_input_output;


import com.badlogic.gdx.graphics.Camera;
import com.strategy_bit.chaos_brawl.config.UnitConfig;
import com.strategy_bit.chaos_brawl.config.WorldSettings;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.resource_system.Resource;
import com.strategy_bit.chaos_brawl.resource_system.ResourceGold;
import com.strategy_bit.chaos_brawl.types.EventType;
import com.strategy_bit.chaos_brawl.util.SpawnArea;
import com.strategy_bit.chaos_brawl.world.InputHandler;

import java.util.ArrayList;


/**
 * Interface for communication between player(AI or human) and game
 *
 * @author AIsopp
 * @version 1.0
 * @since 15.03.2018
 */
public abstract class PawnController {

    protected int teamID;
    protected InputHandler inputHandler;
    protected int currentTargetTeam;
    protected boolean cheatFunctionActive = true;
    /**
     * in screen Coordinates
     */
    protected SpawnArea spawnArea;
    protected ArrayList<Resource> resources;
    protected Camera camera;

    public float getNewRate() {
        return newRate;
    }

    public void setNewRate(float newRate) {
        this.newRate = newRate;
    }

    private float newRate = 1;

    public PawnController(int teamID,InputHandler inputHandler, SpawnArea spawnArea, Camera camera){
        this.inputHandler = inputHandler;
        this.camera = camera;
        this.spawnArea = spawnArea;
        this.teamID = teamID;
        this.resources = new ArrayList<>();
        createResource();

    }

    public int getTeamID() {
        return teamID;
    }

    public int getCurrentTargetTeam() {
        return currentTargetTeam;
    }

    public void setCurrentTargetTeam(int currentTargetTeam) {
        this.currentTargetTeam = currentTargetTeam;
    }



    public void tick(){
        for (Resource r :
                resources) {
            r.add(WorldSettings.RATE *newRate);
        }
    }

    public void createResource(){
        Resource resource=new ResourceGold(teamID);
        resources.add(resource);
    }

    public Resource getMana(){
        return resources.get(0);
    }

    public boolean checkAndSubtract(float cost,String resource){
        for (Resource r :
                resources) {
            if (r.getName().equals(resource)){
                return r.add(-cost);
            }
        }
        return false;
    }
    public boolean spawnUnit(int unitId){
        UnitConfig unitConfig=AssetManager.getInstance().unitManager.unitConfigHashMap.get(unitId);
        float cost= unitConfig.getCost();
        return checkAndSubtract(cost,"Gold");
    }

    public void gameOver (boolean win) {

    }

    /**
     *
     * @param type the type of the event
     * @param params additional parameter
     */
    public void triggeredEvent(EventType type, Object... params){

    }

    public boolean isCheatFunctionActive() {
        return cheatFunctionActive;
    }

    public void setCheatFunctionActive(boolean cheatFunctionActive) {
        this.cheatFunctionActive = cheatFunctionActive;
    }
}
