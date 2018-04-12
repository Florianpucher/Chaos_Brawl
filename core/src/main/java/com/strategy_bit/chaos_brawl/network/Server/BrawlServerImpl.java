package com.strategy_bit.chaos_brawl.network.Server;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.strategy_bit.chaos_brawl.InputHandler;
import com.strategy_bit.chaos_brawl.config.Network;
import com.strategy_bit.chaos_brawl.network.BrawlNetwork;
import com.strategy_bit.chaos_brawl.network.messages.Request.EntityMovingMessage;
import com.strategy_bit.chaos_brawl.network.messages.Message;
import com.strategy_bit.chaos_brawl.network.network_handlers.NetworkInputHandler;
import com.strategy_bit.chaos_brawl.world.World;

import java.io.IOException;

/**
 * @author AIsopp
 * @version 1.0
 * @since 30.03.2018
 */
public class BrawlServerImpl implements BrawlServer {

    private World manager;
    private Server server;
    private InputHandler inputHandler;
    private boolean serverIsRunning;

    public BrawlServerImpl(){
        System.out.println("INIT");
        server = new Server();
        server.addListener(new BrawlServerListener(this));
        serverIsRunning = false;
        BrawlNetwork network = new BrawlNetwork(this);
    }

    @Override
    public void sendData(Message msg) {
        server.sendToAllTCP(msg);

    }

    public void sendDataToAllExcept(Connection connection, Message msg) {
        server.sendToAllExceptTCP(connection.getID(),msg);

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
        server.close();
        serverIsRunning=false;
        try {
            server.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void moveEntity(Vector2 screenCoordinates, long entityID){
        inputHandler.sendTouchInput(screenCoordinates, entityID);
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
    public void addNetworkInputHandler(NetworkInputHandler inputHandler) {

    }

    @Override
    public void removeNetworkInputHandler(NetworkInputHandler inputHandler) {

    }

    public void spawnEntity(Entity entity) {

    }

    @Override
    public World getManager() {
        return manager;
    }

    @Override
    public void setManager(World manager) {
        this.manager = manager;
    }
}
