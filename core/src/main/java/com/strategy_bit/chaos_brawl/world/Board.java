package com.strategy_bit.chaos_brawl.world;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Florian on 16.04.2018.
 */

public interface Board {
    //public BoardType getBoardType();
    public Tile[][] getTileBoard();

    /**
     * returns a matrix like<br>
     * current implementation 0 entries stays for not walkable and 1 for walkable<br>
     * 0,0 0,1 0,2 0,3 0,4 0,5 0,WIDTH <br>
     * 1,0 1,1 <br>
     * 2,0 2,1 <br>
     * 3,0 3,1 <br>
     * 4,0 4,1 <br>
     * 5,0 5,1 <br>
     * 6,0 6,1 <br>
     * HEIGHT,0 <br>
     * @return a matrix in the form of int[HEIGHT][WIDTH]
     */
    public int[][] boardToMatrix();

    /**
     *
     * @param x horizontal position in the tileBoardArray
     * @param y vertical position in the tileBoardArray
     * @return a vector2 that represents the world coordinate of the current tile
     */
    public Vector2 getWorldCoordinateOfTile(int x, int y);
    public Vector2 getTileBoardPositionDependingOnWorldCoordinates(Vector2 worldCoordinates);
}
