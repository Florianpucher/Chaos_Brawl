package com.strategy_bit.chaos_brawl.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * @author AIsopp
 * @version 1.0
 * @since 17.03.2018
 */

public class MovementComponent implements Component {



    /**
     * Will be changed over time
     */
    private Vector2 targetLocation;

    private Vector2 velocity;

    private float speed;

    private boolean newTarget;


    public MovementComponent(float speed, TransformComponent transformComponent) {
        this.speed = speed;
        this.newTarget = false;
        // initial target is current position
        this.targetLocation = transformComponent.getPosition();
        this.velocity = new Vector2(0,0);
    }

    public Vector2 getTargetLocation() {
        return targetLocation;
    }

    public void setTargetLocation(Vector2 targetLocation) {
        this.targetLocation = targetLocation;
        setNewTarget(true);
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

    public boolean hasNewTarget() {
        return newTarget;
    }

    public void setNewTarget(boolean newTarget) {
        this.newTarget = newTarget;
    }
}
