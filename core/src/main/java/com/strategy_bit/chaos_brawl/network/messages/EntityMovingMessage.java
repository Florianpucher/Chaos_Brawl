package com.strategy_bit.chaos_brawl.network.messages;

import com.badlogic.gdx.math.Vector2;

/**
 * @author AIsopp
 * @version 1.0
 * @since 01.04.2018
 */
public class EntityMovingMessage implements Message{
    public long entityID;
    public Vector2 screenCoordinates;
}
