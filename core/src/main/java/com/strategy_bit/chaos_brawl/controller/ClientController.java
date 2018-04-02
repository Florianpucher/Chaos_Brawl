package com.strategy_bit.chaos_brawl.controller;

import com.esotericsoftware.kryo.Kryo;
import com.strategy_bit.chaos_brawl.network.BrawlClient;
import com.strategy_bit.chaos_brawl.network.messages.Message;

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
}
