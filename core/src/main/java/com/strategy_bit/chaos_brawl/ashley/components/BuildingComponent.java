package com.strategy_bit.chaos_brawl.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class BuildingComponent implements Component,Pool.Poolable{
    private boolean changeWhenTargetIsDestroyed;

    public BuildingComponent(boolean changeWhenTargetIsDestroyed) {
        setChangeWhenTargetIsDestroyed(changeWhenTargetIsDestroyed);
    }

    public BuildingComponent() {
        changeWhenTargetIsDestroyed=false;
    }

    public boolean isChangeWhenTargetIsDestroyed() {
        return changeWhenTargetIsDestroyed;
    }

    public void setChangeWhenTargetIsDestroyed(boolean changeWhenTargetIsDestroyed) {
        this.changeWhenTargetIsDestroyed = changeWhenTargetIsDestroyed;
    }

    @Override
    public void reset() {
        changeWhenTargetIsDestroyed=false;
    }
}
