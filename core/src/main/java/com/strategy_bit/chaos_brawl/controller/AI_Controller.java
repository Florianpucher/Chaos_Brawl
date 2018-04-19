package com.strategy_bit.chaos_brawl.controller;

import com.strategy_bit.chaos_brawl.util.Boundary;
import com.strategy_bit.chaos_brawl.world.InputHandler;

/**
 * @author AIsopp
 * @version 1.0
 * @since 19.04.2018
 */
public class AI_Controller extends PlayerController {

    public AI_Controller(int teamID, InputHandler inputHandler, Boundary spawnArea) {
        super(teamID, inputHandler, spawnArea);
    }
}
