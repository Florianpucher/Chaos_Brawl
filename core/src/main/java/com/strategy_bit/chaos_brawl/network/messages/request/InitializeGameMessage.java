package com.strategy_bit.chaos_brawl.network.messages.request;

import com.strategy_bit.chaos_brawl.network.messages.Message;

/**
 * @author AIsopp
 * @version 1.0
 * @since 26.04.2018
 */
public class InitializeGameMessage implements Message {
    public int[] controllers;

    public InitializeGameMessage(int[] controllers) {
        this.controllers = controllers;
    }

    public InitializeGameMessage() {
        // empty constructor is needed for kryo

    }
}
