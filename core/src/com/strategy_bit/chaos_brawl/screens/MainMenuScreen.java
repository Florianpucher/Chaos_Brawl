package com.strategy_bit.chaos_brawl.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import managers.AssetManager;
import managers.ScreenManager;

/**
 * class that represents the main menu screen
 *
 * @author AIsopp
 * @version 1.0
 * @since 22.03.2018
 */
public class MainMenuScreen extends AbstractScreen{

    private final static String NEW_GAME = "New GAME";

    private OrthographicCamera camera;
    private AssetManager assetManager;
    private ScreenManager screenManager;

    @Override
    public void buildStage() {
        super.buildStage();
        assetManager = AssetManager.getInstance();
        screenManager = ScreenManager.getInstance();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        final TextButton btnNewGame = new TextButton(NEW_GAME, assetManager.defaultSkin);
        btnNewGame.setName(NEW_GAME);

        final Table root = new Table(assetManager.defaultSkin);
        root.setBackground(new NinePatchDrawable(assetManager.defaultSkin.getPatch("default-window")));
        root.setFillParent(true);
        float height = Gdx.graphics.getHeight()/8;
        root.center();
        root.add(btnNewGame).width(Gdx.graphics.getWidth()/2).height(height);
        addActor(root);


        ClickListener listener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String name = event.getListenerActor().getName();
                if(name.equals(NEW_GAME)){
                    screenManager.showScreen(ScreenEnum.GAME);
                }
                super.clicked(event, x, y);
            }
        };
        btnNewGame.addListener(listener);
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor((Stage)this);
        System.out.println(Gdx.input.getInputProcessor().getClass().getName());
    }

    @Override
    public void hide() {
        super.hide();
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        act();
        draw();
        camera.update();
    }

    @Override
    public void dispose() {
        super.dispose();
        camera = null;
    }
}
