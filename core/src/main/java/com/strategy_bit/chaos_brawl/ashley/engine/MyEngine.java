package com.strategy_bit.chaos_brawl.ashley.engine;


import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.strategy_bit.chaos_brawl.ashley.util.DisposeAble;

/**
 *
 *
 * @author AIsopp
 * @version 1.0
 * @since 15.03.2018
 */

public class MyEngine extends PooledEngine {

    public MyEngine()
    {
        super(0,0,25,1000);
    }

    public void dispose(){
        this.clearPools();
        ImmutableArray<EntitySystem> systems = getSystems();
        for (EntitySystem system :
                systems) {
            if(system instanceof DisposeAble){
                ((DisposeAble) system).dispose();
            }
        }
    }
}
