package com.strategy_bit.chaos_brawl.world;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.entity.Base;
import com.strategy_bit.chaos_brawl.ashley.entity.Archer;
import com.strategy_bit.chaos_brawl.ashley.entity.PlayerClone;
import com.strategy_bit.chaos_brawl.ashley.entity.Tower;
import com.strategy_bit.chaos_brawl.types.UnitType;

/**
 * @author AIsopp
 * @version 1.0
 * @since 18.03.2018
 */
//TODO move every creation of an entity here
public class SpawnerImpl {
    public SpawnerImpl() {
    }

    public Entity createNewUnit(UnitType unitType, int teamID, Vector2 position) {
        Entity entity = null;

        switch (unitType) {
            case RANGED:
                entity = new Archer(position, teamID);
                break;
            case MELEE:
                entity = new PlayerClone(position, teamID);
                break;
            case MAINBUILDING:
                entity = new Base(position, teamID);
                break;
            case TOWER:
                entity = new Tower(position, teamID);
                break;
        }

        return entity;
    }
}
