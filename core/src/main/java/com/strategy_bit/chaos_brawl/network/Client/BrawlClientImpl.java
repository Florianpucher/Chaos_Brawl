package com.strategy_bit.chaos_brawl.network.Client;

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
import com.strategy_bit.chaos_brawl.network.messages.Request.EntitySpawnMessage;
import com.strategy_bit.chaos_brawl.network.messages.Request.NetworkMembersRequestMessage;
import com.strategy_bit.chaos_brawl.network.network_handlers.NetworkDiscoveryHandler;
import com.strategy_bit.chaos_brawl.network.network_handlers.NetworkInputHandler;
import com.strategy_bit.chaos_brawl.network.network_handlers.NetworkLoungeHandler;
import com.strategy_bit.chaos_brawl.types.UnitType;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * sends data to Host
 * @author AIsopp
 * @version 1.0
 * @since 01.04.2018
 */
public class BrawlClientImpl implements BrawlClient,BrawlMultiplayer {
    private Client client;
    private ArrayList<NetworkDiscoveryHandler> discoveryHandlers;
    private ArrayList<NetworkInputHandler> inputHandlers;
    private ArrayList<NetworkLoungeHandler> loungeHandlers;
    protected Connection[] connections;
    private BrawlClientListener clientListener;

    public BrawlClientImpl() {
        client = new Client();
        client.start();
        clientListener = new BrawlClientListener(this);
        client.addListener(clientListener);
        discoveryHandlers = new ArrayList<NetworkDiscoveryHandler>();
        inputHandlers = new ArrayList<NetworkInputHandler>();
        loungeHandlers = new ArrayList<NetworkLoungeHandler>();
        connections=null;
        BrawlNetwork network = new BrawlNetwork(this);
    }

    @Override
    public void connectToServer(String hostIP) throws IOException {
        client.connect(Network.TIME_OUT, hostIP, Network.TCP_PORT, Network.UDP_PORT);
    }

    @Override
    public void disconnect() throws IOException{
        client.close();
        try {
            client.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void discoverServers() {

        Executor executor = Executors.newSingleThreadExecutor();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<InetAddress> addresses = client.discoverHosts(Network.UDP_PORT, Network.TIME_OUT);

                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        updateDiscoveryHandler(addresses);
                    }
                });
            }
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
        synchronized (connections){
            try {
                connections.wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        return connections;
    }


    @Override
    public void addNetworkDiscoveryHandler(NetworkDiscoveryHandler discoveryHandler) {
        discoveryHandlers.add(discoveryHandler);
    }

    @Override
    public void addNetworkInputHandler(NetworkInputHandler inputHandler) {
        inputHandlers.add(inputHandler);
    }

    @Override
    public void addNetworkLoungeHandler(NetworkLoungeHandler loungeHandler) {
        loungeHandlers.add(loungeHandler);
    }

    @Override
    public void removeNetworkDiscoveryHandler(NetworkDiscoveryHandler discoveryHandler) {
        discoveryHandlers.remove(discoveryHandler);
    }

    @Override
    public void removeNetworkInputHandler(NetworkInputHandler inputHandler) {
        inputHandlers.remove(inputHandler);
    }

    @Override
    public void removeNetworkLoungeHandler(NetworkLoungeHandler loungeHandler) {
        loungeHandlers.remove(loungeHandler);
    }

    private void updateDiscoveryHandler(List<InetAddress> addresses) {
        for (NetworkDiscoveryHandler discoveryHandler :
                discoveryHandlers) {
            discoveryHandler.receiveHosts(addresses);
        }
    }

    private void updateNetworkInputHandler() {

    }

    private void updateNetworkLoungeHandler() {

    }

    @Override
    public void sendTick() {
        //Not needed
    }

    @Override
    public void sendEntitySpawnMsg(Vector2 worldPosition, UnitType unitType, int teamID, long unitID) {
        EntitySpawnMessage spawnMessage = new EntitySpawnMessage(worldPosition,teamID,unitType, unitID);
        sendData(spawnMessage);
    }

    @Override
    public void sendEntityDeleteMsg(long entityID) {

    }

    @Override
    public void sendEntityMovingMessage(long unitID,Array<Vector2> wayPoints) {

    }

    @Override
    public BrawlConnector getBrawlConnector() {
        return clientListener;
    }

    @Override
    public void dispose() {
        try {
            disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
