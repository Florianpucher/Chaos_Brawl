package com.strategy_bit.chaos_brawl.controller;


import com.badlogic.ashley.core.Entity;
import com.strategy_bit.chaos_brawl.ResourceSystem.Resource;
import com.strategy_bit.chaos_brawl.ResourceSystem.ResourceGold;
import com.strategy_bit.chaos_brawl.ashley.entity.Base;
import com.strategy_bit.chaos_brawl.ashley.entity.Player;
import com.strategy_bit.chaos_brawl.ashley.entity.PlayerClone;
import com.strategy_bit.chaos_brawl.ashley.entity.Tower;
import com.strategy_bit.chaos_brawl.config.WorldSettings;
import com.strategy_bit.chaos_brawl.types.UnitType;
import com.strategy_bit.chaos_brawl.util.Boundary;
import com.strategy_bit.chaos_brawl.world.InputHandler;
import com.strategy_bit.chaos_brawl.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


/**
 * Interface for communication between player(AI or human) and game
 *
 * @author AIsopp
 * @version 1.0
 * @since 15.03.2018
 */
public abstract class PawnController {

    protected InputHandler inputHandler;
    /**
     * in screen Coordinates
     */
    protected Boundary spawnArea;
    protected ArrayList<Resource> resources;
    private static final double rate=10.0;

    public PawnController(InputHandler inputHandler, Boundary spawnArea){
        this.inputHandler = inputHandler;
        this.spawnArea = spawnArea;
        this.resources = new ArrayList<Resource>();
        createResource();
    }
    public void tick(){
        for (Resource r :
                resources) {
            r.add(rate);
            //float width= WorldSettings.MAX_WIDTH*(float) r.percentageFull();
            //TODO paint bars somewhere on screen with percentage

        }


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
}
