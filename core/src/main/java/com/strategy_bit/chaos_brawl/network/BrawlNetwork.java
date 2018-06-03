package com.strategy_bit.chaos_brawl.network;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.Kryo;
import com.strategy_bit.chaos_brawl.network.messages.request.ClientConnectedMessage;
import com.strategy_bit.chaos_brawl.network.messages.request.ClientDisconnectedMessage;
import com.strategy_bit.chaos_brawl.network.messages.request.EntityDeleteMessage;
import com.strategy_bit.chaos_brawl.network.messages.request.EntityMovingMessage;
import com.strategy_bit.chaos_brawl.network.messages.request.EntitySpawnMessage;
import com.strategy_bit.chaos_brawl.network.messages.request.InitializeGameMessage;
import com.strategy_bit.chaos_brawl.network.messages.request.NetworkMembersRequestMessage;
import com.strategy_bit.chaos_brawl.network.messages.request.PlayerSelectedNewTargetMessage;
import com.strategy_bit.chaos_brawl.network.messages.request.ResourceTickMessage;
import com.strategy_bit.chaos_brawl.network.messages.response.NetworkMemberResponseMessage;

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


    private BrawlNetwork() {
        // Config class
    }

    /**
     *
     * registers all messages
     * @param brawlNetworkInterface network interface that contains Kryonet implementation
     */
    public static void initializeKryo(BrawlNetworkInterface brawlNetworkInterface){
        Kryo kryo = brawlNetworkInterface.getKryo();
        kryo.register(EntityMovingMessage.class);
        kryo.register(NetworkMemberResponseMessage.class);
        kryo.register(NetworkMembersRequestMessage.class);
        kryo.register(EntitySpawnMessage.class);
        kryo.register(Vector2.class);
        kryo.register(int.class);
        kryo.register(ResourceTickMessage.class);
        kryo.register(EntityDeleteMessage.class);
        kryo.register(Array.class);
        kryo.register(InitializeGameMessage.class);
        kryo.register(int[].class);
        kryo.register(Object[].class);
        kryo.register(Vector2[].class);
        kryo.register(ClientConnectedMessage.class);
        kryo.register(String[].class);
        kryo.register(ClientDisconnectedMessage.class);
        kryo.register(PlayerSelectedNewTargetMessage.class);
    }
}
