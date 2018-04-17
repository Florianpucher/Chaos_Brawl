package com.strategy_bit.chaos_brawl.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.managers.ScreenManager;

/**
 * Created by Florian on 11.04.2018.
 */

public class MapMenuScreen extends AbstractScreen {
    private final static String CHOOSE_MAP = "Choose Map";
    private final static String MAP_1 = "MAP 1";
    private final static String MAP_2 = "MAP 2";
    private final static String MAP_3 = "MAP 3";

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
        final TextButton btnNewMap1 = new TextButton(MAP_1, assetManager.defaultSkin);
        final TextButton btnNewMap2 = new TextButton(MAP_2, assetManager.defaultSkin);
        final TextButton btnNewMap3 = new TextButton(MAP_3, assetManager.defaultSkin);
        btnNewMap1.setName(MAP_1);
        btnNewMap2.setName(MAP_2);
        btnNewMap3.setName(MAP_3);


        final Table root = new Table(assetManager.defaultSkin);
        root.setBackground(new NinePatchDrawable(assetManager.defaultSkin.getPatch("default-window")));
        root.setFillParent(true);
        float height = Gdx.graphics.getHeight()/8;
        root.center();
        root.add(btnNewMap1).width(Gdx.graphics.getWidth()/3).height(height);
        root.row();
        root.add(btnNewMap2).width(Gdx.graphics.getWidth()/3).height(height);
        root.row();
        root.add(btnNewMap3).width(Gdx.graphics.getWidth()/3).height(height);
        addActor(root);


        ClickListener listener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String name = event.getListenerActor().getName();
                if(name.equals(MAP_1)){
                    screenManager.showScreen(ScreenEnum.GAME, 1);
                }
                if(name.equals(MAP_2)){
                    screenManager.showScreen(ScreenEnum.GAME,2);
                }
                if(name.equals(MAP_3)){
                    screenManager.showScreen(ScreenEnum.GAME,3);
                }
                super.clicked(event, x, y);
            }
        };
        btnNewMap1.addListener(listener);
        btnNewMap2.addListener(listener);
        btnNewMap3.addListener(listener);
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
