package com.strategy_bit.chaos_brawl.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.player_input_output.PawnController;

/**
 * handles local input e.g. player input or input from an a. i. controller
 * @author AIsopp
 * @version 1.0
 * @since 15.03.2018
 */
public interface InputHandler {

    /**
     * sends a touch event to the inputhandler
     * @param screenCoordinates moves an entity to these screenCoordinates
     * @param controller the playerController that triggered the touch
     */
    void sendTouchInput(Vector2 screenCoordinates, PawnController controller);

    /**
     * spawns an entity on the given coordinates for the given team
     * @param worldCoordinates the world coordinates where the unit´s initial position is
     * @param unitId the id of unit to spawn
     * @param teamID to which team/player this unit belongs to
     */
    void createEntityWorldCoordinates(Vector2 worldCoordinates, int unitId, int teamID);

    void updateMarker(int t);


    void updateTowersOrUnits(int playerID, int updateType);

    void upgradeEntityInternal(Entity entity, int id);


}
