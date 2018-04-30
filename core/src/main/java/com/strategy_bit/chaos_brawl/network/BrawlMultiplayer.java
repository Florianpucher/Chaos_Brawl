package com.strategy_bit.chaos_brawl.network;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.types.UnitType;

public interface BrawlMultiplayer {
    void sendTick();
    void sendEntitySpawnMsg(Vector2 worldPosition, UnitType unitType, int teamID, long id);
    void sendEntityDeleteMsg(long entityID);
    void sendEntityMovingMessage(long unitID, Array<Vector2> wayPoints);
    BrawlConnector getBrawlConnector();
}
