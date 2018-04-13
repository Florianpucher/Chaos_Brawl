package com.strategy_bit.chaos_brawl.network.Client;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.strategy_bit.chaos_brawl.network.messages.Request.EntityMovingMessage;
import com.strategy_bit.chaos_brawl.network.messages.Request.EntitySpawnMessage;
import com.strategy_bit.chaos_brawl.network.messages.Response.NetworkMemberResponseMessage;

public class BrawlClientListener extends Listener {
    private BrawlClientImpl brawlClient;
    public BrawlClientListener(BrawlClientImpl brawlClient) {
        brawlClient=brawlClient;
    }

    @Override
    public void connected(Connection connection) {

    }

    @Override
    public void disconnected(Connection connection) {
    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof EntityMovingMessage) {
            EntityMovingMessage movingMessage = (EntityMovingMessage) object;
            brawlClient.getManager().sendTouchInputLocal(movingMessage.screenCoordinates,movingMessage.entityID);
        }
        else if (object instanceof EntitySpawnMessage){
            EntitySpawnMessage entitySpawnMessage=(EntitySpawnMessage) object;
            brawlClient.getManager().createEntityLocal(entitySpawnMessage.entity);
        }
        else if (object instanceof NetworkMemberResponseMessage) {
            NetworkMemberResponseMessage replyMessage = (NetworkMemberResponseMessage) object;
            brawlClient.connections=replyMessage.members;
            synchronized (brawlClient.connections){
                brawlClient.connections.notify();
            }
        }
    }
}
