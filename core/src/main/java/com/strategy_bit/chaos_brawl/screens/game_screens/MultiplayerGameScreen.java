package com.strategy_bit.chaos_brawl.screens.game_screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.strategy_bit.chaos_brawl.managers.ScreenManager;
import com.strategy_bit.chaos_brawl.network.BrawlConnector;
import com.strategy_bit.chaos_brawl.network.BrawlMultiplayer;
import com.strategy_bit.chaos_brawl.network.server.BrawlServer;
import com.strategy_bit.chaos_brawl.player_input_output.OtherPlayerController;
import com.strategy_bit.chaos_brawl.player_input_output.PawnController;
import com.strategy_bit.chaos_brawl.player_input_output.PlayerController;
import com.strategy_bit.chaos_brawl.screens.ScreenEnum;
import com.strategy_bit.chaos_brawl.world.MultiplayerInputHandler;
import com.strategy_bit.chaos_brawl.world.MultiplayerWorld;

public class MultiplayerGameScreen extends GameScreen {


    private BrawlMultiplayer brawlMultiplayer;
    private BrawlConnector listener;
    private int player;
    private static final String EXIT = "Exit Game";


    public MultiplayerGameScreen(BrawlMultiplayer brawlMultiplayer,  int[] players) {
        super(1);
        this.brawlMultiplayer = brawlMultiplayer;
        this.player=players[0];
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
        final TextButton btnOptions = new TextButton(EXIT, assetManager.defaultSkin);
        btnOptions.setName(EXIT);

        final Table root = new Table(assetManager.defaultSkin);
        root.setFillParent(true);
        float height = Gdx.graphics.getHeight()/24f;
        root.top();
        root.right();
        root.add(btnOptions).width(Gdx.graphics.getWidth()/10f).height(height);
        addActor(root);

        ClickListener listener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String name = event.getListenerActor().getName();
                if (name.equals(EXIT)){
                    ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
                }
                super.clicked(event, x, y);
            }
        };

        btnOptions.addListener(listener);
    }

    @Override
    protected void initializeGame(){
        for (int i = 0; i < controllers.length; i++) {
            if(i == player){
                playerController = new PlayerController(i, manager, manager.createSpawnAreaForPlayer(i));
                manager.setPlayerController(i, playerController);
                controllers[i] = playerController;
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
