package com.strategy_bit.chaos_brawl.network.Client;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.strategy_bit.chaos_brawl.network.messages.Request.EntityDeleteMessage;
import com.strategy_bit.chaos_brawl.network.messages.Request.EntityMovingMessage;
import com.strategy_bit.chaos_brawl.network.messages.Request.EntitySpawnMessage;
import com.strategy_bit.chaos_brawl.network.messages.Response.NetworkMemberResponseMessage;
import com.strategy_bit.chaos_brawl.world.InputHandler;
import com.strategy_bit.chaos_brawl.world.MultiplayerInputHandler;

public class BrawlClientListener extends Listener {
    private BrawlClientImpl brawlClient;
    private MultiplayerInputHandler inputHandler;
    public BrawlClientListener(BrawlClientImpl brawlClient) {
        this.brawlClient=brawlClient;
    }

    @Override
    public void connected(Connection connection) {

    }

    @Override
    public void disconnected(Connection connection) {
    }

    @Override
    public void received(final Connection connection,final Object object) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                if (object instanceof EntityMovingMessage) {
                    EntityMovingMessage movingMessage = (EntityMovingMessage) object;
                    inputHandler.moveEntityLocal(movingMessage.entityID, movingMessage.wayPoints);
                    //brawlClient.getManager().sendTouchInputLocal(movingMessage.screenCoordinates,movingMessage.entityID);
                }
                else if (object instanceof EntitySpawnMessage){
                    EntitySpawnMessage entitySpawnMessage=(EntitySpawnMessage) object;
                    inputHandler.createEntityLocal(entitySpawnMessage.position,entitySpawnMessage.entityTypeId,entitySpawnMessage.teamId);
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
                }
            }
        });


    }

    public MultiplayerInputHandler getInputHandler() {
        return inputHandler;
    }
}
