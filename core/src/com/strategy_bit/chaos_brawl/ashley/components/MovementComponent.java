package com.strategy_bit.chaos_brawl.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * component that has fields for moving an entity
 *
 * @author AIsopp
 * @version 1.0
 * @since 17.03.2018
 */

public class MovementComponent implements Component {



    /**
     * Will be changed over time
     */
    private Vector2 targetLocation;

    /**
     * will be changed by movementsystem
     * <br>
     * do not change
     */
    private Vector2 velocity;

    /**
     * speed of an entity
     */
    private float speed;




    public MovementComponent(float speed, TransformComponent transformComponent) {
        this.speed = speed;
        // initial target is current position
        this.targetLocation = transformComponent.getPosition();
        this.velocity = new Vector2(0,0);
    }

    public Vector2 getTargetLocation() {
        return targetLocation;
    }

    public void setTargetLocation(Vector2 targetLocation) {
        this.targetLocation = targetLocation;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

}
