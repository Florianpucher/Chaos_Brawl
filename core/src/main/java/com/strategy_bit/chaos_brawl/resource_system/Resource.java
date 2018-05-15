package com.strategy_bit.chaos_brawl.resource_system;

public class Resource {
    private double resourceAmount;
    private static final double MAX_RESOURCES=100.0;
    private String name;

    //for Multiplayer to sync resources between server and clients
    private int teamId;

    public Resource(double resourceAmount, int teamId) {
        this.resourceAmount = resourceAmount;
        this.teamId = teamId;
    }
    public Resource(double resourceAmount) {
        this.resourceAmount = resourceAmount;
        this.teamId = 0;
    }
    public Resource() {
        this.resourceAmount = 0;
        this.teamId = 0;
    }
    public Resource(int teamId) {
        this.resourceAmount = 0;
        this.teamId = teamId;
    }

    public float getResourceAmount() {
        return (float)resourceAmount;
    }

    public void setResourceAmount(double resources) {
        if(resources>MAX_RESOURCES){
            this.resourceAmount=MAX_RESOURCES;
        }else {
            this.resourceAmount = resources;
        }
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
    public synchronized boolean  add(double a){
        if(a<0&&-a>getResourceAmount()){
            return false;
        }
        else {
            setResourceAmount(getResourceAmount() + a);
            return true;
        }
    }
    public double percentageFull(){
        return getResourceAmount()/MAX_RESOURCES;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
