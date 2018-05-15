package com.strategy_bit.chaos_brawl.world;

import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.player_input_output.PawnController;
import com.strategy_bit.chaos_brawl.types.UnitType;

/**
 * @author AIsopp
 * @version 1.0
 * @since 15.03.2018
 */
public interface InputHandler {

    /**
     * moves an entity to the screenCoordinates
     * @param screenCoordinates moves an entity to these screenCoordinates
     * @param controller the playerController that triggered the touch
     */
    void sendTouchInput(Vector2 screenCoordinates, PawnController controller);

    /**
     * spawns an entity on the given coordinates for the given team
     * @param screenCoordinates the screenCoordinates where the input occurs
     * @param entityType the type of entity to spawn
     * @param teamID to which team/player this unit belongs to
     */
    void createEntityScreenCoordinates(Vector2 screenCoordinates, UnitType entityType, int teamID);

    /**
     * spawns an entity on the given coordinates for the given team
     * @param worldCoordinates the world coordinates where the unit´s initial position is
     * @param entityType the type of entity to spawn
     * @param teamID to which team/player this unit belongs to
     */
    void createEntityWorldCoordinates(Vector2 worldCoordinates, UnitType entityType, int teamID);
}
