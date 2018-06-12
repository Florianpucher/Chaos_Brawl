package com.strategy_bit.chaos_brawl.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * @author AIsopp
 * @version 1.0
 * @since 26.04.2018
 */
public interface MultiplayerInputHandler extends InputHandler {
    void createEntityLocal(Vector2 worldCoordinates, int unitId, int teamID, long unitID);
    void moveEntityLocal(long unitID, Array<Vector2> wayPoints);
    void deleteUnitLocal(long unitID);
    void upgradeUnitLocal(int teamID, int upgradeID);
    void unitAttackLocal(long attackerID, long victimID);
    void playerChangesTarget(int playerIndex, int targetIndex);
    void getTick(float deltaTime);
}
