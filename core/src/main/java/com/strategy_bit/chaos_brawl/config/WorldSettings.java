package com.strategy_bit.chaos_brawl.config;

/**
 * class holding every important information of how to render the world
 *
 * @author AIsopp
 * @version 1.0
 * @since 27.03.2018
 */
public class WorldSettings {
    // Settings for Tile board
    /**
     * How much tiles are on the map
     */
    public static final int BOARD_WIDTH = 15;
    /**
     * How much tiles are on the map
     */
    public static final int BOARD_HEIGHT = 11;

    //size of game board that the camera can show
    public static final float FRUSTUM_WIDTH = 40;
    public static final float FRUSTUM_HEIGHT = 30;
    public static final float PIXELS_TO_METRES = 1.0f / 32.0f;



    public static final double RATE = 0.1d;


    private WorldSettings(){
        // Do not initialize
    }
}
