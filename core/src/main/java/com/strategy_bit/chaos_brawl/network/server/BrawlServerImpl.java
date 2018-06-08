package com.strategy_bit.chaos_brawl.network.server;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.strategy_bit.chaos_brawl.config.Network;
import com.strategy_bit.chaos_brawl.managers.ScreenManager;
import com.strategy_bit.chaos_brawl.network.BrawlConnector;
import com.strategy_bit.chaos_brawl.network.BrawlMultiplayer;
import com.strategy_bit.chaos_brawl.network.BrawlNetwork;
import com.strategy_bit.chaos_brawl.network.messages.Message;
import com.strategy_bit.chaos_brawl.network.messages.request.EntityDeleteMessage;
import com.strategy_bit.chaos_brawl.network.messages.request.EntityMovingMessage;
import com.strategy_bit.chaos_brawl.network.messages.request.EntitySpawnMessage;
import com.strategy_bit.chaos_brawl.network.messages.request.InitializeGameMessage;
import com.strategy_bit.chaos_brawl.network.messages.request.ResourceTickMessage;
import com.strategy_bit.chaos_brawl.network.network_handlers.NetworkConnectionHandler;
import com.strategy_bit.chaos_brawl.screens.ScreenEnum;

import java.io.IOException;

/**
 * @author AIsopp
 * @version 1.0
 * @since 30.03.2018
 */
public class BrawlServerImpl implements BrawlServer,BrawlMultiplayer {

    private Server server;
    private boolean serverIsRunning;
    private BrawlServerListener serverListener;

    public BrawlServerImpl(){
        server = new Server();
        serverListener = new BrawlServerListener(this);
        server.addListener(serverListener);
        serverIsRunning = false;
        BrawlNetwork.initializeKryo(this);
    }

    @Override
    public void sendData(Message msg) {
        server.sendToAllTCP(msg);

    }

    @Override
    public void sendGameInitializingMessage(){
        Connection[] connections = server.getConnections();


        int[] playersToSend=new int[connections.length+1];
        for (int i = 0; i < playersToSend.length; i++) {
            playersToSend[i]=i;
        }
        int map = 1;
        if(connections.length +1 == 4){
            map = 4;
        }
        for (int i = 0; i < connections.length; i++) {

            for (int j = 0; j < playersToSend.length; j++) {
                playersToSend[j]=(playersToSend[j]+1)%playersToSend.length;
            }
            InitializeGameMessage gameMessage = new InitializeGameMessage(playersToSend,map);
            server.sendToTCP(connections[i].getID(), gameMessage);
        }
        for (int j = 0; j < playersToSend.length; j++) {
            playersToSend[j]=(playersToSend[j]+1)%playersToSend.length;
        }
        ScreenManager screenManager = ScreenManager.getInstance();
        screenManager.showScreenWithoutAddingOldOneToStack(ScreenEnum.MULTIPLAYERGAME, this, playersToSend, map);

    }

    public void sendDataToAllExcept(Connection connection, Message msg) {
        server.sendToAllExceptTCP(connection.getID(),msg);

    }

    public void sendDataOnlyTo(Connection connection, Message msg) {
        server.sendToTCP(connection.getID(),msg);

    }

    @Override
    public boolean isServerIsRunning() {
        return serverIsRunning;
    }

    @Override
    public void startServer() throws IOException {
        server.start();
        server.bind(Network.TCP_PORT, Network.UDP_PORT);
        serverIsRunning = true;

    }

    @Override
    public void closeServer() {
        server.stop();
        serverIsRunning=false;
        try {
            server.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Kryo getKryo() {
        return server.getKryo();
    }

    @Override
    public Connection[] getNetworkMembers() {
        return server.getConnections();
    }

    @Override
    public void setNetworkConnectionHandler(NetworkConnectionHandler connectionHandler) {
        serverListener.setNetworkConnectionHandler(connectionHandler);
    }

    @Override
    public String getName() {
        return "host";
    }

    @Override
    public void sendTick() {
        sendData(new ResourceTickMessage());
    }

    @Override
    public void sendEntitySpawnMsg(Vector2 worldPosition, int unitId, int teamID, long unitID) {
        sendData(new EntitySpawnMessage(worldPosition,teamID,unitId, unitID));
    }

    @Override
    public void sendEntityDeleteMsg(long entityID) {
        sendData(new EntityDeleteMessage(entityID));
    }

    @Override
    public void sendEntityUpgradeMsg(long entityID) {

    }

    @Override
    public void sendEntityMovingMessage(long unitID, Array<Vector2> wayPoints) {
        sendData(new EntityMovingMessage(unitID, wayPoints));
    }

    @Override
    public BrawlConnector getBrawlConnector() {
        return serverListener;
    }

    @Override
    public void dispose() {
        closeServer();
    }

    @Override
    public void sendNewTargetMsg(int playerIndex, int targetIndex) {
        throw new UnsupportedOperationException("Server does not send any messages of this type");
    }

    @Override
    public boolean isHost() {
        return true;
    }

    public void closeConnectionTo(int id){
        server.getConnections()[id].close();
    }
}
