package com.strategy_bit.chaos_brawl.world;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.entity.Archer;
import com.strategy_bit.chaos_brawl.ashley.entity.Base;
import com.strategy_bit.chaos_brawl.ashley.entity.Berserker;
import com.strategy_bit.chaos_brawl.ashley.entity.Knight;
import com.strategy_bit.chaos_brawl.ashley.entity.Mage;
import com.strategy_bit.chaos_brawl.ashley.entity.SwordFighter;
import com.strategy_bit.chaos_brawl.ashley.entity.Templar;
import com.strategy_bit.chaos_brawl.ashley.entity.Tower;
import com.strategy_bit.chaos_brawl.types.UnitType;

/**
 * @author AIsopp
 * @version 1.0
 * @since 18.03.2018
 */
public class SpawnerImpl {

    public Entity createNewUnit(UnitType unitType, int teamID, Vector2 position) {
        Entity entity;

        switch (unitType) {
            case RANGED:
                entity = new Archer(position, teamID);
                break;
            case SWORDFIGHTER:
                entity = new SwordFighter(position, teamID);
                break;
            case KNIGHT:
                entity = new Knight(position, teamID);
                break;
            case MAGE:
                entity = new Mage(position, teamID);
                break;
            case BERSERKER:
                entity = new Berserker(position, teamID);
                break;
            case UPGRADE_UNITS:
                // Todo
            case TEMPLAR:
                entity = new Templar(position, teamID);
                break;
            case MAINBUILDING:
                entity = new Base(position, teamID);
                break;
            case TOWER:
                entity = new Tower(position, teamID);
                break;
            default:
                throw new UnsupportedOperationException("This unitType is not registered by SpawnerImpl");
        }

        return entity;
    }
}
