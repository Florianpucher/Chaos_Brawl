package com.strategy_bit.chaos_brawl.screens.menu_screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.managers.SoundManager;
import com.strategy_bit.chaos_brawl.network.BrawlMultiplayer;
import com.strategy_bit.chaos_brawl.screens.ScreenEnum;

/**
 * Created by Florian on 16.05.2018.
 */

public class FourPlayerMapMenuScreen extends MenuScreen{

    private static final String MAP_4 = "MAP 4";
    private BrawlMultiplayer brawlMultiplayer;
    private int player;

    public FourPlayerMapMenuScreen(BrawlMultiplayer brawlMultiplayer, int[] players){
        this.brawlMultiplayer = brawlMultiplayer;
        this.player=players[0];
    }

    @Override
    public void buildStage() {
        super.buildStage();
        final TextButton btnNewMap1 = new TextButton(MAP_4, assetManager.getDefaultSkin());

        btnNewMap1.setName(MAP_4);

        final Table root = new Table(assetManager.getDefaultSkin());
        root.setBackground(new NinePatchDrawable(assetManager.getDefaultSkin().getPatch(AssetManager.MENU_BACKGROUND)));
        root.setFillParent(true);
        float height = Gdx.graphics.getHeight()/8f;
        root.center();
        root.add(btnNewMap1).width(Gdx.graphics.getWidth()/3f).height(height);
        root.row();


        ClickListener listener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String name = event.getListenerActor().getName();
                SoundManager.getInstance().playSound("click");

                if(name.equals(MAP_4)){
                    screenManager.showScreen(ScreenEnum.MULTIPLAYERGAME, brawlMultiplayer, player);
                }
                super.clicked(event, x, y);
            }
        };
        btnNewMap1.addListener(listener);
    }
}

