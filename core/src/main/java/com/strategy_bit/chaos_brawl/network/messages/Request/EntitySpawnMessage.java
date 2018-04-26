package com.strategy_bit.chaos_brawl.network.messages.Request;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.network.messages.Message;
import com.strategy_bit.chaos_brawl.types.UnitType;

/**
 * @author AIsopp
 * @version 1.0
 * @since 01.04.2018
 */
public class EntitySpawnMessage implements Message {
    public Vector2 position;
    public int teamId;
    public UnitType entityTypeId;

    public EntitySpawnMessage(Vector2 position, int teamId, UnitType entityTypeId) {
        this.position = position;
        this.teamId = teamId;
        this.entityTypeId = entityTypeId;
    }

    public EntitySpawnMessage() {
    }
}
