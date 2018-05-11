package com.strategy_bit.chaos_brawl.network.client;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.strategy_bit.chaos_brawl.managers.ScreenManager;
import com.strategy_bit.chaos_brawl.network.BrawlConnector;
import com.strategy_bit.chaos_brawl.network.messages.request.ClientConnectedMessage;
import com.strategy_bit.chaos_brawl.network.messages.request.ClientDisconnectedMessage;
import com.strategy_bit.chaos_brawl.network.messages.request.EntityDeleteMessage;
import com.strategy_bit.chaos_brawl.network.messages.request.EntityMovingMessage;
import com.strategy_bit.chaos_brawl.network.messages.request.EntitySpawnMessage;
import com.strategy_bit.chaos_brawl.network.messages.request.InitializeGameMessage;
import com.strategy_bit.chaos_brawl.network.messages.request.ResourceTickMessage;
import com.strategy_bit.chaos_brawl.network.messages.response.NetworkMemberResponseMessage;
import com.strategy_bit.chaos_brawl.network.network_handlers.NetworkConnectionHandler;
import com.strategy_bit.chaos_brawl.screens.ScreenEnum;
import com.strategy_bit.chaos_brawl.world.MultiplayerInputHandler;

public class BrawlClientListener extends Listener implements BrawlConnector {
    private BrawlClientImpl brawlClient;
    private MultiplayerInputHandler inputHandler;
    public BrawlClientListener(BrawlClientImpl brawlClient) {
        this.brawlClient=brawlClient;
    }
    private NetworkConnectionHandler connectionHandler;

    @Override
    public void connected(Connection connection) {
        if(connectionHandler != null){
            Gdx.app.postRunnable(() -> connectionHandler.connected());
        }


    }

    @Override
    public void disconnected(Connection connection) {
        if(connectionHandler != null){
            Gdx.app.postRunnable(() -> connectionHandler.disconnected());
        }
    }

    @Override
    public void received(final Connection connection,final Object object) {
        Gdx.app.postRunnable(() -> {

            if (object instanceof EntityMovingMessage) {
                EntityMovingMessage movingMessage = (EntityMovingMessage) object;
                inputHandler.moveEntityLocal(movingMessage.entityID, movingMessage.toLibGdxArray());
            }
            else if (object instanceof ResourceTickMessage){
                inputHandler.getTick();
            }

            else if (object instanceof EntitySpawnMessage){
                EntitySpawnMessage entitySpawnMessage=(EntitySpawnMessage) object;
                inputHandler.createEntityLocal(entitySpawnMessage.position,entitySpawnMessage.entityTypeId,entitySpawnMessage.teamId, entitySpawnMessage.unitID);
            }else if(object instanceof EntityDeleteMessage){
                EntityDeleteMessage deleteMessage = (EntityDeleteMessage) object;
                inputHandler.deleteUnitLocal(deleteMessage.unitID);
            }
            else if (object instanceof NetworkMemberResponseMessage) {
                if(connectionHandler != null){
                    int i=1;
                    for (String ip:
                            ((NetworkMemberResponseMessage) object).members) {
                        connectionHandler.anotherClientConnected(ip, i++);
                    }
                }
            }else if(object instanceof InitializeGameMessage) {
                InitializeGameMessage initializeGameMessage = (InitializeGameMessage) object;
                //TODO move screen switch out of networking
                connectionHandler = null;
                ScreenManager screenManager = ScreenManager.getInstance();
                screenManager.showScreenWithoutAddingOldOneToStack(ScreenEnum.MULTIPLAYERGAME, brawlClient, initializeGameMessage.controllers);
            }
            else if(object instanceof ClientConnectedMessage) {
                ClientConnectedMessage clientConnectedMessage = (ClientConnectedMessage) object;
                if(connectionHandler != null){
                    connectionHandler.anotherClientConnected(clientConnectedMessage.name,clientConnectedMessage.id);
                }
            }
            else if (object instanceof ClientDisconnectedMessage){
                ClientDisconnectedMessage clientDisconnectedMessage = (ClientDisconnectedMessage) object;
                if(connectionHandler != null){
                    connectionHandler.anotherClientDisconnected(clientDisconnectedMessage.id);
                }
            }
        });


    }

    @Override
    public MultiplayerInputHandler getInputHandler() {
        return inputHandler;
    }

    @Override
    public void setMultiplayerInputHandler(MultiplayerInputHandler multiplayerInputHandler) {
        this.inputHandler = multiplayerInputHandler;
    }

    @Override
    public void setNetworkConnectionHandler(NetworkConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }
}
