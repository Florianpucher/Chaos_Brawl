package com.strategy_bit.chaos_brawl.screens;

import com.strategy_bit.chaos_brawl.controller.PlayerController;
import com.strategy_bit.chaos_brawl.network.BrawlMultiplayer;
import com.strategy_bit.chaos_brawl.network.BrawlNetworkInterface;
import com.strategy_bit.chaos_brawl.world.MultiplayerWorld;

public class MultiplayerGameScreen extends AbstractScreen {

    private MultiplayerWorld manager;

    public MultiplayerGameScreen(BrawlMultiplayer brawlMultiplayer) {
        manager = new MultiplayerWorld(brawlMultiplayer);
        brawlMultiplayer.setManager(manager);
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
