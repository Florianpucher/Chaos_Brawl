package com.strategy_bit.chaos_brawl.screens.menu_screens;

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
import com.strategy_bit.chaos_brawl.screens.AbstractScreen;
import com.strategy_bit.chaos_brawl.screens.ScreenEnum;

/**
 * class that represents the main menu screen
 *
 * @author AIsopp
 * @version 1.0
 * @since 22.03.2018
 */
public class MainMenuScreen extends MenuScreen {

    private final static String NEW_GAME = "New GAME";
    private final static String MULTIPLAYER = "Multiplayer";

    @Override
    public void buildStage() {
        super.buildStage();
        final TextButton btnNewGame = new TextButton(NEW_GAME, assetManager.defaultSkin);
        btnNewGame.setName(NEW_GAME);
        final TextButton btnMultiplayer = new TextButton(MULTIPLAYER, assetManager.defaultSkin);
        btnMultiplayer.setName(MULTIPLAYER);

        final Table root = new Table(assetManager.defaultSkin);
        root.setBackground(new NinePatchDrawable(assetManager.defaultSkin.getPatch("default-window")));
        root.setFillParent(true);
        float height = Gdx.graphics.getHeight()/8;
        root.center();
        root.add(btnNewGame).width(Gdx.graphics.getWidth()/2).height(height);
        root.row().space(10);
        root.add(btnMultiplayer).width(Gdx.graphics.getWidth()/2).height(height);
        addActor(root);


        ClickListener listener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                String name = event.getListenerActor().getName();
                if(name.equals(NEW_GAME)){
                    screenManager.showScreen(ScreenEnum.MAP_MENU);
                }
                else if(name.equals(MULTIPLAYER)){
                    screenManager.showScreen(ScreenEnum.NETWORK_SCREEN);
                }

            }
        };
        btnNewGame.addListener(listener);
        btnMultiplayer.addListener(listener);

    }
}
