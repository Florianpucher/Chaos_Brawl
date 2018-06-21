package com.strategy_bit.chaos_brawl.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * multiplayer extension of inputhandler used by networking components
 * @author AIsopp
 * @version 1.0
 * @since 26.04.2018
 */
public interface MultiplayerInputHandler extends InputHandler {
    /**
     * client only. spawns an unit
     * @param worldCoordinates where to spawn
     * @param unitIdType which unit to spawn
     * @param teamID for which player
     * @param unitID unique ID for this unit
     */
    void createEntityLocal(Vector2 worldCoordinates, int unitIdType, int teamID, long unitID);

    /**
     * client only. Set path for local unit
     * @param unitID which unit
     * @param wayPoints waypoints
     */
    void moveEntityLocal(long unitID, Array<Vector2> wayPoints);

    /**
     * client only. remove unit from game
     * @param unitID which unit
     */
    void deleteUnitLocal(long unitID);

    /**
     * client only. upgrades units or towers for a player
     * @param teamID for which player
     * @param upgradeID which upgrade type
     */
    void upgradeUnitLocal(int teamID, int upgradeID);

    /**
     * client only. a unit attacks another units
     * @param attackerID which unit is the attacker
     * @param victimID which unit is the victim
     */
    void unitAttackLocal(long attackerID, long victimID);

    /**
     * server only. which player base the units should attack
     * @param playerIndex who changed his target
     * @param targetIndex the target player
     */
    void playerChangesTarget(int playerIndex, int targetIndex);

    /**
     * client only. add resources to all local players
     * @param deltaTime the delta time of the server
     */
    void getTick(float deltaTime);
}
