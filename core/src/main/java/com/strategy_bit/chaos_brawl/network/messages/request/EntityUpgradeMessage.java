package com.strategy_bit.chaos_brawl.network.messages.request;
import com.strategy_bit.chaos_brawl.network.messages.Message;

public class EntityUpgradeMessage implements Message {
    public int teamID;
    public int upgradeID;

    public EntityUpgradeMessage(int teamID, int upgradeID) {
        this.teamID = teamID;
        this.upgradeID = upgradeID;
    }

    public EntityUpgradeMessage(){
        // empty constructor is needed for kryo
    }
}
