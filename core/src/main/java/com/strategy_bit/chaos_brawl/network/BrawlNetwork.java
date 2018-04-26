package com.strategy_bit.chaos_brawl.network;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.Kryo;
import com.strategy_bit.chaos_brawl.network.messages.Request.EntityDeleteMessage;
import com.strategy_bit.chaos_brawl.network.messages.Request.EntityMovingMessage;
import com.strategy_bit.chaos_brawl.network.messages.Request.EntitySpawnMessage;
import com.strategy_bit.chaos_brawl.network.messages.Request.ResourceTickMessage;
import com.strategy_bit.chaos_brawl.network.messages.Response.NetworkMemberResponseMessage;
import com.strategy_bit.chaos_brawl.network.messages.Request.NetworkMembersRequestMessage;
import com.strategy_bit.chaos_brawl.types.UnitType;

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
        kryo.register(Vector2.class);
        kryo.register(UnitType.class);
        kryo.register(ResourceTickMessage.class);
        kryo.register(EntityDeleteMessage.class);
        kryo.register(Array.class);
    }
}
