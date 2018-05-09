package com.strategy_bit.chaos_brawl.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.managers.ScreenManager;

/**
 * @author AIsopp
 * @version 1.0
 * @since 22.03.2018
 */

public class AbstractScreen extends Stage implements Screen {

    protected ScreenManager screenManager;
    protected AssetManager assetManager;

    public AbstractScreen() {
        this.screenManager = ScreenManager.getInstance();
        this.assetManager = AssetManager.getInstance();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    public void buildStage(){

    }

    @Override
    public void dispose() {
        clear();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keyCode) {
        System.out.println("Received Input: " + keyCode);
        if(keyCode == Input.Keys.BACK || keyCode == Input.Keys.ESCAPE){
            handleBackKey();
        }
        return super.keyDown(keyCode);
    }

    /**
     * what should the screen do if the back button on android has been clicked
     */
    protected void handleBackKey(){
        screenManager.switchToLastScreen();
    }
}
