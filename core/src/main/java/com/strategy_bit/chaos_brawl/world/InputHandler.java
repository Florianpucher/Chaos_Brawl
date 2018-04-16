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

    /**
     * moves an entity to the screenCoordinates
     * @param screenCoordinates moves an entity to these screenCoordinates
     * @param entityID which entity do you want to move
     */
    void sendTouchInput(Vector2 screenCoordinates, long entityID);

    /**
     * spawns an entity on the given coordinates for the given team
     * @param screenCoordinates the screenCoordinates where the input occurs
     * @param entityType the type of entity to spawn
     * @param teamID to which team/player this unit belongs to
     */
    void createEntity(Vector2 screenCoordinates, UnitType entityType, int teamID);
}
