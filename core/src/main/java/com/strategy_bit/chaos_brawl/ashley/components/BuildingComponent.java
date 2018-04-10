package com.strategy_bit.chaos_brawl.ashley.components;

import com.badlogic.ashley.core.Component;

public class BuildingComponent implements Component{
    private boolean deleteWhenTargetIsDestroyed;

    public BuildingComponent(boolean deleteWhenTargetIsDestroyed) {
        setDeleteWhenTargetIsDestroyed(deleteWhenTargetIsDestroyed);
    }

    public boolean isDeleteWhenTargetIsReached() {
        return deleteWhenTargetIsDestroyed;
    }

    public void setDeleteWhenTargetIsDestroyed(boolean deleteWhenTargetIsDestroyed) {
        this.deleteWhenTargetIsDestroyed = deleteWhenTargetIsDestroyed;
    }
}
