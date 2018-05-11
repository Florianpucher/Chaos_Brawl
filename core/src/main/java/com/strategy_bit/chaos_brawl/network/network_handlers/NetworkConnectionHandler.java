package com.strategy_bit.chaos_brawl.network.network_handlers;

/**
 * @author AIsopp
 * @version 1.0
 * @since 11.05.2018
 */
public interface NetworkConnectionHandler {
    void connected();
    void disconnected();
    void anotherClientConnected(String clientName, int id);
    void anotherClientDisconnected(int id);
}
