package com.strategy_bit.chaos_brawl.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.strategy_bit.chaos_brawl.network.network_handlers.NetworkInputHandler;
import com.strategy_bit.chaos_brawl.world.World;

/**
 * @author AIsopp
 * @version 1.0
 * @since 01.04.2018
 */
public interface BrawlNetworkInterface {
    Kryo getKryo();
    Connection[] getNetworkMembers();
    void addNetworkInputHandler(NetworkInputHandler inputHandler);
    void removeNetworkInputHandler(NetworkInputHandler inputHandler);
    void setManager(World manager);
    World getManager();
}
