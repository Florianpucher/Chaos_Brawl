package com.strategy_bit.chaos_brawl.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.strategy_bit.chaos_brawl.network.network_handlers.NetworkDiscoveryHandler;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.managers.ScreenManager;
import com.strategy_bit.chaos_brawl.network.Client.BrawlClient;
import com.strategy_bit.chaos_brawl.network.Client.BrawlClientImpl;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

/**
 * @author AIsopp
 * @version 1.0
 * @since 02.04.2018
 */
public class ClientConnectToScreen extends AbstractScreen implements NetworkDiscoveryHandler{

    private final static String REFRESH = "Refresh";
    private static String DIRECT = "10.0.2.2";

    private AssetManager assetManager;
    private ScreenManager screenManager;
    private OrthographicCamera camera;

    private BrawlClient brawlClient;
    private TextButton btnDirectConnect;

    public ClientConnectToScreen() {
        this.brawlClient = new BrawlClientImpl();
        brawlClient.addNetworkDiscoveryHandler(this);
    }

    @Override
    public void buildStage() {
        super.buildStage();
        assetManager = AssetManager.getInstance();
        screenManager = ScreenManager.getInstance();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        final TextButton btnHostGame = new TextButton(REFRESH, assetManager.defaultSkin);
        btnHostGame.setName(REFRESH);
        btnDirectConnect= new TextButton(DIRECT, assetManager.defaultSkin);
        btnDirectConnect.setName(DIRECT);

        final Table root = new Table(assetManager.defaultSkin);
        root.setBackground(new NinePatchDrawable(assetManager.defaultSkin.getPatch("default-window")));
        root.setFillParent(true);
        float height = Gdx.graphics.getHeight()/8;
        root.center();
        root.add(btnHostGame).width(Gdx.graphics.getWidth()/4).height(height);
        root.row().space(10);
        root.add(btnDirectConnect).width(Gdx.graphics.getWidth()/4).height(height);
        addActor(root);

        ClickListener listener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                String name = event.getListenerActor().getName();
                if(name.equals(REFRESH)){
                    brawlClient.discoverServers();
                    //screenManager.showScreen(ScreenEnum.GAME);
                }
                else if (name.equals(DIRECT)){
                    try {
                        brawlClient.connectToServer(DIRECT);
                        screenManager.showScreen(ScreenEnum.CLIENT_LOBBY_SCREEN);
                        //screenManager.showScreen(ScreenEnum.MULTIPLAYERGAME,brawlClient);
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                }
            }
        };
        btnHostGame.addListener(listener);
        btnDirectConnect.addListener(listener);
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
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void hide() {
        super.hide();
        Gdx.input.setInputProcessor(null);
    }


    @Override
    public void receiveHosts(List<InetAddress> hostIPs) {
        for (InetAddress address :
                hostIPs) {
            System.out.format("HostAddress: %s \nHostName: %s \n", address.getHostAddress(), address.getHostName());
        }
        if(!hostIPs.isEmpty()){
            DIRECT=hostIPs.get(0).getHostAddress();
            btnDirectConnect.setName(DIRECT);
            btnDirectConnect.setText(DIRECT);
        }
    }

    @Override
    protected void handleBackKey() {
        try {
            brawlClient.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.handleBackKey();
    }
}
