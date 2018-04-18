package com.strategy_bit.chaos_brawl.util;

import com.badlogic.gdx.math.Vector2;

/**
 * @author AIsopp
 * @version 1.0
 * @since 18.04.2018
 */
public class Boundary {

    private Vector2 lowerLeft;
    private Vector2 lowerRight;
    private Vector2 upperLeft;
    private Vector2 upperRight;

    public Boundary(Vector2 lowerLeft, Vector2 lowerRight, Vector2 upperLeft, Vector2 upperRight) {
        this.lowerLeft = lowerLeft;
        this.lowerRight = lowerRight;
        this.upperLeft = upperLeft;
        this.upperRight = upperRight;
    }


    public boolean checkIfVectorIsInside(Vector2 vectorToCheck){
        //TODO atm it can only calculate vectors inside a square
        return lowerLeft.x <= vectorToCheck.x && lowerRight.x >= vectorToCheck.x &&
                upperLeft.y <= vectorToCheck.y && lowerLeft.y >= vectorToCheck.y;

    }


    public Vector2[] getMatrixArray(){
        return new Vector2[]{lowerLeft,lowerRight,upperLeft,upperRight};
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(lowerLeft).append(lowerRight).append(upperLeft).append(upperRight);
        return builder.toString();
    }
}
