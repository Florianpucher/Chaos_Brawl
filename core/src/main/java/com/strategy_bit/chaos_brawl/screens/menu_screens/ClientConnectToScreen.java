package com.strategy_bit.chaos_brawl.screens.menu_screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.strategy_bit.chaos_brawl.network.client.BrawlClient;
import com.strategy_bit.chaos_brawl.network.client.BrawlClientImpl;
import com.strategy_bit.chaos_brawl.network.network_handlers.NetworkConnectionHandler;
import com.strategy_bit.chaos_brawl.network.network_handlers.NetworkDiscoveryHandler;
import com.strategy_bit.chaos_brawl.screens.ScreenEnum;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

/**
 * @author AIsopp
 * @version 1.0
 * @since 02.04.2018
 */
public class ClientConnectToScreen extends MenuScreen implements NetworkDiscoveryHandler, NetworkConnectionHandler {

    private final static String REFRESH = "Refresh";
    private static String DIRECT = "10.0.2.2";


    private BrawlClient brawlClient;
    private TextButton btnDirectConnect;

    public ClientConnectToScreen() {
        this.brawlClient = new BrawlClientImpl();
        brawlClient.addNetworkDiscoveryHandler(this);
        brawlClient.setNetworkConnectionHandler(this);
    }

    @Override
    public void buildStage() {
        super.buildStage();
        final TextButton btnHostGame = new TextButton(REFRESH, assetManager.defaultSkin);
        btnHostGame.setName(REFRESH);
        btnDirectConnect = new TextButton(DIRECT, assetManager.defaultSkin);
        btnDirectConnect.setName(DIRECT);

        final Table root = new Table(assetManager.defaultSkin);
        root.setBackground(new NinePatchDrawable(assetManager.defaultSkin.getPatch("default-window")));
        root.setFillParent(true);
        float height = Gdx.graphics.getHeight() / 8;
        root.center();
        root.add(btnHostGame).width(Gdx.graphics.getWidth() / 4).height(height);
        root.row().space(10);
        root.add(btnDirectConnect).width(Gdx.graphics.getWidth() / 4).height(height);
        addActor(root);

        final NetworkDiscoveryHandler discoveryHandler = this;
        ClickListener listener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                String name = event.getListenerActor().getName();
                if (name.equals(REFRESH)) {
                    brawlClient.discoverServers();
                } else if (name.equals(DIRECT)) {
                    try {
                        brawlClient.connectToServer(DIRECT);
                        brawlClient.removeNetworkDiscoveryHandler(discoveryHandler);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        btnHostGame.addListener(listener);
        btnDirectConnect.addListener(listener);
    }


    @Override
    public void receiveHosts(List<InetAddress> hostIPs) {
        for (InetAddress address :
                hostIPs) {
            System.out.format("HostAddress: %s \nHostName: %s \n", address.getHostAddress(), address.getHostName());
        }
        if (!hostIPs.isEmpty()) {
            DIRECT = hostIPs.get(0).getHostAddress();
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

    @Override
    public void connected() {

        screenManager.showScreen(ScreenEnum.CLIENT_LOBBY_SCREEN, brawlClient);

    }

    @Override
    public void disconnected() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void anotherClientConnected(String clientName, int id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void anotherClientDisconnected(int id) {
        throw new UnsupportedOperationException();
    }
}
