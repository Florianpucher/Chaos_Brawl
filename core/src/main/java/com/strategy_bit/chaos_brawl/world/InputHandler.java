package com.strategy_bit.chaos_brawl.world;

import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.player_input_output.PawnController;

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
     * @param unitId the id of unit to spawn
     * @param teamID to which team/player this unit belongs to
     */
    void createEntityScreenCoordinates(Vector2 screenCoordinates, int unitId, int teamID);

    /**
     * spawns an entity on the given coordinates for the given team
     * @param worldCoordinates the world coordinates where the unit´s initial position is
     * @param unitId the id of unit to spawn
     * @param teamID to which team/player this unit belongs to
     */
    void createEntityWorldCoordinates(Vector2 worldCoordinates, int unitId, int teamID);

    boolean checkAbility(Vector2 screenCoordinates,int teamId);
}
