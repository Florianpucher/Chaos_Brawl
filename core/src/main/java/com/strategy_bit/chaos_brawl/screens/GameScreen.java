package com.strategy_bit.chaos_brawl.screens;

import com.strategy_bit.chaos_brawl.World;
import com.strategy_bit.chaos_brawl.controller.PlayerController;

/**
 * gamescreen implementation
 * @author AIsopp
 * @version 1.0
 * @since 22.03.2018
 */

public class GameScreen extends AbstractScreen {

    private World manager;

    public GameScreen() {
        manager = new World();
        //add User input

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
        controller.setInputHandler(manager);
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
