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
import com.strategy_bit.chaos_brawl.network.messages.request.NetworkMembersRequestMessage;
import com.strategy_bit.chaos_brawl.network.messages.request.ResourceTickMessage;
import com.strategy_bit.chaos_brawl.network.messages.response.NetworkMemberResponseMessage;
import com.strategy_bit.chaos_brawl.screens.ClientLobbyScreen;
import com.strategy_bit.chaos_brawl.screens.ScreenEnum;
import com.strategy_bit.chaos_brawl.world.MultiplayerInputHandler;

public class BrawlClientListener extends Listener implements BrawlConnector {
    private BrawlClientImpl brawlClient;
    private MultiplayerInputHandler inputHandler;
    public BrawlClientListener(BrawlClientImpl brawlClient) {
        this.brawlClient=brawlClient;
    }

    @Override
    public void connected(Connection connection) {
        //TODO add here network lounge
        brawlClient.sendData(new NetworkMembersRequestMessage());
    }

    @Override
    public void disconnected(Connection connection) {
        if (ScreenManager.getInstance().getCurrentScreen() instanceof ClientLobbyScreen){
            ((ClientLobbyScreen)ScreenManager.getInstance().getCurrentScreen()).returnToSearch();
        }
    }

    @Override
    public void received(final Connection connection,final Object object) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                //System.out.println("Received Message");
                if (object instanceof EntityMovingMessage) {
                    EntityMovingMessage movingMessage = (EntityMovingMessage) object;
                    inputHandler.moveEntityLocal(movingMessage.entityID, movingMessage.toLibGdxArray());
                    //brawlClient.getManager().sendTouchInputLocal(movingMessage.screenCoordinates,movingMessage.entityID);
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
                    NetworkMemberResponseMessage replyMessage = (NetworkMemberResponseMessage) object;
                    //brawlClient.connections=replyMessage.members;
                    ScreenManager screenManager = ScreenManager.getInstance();
                    if (screenManager.getCurrentScreen() instanceof ClientLobbyScreen){
                        int i=1;
                        for (String ip:
                                ((NetworkMemberResponseMessage) object).members) {
                            ((ClientLobbyScreen)(screenManager.getCurrentScreen())).addClient(ip,i++);
                        }
                    }



                }else if(object instanceof InitializeGameMessage) {
                    InitializeGameMessage initializeGameMessage = (InitializeGameMessage) object;
                    ScreenManager screenManager = ScreenManager.getInstance();
                    screenManager.showScreenWithoutAddingOldOneToStack(ScreenEnum.MULTIPLAYERGAME, brawlClient, initializeGameMessage.controllers);
                }
                else if(object instanceof ClientConnectedMessage) {
                    ClientConnectedMessage clientConnectedMessage = (ClientConnectedMessage) object;
                    ScreenManager screenManager = ScreenManager.getInstance();
                    ((ClientLobbyScreen)(screenManager.getCurrentScreen())).addClient(clientConnectedMessage.name,clientConnectedMessage.id);
                }
                else if (object instanceof ClientDisconnectedMessage){
                    ClientDisconnectedMessage clientDisconnectedMessage = (ClientDisconnectedMessage) object;
                    ScreenManager screenManager = ScreenManager.getInstance();
                    if (screenManager.getCurrentScreen() instanceof ClientLobbyScreen)
                    ((ClientLobbyScreen)(screenManager.getCurrentScreen())).removeClient(clientDisconnectedMessage.id);
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
}
