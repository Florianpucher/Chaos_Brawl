package com.strategy_bit.chaos_brawl.ashley.engine;


import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.strategy_bit.chaos_brawl.ashley.util.DisposeAble;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 *
 * @author AIsopp
 * @version 1.0
 * @since 15.03.2018
 */

public class MyEngine extends Engine {

    protected HashMap<Long, Entity> units;

    public MyEngine(HashMap<Long, Entity> units) {
        this.units = units;
    }

    public void dispose(){

        ImmutableArray<EntitySystem> systems = getSystems();
        for (EntitySystem system :
                systems) {
            if(system instanceof DisposeAble){
                ((DisposeAble) system).dispose();
            }
        }
    }

    @Override
    public void removeEntity(Entity entity) {
        super.removeEntity(entity);
        Iterator<Map.Entry<Long,Entity>> iterator = units.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<Long,Entity> entry = iterator.next();
            if(entry.getValue() == entity){
                iterator.remove();
                break;
            }
        }
    }
}
