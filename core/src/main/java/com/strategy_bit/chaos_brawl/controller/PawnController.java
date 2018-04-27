package com.strategy_bit.chaos_brawl.controller;


import com.strategy_bit.chaos_brawl.ResourceSystem.Resource;
import com.strategy_bit.chaos_brawl.ResourceSystem.ResourceGold;
import com.strategy_bit.chaos_brawl.types.UnitType;
import com.strategy_bit.chaos_brawl.util.Boundary;
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
    /**
     * in screen Coordinates
     */
    protected Boundary spawnArea;
    protected ArrayList<Resource> resources;
    private static final double rate=1.0;

    public float getNewRate() {
        return newRate;
    }

    public void setNewRate(float newRate) {
        this.newRate = newRate;
    }

    private float newRate = 1;

    public PawnController(int teamID,InputHandler inputHandler, Boundary spawnArea){
        this.inputHandler = inputHandler;
        this.spawnArea = spawnArea;
        this.teamID = teamID;
        this.resources = new ArrayList<Resource>();
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
            r.add(rate*newRate);
        }
    }
    /*public void startTicking(){
        //TODO change this
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                tick();
            }
        }, 0, 1000);
        
        System.out.println("Archer "+teamID+" started ticking");
    }*/
    public void createResource(){
        Resource resource=new ResourceGold(teamID);
        resources.add(resource);
    }

    public boolean checkAndSubtract(double cost,String resource){
        for (Resource r :
                resources) {
            if (r.getName().equals(resource)){
                return r.add(-cost);
            }
        }
        return false;
    }
    public boolean spawnUnit(UnitType unitType){
        double cost= unitType.getCosts();
        return checkAndSubtract(cost,"Gold");
    }
}
