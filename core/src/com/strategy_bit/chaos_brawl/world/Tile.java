package com.strategy_bit.chaos_brawl.world;

import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.types.TileType;

/**
 * Tile interface for {@link Board}
 *
 *
 * @author AIsopp
 * @version 1.0
 * @since 27.03.2018
 */
public interface Tile {



    public TileType getType();
    public void setType(TileType type);
    public void setPosition(Vector2 position);
    public Vector2 getPosition();
    public void setScale(Vector2 size);
    public Vector2 getScale();

}
