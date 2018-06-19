package com.strategy_bit.chaos_brawl.screens.menu_screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.managers.ScreenManager;
import com.strategy_bit.chaos_brawl.managers.SoundManager;
import com.strategy_bit.chaos_brawl.network.server.BrawlServer;
import com.strategy_bit.chaos_brawl.network.server.BrawlServerImpl;
import com.strategy_bit.chaos_brawl.screens.ScreenEnum;

import java.io.IOException;

/**
 * @author AIsopp
 * @version 1.0
 * @since 02.04.2018
 */
public class HostLoungeScreen extends MenuScreen {

    private static final String START_SERVER = "Start Server";

    private BrawlServer brawlServer;

    public HostLoungeScreen() {

        brawlServer = new BrawlServerImpl();
    }

    @Override
    public void buildStage() {
        super.buildStage();
        final TextButton btnStartServer = new TextButton(START_SERVER, assetManager.getDefaultSkin());
        btnStartServer.setName(START_SERVER);

        final Table root = new Table(assetManager.getDefaultSkin());
        root.setBackground(new NinePatchDrawable(assetManager.getDefaultSkin().getPatch(AssetManager.MENU_BACKGROUND)));
        root.setFillParent(true);
        float height = Gdx.graphics.getHeight()/8f;
        root.center();
        root.add(btnStartServer).width(Gdx.graphics.getWidth()/4f).height(height);
        addActor(root);

        ClickListener listener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                SoundManager.getInstance().playSound("click");

                String name = event.getListenerActor().getName();
                if(name.equals(START_SERVER)){
                    startServer();
                }
            }
        };
        btnStartServer.addListener(listener);
    }

    private void startServer() {
        if (brawlServer.isServerIsRunning()){
            return;
        }
        try {
            brawlServer.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ScreenManager.getInstance().showScreen(ScreenEnum.HOST_LOBBY_SCREEN,brawlServer);
    }

    @Override
    protected void handleBackKey() {
        brawlServer.closeServer();
        super.handleBackKey();
    }
}
