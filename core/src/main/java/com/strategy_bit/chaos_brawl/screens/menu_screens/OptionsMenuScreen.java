package com.strategy_bit.chaos_brawl.screens.menu_screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.managers.SoundManager;
import com.strategy_bit.chaos_brawl.screens.ScreenEnum;

/**
 * Created by Florian on 27.05.2018.
 */

public class OptionsMenuScreen extends MenuScreen{
    private static final String back = "Return to main menu";

    @Override
    public void buildStage() {
        super.buildStage();
        final TextButton btnBack = new TextButton(back, assetManager.defaultSkin);
        btnBack.setName(back);
        final Slider sliderMaster = new Slider(0f, 1f, 0.01f, false, assetManager.sliderStyle);
        final Slider sliderMusic = new Slider(0f, 1f, 0.01f, false, assetManager.sliderStyle);
        final Slider sliderSound = new Slider(0f, 1f, 0.01f, false, assetManager.sliderStyle);
        final Label textMaster = new Label("Master Volume", assetManager.defaultSkin);
        final Label textMusic = new Label("Music Volume", assetManager.defaultSkin);
        final Label textSound = new Label("Sound Volume", assetManager.defaultSkin);
        sliderMaster.setValue(1f);
        sliderMusic.setValue(1f);
        sliderSound.setValue(1f);

        final Table root = new Table(assetManager.defaultSkin);
        root.setBackground(new NinePatchDrawable(assetManager.defaultSkin.getPatch(AssetManager.MENU_BACKGROUND)));
        root.setFillParent(true);
        float height = Gdx.graphics.getHeight()/8f;
        root.center();
        root.add(textMaster).width(Gdx.graphics.getWidth()/3f).height(height);
        root.add(sliderMaster).width(Gdx.graphics.getWidth()/3f).height(height);
        root.row();
        root.add(textMusic).width(Gdx.graphics.getWidth()/3f).height(height);
        root.add(sliderMusic).width(Gdx.graphics.getWidth()/3f).height(height);
        root.row();
        root.add(textSound).width(Gdx.graphics.getWidth()/3f).height(height);
        root.add(sliderSound).width(Gdx.graphics.getWidth()/3f).height(height);
        root.row().height(height*3f);
        root.row();
        root.add(btnBack).center().width(Gdx.graphics.getWidth()/3f).height(height);
        addActor(root);

        sliderMaster.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                SoundManager.getInstance().setMasterVolume(sliderMaster.getValue());
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            };
        });

        sliderMusic.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                SoundManager.getInstance().setVolumeMusic(sliderMusic.getValue());
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            };
        });

        sliderSound.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                SoundManager.getInstance().setVolumeSounds(sliderSound.getValue());
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            };
        });

        ClickListener listener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                String name = event.getListenerActor().getName();
                if(name.equals(back)){
                    screenManager.showScreen(ScreenEnum.MAIN_MENU);
                }

            }
        };
        btnBack.addListener(listener);

    }

}
