package com.strategy_bit.chaos_brawl.network;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.strategy_bit.chaos_brawl.network.messages.NetworkMembersSendMessage;
import com.strategy_bit.chaos_brawl.network.network_handlers.NetworkDiscoveryHandler;
import com.strategy_bit.chaos_brawl.network.network_handlers.NetworkInputHandler;
import com.strategy_bit.chaos_brawl.config.Network;
import com.strategy_bit.chaos_brawl.network.messages.EntityMovingMessage;
import com.strategy_bit.chaos_brawl.network.messages.Message;
import com.strategy_bit.chaos_brawl.network.messages.NetworkMemberReplyMessage;
import com.strategy_bit.chaos_brawl.network.network_handlers.NetworkLoungeHandler;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author AIsopp
 * @version 1.0
 * @since 01.04.2018
 */
public class BrawlClientImpl implements BrawlClient {

    private Client client;
    private ArrayList<NetworkDiscoveryHandler> discoveryHandlers;
    private ArrayList<NetworkInputHandler> inputHandlers;
    private ArrayList<NetworkLoungeHandler> loungeHandlers;

    public BrawlClientImpl() {
        client = new Client();
        client.start();
        client.addListener(listener);
        discoveryHandlers = new ArrayList<NetworkDiscoveryHandler>();
        inputHandlers = new ArrayList<NetworkInputHandler>();
        loungeHandlers = new ArrayList<NetworkLoungeHandler>();
        BrawlNetwork network = new BrawlNetwork(this);
    }

    @Override
    public void connectToServer(String hostIP) throws IOException {
        client.connect(Network.TIME_OUT, hostIP, Network.TCP_PORT, Network.UDP_PORT);
    }

    @Override
    public void disconnect() {
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


    private Listener listener = new Listener() {
        public void received(Connection connection, Object object) {
            if (object instanceof EntityMovingMessage) {
                EntityMovingMessage movingMessage = (EntityMovingMessage) object;
                //inputHandler.sendTouchInput(movingMessage.screenCoordinates, movingMessage.entityID);
            } else if (object instanceof NetworkMemberReplyMessage) {
                NetworkMemberReplyMessage replyMessage = (NetworkMemberReplyMessage) object;
            }
        }
    };

    @Override
    public Kryo getKryo() {
        return client.getKryo();
    }

    @Override
    public Connection[] getNetworkMembers() {
        sendData(new NetworkMembersSendMessage());
        return null;
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

}
