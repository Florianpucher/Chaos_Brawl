package com.strategy_bit.chaos_brawl.player_input_output;

import com.strategy_bit.chaos_brawl.util.SpawnArea;
import com.strategy_bit.chaos_brawl.world.InputHandler;

/**
 *
 * dummy class that tells us that this player plays on a remote device
 * @author AIsopp
 * @version 1.0
 * @since 19.04.2018
 */
public class OtherPlayerController extends PawnController {
    public OtherPlayerController(int teamID, InputHandler inputHandler, SpawnArea spawnArea) {
        super(teamID, inputHandler, spawnArea, null);
    }
}
