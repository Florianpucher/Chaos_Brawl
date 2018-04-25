package com.strategy_bit.chaos_brawl.resource_system;

public class ResourceGold extends Resource {
    public ResourceGold(double resourceAmount, int teamId) {
        super(resourceAmount, teamId);
        setName("Gold");
    }

    public ResourceGold(double resourceAmount) {
        super(resourceAmount);
        setName("Gold");
    }

    public ResourceGold() {
        setName("Gold");
    }

    public ResourceGold(int teamId) {
        super(teamId);
        setName("Gold");
    }
}
