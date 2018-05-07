package com.strategy_bit.chaos_brawl.network.Server;

import com.badlogic.gdx.net.ServerSocket;
import com.strategy_bit.chaos_brawl.network.BrawlNetworkInterface;
import com.strategy_bit.chaos_brawl.network.messages.Message;

import java.io.IOException;

/**
 * @author AIsopp
 * @version 1.0
 * @since 29.03.2018
 */
public interface BrawlServer extends BrawlNetworkInterface {





    void sendGameInitializingMessage(int[] players);
    void sendData(Message msg);
    void startServer() throws IOException;
    void closeServer();
    boolean isServerIsRunning();
    void closeConnectionTo(int id);
}
