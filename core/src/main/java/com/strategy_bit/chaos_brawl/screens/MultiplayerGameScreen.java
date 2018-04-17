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

public class MultiplayerGameScreen extends AbstractScreen {

    private MultiplayerWorld manager;

    public MultiplayerGameScreen(BrawlMultiplayer brawlMultiplayer) {
        manager = new MultiplayerWorld(brawlMultiplayer);
        brawlMultiplayer.setManager(manager);
        //TODO remove if testing phase is over
        if(brawlMultiplayer instanceof BrawlServer){
            initializeGame();
        }

        //add User input
    }


    protected void initializeGame(){
        manager.createEntityWorldCoordinates(new Vector2(5,7.5f), UnitType.MELEE, 9);
        manager.createEntityWorldCoordinates(new Vector2(3,12), UnitType.TOWER, 9);
        manager.createEntityWorldCoordinates(new Vector2(3,5), UnitType.TOWER, 9);
        manager.createEntityWorldCoordinates(new Vector2(2,9), UnitType.MAINBUILDING, 9);
        manager.createEntityWorldCoordinates(new Vector2(17,12), UnitType.TOWER, 8);
        manager.createEntityWorldCoordinates(new Vector2(17,5), UnitType.TOWER, 8);
        manager.createEntityWorldCoordinates(new Vector2(19,9), UnitType.MAINBUILDING, 8);
    }


    @Override
    public void buildStage() {
        super.buildStage();

    }

    @Override
    public void show() {
        super.show();
        //TODO input needs to be changed
        PlayerController controller = new PlayerController(1);
        controller.setInputHandler(manager);
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(controller);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {
        manager.render();
        super.render(delta);

    }

    @Override
    public void dispose() {
        super.dispose();
        manager.dispose();
    }
}
