package com.strategy_bit.chaos_brawl.network.messages.request;

import com.strategy_bit.chaos_brawl.network.messages.Message;

/**
 * @author AIsopp
 * @version 1.0
 * @since 26.04.2018
 */
public class InitializeGameMessage implements Message {
    public int[] controllers;
    public int map;
    public InitializeGameMessage(int[] controllers, int map) {
        this.controllers = controllers;
        this.map = map;
    }

    public InitializeGameMessage() {
        // empty constructor is needed for kryo

    }
}
