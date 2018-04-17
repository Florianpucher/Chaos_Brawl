package com.strategy_bit.chaos_brawl.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
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
    private GameHUD gameHUD;
    private PlayerController controller;

    public GameScreen(int map) {
        manager = new World(map);
        //add User input
        manager.createPlayer();
        manager.createTower();
        manager.createBase();
        manager.createDummy();
    }

    @Override
    public void buildStage() {
        super.buildStage();

        controller = new PlayerController(1);
        controller.setInputHandler(manager);
        gameHUD = new GameHUD(manager, controller.getTeamID());
        addActor(gameHUD);
    }

    @Override
    public void show() {
        super.show();
        //TODO input needs to be changed
        // The order of adding the input processors plays an important role!!!
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(gameHUD);
        inputMultiplexer.addProcessor(controller);
        Gdx.input.setInputProcessor(inputMultiplexer);
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
    public void dispose() {
        super.dispose();
        manager.dispose();
    }


}
