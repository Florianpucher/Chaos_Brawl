package com.strategy_bit.chaos_brawl.ashley.components;

import com.badlogic.ashley.core.Component;

public class ResourceComponent implements Component {
    private double resources;
    private static final double MAX_RESOURCES=100.0;

    //for Multiplayer to sync resources between server and clients
    private int teamId;

    public ResourceComponent(double resources, int teamId) {
        this.resources = resources;
        this.teamId = teamId;
    }
    public ResourceComponent(double resources) {
        this.resources = resources;
        this.teamId = 0;
    }
    public ResourceComponent() {
        this.resources = 0;
        this.teamId = 0;
    }
    public ResourceComponent(int teamId) {
        this.resources = 0;
        this.teamId = teamId;
    }

    public double getResources() {
        return resources;
    }

    public void setResources(double resources) {
        if(resources>MAX_RESOURCES){
            this.resources=MAX_RESOURCES;
        }else {
            this.resources = resources;
        }
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
    public void add(double a){
        setResources(getResources()+a);
    }
    public double percentageFull(){
        return getResources()/MAX_RESOURCES;
    }
}
