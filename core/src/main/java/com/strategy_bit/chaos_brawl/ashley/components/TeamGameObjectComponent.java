package com.strategy_bit.chaos_brawl.ashley.components;

import com.badlogic.ashley.core.Component;

public class TeamGameObjectComponent implements Component {

    private int teamId;
    private double hitPoints;
    private double maxHP;

    public TeamGameObjectComponent(double hitPoints, int teamId) {
        setHitPoints(hitPoints);
        setTeamId(teamId);
        maxHP = hitPoints;
    }

    public double getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(double hitPoints) {
        this.hitPoints = hitPoints;
    }

    public double getMaxHP() {
        return maxHP;

    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

}
