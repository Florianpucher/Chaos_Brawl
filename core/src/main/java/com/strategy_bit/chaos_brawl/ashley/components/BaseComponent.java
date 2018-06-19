package com.strategy_bit.chaos_brawl.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class BaseComponent implements Component,Pool.Poolable {
    private boolean destroyed;

    public BaseComponent() {
        destroyed=false;
    }

    @Override
    public void reset() {
        destroyed=false;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }
}
