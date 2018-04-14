package com.strategy_bit.chaos_brawl.network;

import com.esotericsoftware.kryo.Kryo;
import com.strategy_bit.chaos_brawl.network.messages.Request.EntityMovingMessage;
import com.strategy_bit.chaos_brawl.network.messages.Request.EntitySpawnMessage;
import com.strategy_bit.chaos_brawl.network.messages.Response.NetworkMemberResponseMessage;
import com.strategy_bit.chaos_brawl.network.messages.Request.NetworkMembersRequestMessage;

/**
 * Registers all Messages
 * <br>
 * Needs to be initialized after Server or Client
 *
 *
 * @author AIsopp
 * @version 1.0
 * @since 01.04.2018
 */
public class BrawlNetwork {

    /**
     *
     * registers all messages
     * @param brawlNetworkInterface network interface that contains Kryonet implementation
     */
    public BrawlNetwork(BrawlNetworkInterface brawlNetworkInterface) {
        Kryo kryo = brawlNetworkInterface.getKryo();
        kryo.register(EntityMovingMessage.class);
        kryo.register(NetworkMemberResponseMessage.class);
        kryo.register(NetworkMembersRequestMessage.class);
        kryo.register(EntitySpawnMessage.class);

    }
}
