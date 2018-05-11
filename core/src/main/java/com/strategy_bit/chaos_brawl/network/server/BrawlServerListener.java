package com.strategy_bit.chaos_brawl.network.server;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.strategy_bit.chaos_brawl.network.BrawlConnector;
import com.strategy_bit.chaos_brawl.network.messages.request.ClientConnectedMessage;
import com.strategy_bit.chaos_brawl.network.messages.request.ClientDisconnectedMessage;
import com.strategy_bit.chaos_brawl.network.messages.request.EntitySpawnMessage;
import com.strategy_bit.chaos_brawl.network.messages.request.NetworkMembersRequestMessage;
import com.strategy_bit.chaos_brawl.network.messages.response.NetworkMemberResponseMessage;
import com.strategy_bit.chaos_brawl.network.network_handlers.NetworkConnectionHandler;
import com.strategy_bit.chaos_brawl.world.MultiplayerInputHandler;

public class BrawlServerListener extends Listener implements BrawlConnector {
    private BrawlServerImpl brawlServer;
    private MultiplayerInputHandler multiplayerInputHandler;
    private NetworkConnectionHandler connectionHandler;

    public BrawlServerListener(BrawlServerImpl brawlServer) {
        this.brawlServer = brawlServer;
    }

    @Override
    public void connected(Connection connection) {
        if (connectionHandler != null) {
            connectionHandler.anotherClientConnected(connection.getRemoteAddressTCP().getHostName(), connection.getID());
        }
        brawlServer.sendDataToAllExcept(connection, new ClientConnectedMessage(connection.getRemoteAddressTCP().getHostName(), connection.getID()));

    }

    @Override
    public void disconnected(Connection connection) {
        if (connectionHandler != null) {
            connectionHandler.anotherClientDisconnected(connection.getID());
        }
        brawlServer.sendData(new ClientDisconnectedMessage(connection.getRemoteAddressUDP().getHostName(), connection.getID()));
    }

    @Override
    public void received(final Connection connection, final Object object) {
        Gdx.app.postRunnable(() -> {
            if (object instanceof EntitySpawnMessage) {
                EntitySpawnMessage entitySpawnMessage = (EntitySpawnMessage) object;
                multiplayerInputHandler.createEntityWorldCoordinates(entitySpawnMessage.position, entitySpawnMessage.entityTypeId, entitySpawnMessage.teamId);
            } else if (object instanceof NetworkMembersRequestMessage) {
                brawlServer.sendDataOnlyTo(connection, new NetworkMemberResponseMessage(brawlServer.getName(), brawlServer.getNetworkMembers()));
            }
        });
    }

    @Override
    public MultiplayerInputHandler getInputHandler() {
        return multiplayerInputHandler;
    }

    @Override
    public void setMultiplayerInputHandler(MultiplayerInputHandler multiplayerInputHandler) {
        this.multiplayerInputHandler = multiplayerInputHandler;
    }

    @Override
    public void setNetworkConnectionHandler(NetworkConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }
}
