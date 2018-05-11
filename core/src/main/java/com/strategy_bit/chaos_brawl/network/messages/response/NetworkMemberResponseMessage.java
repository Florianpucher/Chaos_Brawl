package com.strategy_bit.chaos_brawl.network.messages.response;

import com.esotericsoftware.kryonet.Connection;
import com.strategy_bit.chaos_brawl.network.messages.Message;

/**
 * @author AIsopp
 * @version 1.0
 * @since 02.04.2018
 */
public class NetworkMemberResponseMessage implements Message {
    public String[] members;

    public NetworkMemberResponseMessage(String host, Connection[] members) {
        this.members=new String[members.length + 1];
        int i=1;
        this.members[0] = host;
        for (Connection connection:
             members) {
            this.members[i++]=connection.getRemoteAddressTCP().getHostName();
        }
    }

    public NetworkMemberResponseMessage() {
    }
}
