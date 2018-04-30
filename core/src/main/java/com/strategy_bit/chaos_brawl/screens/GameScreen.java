package com.strategy_bit.chaos_brawl.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.strategy_bit.chaos_brawl.player_input_output.AI_Controller;
import com.strategy_bit.chaos_brawl.player_input_output.PawnController;
import com.strategy_bit.chaos_brawl.world.World;
import com.strategy_bit.chaos_brawl.player_input_output.PlayerController;

/**
 * gamescreen implementation
 * @author AIsopp
 * @version 1.0
 * @since 22.03.2018
 */

public class GameScreen extends AbstractScreen {

    protected World manager;
    protected PlayerController controller;
    private int map;
    protected PawnController[] controllers;
    public GameScreen(int map) {
        this.map = map;
    }


    @Override
    public void buildStage() {
        super.buildStage();
        initializeGame();
    }

    protected void initializeGame(){
        manager = new World(map,2);
        controllers = new PawnController[2];
        controller = new PlayerController(0, manager, manager.createSpawnAreaForPlayer(0));
        controllers[0] = controller;
        AI_Controller otherPlayerController = new AI_Controller(1,manager, manager.createSpawnAreaForPlayer(1));
        controllers[1] = otherPlayerController;
        manager.setPlayerController(0, controller);
        //controller.setCurrentTargetTeam(1);
        manager.setPlayerController(1,otherPlayerController);
        //otherPlayerController.setCurrentTargetTeam(0);
        manager.initializeGameForPlayers();
        //controller.startTicking();
        //otherPlayerController.startTicking();
        setInitialTargets();
    }

    protected void setInitialTargets(){
        for (int i = 0; i < controllers.length; i++) {
            controllers[i].setCurrentTargetTeam((i + 1) % controllers.length);
        }
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
        for (PawnController controller :
             controllers) {
            if(controller instanceof AI_Controller){
                ((AI_Controller) controller).startAI();
            }
        }
    }

    @Override
    public void pause() {
        super.pause();
        for (PawnController controller :
                controllers) {
            if(controller instanceof AI_Controller){
                ((AI_Controller) controller).pauseAI();
            }
        }
    }


    @Override
    public void resume() {
        super.resume();
        for (PawnController controller :
                controllers) {
            if(controller instanceof AI_Controller){
                ((AI_Controller) controller).resumeAI();
            }
        }
    }

    @Override
    public void render(float delta) {
        manager.render();
        super.render(delta);
        act();
        draw();
        //manager.getCamera().update();
    }

    @Override
    public void hide() {
        super.hide();
        for (PawnController controller :
                controllers) {
            if(controller instanceof AI_Controller){
                ((AI_Controller) controller).stopAI();
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        manager.dispose();
        controller.dispose();
    }


}
