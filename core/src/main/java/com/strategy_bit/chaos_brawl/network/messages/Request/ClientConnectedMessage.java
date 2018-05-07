package com.strategy_bit.chaos_brawl.network.messages.Request;

import com.strategy_bit.chaos_brawl.network.messages.Message;

public class ClientConnectedMessage implements Message {
    public String name;
    public int id;

    public ClientConnectedMessage() {
    }

    public ClientConnectedMessage(String name, int id) {
        this.name=name;
        this.id=id;
    }
}
