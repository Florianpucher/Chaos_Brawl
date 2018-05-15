package com.strategy_bit.chaos_brawl.network.messages.request;

import com.strategy_bit.chaos_brawl.network.messages.Message;

/**
 * @author AIsopp
 * @version 1.0
 * @since 26.04.2018
 */
public class EntityDeleteMessage implements Message{
    public long unitID;

    public EntityDeleteMessage(long unitID) {
        this.unitID = unitID;
    }

    public EntityDeleteMessage(){
        // empty constructor is needed for kryo
    }
}
