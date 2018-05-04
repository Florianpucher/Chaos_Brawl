package com.strategy_bit.chaos_brawl.screens;

import com.strategy_bit.chaos_brawl.config.Network;
import com.strategy_bit.chaos_brawl.network.BrawlConnector;
import com.strategy_bit.chaos_brawl.network.BrawlMultiplayer;
import com.strategy_bit.chaos_brawl.network.server.BrawlServer;
import com.strategy_bit.chaos_brawl.player_input_output.OtherPlayerController;
import com.strategy_bit.chaos_brawl.player_input_output.PawnController;
import com.strategy_bit.chaos_brawl.player_input_output.PlayerController;
import com.strategy_bit.chaos_brawl.world.MultiplayerInputHandler;
import com.strategy_bit.chaos_brawl.world.MultiplayerWorld;

public class MultiplayerGameScreen extends GameScreen {

    //private MultiplayerWorld manager;
    private BrawlMultiplayer brawlMultiplayer;
    private BrawlConnector listener;
    private int player;


    public MultiplayerGameScreen(BrawlMultiplayer brawlMultiplayer,  int[] players) {
        super(1);
        this.brawlMultiplayer = brawlMultiplayer;
        for (int i = 0; i < players.length; i++) {
            if(players[i] == Network.YOUR_CLIENT_CONTROLLER){
                this.player = i;
            }
        }
        controllers = new PawnController[players.length];
        this.listener = brawlMultiplayer.getBrawlConnector();
    }

    public void setListener(BrawlConnector listener){
        this.listener = listener;
    }


    @Override
    public void buildStage() {
        boolean isServer = false;
        if(brawlMultiplayer instanceof BrawlServer){
            isServer = true;
        }
        manager = new MultiplayerWorld(isServer, brawlMultiplayer, controllers.length);
        listener.setMultiplayerInputHandler((MultiplayerInputHandler) manager);
        initializeGame();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    protected void initializeGame(){
        for (int i = 0; i < controllers.length; i++) {
            if(i == player){
                controller = new PlayerController(i, manager, manager.createSpawnAreaForPlayer(i));
                manager.setPlayerController(i, controller);
                controllers[i] = controller;
            }else{
                PawnController otherPlayerController = new OtherPlayerController(i, manager, manager.createSpawnAreaForPlayer(i));
                controllers[i] = otherPlayerController;
                manager.setPlayerController(i, otherPlayerController);
            }
        }

        setInitialTargets();
        if(brawlMultiplayer instanceof BrawlServer){
            manager.initializeGameForPlayers();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        brawlMultiplayer.dispose();
    }
}
