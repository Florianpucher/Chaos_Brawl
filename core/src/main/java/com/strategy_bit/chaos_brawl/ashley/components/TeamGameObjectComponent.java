package com.strategy_bit.chaos_brawl.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class TeamGameObjectComponent implements Component,Pool.Poolable {

    private int teamId;
    private double hitPoints;
    private double maxHP;

    public TeamGameObjectComponent(double hitPoints, int teamId) {
        setHitPoints(hitPoints);
        setTeamId(teamId);
        maxHP = hitPoints;
    }

    public void setEverything(double hitPoints, int teamId) {
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

    public TeamGameObjectComponent() {
        teamId=0;
        hitPoints=0;
        maxHP=0;
    }

    @Override
    public void reset() {
        teamId=0;
        hitPoints=0;
        maxHP=0;
    }
}
