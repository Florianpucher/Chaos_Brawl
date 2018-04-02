package com.strategy_bit.chaos_brawl.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.strategy_bit.chaos_brawl.InputHandler;
import com.strategy_bit.chaos_brawl.config.Network;
import com.strategy_bit.chaos_brawl.network.messages.EntityMovingMessage;
import com.strategy_bit.chaos_brawl.network.messages.Message;

import java.io.IOException;

/**
 * @author AIsopp
 * @version 1.0
 * @since 01.04.2018
 */
public class BrawlClientImpl implements BrawlClient {

    private Client client;
    private InputHandler inputHandler;

    public BrawlClientImpl(String hostIP, InputHandler inputHandler) throws IOException{
        this.inputHandler = inputHandler;
        client = new Client();
        client.start();
        client.connect(Network.TIME_OUT, hostIP, Network.TCP_PORT);
        client.addListener(listener);
        BrawlNetwork network = new BrawlNetwork(this);
    }

    @Override
    public void sendData(Message msg) {

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
        return client.getKryo();
    }
}
