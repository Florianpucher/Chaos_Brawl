package com.strategy_bit.chaos_brawl.screens.game_screens;

import com.strategy_bit.chaos_brawl.network.BrawlConnector;
import com.strategy_bit.chaos_brawl.network.BrawlMultiplayer;
import com.strategy_bit.chaos_brawl.player_input_output.OtherPlayerController;
import com.strategy_bit.chaos_brawl.player_input_output.PawnController;
import com.strategy_bit.chaos_brawl.player_input_output.PlayerController;
import com.strategy_bit.chaos_brawl.world.MultiplayerInputHandler;
import com.strategy_bit.chaos_brawl.world.MultiplayerWorld;

public class MultiplayerGameScreen extends GameScreen {


    private BrawlMultiplayer brawlMultiplayer;
    private BrawlConnector listener;
    // local player index
    private int localPlayerIndex;
    private int map;


    public MultiplayerGameScreen(BrawlMultiplayer brawlMultiplayer,  int[] players, int map) {
        super(map);
        this.map = map;
        this.brawlMultiplayer = brawlMultiplayer;
        this.localPlayerIndex=players[0];
        controllers = new PawnController[players.length];
        this.listener = brawlMultiplayer.getBrawlConnector();
    }

    public void setListener(BrawlConnector listener){
        this.listener = listener;
    }


    @Override
    public void buildStage() {
        manager = new MultiplayerWorld(brawlMultiplayer, controllers.length, map);
        listener.setMultiplayerInputHandler((MultiplayerInputHandler) manager);
        initializeGame();
    }

    @Override
    protected void initializeGame(){
        for (int i = 0; i < controllers.length; i++) {
            if(i == localPlayerIndex){
                playerController = new PlayerController(i, manager, manager.createSpawnAreaForPlayer(i, 4));
                manager.setPlayerController(i, playerController);
                controllers[i] = playerController;
            }else{
                PawnController otherPlayerController = new OtherPlayerController(i, manager, manager.createSpawnAreaForPlayer(i, 4));
                controllers[i] = otherPlayerController;
                manager.setPlayerController(i, otherPlayerController);
            }
        }

        setInitialTargets();
        if(brawlMultiplayer.isHost()){
            //TODO make this dynamic for multiple maps
            manager.initializeGameForPlayers(map, controllers.length);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        brawlMultiplayer.dispose();
    }
}
