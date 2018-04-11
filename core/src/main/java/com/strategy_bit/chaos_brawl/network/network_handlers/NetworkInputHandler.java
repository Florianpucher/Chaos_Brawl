package com.strategy_bit.chaos_brawl.network.network_handlers;

import com.esotericsoftware.kryonet.Connection;
import com.strategy_bit.chaos_brawl.InputHandler;

/**
 * @author AIsopp
 * @version 1.0
 * @since 05.04.2018
 */
public interface NetworkInputHandler extends InputHandler {

    void sendNetworkMembers(Connection[] connections);
}
