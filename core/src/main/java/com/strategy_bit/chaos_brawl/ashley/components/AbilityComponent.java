package com.strategy_bit.chaos_brawl.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.strategy_bit.chaos_brawl.ashley.engine.MyEngine;
import com.strategy_bit.chaos_brawl.types.AbilityType;
import com.strategy_bit.chaos_brawl.util.Boundary;


public class AbilityComponent implements Component,Pool.Poolable{
    private int abilityId;
    private int teamId;

    public AbilityComponent( ) {
        this.abilityId=0;
        this.teamId=-1;
    }

    public int getAbilityId() {
        return abilityId;
    }

    public void setAbilityId(int abilityId) {
        this.abilityId = abilityId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    @Override
    public void reset() {
        this.abilityId=0;
        this.teamId=-1;
    }
}
