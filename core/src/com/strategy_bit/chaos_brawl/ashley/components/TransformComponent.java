package com.strategy_bit.chaos_brawl.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.config.Z_Index;

/**
 * components that holds the position, scale, z-index and rotation of an entity
 *
 * @author AIsopp
 * @version 1.0
 * @since 15.03.2018
 */

public class TransformComponent implements Component {
    private Vector2 position;
    private Vector2 scale;
    /**
     * the z index of this gameObject
     */
    private int z;
    private float rotation;

    /**
     * Creates a new TransformComponent with the default settings
     * <br>
     * <ul>
     *     <li>scale = (1,1)</li>
     *     <li>z = 1</li>
     *     <li>rotation = 0f</li>
     *     <li>position = (0,0)</li>
     * </ul>
     */
    public TransformComponent() {
        scale = new Vector2(1,1);
        z = Z_Index.DEFAULT;
        rotation = 0f;
        position = new Vector2(0,0);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public Vector2 getScale() {
        return scale;
    }

    public void setScale(Vector2 scale) {
        this.scale = scale;
    }
}
