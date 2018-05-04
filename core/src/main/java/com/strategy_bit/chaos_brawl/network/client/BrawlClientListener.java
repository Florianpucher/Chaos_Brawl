package com.strategy_bit.chaos_brawl.network.client;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.strategy_bit.chaos_brawl.managers.ScreenManager;
import com.strategy_bit.chaos_brawl.network.BrawlConnector;
import com.strategy_bit.chaos_brawl.network.messages.request.EntityDeleteMessage;
import com.strategy_bit.chaos_brawl.network.messages.request.EntityMovingMessage;
import com.strategy_bit.chaos_brawl.network.messages.request.EntitySpawnMessage;
import com.strategy_bit.chaos_brawl.network.messages.request.InitializeGameMessage;
import com.strategy_bit.chaos_brawl.network.messages.request.ResourceTickMessage;
import com.strategy_bit.chaos_brawl.network.messages.response.NetworkMemberResponseMessage;
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
    }

    @Override
    public void disconnected(Connection connection) {
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
                    brawlClient.connections=replyMessage.members;
                    synchronized (brawlClient.connections){
                        brawlClient.connections.notify();
                    }
                }else if(object instanceof InitializeGameMessage) {
                    InitializeGameMessage initializeGameMessage = (InitializeGameMessage) object;
                    ScreenManager screenManager = ScreenManager.getInstance();
                    screenManager.showScreenWithoutAddingOldOneToStack(ScreenEnum.MULTIPLAYERGAME, brawlClient, this, initializeGameMessage.controllers);
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
