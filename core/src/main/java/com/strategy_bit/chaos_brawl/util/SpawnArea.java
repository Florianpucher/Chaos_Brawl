package com.strategy_bit.chaos_brawl.util;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * @author AIsopp
 * @version 1.0
 * @since 08.06.2018
 */
public class SpawnArea extends Rectangle {

    private transient Array<Rectangle> spawnAreaBlockers;
    public SpawnArea(float x, float y, float width, float height) {
        super(x, y, width, height);
        spawnAreaBlockers = new Array<>();
    }

}
