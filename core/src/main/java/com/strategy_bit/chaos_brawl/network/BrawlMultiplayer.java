package com.strategy_bit.chaos_brawl.network;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.world.MultiplayerWorld;

public interface BrawlMultiplayer {
    void setManager(MultiplayerWorld manager);
    MultiplayerWorld getManager();
    void spawnEntity(Entity entity);
    void moveEntity(Vector2 screenCoordinates, long entityID);
}
