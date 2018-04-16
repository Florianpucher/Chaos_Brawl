package com.strategy_bit.chaos_brawl.network.network_handlers;

import java.net.InetAddress;
import java.util.List;

/**
 * @author AIsopp
 * @version 1.0
 * @since 05.04.2018
 */
public interface NetworkDiscoveryHandler {

    void receiveHosts(List<InetAddress> hostIPs);

}
