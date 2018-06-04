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
    private static final String back = "BACK";

    private boolean isActive = true;

    private boolean playable = true;

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
                    if (isActive){

                        AssetManager.getInstance().music.setVolume(0.0f);
                        AssetManager.getInstance().setPlayable(false);
                        System.out.println("music off");
                    }
                    if (!isActive){
                        AssetManager.getInstance().music.setVolume(1.0f);
                        AssetManager.getInstance().setPlayable(true);
                        System.out.println("music on");
                    }
                    if (isActive){
                        isActive = false;
                    } else {
                        isActive = true;
                    }
                }
                if(name.equals(exit)){
                    Gdx.app.exit();
                }
                if (name.equals(back)){
                    screenManager.switchToLastScreen();
                }
                super.clicked(event, x, y);
            }
        };

        btnMusic.addListener(listener);
        btnExit.addListener(listener);
        btnBack.addListener(listener);
    }

}
