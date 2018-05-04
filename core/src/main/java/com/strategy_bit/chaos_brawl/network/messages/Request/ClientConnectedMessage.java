package com.strategy_bit.chaos_brawl.network.messages.Request;

import com.strategy_bit.chaos_brawl.network.messages.Message;

public class ClientConnectedMessage implements Message {
    public String name;

    public ClientConnectedMessage() {
    }

    public ClientConnectedMessage(String name) {
        this.name=name;
    }
}
