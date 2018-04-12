package com.strategy_bit.chaos_brawl.network.messages.Response;

import com.esotericsoftware.kryonet.Connection;
import com.strategy_bit.chaos_brawl.network.messages.Message;

/**
 * @author AIsopp
 * @version 1.0
 * @since 02.04.2018
 */
public class NetworkMemberResponseMessage implements Message {
    public Connection[] members;

    public NetworkMemberResponseMessage(Connection[] members) {
        this.members = members;
    }
}
