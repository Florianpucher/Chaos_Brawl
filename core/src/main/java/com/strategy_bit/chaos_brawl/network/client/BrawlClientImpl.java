package com.strategy_bit.chaos_brawl.network.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.strategy_bit.chaos_brawl.config.Network;
import com.strategy_bit.chaos_brawl.network.BrawlConnector;
import com.strategy_bit.chaos_brawl.network.BrawlMultiplayer;
import com.strategy_bit.chaos_brawl.network.BrawlNetwork;
import com.strategy_bit.chaos_brawl.network.messages.Message;
import com.strategy_bit.chaos_brawl.network.messages.request.EntitySpawnMessage;
import com.strategy_bit.chaos_brawl.network.messages.request.NetworkMembersRequestMessage;
import com.strategy_bit.chaos_brawl.network.messages.request.PlayerSelectedNewTargetMessage;
import com.strategy_bit.chaos_brawl.network.network_handlers.NetworkConnectionHandler;
import com.strategy_bit.chaos_brawl.network.network_handlers.NetworkDiscoveryHandler;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * sends data to Host
 *
 * @author AIsopp
 * @version 1.0
 * @since 01.04.2018
 */
public class BrawlClientImpl implements BrawlClient, BrawlMultiplayer {
    private Client client;
    private ArrayList<NetworkDiscoveryHandler> discoveryHandlers;
    protected Connection[] connections;
    private BrawlClientListener clientListener;

    public BrawlClientImpl() {
        client = new Client();
        client.start();
        clientListener = new BrawlClientListener(this);
        client.addListener(clientListener);
        discoveryHandlers = new ArrayList<>();
        connections = new Connection[0];
        BrawlNetwork.initializeKryo(this);
    }

    @Override
    public void connectToServer(String hostIP) throws IOException {
        client.connect(Network.TIME_OUT, hostIP, Network.TCP_PORT, Network.UDP_PORT);
    }

    @Override
    public void disconnect() {

        client.stop();
        try {
            client.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void discoverServers() {

        Executor executor = Executors.newSingleThreadExecutor();
        Runnable runnable = () -> {
            final List<InetAddress> addresses = client.discoverHosts(Network.UDP_PORT, Network.TIME_OUT);

            Gdx.app.postRunnable(() -> updateDiscoveryHandler(addresses));
        };
        executor.execute(runnable);
    }

    @Override
    public void sendData(Message msg) {
        client.sendTCP(msg);
    }


    @Override
    public Kryo getKryo() {
        return client.getKryo();
    }

    @Override
    public Connection[] getNetworkMembers() {
        sendData(new NetworkMembersRequestMessage());
        return connections;
    }


    @Override
    public void addNetworkDiscoveryHandler(NetworkDiscoveryHandler discoveryHandler) {
        discoveryHandlers.add(discoveryHandler);
    }


    @Override
    public void removeNetworkDiscoveryHandler(NetworkDiscoveryHandler discoveryHandler) {
        discoveryHandlers.remove(discoveryHandler);
    }

    @Override
    public String getName() {
        return "client";
    }


    @Override
    public void setNetworkConnectionHandler(NetworkConnectionHandler connectionHandler) {
        clientListener.setNetworkConnectionHandler(connectionHandler);
    }

    private void updateDiscoveryHandler(List<InetAddress> addresses) {
        for (NetworkDiscoveryHandler discoveryHandler :
                discoveryHandlers) {
            discoveryHandler.receiveHosts(addresses);
        }
    }

    @Override
    public void sendTick() {
        throw new UnsupportedOperationException("Only the host sends tick messages");
    }

    @Override
    public void sendEntitySpawnMsg(Vector2 worldPosition, int unitId, int teamID, long unitID) {
        EntitySpawnMessage spawnMessage = new EntitySpawnMessage(worldPosition, teamID, unitId, unitID);
        sendData(spawnMessage);
    }

    @Override
    public void sendEntityDeleteMsg(long entityID) {
        throw new UnsupportedOperationException("Only the host sends deleting messages");
    }

    @Override
    public void sendEntityUpgradeMsg(long entityID) {
        throw new UnsupportedOperationException("Only the host sends upgrade messages");
    }

    @Override
    public void sendEntityMovingMessage(long unitID, Array<Vector2> wayPoints) {
        throw new UnsupportedOperationException("Only the host sends moving messages");
    }

    @Override
    public BrawlConnector getBrawlConnector() {
        return clientListener;
    }

    @Override
    public void dispose() {

        disconnect();

    }

    @Override
    public void sendNewTargetMsg(int playerIndex, int targetIndex) {
        client.sendTCP(new PlayerSelectedNewTargetMessage(playerIndex, targetIndex));
    }

    @Override
    public boolean isHost() {
        return false;
    }
}
