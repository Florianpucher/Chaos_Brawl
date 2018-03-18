package com.strategy_bit.chaos_brawl;

import com.badlogic.gdx.math.Vector2;

/**
 * @author AIsopp
 * @version 1.0
 * @since 15.03.2018
 */

public interface InputHandler {

    void sendTouchInput(Vector2 screenCoordinates, long entityID);
}
