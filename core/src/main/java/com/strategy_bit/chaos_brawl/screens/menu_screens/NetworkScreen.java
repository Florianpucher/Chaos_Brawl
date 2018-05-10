package com.strategy_bit.chaos_brawl.screens.menu_screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.strategy_bit.chaos_brawl.screens.ScreenEnum;

/**
 * @author AIsopp
 * @version 1.0
 * @since 02.04.2018
 */
public class NetworkScreen extends MenuScreen {

    private final static String HOST_GAME = "Host Game";
    private final static String CLIENT_GAME = "Connect to Game";



    @Override
    public void buildStage() {
        super.buildStage();

        final TextButton btnHostGame = new TextButton(HOST_GAME, assetManager.defaultSkin);
        btnHostGame.setName(HOST_GAME);
        final TextButton btnConnectToGame = new TextButton(CLIENT_GAME, assetManager.defaultSkin);
        btnConnectToGame.setName(CLIENT_GAME);


        final Table root = new Table(assetManager.defaultSkin);
        root.setBackground(new NinePatchDrawable(assetManager.defaultSkin.getPatch("default-window")));
        root.setFillParent(true);
        float height = Gdx.graphics.getHeight()/8;
        root.center();
        root.add(btnHostGame).width(Gdx.graphics.getWidth()/2).height(height);
        root.row().space(10);
        root.add(btnConnectToGame).width(Gdx.graphics.getWidth()/2).height(height);
        addActor(root);

        ClickListener listener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                String name = event.getListenerActor().getName();
                if(name.equals(HOST_GAME)){
                    screenManager.showScreen(ScreenEnum.HOST_LOUNGE_SCREEN);
                    //screenManager.showScreen(ScreenEnum.GAME);
                }else if(name.endsWith(CLIENT_GAME)){
                    screenManager.showScreen(ScreenEnum.CLIENT_CONNECT_SCREEN);
                }
            }
        };
        btnHostGame.addListener(listener);
        btnConnectToGame.addListener(listener);
    }
}
