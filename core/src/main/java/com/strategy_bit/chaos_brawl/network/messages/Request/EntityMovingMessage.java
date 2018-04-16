package com.strategy_bit.chaos_brawl.network.messages.Request;

import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.network.messages.Message;

/**
 * @author AIsopp
 * @version 1.0
 * @since 01.04.2018
 */
public class EntityMovingMessage implements Message {

    public long entityID;
    public Vector2 screenCoordinates;

    public EntityMovingMessage(Vector2 screenCoordinates,long entityID ) {
        this.entityID = entityID;
        this.screenCoordinates = screenCoordinates;
    }

    public EntityMovingMessage() {
    }
}
