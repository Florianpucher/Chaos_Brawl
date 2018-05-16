package com.strategy_bit.chaos_brawl.screens.game_screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.strategy_bit.chaos_brawl.player_input_output.AiController;
import com.strategy_bit.chaos_brawl.player_input_output.PawnController;
import com.strategy_bit.chaos_brawl.player_input_output.PlayerController;
import com.strategy_bit.chaos_brawl.screens.AbstractScreen;
import com.strategy_bit.chaos_brawl.world.World;

/**
 * gamescreen implementation
 * @author AIsopp
 * @version 1.0
 * @since 22.03.2018
 */

public class GameScreen extends AbstractScreen {

    protected World manager;
    protected PlayerController playerController;
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
        playerController = new PlayerController(0, manager, manager.createSpawnAreaForPlayer(0));
        controllers[0] = playerController;
        AiController otherPlayerController = new AiController(1,manager, manager.createSpawnAreaForPlayer(1));
        controllers[1] = otherPlayerController;
        manager.setPlayerController(0, playerController);

        manager.setPlayerController(1,otherPlayerController);

        manager.initializeGameForPlayers(map, 2);
        setInitialTargets();
    }

    void setInitialTargets(){
        for (int i = 0; i < controllers.length; i++) {
            controllers[i].setCurrentTargetTeam((i + 1) % controllers.length);
        }
    }

    @Override
    public void show() {
        super.show();
        //TODO input needs to be changed
        // The order of adding the input processors plays an important role!!!
        playerController.addGameHUD(this);
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(playerController);
        Gdx.input.setInputProcessor(inputMultiplexer);
        for (PawnController controller :
             controllers) {
            if(controller instanceof AiController){
                ((AiController) controller).startAI();
            }
        }
    }

    @Override
    public void pause() {
        super.pause();
        for (PawnController controller :
                controllers) {
            if(controller instanceof AiController){
                ((AiController) controller).pauseAI();
            }
        }
    }


    @Override
    public void resume() {
        super.resume();
        for (PawnController controller :
                controllers) {
            if(controller instanceof AiController){
                ((AiController) controller).resumeAI();
            }
        }
    }

    @Override
    public void render(float delta) {
        playerController.render(delta);
        manager.render();
        super.render(delta);
    }

    @Override
    public void hide() {
        super.hide();
        for (PawnController controller :
                controllers) {
            if(controller instanceof AiController){
                ((AiController) controller).stopAI();
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        manager.dispose();
        playerController.dispose();
    }


}
