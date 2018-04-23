package com.strategy_bit.chaos_brawl.world;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Florian on 16.04.2018.
 */

public interface Board {
    //public BoardType getBoardType();
    public Tile[][] getTileBoard();
    public int[][] boardToMatrix();

    /**
     *
     * @param x horizontal position in the tileBoardArray
     * @param y vertical position in the tileBoardArray
     * @return a vector2 that represents the world coordinate of the current tile
     */
    public Vector2 getWorldCoordinateOfTile(int x, int y);
}
