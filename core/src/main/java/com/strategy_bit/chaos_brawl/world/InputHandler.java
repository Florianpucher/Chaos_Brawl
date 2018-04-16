package com.strategy_bit.chaos_brawl.world;

import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.types.UnitType;

/**
 * @author AIsopp
 * @version 1.0
 * @since 15.03.2018
 */
//TODO add more methods for interaction with pawnControllers and his subclasses
public interface InputHandler {

    void sendTouchInput(Vector2 screenCoordinates, long entityID);
    void createEntity(Vector2 screenCoordinates, UnitType entityType, int teamID);
}
