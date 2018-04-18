package com.strategy_bit.chaos_brawl.controller;


import com.badlogic.gdx.math.Matrix4;
import com.strategy_bit.chaos_brawl.world.InputHandler;

/**
 * Interface for communication between player(AI or human) and game
 *
 * @author AIsopp
 * @version 1.0
 * @since 15.03.2018
 */
public abstract class PawnController {

    protected InputHandler inputHandler;

    public PawnController(InputHandler inputHandler){
        this.inputHandler = inputHandler;
    }
}
