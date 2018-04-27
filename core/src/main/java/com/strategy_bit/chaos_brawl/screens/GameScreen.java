package com.strategy_bit.chaos_brawl.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.controller.AI_Controller;
import com.strategy_bit.chaos_brawl.controller.PawnController;
import com.strategy_bit.chaos_brawl.types.UnitType;
import com.strategy_bit.chaos_brawl.views.GameHUD;
import com.strategy_bit.chaos_brawl.world.World;
import com.strategy_bit.chaos_brawl.controller.PlayerController;

/**
 * gamescreen implementation
 * @author AIsopp
 * @version 1.0
 * @since 22.03.2018
 */

public class GameScreen extends AbstractScreen {

    private World manager;
    private PlayerController controller;
    protected PawnController otherPlayerController;

    public GameScreen(int map) {
        manager = new World(map,2);
    }


    @Override
    public void buildStage() {
        super.buildStage();

        controller = new PlayerController(0, manager, manager.createSpawnAreaForPlayer(1));
        otherPlayerController = new AI_Controller(1,manager, manager.createSpawnAreaForPlayer(2));
        initializeGame();
    }

    protected void initializeGame(){
        manager.setPlayerController(0, controller);
        controller.setCurrentTargetTeam(1);
        manager.setPlayerController(1,otherPlayerController);
        otherPlayerController.setCurrentTargetTeam(0);
        manager.initializeGameForPlayers();
        //controller.startTicking();
        //otherPlayerController.startTicking();
    }

    @Override
    public void show() {
        super.show();
        //TODO input needs to be changed
        // The order of adding the input processors plays an important role!!!
        controller.addGameHUD(this);
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(controller);
        Gdx.input.setInputProcessor(inputMultiplexer);
        if(otherPlayerController instanceof AI_Controller){
            ((AI_Controller) otherPlayerController).startAI();
        }
    }

    @Override
    public void pause() {
        super.pause();
        if(otherPlayerController instanceof AI_Controller){
            ((AI_Controller) otherPlayerController).pauseAI();
        }
    }


    @Override
    public void resume() {
        super.resume();
        if(otherPlayerController instanceof AI_Controller){
            ((AI_Controller) otherPlayerController).resumeAI();
        }
    }

    @Override
    public void render(float delta) {
        controller.render(delta);
        manager.render();
        super.render(delta);
        act();
        draw();
        //manager.getCamera().update();
    }

    @Override
    public void hide() {
        super.hide();
        if(otherPlayerController instanceof AI_Controller){
            ((AI_Controller) otherPlayerController).stopAI();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        manager.dispose();
        controller.dispose();
    }


}
