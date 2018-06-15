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

    private static MyEngine instance;

    public static MyEngine getInstance() {
        if (instance == null) {
            throw new UnsupportedOperationException("Engine doesn't exist");
        }
        return instance;
    }

    public MyEngine(int entityPoolInitialSize, int entityPoolMaxSize, int componentPoolInitialSize, int componentPoolMaxSize) {
        super(entityPoolInitialSize,entityPoolMaxSize,componentPoolInitialSize,componentPoolMaxSize);
    }

    public static MyEngine createEngine() {
        instance=new MyEngine(0,0,25,1000);
        return instance;
    }

    public static void deleteEngine()
    {
        if(instance != null)
        {
            instance.dispose();
            instance = null;
        }
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
