package com.strategy_bit.chaos_brawl.ashley.engine;

import com.badlogic.gdx.utils.Pool;
import com.strategy_bit.chaos_brawl.ashley.entity.Projectile;


public class BulletPool extends Pool<Projectile>{

    // constructor with initial object count and max object count
    // max is the maximum of object held in the pool and not the
    // maximum amount of objects that can be created by the pool
    public BulletPool(int init, int max){
        super(init,max);
    }

    // make pool with default 16 initial objects and no max
    public BulletPool(){
        super();
    }

    // method to create a single object
    @Override
    protected Projectile newObject() {
        return new Projectile();
    }

}