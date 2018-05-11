package com.strategy_bit.chaos_brawl.network;

import com.strategy_bit.chaos_brawl.network.network_handlers.NetworkConnectionHandler;
import com.strategy_bit.chaos_brawl.world.MultiplayerInputHandler;

/**
 * @author AIsopp
 * @version 1.0
 * @since 26.04.2018
 */
public interface BrawlConnector {
    public MultiplayerInputHandler getInputHandler();
    void setMultiplayerInputHandler(MultiplayerInputHandler multiplayerInputHandler);
    void setNetworkConnectionHandler(NetworkConnectionHandler connectionHandler);
}
