package com.strategy_bit.chaos_brawl.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector2;
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

    public GameScreen(int map) {
        manager = new World(map);
    }


    protected void initializeGame(){
        manager.createEntityWorldCoordinates(new Vector2(5,7.5f), UnitType.MELEE, 9);
        manager.createEntityWorldCoordinates(new Vector2(3,12), UnitType.TOWER, 9);
        manager.createEntityWorldCoordinates(new Vector2(3,5), UnitType.TOWER, 9);
        manager.createEntityWorldCoordinates(new Vector2(2,9), UnitType.MAINBUILDING, 9);
        manager.createEntityWorldCoordinates(new Vector2(17,12), UnitType.TOWER, 8);
        manager.createEntityWorldCoordinates(new Vector2(17,5), UnitType.TOWER, 8);
        manager.createEntityWorldCoordinates(new Vector2(19,9), UnitType.MAINBUILDING, 8);
        manager.createResource(9);
    }

    @Override
    public void buildStage() {
        super.buildStage();

        controller = new PlayerController(9, manager, manager.createSpawnAreaForPlayer(1));

        initializeGame();
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
        controller.dispose();
    }


}
