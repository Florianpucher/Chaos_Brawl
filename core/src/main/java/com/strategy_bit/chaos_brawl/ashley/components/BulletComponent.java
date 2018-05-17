package com.strategy_bit.chaos_brawl.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * @author AIsopp
 * @version 1.0
 * @since 24.03.2018
 */

public class BulletComponent implements Component,Pool.Poolable{
    private boolean deleteWhenTargetIsReached;
    private long targetId;
    private float damage;

    public BulletComponent(boolean deleteWhenTargetIsReached, long targetId,float damage) {
        setDeleteWhenTargetIsReached(deleteWhenTargetIsReached);
        setTargetId(targetId);
        setDamage(damage);
    }

    public void setDeleteWhenTargetIsReachedAndTargetIdAndDamage(boolean deleteWhenTargetIsReached, long targetId,float damage) {
        setDeleteWhenTargetIsReached(deleteWhenTargetIsReached);
        setTargetId(targetId);
        setDamage(damage);
    }

    public boolean isDeleteWhenTargetIsReached() {
        return deleteWhenTargetIsReached;
    }

    public void setDeleteWhenTargetIsReached(boolean deleteWhenTargetIsReached) {
        this.deleteWhenTargetIsReached = deleteWhenTargetIsReached;
    }

    public long getTargetId() {
        return targetId;
    }

    public void setTargetId(long targetId) {
        this.targetId = targetId;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public BulletComponent() {
        deleteWhenTargetIsReached=false;
        targetId=0;
        damage=0;
    }

    @Override
    public void reset() {
        deleteWhenTargetIsReached=false;
        targetId=0;
        damage=0;
    }
}

