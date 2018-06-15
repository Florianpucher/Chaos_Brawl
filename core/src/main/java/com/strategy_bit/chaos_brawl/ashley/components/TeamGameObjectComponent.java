package com.strategy_bit.chaos_brawl.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class TeamGameObjectComponent implements Component,Pool.Poolable {

    private int unitId;
    private int teamId;
    private float hitPoints;
    private float maxHP;
    private int unitType;

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public TeamGameObjectComponent(float hitPoints, int teamId) {
        setHitPoints(hitPoints);
        setTeamId(teamId);
        maxHP = hitPoints;
    }

    public void setEverything(float hitPoints, int teamId) {
        setHitPoints(hitPoints);
        setTeamId(teamId);
        maxHP = hitPoints;
    }

    public float getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(float hitPoints) {
        this.hitPoints = hitPoints;
    }

    public float getMaxHP() {
        return maxHP;

    }

    public int getUnitType() {
        return unitType;
    }

    public void setUnitType (int unitType) {
        this.unitType = unitType;
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
