package com.strategy_bit.chaos_brawl.ashley.components;

import com.badlogic.ashley.core.Component;

public class UpgradeComponent implements Component {

    private int unitID;

    public int getTowerID() {
        return towerID;
    }

    public void setTowerID(int towerID) {
        this.towerID = towerID + 1;
    }

    private int towerID;

    public int getUnitID() {
        return unitID;
    }

    public void setUnitID(int unitID) {
        this.unitID = unitID + 3;
    }


    public UpgradeComponent() {
        // leave this empty
    }
}
