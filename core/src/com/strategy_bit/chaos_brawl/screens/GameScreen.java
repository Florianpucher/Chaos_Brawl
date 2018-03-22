package com.strategy_bit.chaos_brawl.screens;

import managers.GameManager;
import com.strategy_bit.chaos_brawl.controller.PlayerController;

/**
 * gamescreen implementation
 * @author AIsopp
 * @version 1.0
 * @since 22.03.2018
 */

public class GameScreen extends AbstractScreen {

    private GameManager manager;

    public GameScreen() {
        manager = new GameManager();
        //add User input
        //TODO input needs to be changed
        PlayerController controller = new PlayerController();
        controller.setInputHandler(manager);
    }

    @Override
    public void buildStage() {
        super.buildStage();

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
