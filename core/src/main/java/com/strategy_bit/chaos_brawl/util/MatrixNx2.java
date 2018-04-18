package com.strategy_bit.chaos_brawl.util;

import com.badlogic.gdx.math.Vector2;

/**
 * @author AIsopp
 * @version 1.0
 * @since 18.04.2018
 */
public class MatrixNx2 {

    private Vector2[] innerMatrix;

    /**
     *
     * @param vectors the input Vectors
     */
    public MatrixNx2(Vector2... vectors){
        innerMatrix = vectors;
    }

    /**
     *
     * @return a N x 2 array representing the matrix
     */
    public Vector2[] getMatrixArray() {
        return innerMatrix;
    }
}
