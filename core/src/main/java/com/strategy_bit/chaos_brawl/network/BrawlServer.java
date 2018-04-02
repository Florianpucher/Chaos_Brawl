package com.strategy_bit.chaos_brawl.network;

import com.badlogic.gdx.net.ServerSocket;
import com.strategy_bit.chaos_brawl.network.messages.Message;

/**
 * @author AIsopp
 * @version 1.0
 * @since 29.03.2018
 */
public interface BrawlServer extends  BrawlNetworkInterface{





    void sendData(Message msg);
}
