package com.strategy_bit.chaos_brawl.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.managers.ScreenManager;
import com.strategy_bit.chaos_brawl.network.Server.BrawlServer;
import com.strategy_bit.chaos_brawl.network.Server.BrawlServerImpl;

import java.io.IOException;

/**
 * @author AIsopp
 * @version 1.0
 * @since 02.04.2018
 */
public class HostLoungeScreen extends AbstractScreen {

    private final static String START_SERVER = "Start Server";

    private AssetManager assetManager;
    private OrthographicCamera camera;
    private BrawlServer brawlServer;

    public HostLoungeScreen() {

        brawlServer = new BrawlServerImpl();
        System.out.println("Init Server done");
    }

    @Override
    public void buildStage() {
        super.buildStage();
        assetManager = AssetManager.getInstance();
        screenManager = ScreenManager.getInstance();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        final TextButton btnStartServer = new TextButton(START_SERVER, assetManager.defaultSkin);
        btnStartServer.setName(START_SERVER);

        final Table root = new Table(assetManager.defaultSkin);
        root.setBackground(new NinePatchDrawable(assetManager.defaultSkin.getPatch("default-window")));
        root.setFillParent(true);
        float height = Gdx.graphics.getHeight()/8;
        root.center();
        root.add(btnStartServer).width(Gdx.graphics.getWidth()/4).height(height);
        addActor(root);

        ClickListener listener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                String name = event.getListenerActor().getName();
                if(name.equals(START_SERVER)){
                    startServer();
                }
            }
        };
        btnStartServer.addListener(listener);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        act();
        draw();
        camera.update();
    }

    @Override
    public void show() {
        super.show();
        System.out.println("SHOW LOUNGE");
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void hide() {
        //TODO do not close server here
        super.hide();
        Gdx.input.setInputProcessor(null);
        //brawlServer.closeServer();
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
    }

    @Override
    protected void handleBackKey() {
        brawlServer.closeServer();
        super.handleBackKey();
    }
}
