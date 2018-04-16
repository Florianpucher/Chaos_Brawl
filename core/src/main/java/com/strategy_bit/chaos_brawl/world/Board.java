package com.strategy_bit.chaos_brawl.world;

/**
 * Created by Florian on 16.04.2018.
 */

public interface Board {
    //public BoardType getBoardType();
    public Tile[][] getTileBoard();
    public int[][] boardToMatrix(BoardA board);
}
