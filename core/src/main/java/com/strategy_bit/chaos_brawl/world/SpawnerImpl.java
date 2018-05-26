package com.strategy_bit.chaos_brawl.world;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;

import com.strategy_bit.chaos_brawl.ashley.entities.Archer;
import com.strategy_bit.chaos_brawl.ashley.entities.Base;
import com.strategy_bit.chaos_brawl.ashley.entities.Berserker;
import com.strategy_bit.chaos_brawl.ashley.entities.Knight;
import com.strategy_bit.chaos_brawl.ashley.entities.Mage;
import com.strategy_bit.chaos_brawl.ashley.entities.SwordFighter;
import com.strategy_bit.chaos_brawl.ashley.entities.Templar;
import com.strategy_bit.chaos_brawl.ashley.entities.Tower;
import com.strategy_bit.chaos_brawl.ashley.engine.MyEngine;
import com.strategy_bit.chaos_brawl.types.UnitType;

/**
 * @author AIsopp
 * @version 1.0
 * @since 18.03.2018
 */
public class SpawnerImpl {

    public Entity createNewUnit(UnitType unitType, int teamID, Vector2 position) {
        Entity entity= new Entity();

        switch (unitType) {
            case RANGED:
                Archer.setComponents(entity,position, teamID);
                break;
            case SWORDFIGHTER:
                SwordFighter.setComponents(entity,position, teamID);
                break;
            case KNIGHT:
                Knight.setComponents(entity,position, teamID);
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
                Base.setComponents(entity,position, teamID);
                break;
            case TOWER:
                Tower.setComponents(entity,position, teamID);
                break;
            default:
                throw new UnsupportedOperationException("This unitType is not registered by SpawnerImpl");
        }

        return entity;
    }
}
