package com.strategy_bit.chaos_brawl.controller;

import com.strategy_bit.chaos_brawl.util.Boundary;
import com.strategy_bit.chaos_brawl.world.InputHandler;

/**
 *
 * dummy class that tells us that this player plays on a remote device
 * @author AIsopp
 * @version 1.0
 * @since 19.04.2018
 */
public class OtherPlayerController extends PawnController {
    public OtherPlayerController(int teamID, InputHandler inputHandler, Boundary spawnArea) {
        super(teamID, inputHandler, spawnArea);
    }
}
