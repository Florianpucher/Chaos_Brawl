package com.strategy_bit.chaos_brawl.screens;

import com.strategy_bit.chaos_brawl.config.Network;
import com.strategy_bit.chaos_brawl.network.BrawlConnector;
import com.strategy_bit.chaos_brawl.network.BrawlMultiplayer;
import com.strategy_bit.chaos_brawl.network.Client.BrawlClientListener;
import com.strategy_bit.chaos_brawl.network.Server.BrawlServer;
import com.strategy_bit.chaos_brawl.network.Server.BrawlServerListener;
import com.strategy_bit.chaos_brawl.player_input_output.OtherPlayerController;
import com.strategy_bit.chaos_brawl.player_input_output.PlayerController;
import com.strategy_bit.chaos_brawl.world.MultiplayerInputHandler;
import com.strategy_bit.chaos_brawl.world.MultiplayerWorld;

public class MultiplayerGameScreen extends GameScreen {

    //private MultiplayerWorld manager;
    private BrawlMultiplayer brawlMultiplayer;
    private BrawlConnector listener;
    private int player;
    private int[] players;

    public MultiplayerGameScreen(BrawlMultiplayer brawlMultiplayer,  int[] players) {
        super(1);
        this.brawlMultiplayer = brawlMultiplayer;
        for (int i = 0; i < players.length; i++) {
            if(players[i] == Network.YOUR_CLIENT_CONTROLLER){
                this.player = i;
            }
        }
        this.players = players;
        this.listener = brawlMultiplayer.getBrawlConnector();
        //this.listener = listener;
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
        manager = new MultiplayerWorld(isServer, brawlMultiplayer, players.length);
        listener.setMultiplayerInputHandler((MultiplayerInputHandler) manager);
        initializeGame();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    protected void initializeGame(){
        if(brawlMultiplayer instanceof BrawlServer){
            controller = new PlayerController(player, manager, manager.createSpawnAreaForPlayer(player));
            //TODO current attack target implementation will only work for two players
            manager.setPlayerController(player, controller);
            OtherPlayerController otherPlayerController = null;
            int otherPlayer = 0;
            for (int i = 0; i < players.length; i++) {
                if(i == player){
                    continue;
                }
                otherPlayerController = new OtherPlayerController(i,manager, manager.createSpawnAreaForPlayer(i));
                otherPlayer = i;
                manager.setPlayerController(i, otherPlayerController);
            }
            //While there are only two players set themselve as enemies
            manager.initializeGameForPlayers();
            if (otherPlayerController != null) {
                controller.setCurrentTargetTeam(otherPlayer);
                otherPlayerController.setCurrentTargetTeam(player);
            }

        }else{
            controller = new PlayerController(player, manager, manager.createSpawnAreaForPlayer(player));
            manager.setPlayerController(player, controller);
        }
    }
}
