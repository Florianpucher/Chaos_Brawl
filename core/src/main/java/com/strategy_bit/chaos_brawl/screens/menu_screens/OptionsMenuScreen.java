package com.strategy_bit.chaos_brawl.screens.menu_screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.strategy_bit.chaos_brawl.ChaosBrawlGame;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.screens.ScreenEnum;

/**
 * Created by Florian on 27.05.2018.
 */

public class OptionsMenuScreen extends MenuScreen {
    private static final String music = "MUSIC";
    private static final String exit = "EXIT GAME";;
    private static final String back = "RESUME GAME";

    @Override
    public void buildStage() {
        super.buildStage();
        final TextButton btnMusic = new TextButton(music, assetManager.defaultSkin);
        final TextButton btnExit = new TextButton(exit, assetManager.defaultSkin);
        final TextButton btnBack = new TextButton(back, assetManager.defaultSkin);
        btnMusic.setName(music);
        btnExit.setName(exit);
        btnBack.setName(back);


        final Table root = new Table(assetManager.defaultSkin);
        root.setBackground(new NinePatchDrawable(assetManager.defaultSkin.getPatch("default-window")));
        root.setFillParent(true);
        float height = Gdx.graphics.getHeight()/8f;
        root.center();
        root.add(btnMusic).width(Gdx.graphics.getWidth()/3f).height(height);
        root.row();
        root.add(btnBack).width(Gdx.graphics.getWidth()/3f).height(height);
        root.row();
        root.add(btnExit).width(Gdx.graphics.getWidth()/3f).height(height);
        addActor(root);


        ClickListener listener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String name = event.getListenerActor().getName();
                if(name.equals(music)){
                    AssetManager.getInstance().music.setVolume(0.0f);
                    AssetManager.getInstance().victory.stop();
                    AssetManager.getInstance().defeat.stop();
                    AssetManager.getInstance().upgradeExecuted.stop();
                    AssetManager.getInstance().attackBow.stop();
                    AssetManager.getInstance().attackSword.stop();
                    AssetManager.getInstance().attackFireball.stop();
                    AssetManager.getInstance().hitArrow.stop();
                    AssetManager.getInstance().drawSword.stop();
                    AssetManager.getInstance().drawKatana.stop();
                    //AssetManager.getInstance().getRandomDrawKatanaSound().stop();
                    AssetManager.getInstance().explosionSound.stop();

                }
                if(name.equals(exit)){
                    screenManager.showScreen(ScreenEnum.MAIN_MENU);
                }
                if (name.equals(back)){
                    screenManager.showScreen(ScreenEnum.GAME);
                }
                super.clicked(event, x, y);
            }
        };

        btnMusic.addListener(listener);
        btnExit.addListener(listener);
        btnBack.addListener(listener);
    }
}
