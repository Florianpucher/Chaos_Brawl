package com.strategy_bit.chaos_brawl.network.messages.request;

import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.network.messages.Message;

/**
 * @author AIsopp
 * @version 1.0
 * @since 01.04.2018
 */
public class EntitySpawnMessage implements Message {
    public Vector2 position;
    public int teamId;
    public int entityTypeId;
    public long unitID;

    public EntitySpawnMessage(Vector2 position, int teamId, int unitId, long unitID) {
        this.position = position;
        this.teamId = teamId;
        this.entityTypeId = unitId;
        this.unitID = unitID;
    }

    public EntitySpawnMessage() {
        // empty constructor is needed for kryo
    }
}
