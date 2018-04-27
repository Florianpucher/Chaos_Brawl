package com.strategy_bit.chaos_brawl.network.messages.Request;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.network.messages.Message;

/**
 * @author AIsopp
 * @version 1.0
 * @since 01.04.2018
 */
public class EntityMovingMessage implements Message {

    public long entityID;
    public Vector2[] wayPoints;

    public EntityMovingMessage(long entityID, Array<Vector2> wayPoints) {
        this.entityID = entityID;
        this.wayPoints = wayPoints.toArray(Vector2.class);
    }

    public EntityMovingMessage() {
    }


    public Array<Vector2> toLibGdxArray(){
        return new Array<Vector2>(wayPoints);
    }
}
