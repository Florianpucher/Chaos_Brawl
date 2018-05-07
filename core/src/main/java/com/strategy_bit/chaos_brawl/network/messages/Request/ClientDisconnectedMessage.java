package com.strategy_bit.chaos_brawl.network.messages.request;

import com.strategy_bit.chaos_brawl.network.messages.Message;

public class ClientDisconnectedMessage implements Message {
    public int id;
    public String name;
    public ClientDisconnectedMessage() {
    }

    public ClientDisconnectedMessage(String name,int id) {
        this.id=id;
        this.name=name;
    }
}
