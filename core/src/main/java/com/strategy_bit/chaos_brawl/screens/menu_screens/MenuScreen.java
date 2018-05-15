package com.strategy_bit.chaos_brawl.screens.menu_screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.strategy_bit.chaos_brawl.screens.AbstractScreen;

/**
 * @author AIsopp
 * @version 1.0
 * @since 10.05.2018
 */
abstract class MenuScreen extends AbstractScreen {
    protected OrthographicCamera camera;


    @Override
    public void buildStage() {
        super.buildStage();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void hide() {
        super.hide();
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        camera.update();
    }

    @Override
    public void dispose() {
        super.dispose();
        camera = null;
    }
}
