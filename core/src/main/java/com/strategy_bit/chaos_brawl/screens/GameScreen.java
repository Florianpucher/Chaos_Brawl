package com.strategy_bit.chaos_brawl.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
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

    public GameScreen(int map) {
        if(map == 1){
            manager = new World(1);
        }
        if(map == 2){
            manager = new World(2);
        }
        if(map == 3){
            manager = new World(3);
        }
        //add User input
        manager.createPlayer();
        manager.createDummy();
    }

    @Override
    public void buildStage() {
        super.buildStage();

    }

    @Override
    public void show() {
        super.show();
        //TODO input needs to be changed
        PlayerController controller = new PlayerController();
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(controller);
        controller.setInputHandler(manager);
        Gdx.input.setInputProcessor(inputMultiplexer);
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

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }
}
