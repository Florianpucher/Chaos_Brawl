package com.strategy_bit.chaos_brawl.network;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public interface BrawlMultiplayer {
    void sendTick(float deltaTime);
    void sendEntitySpawnMsg(Vector2 worldPosition, int unitId, int teamID, long id);
    void sendEntityDeleteMsg(long entityID);
    void sendEntityUpgradeMsg(int teamID, int upgradeID);
    void sendEntityMovingMessage(long unitID, Array<Vector2> wayPoints);
    BrawlConnector getBrawlConnector();
    void dispose();
    void sendNewTargetMsg(int playerIndex, int targetIndex);

    /**
     *
     * @return true if this brawlMultiplayer is a host otherwise false
     */
    boolean isHost();
}
