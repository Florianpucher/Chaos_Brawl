package com.strategy_bit.chaos_brawl.network;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public interface BrawlMultiplayer {
    void sendTick(float deltaTime);
    void sendEntitySpawnMsg(Vector2 worldPosition, int unitId, int teamID, long id);
    void sendEntityAttackMsg(long attackerID, long victimID);
    void sendEntityDeleteMsg(long entityID);
    void sendEntityUpgradeMsg(int teamID, int upgradeID);
    void sendEntityMovingMessage(long unitID, Array<Vector2> wayPoints);

    /**
     *
     * @return a class that can send results from incoming message to a {@link com.strategy_bit.chaos_brawl.world.MultiplayerInputHandler}
     */
    BrawlConnector getBrawlConnector();

    /**
     * releases resources allocated with this class
     */
    void dispose();

    /**
     * send a message that a player is now attacking another player.
     * @param playerIndex which player attacks another one
     * @param targetIndex the player´s new target player
     */
    void sendNewTargetMsg(int playerIndex, int targetIndex);

    /**
     *
     * @return true if this brawlMultiplayer is a host otherwise false
     */
    boolean isHost();
}
