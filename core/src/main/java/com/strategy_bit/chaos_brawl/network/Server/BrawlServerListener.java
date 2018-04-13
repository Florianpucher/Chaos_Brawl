package com.strategy_bit.chaos_brawl.network.Server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.strategy_bit.chaos_brawl.managers.ScreenManager;
import com.strategy_bit.chaos_brawl.network.messages.Request.EntityMovingMessage;
import com.strategy_bit.chaos_brawl.network.messages.Request.EntitySpawnMessage;
import com.strategy_bit.chaos_brawl.network.messages.Request.NetworkMembersRequestMessage;
import com.strategy_bit.chaos_brawl.network.messages.Response.NetworkMemberResponseMessage;
import com.strategy_bit.chaos_brawl.screens.ScreenEnum;

public class BrawlServerListener extends Listener {
    private BrawlServerImpl brawlServer;
    public BrawlServerListener(BrawlServerImpl brawlServer) {
        brawlServer=brawlServer;
    }

    @Override
    public void connected(Connection connection) {
        //Start a 2 Player-Game
        //TODO: add 3 and 4 Player-Games
        ScreenManager.getInstance().showScreen(ScreenEnum.MULTIPLAYERGAME,brawlServer);
    }

    @Override
    public void disconnected(Connection connection) {
    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof EntityMovingMessage) {
            EntityMovingMessage movingMessage = (EntityMovingMessage) object;
            brawlServer.getManager().sendTouchInputLocal(movingMessage.screenCoordinates, movingMessage.entityID);
            brawlServer.sendDataToAllExcept(connection,movingMessage);
        }
        else if (object instanceof EntitySpawnMessage){
            EntitySpawnMessage entitySpawnMessage=(EntitySpawnMessage) object;
            brawlServer.getManager().createEntityLocal(entitySpawnMessage.entity);
            brawlServer.sendDataToAllExcept(connection,entitySpawnMessage);
        }
        else if (object instanceof NetworkMembersRequestMessage){
            brawlServer.sendData(new NetworkMemberResponseMessage(brawlServer.getNetworkMembers()));
        }
    }
}
