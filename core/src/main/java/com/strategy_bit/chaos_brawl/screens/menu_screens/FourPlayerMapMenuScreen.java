package com.strategy_bit.chaos_brawl.screens.menu_screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.strategy_bit.chaos_brawl.screens.ScreenEnum;

/**
 * Created by Florian on 16.05.2018.
 */

public class FourPlayerMapMenuScreen extends MenuScreen{

    private static final String MAP_4 = "MAP 4";

    @Override
    public void buildStage() {
        super.buildStage();
        final TextButton btnNewMap1 = new TextButton(MAP_4, assetManager.defaultSkin);

        btnNewMap1.setName(MAP_4);

        final Table root = new Table(assetManager.defaultSkin);
        root.setBackground(new NinePatchDrawable(assetManager.defaultSkin.getPatch("default-window")));
        root.setFillParent(true);
        float height = Gdx.graphics.getHeight()/8f;
        root.center();
        root.add(btnNewMap1).width(Gdx.graphics.getWidth()/3f).height(height);
        root.row();


        ClickListener listener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String name = event.getListenerActor().getName();
                if(name.equals(MAP_4)){
                    screenManager.showScreen(ScreenEnum.GAME, 4);
                }
                super.clicked(event, x, y);
            }
        };
        btnNewMap1.addListener(listener);
    }
}

