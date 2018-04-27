package com.strategy_bit.chaos_brawl.controller;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.strategy_bit.chaos_brawl.ResourceSystem.Resource;
import com.strategy_bit.chaos_brawl.ResourceSystem.ResourceGold;
import com.strategy_bit.chaos_brawl.ashley.entity.Base;
import com.strategy_bit.chaos_brawl.ashley.entity.Player;
import com.strategy_bit.chaos_brawl.ashley.entity.PlayerClone;
import com.strategy_bit.chaos_brawl.ashley.entity.Tower;
import com.strategy_bit.chaos_brawl.ashley.systems.RenderSystem;
import com.strategy_bit.chaos_brawl.config.WorldSettings;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.types.UnitType;
import com.strategy_bit.chaos_brawl.util.Boundary;
import com.strategy_bit.chaos_brawl.world.InputHandler;
import com.strategy_bit.chaos_brawl.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;


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

    private static final double rate=0.1;

    private SpriteBatch batch;


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
            r.add(rate);
        }
    }
    public void startTicking(){
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                tick();
            }
        }, 0, 100);
    }
    public void createResource(){
        Resource resource=new ResourceGold();
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
        double cost=Double.MIN_VALUE;
        switch (unitType){
            case RANGED:
                cost=PlayerClone.COST;
                break;
            case MELEE:
                cost=Player.COST;
                break;
            default:
                break;
        }
        return checkAndSubtract(cost,"Gold");
    }

    public void gameOver (boolean win ) {

        batch = new SpriteBatch();

        if (win == true){
            Texture victory = AssetManager.getInstance().victoryScreen;
            batch.begin();
            batch.draw(victory, 10, 7);
            batch.end();
        }
        else if (win == false){
            Texture defeat = AssetManager.getInstance().defeatScreen;
            batch.begin();
            batch.draw(defeat, 10, 7);
            batch.end();
        }
    }
}
