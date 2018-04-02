package com.strategy_bit.chaos_brawl.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.strategy_bit.chaos_brawl.InputHandler;
import com.strategy_bit.chaos_brawl.config.Network;
import com.strategy_bit.chaos_brawl.network.messages.EntityMovingMessage;
import com.strategy_bit.chaos_brawl.network.messages.Message;

import java.io.IOException;

/**
 * @author AIsopp
 * @version 1.0
 * @since 30.03.2018
 */
public class BrawlServerImpl implements BrawlServer {

    private Server server;
    private InputHandler inputHandler;

    public BrawlServerImpl(InputHandler inputHandler) throws IOException {
        this.inputHandler = inputHandler;
        server = new Server();
        server.start();
        server.bind(Network.TCP_PORT);
        server.addListener(listener);
        BrawlNetwork network = new BrawlNetwork(this);
    }

    @Override
    public void sendData(Message msg) {
        server.sendToAllTCP(msg);

    }


    private Listener listener = new Listener() {
        public void received(Connection connection, Object object) {
            if (object instanceof EntityMovingMessage) {
                EntityMovingMessage movingMessage = (EntityMovingMessage) object;
                inputHandler.sendTouchInput(movingMessage.screenCoordinates, movingMessage.entityID);
            }
        }
    };

    @Override
    public Kryo getKryo() {
        return server.getKryo();
    }
}
