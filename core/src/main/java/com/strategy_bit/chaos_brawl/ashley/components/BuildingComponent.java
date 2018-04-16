package com.strategy_bit.chaos_brawl.ashley.components;

import com.badlogic.ashley.core.Component;

public class BuildingComponent implements Component{
    private boolean changeWhenTargetIsDestroyed;

    public BuildingComponent(boolean changeWhenTargetIsDestroyed) {
        setChangeWhenTargetIsDestroyed(changeWhenTargetIsDestroyed);
    }

    public boolean isChangeWhenTargetIsDestroyed() {
        return changeWhenTargetIsDestroyed;
    }

    public void setChangeWhenTargetIsDestroyed(boolean changeWhenTargetIsDestroyed) {
        this.changeWhenTargetIsDestroyed = changeWhenTargetIsDestroyed;
    }
}
