package com.strategy_bit.chaos_brawl.controller;


import com.strategy_bit.chaos_brawl.InputHandler;

/**
 * Interface for communication between player(AI or human) and game
 *
 * @author AIsopp
 * @version 1.0
 * @since 15.03.2018
 */
public class PawnController {

    protected InputHandler inputHandler;

    public InputHandler getInputHandler(){
        return inputHandler;
    }
    public void setInputHandler(InputHandler inputHandler){
        this.inputHandler = inputHandler;
    }
}
