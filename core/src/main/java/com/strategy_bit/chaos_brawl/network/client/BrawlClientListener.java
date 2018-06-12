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

    BrawlClientListener(BrawlClientImpl brawlClient) {
        this.brawlClient = brawlClient;
    }

    private NetworkConnectionHandler connectionHandler;

    @Override
    public void connected(Connection connection) {
        if (connectionHandler != null) {
            Gdx.app.postRunnable(() -> connectionHandler.connected());
        }


    }

    @Override
    public void disconnected(Connection connection) {
        if (connectionHandler != null) {
            Gdx.app.postRunnable(() -> connectionHandler.disconnected());
        }
    }

    @Override
    public void received(final Connection connection, final Object object) {
        Gdx.app.postRunnable(() -> {
            if (!handleGameMessages(object) && !handleSetupMessages(object)) {

                Gdx.app.log("NETWORK", "Received unknown message");

            }
        });
    }

    /**
     * consists all methods for messages that should interact with the game
     *
     * @param object message
     * @return true if message is handled by this method
     */
    private boolean handleGameMessages(final Object object) {
        if (object instanceof EntityMovingMessage) {
            EntityMovingMessage movingMessage = (EntityMovingMessage) object;
            inputHandler.moveEntityLocal(movingMessage.entityID, movingMessage.toLibGdxArray());
            return true;
        } else if (object instanceof ResourceTickMessage) {
            float deltaTime= ((ResourceTickMessage) object).deltaTime;
            inputHandler.getTick(deltaTime);
            return true;
        } else if (object instanceof EntitySpawnMessage) {
            EntitySpawnMessage entitySpawnMessage = (EntitySpawnMessage) object;
            inputHandler.createEntityLocal(entitySpawnMessage.position, entitySpawnMessage.entityTypeId, entitySpawnMessage.teamId, entitySpawnMessage.unitID);
            return true;
        } else if (object instanceof EntityDeleteMessage) {
            EntityDeleteMessage deleteMessage = (EntityDeleteMessage) object;
            inputHandler.deleteUnitLocal(deleteMessage.unitID);
            return true;
        }
        return false;
    }

    /**
     * consists all methods for messages that should interact with the game setup
     *
     * @param object message
     * @return true if message is handled by this method
     */
    private boolean handleSetupMessages(final Object object) {
        if (object instanceof NetworkMemberResponseMessage) {
            if (connectionHandler != null) {
                int i = 1;
                for (String ip :
                        ((NetworkMemberResponseMessage) object).members) {
                    connectionHandler.anotherClientConnected(ip, i++);
                }
            }
            return true;
        } else if (object instanceof InitializeGameMessage) {
            InitializeGameMessage initializeGameMessage = (InitializeGameMessage) object;
            connectionHandler = null;
            ScreenManager screenManager = ScreenManager.getInstance();
            screenManager.showScreenWithoutAddingOldOneToStack(ScreenEnum.MULTIPLAYERGAME, brawlClient, initializeGameMessage.controllers,initializeGameMessage.map);
            return true;
        } else if (object instanceof ClientConnectedMessage) {
            ClientConnectedMessage clientConnectedMessage = (ClientConnectedMessage) object;
            if (connectionHandler != null) {
                connectionHandler.anotherClientConnected(clientConnectedMessage.name, clientConnectedMessage.id);
            }
            return true;
        } else if (object instanceof ClientDisconnectedMessage) {
            ClientDisconnectedMessage clientDisconnectedMessage = (ClientDisconnectedMessage) object;
            if (connectionHandler != null) {
                connectionHandler.anotherClientDisconnected(clientDisconnectedMessage.id);
            }
            return true;
        }
        return false;
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
