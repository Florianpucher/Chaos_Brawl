package com.strategy_bit.chaos_brawl.screens.menu_screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.network.BrawlMultiplayer;
import com.strategy_bit.chaos_brawl.screens.ScreenEnum;

/**
 * Created by Florian on 11.04.2018.
 */

public class MapMenuScreen extends MenuScreen{
    private static final String MAP_1 = "MAP 1";
    private static final String MAP_2 = "MAP 2";
    private static final String MAP_3 = "MAP 3";
    private static final String MAP_ = "MAP ";

    private BrawlMultiplayer brawlMultiplayer;
    private int player;
    private boolean multiplayer = false;

    public MapMenuScreen(BrawlMultiplayer brawlMultiplayer, int[] players){
        this.brawlMultiplayer = brawlMultiplayer;
        this.player=players[0];
        multiplayer = true;
    }

    public MapMenuScreen(){

    }


    @Override
    public void buildStage() {
        super.buildStage();
        final TextButton btnNewMap1 = new TextButton(MAP_1, assetManager.defaultSkin);
        final TextButton btnNewMap2 = new TextButton(MAP_2, assetManager.defaultSkin);
        final TextButton btnNewMap3 = new TextButton(MAP_3, assetManager.defaultSkin);
        btnNewMap1.setName(MAP_1);
        btnNewMap2.setName(MAP_2);
        btnNewMap3.setName(MAP_3);


        final Table root = new Table(assetManager.defaultSkin);
        root.setBackground(new NinePatchDrawable(assetManager.defaultSkin.getPatch(AssetManager.MENU_BACKGROUND)));
        root.setFillParent(true);
        float height = Gdx.graphics.getHeight()/8f;
        root.center();
        root.add(btnNewMap1).width(Gdx.graphics.getWidth()/3f).height(height);
        root.row();
        root.add(btnNewMap2).width(Gdx.graphics.getWidth()/3f).height(height);
        root.row();
        root.add(btnNewMap3).width(Gdx.graphics.getWidth()/3f).height(height);
        addActor(root);


        ClickListener listener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String name = event.getListenerActor().getName();

                for (int i = 1; i < 4; i++) {
                    if (name.equals(MAP_ + Integer.valueOf(i).toString())) {
                        if (multiplayer) {
                            screenManager.showScreen(ScreenEnum.MULTIPLAYERGAME, brawlMultiplayer, player, i);
                        } else {
                            screenManager.showScreen(ScreenEnum.GAME, i);
                        }
                    }
                }

                super.clicked(event, x, y);
            }
        };
        btnNewMap1.addListener(listener);
        btnNewMap2.addListener(listener);
        btnNewMap3.addListener(listener);
    }
}
