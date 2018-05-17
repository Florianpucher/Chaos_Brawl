package com.strategy_bit.chaos_brawl.ashley.engine;


import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.strategy_bit.chaos_brawl.ashley.entity.Projectile;
import com.strategy_bit.chaos_brawl.ashley.util.DisposeAble;
import com.strategy_bit.chaos_brawl.world.MultiplayerInputHandler;

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

public class MyEngine extends PooledEngine {

    private BulletPool bulletPool;
    private static MyEngine instance;

    public static MyEngine getInstance() {
        if (instance == null) {
            throw new UnsupportedOperationException("Engine doesn't exist");
        }
        return instance;
    }

    protected HashMap<Long, Entity> units;

    public MyEngine() {
        super();
        bulletPool=new BulletPool(50,250);
    }

    //Only for multipPlayerGames
    protected MultiplayerInputHandler inputHandler;

    public MyEngine(int entityPoolInitialSize, int entityPoolMaxSize, int componentPoolInitialSize, int componentPoolMaxSize) {
        super(entityPoolInitialSize,entityPoolMaxSize,componentPoolInitialSize,componentPoolMaxSize);
        bulletPool=new BulletPool(50,250);
    }

    public static MyEngine createEngine(HashMap<Long, Entity> units) {
        instance=new MyEngine(25,100,25,100);
        instance.units = units;
        return instance;
    }

    public Projectile createProjectile(){
        return bulletPool.obtain();
    }

    @Override
    public Entity createEntity() {
        return super.createEntity();
    }

    public void dispose(){
        this.bulletPool.clear();
        this.clearPools();
        ImmutableArray<EntitySystem> systems = getSystems();
        for (EntitySystem system :
                systems) {
            if(system instanceof DisposeAble){
                ((DisposeAble) system).dispose();
            }
        }
    }

    public void setInputHandler(MultiplayerInputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    @Override
    public void removeEntity(Entity entity) {
        super.removeEntity(entity);
        Iterator<Map.Entry<Long,Entity>> iterator = units.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<Long,Entity> entry = iterator.next();
            if(entry.getValue() == entity){
                if(inputHandler != null){
                    inputHandler.deleteUnitLocal(entry.getKey());
                }
                iterator.remove();
                break;
            }
        }
    }
}
