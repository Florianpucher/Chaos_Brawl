package com.strategy_bit.chaos_brawl;


import com.badlogic.ashley.core.Entity;
import com.strategy_bit.chaos_brawl.ashley.entity.Player;

/**
 * @author AIsopp
 * @version 1.0
 * @since 18.03.2018
 */
//TODO move every creation of an entity here
public class SpawnerImpl {
    public static final int SPAWN_PLAYER = 0;


    public Entity createNewUnit(int entityCode) {
        Entity entity = null;

        switch (entityCode) {
            case SPAWN_PLAYER:
                entity = new Player();
                break;
        }

        return entity;
    }
}
