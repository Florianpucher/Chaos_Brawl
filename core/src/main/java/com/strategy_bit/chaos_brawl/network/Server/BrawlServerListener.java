package com.strategy_bit.chaos_brawl.network.Server;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.strategy_bit.chaos_brawl.config.Network;
import com.strategy_bit.chaos_brawl.managers.ScreenManager;
import com.strategy_bit.chaos_brawl.network.BrawlConnector;
import com.strategy_bit.chaos_brawl.network.messages.Request.ClientConnectedMessage;
import com.strategy_bit.chaos_brawl.network.messages.Request.EntityMovingMessage;
import com.strategy_bit.chaos_brawl.network.messages.Request.EntitySpawnMessage;
import com.strategy_bit.chaos_brawl.network.messages.Request.NetworkMembersRequestMessage;
import com.strategy_bit.chaos_brawl.network.messages.Response.NetworkMemberResponseMessage;
import com.strategy_bit.chaos_brawl.screens.HostLobbyScreen;
import com.strategy_bit.chaos_brawl.screens.ScreenEnum;
import com.strategy_bit.chaos_brawl.world.MultiplayerInputHandler;

public class BrawlServerListener extends Listener implements BrawlConnector {
    private BrawlServerImpl brawlServer;
    private MultiplayerInputHandler multiplayerInputHandler;
    public BrawlServerListener(BrawlServerImpl brawlServer) {
        this.brawlServer=brawlServer;
    }

    @Override
    public void connected(Connection connection) {
        //Start a 2 Archer-Game
        //TODO: add 3 and 4 Archer-Games
        if (ScreenManager.getInstance().getCurrentScreen() instanceof HostLobbyScreen)
        ((HostLobbyScreen)ScreenManager.getInstance().getCurrentScreen()).addClient(connection.getRemoteAddressTCP().getHostName());
        brawlServer.sendDataToAllExcept(connection,new ClientConnectedMessage(connection.getRemoteAddressTCP().getHostName()));
        /*Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                //just two players
                int[] players = new int[]{Network.YOUR_CLIENT_CONTROLLER,Network.OTHER_CLIENT_CONTROLLER};
                brawlServer.sendGameInitializingMessage(players);
                ScreenManager.getInstance().showScreen(ScreenEnum.MULTIPLAYERGAME,brawlServer,this,players);
            }
        });
        */

    }

    @Override
    public void disconnected(Connection connection) {
    }

    @Override
    public void received(final Connection connection,final  Object object) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                if (object instanceof EntityMovingMessage) {
                    //There should not come any kind of those messages atm
                    //EntityMovingMessage movingMessage = (EntityMovingMessage) object;
                    //brawlServer.getManager().sendTouchInputLocal(movingMessage.screenCoordinates, movingMessage.entityID);
                    //brawlServer.sendDataToAllExcept(connection,movingMessage);
                    //multiplayerInputHandler.
                }
                else if (object instanceof EntitySpawnMessage){
                    EntitySpawnMessage entitySpawnMessage=(EntitySpawnMessage) object;
                    //brawlServer.getManager().createEntityLocal(entitySpawnMessage.position,entitySpawnMessage.teamId,entitySpawnMessage.entityTypeId);
                    multiplayerInputHandler.createEntityWorldCoordinates(entitySpawnMessage.position,entitySpawnMessage.entityTypeId, entitySpawnMessage.teamId);
                    //brawlServer.sendDataToAllExcept(connection,entitySpawnMessage);
                }
                else if (object instanceof NetworkMembersRequestMessage){
                    brawlServer.sendDataOnlyTo(connection,new NetworkMemberResponseMessage(brawlServer.getNetworkMembers()));
                }
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
}
