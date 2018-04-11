package com.strategy_bit.chaos_brawl.network.messages;

import com.esotericsoftware.kryonet.Connection;

/**
 * @author AIsopp
 * @version 1.0
 * @since 02.04.2018
 */
public class NetworkMemberReplyMessage implements Message {
    public Connection[] members;
}
