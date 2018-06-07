package com.strategy_bit.chaos_brawl.screens.menu_screens;

import com.strategy_bit.chaos_brawl.network.client.BrawlClient;
import com.strategy_bit.chaos_brawl.network.messages.request.NetworkMembersRequestMessage;

/**
 * @author AIsopp
 * @version 1.0
 * @since 02.04.2018
 */
public class ClientLobbyScreen extends LobbyScreen{

    private BrawlClient brawlClient;

    public ClientLobbyScreen(BrawlClient brawlClient) {
        this.brawlClient = brawlClient;
        brawlClient.setNetworkConnectionHandler(this);
        brawlClient.sendData(new NetworkMembersRequestMessage());
    }

    @Override
    public void buildStage() {
        super.buildStage();
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
