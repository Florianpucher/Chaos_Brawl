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


    private Array<Rectangle> spawnAreaBlockers;
    public SpawnArea(float x, float y, float width, float height) {
        super(x, y, width, height);
        spawnAreaBlockers = new Array<>();
    }

    public void addSpawnAreaBlocker(float x, float y, float width, float height){
        Rectangle blocker = new Rectangle(x, y, width, height);
        spawnAreaBlockers.add(blocker);
    }

    public boolean positionIsInsideSpawnArea(Vector2 position){
        return positionIsInsideSpawnArea(position.x, position.y);
    }

    public boolean positionIsInsideSpawnArea(float x, float y){
        for (Rectangle blocker :
                spawnAreaBlockers) {
            if (blocker.contains(x,y)){
                return false;
            }
        }
        return contains(x,y);
    }
}
