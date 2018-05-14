package com.strategy_bit.chaos_brawl.network.messages.request;

import com.strategy_bit.chaos_brawl.network.messages.Message;

/**
 * @author AIsopp
 * @version 1.0
 * @since 14.05.2018
 */
public class PlayerSelectedNewTargetMessage implements Message {

    public int playerTeamID;
    public int targetTeamID;

    public PlayerSelectedNewTargetMessage() {
    }

    public PlayerSelectedNewTargetMessage(int playerTeamID, int targetTeamID) {
        this.playerTeamID = playerTeamID;
        this.targetTeamID = targetTeamID;
    }
}
