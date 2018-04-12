package com.strategy_bit.chaos_brawl.controller;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.strategy_bit.chaos_brawl.network.Client.BrawlClient;
import com.strategy_bit.chaos_brawl.network.messages.Message;
import com.strategy_bit.chaos_brawl.network.network_handlers.NetworkDiscoveryHandler;
import com.strategy_bit.chaos_brawl.network.network_handlers.NetworkInputHandler;
import com.strategy_bit.chaos_brawl.network.network_handlers.NetworkLoungeHandler;
import com.strategy_bit.chaos_brawl.world.World;

import java.io.IOException;

/**
 * @author AIsopp
 * @version 1.0
 * @since 30.03.2018
 */
public class ClientController extends NetworkController implements BrawlClient {


    @Override
    public void sendData(Message msg) {

    }

    @Override
    public Kryo getKryo() {
        return null;
    }

    @Override
    public void disconnect() throws IOException {

    }

    @Override
    public void discoverServers() {

    }

    @Override
    public void connectToServer(String hostIP) throws IOException {

    }

    @Override
    public void addNetworkDiscoveryHandler(NetworkDiscoveryHandler discoveryHandler) {

    }

    @Override
    public void addNetworkLoungeHandler(NetworkLoungeHandler loungeHandler) {

    }

    @Override
    public void removeNetworkDiscoveryHandler(NetworkDiscoveryHandler discoveryHandler) {

    }

    @Override
    public void removeNetworkLoungeHandler(NetworkLoungeHandler loungeHandler) {

    }

    @Override
    public Connection[] getNetworkMembers() {
        return new Connection[0];
    }

    @Override
    public void addNetworkInputHandler(NetworkInputHandler inputHandler) {

    }

    @Override
    public void removeNetworkInputHandler(NetworkInputHandler inputHandler) {

    }

    @Override
    public void setManager(World manager) {
        
    }

    @Override
    public World getManager() {
        return null;
    }
}
