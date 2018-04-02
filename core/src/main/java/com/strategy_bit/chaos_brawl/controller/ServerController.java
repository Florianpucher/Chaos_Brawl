package com.strategy_bit.chaos_brawl.controller;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import com.strategy_bit.chaos_brawl.network.BrawlServer;
import com.strategy_bit.chaos_brawl.network.messages.Message;

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
}
