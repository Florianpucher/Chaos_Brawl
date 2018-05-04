package com.strategy_bit.chaos_brawl.network.client;

import com.strategy_bit.chaos_brawl.network.BrawlNetworkInterface;
import com.strategy_bit.chaos_brawl.network.messages.Message;
import com.strategy_bit.chaos_brawl.network.network_handlers.NetworkDiscoveryHandler;
import com.strategy_bit.chaos_brawl.network.network_handlers.NetworkLoungeHandler;

import java.io.IOException;

/**
 * @author AIsopp
 * @version 1.0
 * @since 29.03.2018
 */
public interface BrawlClient extends BrawlNetworkInterface {



    void sendData(Message msg);
    void discoverServers();
    void connectToServer(String hostIP) throws IOException;
    void disconnect() throws IOException;
    void addNetworkDiscoveryHandler(NetworkDiscoveryHandler discoveryHandler);
    void addNetworkLoungeHandler(NetworkLoungeHandler loungeHandler);
    void removeNetworkDiscoveryHandler(NetworkDiscoveryHandler discoveryHandler);
    void removeNetworkLoungeHandler(NetworkLoungeHandler loungeHandler);
}
