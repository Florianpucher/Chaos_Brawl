package com.strategy_bit.chaos_brawl.ashley.components;

import com.badlogic.ashley.core.Component;

/**
 * @author AIsopp
 * @version 1.0
 * @since 24.03.2018
 */

public class BulletComponent implements Component{
    private boolean deleteWhenTargetIsReached;

    public BulletComponent(boolean deleteWhenTargetIsReached) {
        setDeleteWhenTargetIsReached(deleteWhenTargetIsReached);
    }

    public boolean isDeleteWhenTargetIsReached() {
        return deleteWhenTargetIsReached;
    }

    public void setDeleteWhenTargetIsReached(boolean deleteWhenTargetIsReached) {
        this.deleteWhenTargetIsReached = deleteWhenTargetIsReached;
    }
}
