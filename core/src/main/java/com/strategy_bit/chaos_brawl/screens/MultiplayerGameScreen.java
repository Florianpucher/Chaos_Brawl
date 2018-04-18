package com.strategy_bit.chaos_brawl.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.controller.PlayerController;
import com.strategy_bit.chaos_brawl.network.BrawlMultiplayer;
import com.strategy_bit.chaos_brawl.network.BrawlNetworkInterface;
import com.strategy_bit.chaos_brawl.network.Server.BrawlServer;
import com.strategy_bit.chaos_brawl.types.UnitType;
import com.strategy_bit.chaos_brawl.world.MultiplayerWorld;

public class MultiplayerGameScreen extends GameScreen {

    private MultiplayerWorld manager;
    private BrawlMultiplayer brawlMultiplayer;

    public MultiplayerGameScreen(BrawlMultiplayer brawlMultiplayer) {
        super(1);
        this.brawlMultiplayer = brawlMultiplayer;
        manager = new MultiplayerWorld(brawlMultiplayer);
        brawlMultiplayer.setManager(manager);
        //TODO remove if testing phase is over
        //add User input
    }


    @Override
    protected void initializeGame(){
        if(brawlMultiplayer instanceof BrawlServer){
            manager.createEntityWorldCoordinates(new Vector2(5,7.5f), UnitType.MELEE, 9);
            manager.createEntityWorldCoordinates(new Vector2(3,12), UnitType.TOWER, 9);
            manager.createEntityWorldCoordinates(new Vector2(3,5), UnitType.TOWER, 9);
            manager.createEntityWorldCoordinates(new Vector2(2,9), UnitType.MAINBUILDING, 9);
            manager.createEntityWorldCoordinates(new Vector2(17,12), UnitType.TOWER, 8);
            manager.createEntityWorldCoordinates(new Vector2(17,5), UnitType.TOWER, 8);
            manager.createEntityWorldCoordinates(new Vector2(19,9), UnitType.MAINBUILDING, 8);
        }
    }
}
