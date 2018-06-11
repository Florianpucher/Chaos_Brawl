package com.strategy_bit.chaos_brawl.network.messages.request;

import com.strategy_bit.chaos_brawl.network.messages.Message;

public class ClientConnectedMessage implements Message {
    public String name;
    public int id;

    public ClientConnectedMessage() {
        // empty constructor is needed for kryo
    }

    public ClientConnectedMessage(String name, int id) {
        this.name=name;
        this.id=id;
    }
}
