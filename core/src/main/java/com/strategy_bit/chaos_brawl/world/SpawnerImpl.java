package com.strategy_bit.chaos_brawl.world;


import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.components.ResourceComponent;
import com.strategy_bit.chaos_brawl.ashley.entity.Base;
import com.strategy_bit.chaos_brawl.ashley.entity.Player;
import com.strategy_bit.chaos_brawl.ashley.entity.PlayerClone;
import com.strategy_bit.chaos_brawl.ashley.entity.Resource;
import com.strategy_bit.chaos_brawl.ashley.entity.Tower;
import com.strategy_bit.chaos_brawl.types.UnitType;

import static com.strategy_bit.chaos_brawl.types.UnitType.*;

/**
 * @author AIsopp
 * @version 1.0
 * @since 18.03.2018
 */
//TODO move every creation of an entity here
public class SpawnerImpl {
    World world;
    public SpawnerImpl(World world) {
        this.world=world;
    }

    public Entity createNewUnit(UnitType unitType, int teamID, Vector2 position) {
        Entity entity = null;
        double cost=Double.MIN_VALUE;

        switch (unitType) {
            case RANGED:
                entity = new PlayerClone(position, teamID);
                cost=PlayerClone.COST;
                break;
            case MELEE:
                entity = new Player(position, teamID);
                cost=Player.COST;
                break;
            case MAINBUILDING:
                entity = new Base(position, teamID);
                break;
            case TOWER:
                entity = new Tower(position, teamID);
                break;
        }
        if(cost!=Double.MIN_VALUE){
            Entity resource=world.resources.get((long)teamID);
            if(resource!=null) {
                ResourceComponent resourceComponent = resource.getComponent(ResourceComponent.class);
                if (!resourceComponent.add(-cost)) {
                    //not enough resoureces
                    return null;
                }
            }
        }
        return entity;
    }
}
