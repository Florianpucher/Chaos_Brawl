package com.strategy_bit.chaos_brawl.network.messages.request;

import com.strategy_bit.chaos_brawl.network.messages.Message;

/**
 * @author AIsopp
 * @version 1.0
 * @since 26.04.2018
 */
public class ResourceTickMessage implements Message {
    public float deltaTime;

    public ResourceTickMessage(float deltaTime) {
        this.deltaTime = deltaTime;
    }

    public ResourceTickMessage() {
    }
}
