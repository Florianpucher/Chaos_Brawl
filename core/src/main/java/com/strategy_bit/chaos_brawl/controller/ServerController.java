package com.strategy_bit.chaos_brawl.controller;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.strategy_bit.chaos_brawl.network.Server.BrawlServer;
import com.strategy_bit.chaos_brawl.network.messages.Message;
import com.strategy_bit.chaos_brawl.network.network_handlers.NetworkInputHandler;
import com.strategy_bit.chaos_brawl.world.World;

import java.io.IOException;

/**
 * @author AIsopp
 * @version 1.0
 * @since 30.03.2018
 */
public class ServerController extends NetworkController implements BrawlServer {


    public ServerController() {
        Server server = new Server();
    }


    @Override
    public void sendData(Message msg) {

    }

    @Override
    public Kryo getKryo() {
        return null;
    }

    @Override
    public void startServer() throws IOException {

    }

    @Override
    public void closeServer() {

    }

    @Override
    public boolean isServerIsRunning() {
        return false;
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
