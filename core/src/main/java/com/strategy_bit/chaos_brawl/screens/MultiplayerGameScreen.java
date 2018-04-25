package com.strategy_bit.chaos_brawl.screens;

import com.strategy_bit.chaos_brawl.network.BrawlMultiplayer;
import com.strategy_bit.chaos_brawl.network.Server.BrawlServer;
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
           super.initializeGame();
        }
    }
}
