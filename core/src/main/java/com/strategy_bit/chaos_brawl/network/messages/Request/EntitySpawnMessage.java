package com.strategy_bit.chaos_brawl.network.messages.Request;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.network.messages.Message;

/**
 * @author AIsopp
 * @version 1.0
 * @since 01.04.2018
 */
public class EntitySpawnMessage implements Message {
    public Entity entity;
}
