package com.strategy_bit.chaos_brawl.network.server;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.strategy_bit.chaos_brawl.network.BrawlConnector;
import com.strategy_bit.chaos_brawl.network.messages.request.ClientConnectedMessage;
import com.strategy_bit.chaos_brawl.network.messages.request.ClientDisconnectedMessage;
import com.strategy_bit.chaos_brawl.network.messages.request.EntitySpawnMessage;
import com.strategy_bit.chaos_brawl.network.messages.request.EntityUpgradeMessage;
import com.strategy_bit.chaos_brawl.network.messages.request.NetworkMembersRequestMessage;
import com.strategy_bit.chaos_brawl.network.messages.request.PlayerSelectedNewTargetMessage;
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
        boolean informOther=true;
        if (connectionHandler != null) {
            //refuse connection if the lobby is full
            try {
                connectionHandler.anotherClientConnected(connection.getRemoteAddressTCP().getHostName(), connection.getID());
            }catch (IllegalStateException e){
                informOther=false;
                //kick client
                connection.close();
            }
        }
        if (informOther) {
            brawlServer.sendDataToAllExcept(connection, new ClientConnectedMessage(connection.getRemoteAddressTCP().getHostName(), connection.getID()));
        }
    }

    @Override
    public void disconnected(Connection connection) {
        boolean informOther=true;
        if (connectionHandler != null) {
            //connection was never in lobby:
            // - do nothing
            try {
                connectionHandler.anotherClientDisconnected(connection.getID());
            }catch (IllegalStateException e){
                informOther=false;
            }
        }
        if (informOther) {
            brawlServer.sendData(new ClientDisconnectedMessage(connection.getRemoteAddressUDP().getHostName(), connection.getID()));
        }
    }

    @Override
    public void received(final Connection connection, final Object object) {
        Gdx.app.postRunnable(() -> {
            if (object instanceof EntitySpawnMessage) {
                EntitySpawnMessage entitySpawnMessage = (EntitySpawnMessage) object;
                multiplayerInputHandler.createEntityWorldCoordinates(entitySpawnMessage.position, entitySpawnMessage.entityTypeId, entitySpawnMessage.teamId);
            } else if (object instanceof NetworkMembersRequestMessage) {
                brawlServer.sendDataOnlyTo(connection, new NetworkMemberResponseMessage(brawlServer.getName(), brawlServer.getNetworkMembers()));
            } else if(object instanceof PlayerSelectedNewTargetMessage){
                PlayerSelectedNewTargetMessage selectedNewTargetMessage = (PlayerSelectedNewTargetMessage) object;
                multiplayerInputHandler.playerChangesTarget(selectedNewTargetMessage.playerTeamID, selectedNewTargetMessage.targetTeamID);
            }else if(object instanceof EntityUpgradeMessage){
                EntityUpgradeMessage entityUpgradeMessage = (EntityUpgradeMessage) object;
                multiplayerInputHandler.updateTowersOrUnits(entityUpgradeMessage.teamID, entityUpgradeMessage.upgradeID);
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
