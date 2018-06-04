package com.strategy_bit.chaos_brawl.screens.menu_screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.network.client.BrawlClient;
import com.strategy_bit.chaos_brawl.network.messages.request.NetworkMembersRequestMessage;

/**
 * @author AIsopp
 * @version 1.0
 * @since 02.04.2018
 */
public class ClientLobbyScreen extends LobbyScreen{

    //TODO start game here
    private BrawlClient brawlClient;

    public ClientLobbyScreen(BrawlClient brawlClient) {
        this.brawlClient = brawlClient;
        brawlClient.setNetworkConnectionHandler(this);
        brawlClient.sendData(new NetworkMembersRequestMessage());
    }

    @Override
    public void buildStage() {
        super.buildStage();
        final TextButton btnPlayer1 = new TextButton(PLAYER_1, assetManager.defaultSkin);
        btnPlayer1.setName(PLAYER_1);
        final TextButton btnPlayer2 = new TextButton(PLAYER_2, assetManager.defaultSkin);
        btnPlayer2.setName(PLAYER_2);
        final TextButton btnPlayer3 = new TextButton(PLAYER_3, assetManager.defaultSkin);
        btnPlayer3.setName(PLAYER_3);
        final TextButton btnPlayer4 = new TextButton(PLAYER_4, assetManager.defaultSkin);
        btnPlayer4.setName(PLAYER_4);
        textButtons=new Array<>();
        textButtons.add(btnPlayer1);
        textButtons.add(btnPlayer2);
        textButtons.add(btnPlayer3);
        textButtons.add(btnPlayer4);

        final Table root = new Table(assetManager.defaultSkin);
        root.setBackground(new NinePatchDrawable(assetManager.defaultSkin.getPatch("default-window")));
        root.setFillParent(true);
        float height = Gdx.graphics.getHeight()/8f;
        root.top();
        root.add(btnPlayer1).width(Gdx.graphics.getWidth()/4f).height(height);
        root.row();
        root.add(btnPlayer2).width(Gdx.graphics.getWidth()/4f).height(height);
        root.row();
        root.add(btnPlayer3).width(Gdx.graphics.getWidth()/4f).height(height);
        root.row();
        root.add(btnPlayer4).width(Gdx.graphics.getWidth()/4f).height(height);
        addActor(root);

    }

    public void returnToSearch(){
        screenManager.switchToLastScreen();
    }

    @Override
    public void connected() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void disconnected() {
        returnToSearch();
    }

    @Override
    public void anotherClientConnected(String clientName, int id) {
        addClient(clientName, id);
    }

    @Override
    public void anotherClientDisconnected(int id) {
        removeClient(id);
    }

    @Override
    public void dispose() {
        super.dispose();
        brawlClient.setNetworkConnectionHandler(null);
    }
}
