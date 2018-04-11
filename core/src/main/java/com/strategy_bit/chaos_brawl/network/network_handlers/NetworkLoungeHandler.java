package com.strategy_bit.chaos_brawl.network.network_handlers;

import com.esotericsoftware.kryonet.Connection;

/**
 * @author AIsopp
 * @version 1.0
 * @since 05.04.2018
 */
public interface NetworkLoungeHandler {
    void sendNetworkMembers(Connection[] connections);
}
