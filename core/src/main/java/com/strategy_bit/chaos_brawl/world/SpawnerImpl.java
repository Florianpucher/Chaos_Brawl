package com.strategy_bit.chaos_brawl.world;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.engine.MyEngine;
import com.strategy_bit.chaos_brawl.ashley.entities.Unit;
import com.strategy_bit.chaos_brawl.config.UnitConfig;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.managers.UnitManager;

/**
 * @author AIsopp
 * @version 1.0
 * @since 18.03.2018
 */
public class SpawnerImpl {

    public Entity createNewUnit(int unitId, int teamID, Vector2 position, MyEngine engine) {
        Entity entity= new Entity();
        UnitConfig unitConfig= UnitManager.getInstance().getUnitConfig(unitId);

        Unit.setComponents(entity,unitConfig,teamID, position, engine);

        return entity;
    }
}
