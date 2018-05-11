package com.strategy_bit.chaos_brawl.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;

/**
 * component that has fields for moving an entity
 *
 * @author AIsopp
 * @version 1.0
 * @since 17.03.2018
 */

public class MovementComponent implements Component {
    //TODO maybe remove targetLocation and replace it completely with path
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

    /**
     * precalculated path to target
     */
    private Queue<Vector2> path;




    public MovementComponent(float speed, TransformComponent transformComponent) {
        this.speed = speed;
        // initial target is current position
        this.targetLocation = transformComponent.getPosition();
        this.velocity = new Vector2(0,0);
        path=new Queue<Vector2>();
    }

    public Vector2 getTargetLocation() {
        if(path.size<1) {
            return targetLocation;
        }
        else{
            return path.last();
        }
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

    public void popCurTarget(){
        if(!(path.size<1)) {
            path.removeLast();
        }
    }

    public Queue<Vector2> getPath() {
        return path;
    }

    public void setPath(Array<Vector2> path){
        this.path.clear();
        for (Vector2 vector2:path) {
            this.path.addFirst(vector2);
        }
    }

    public boolean hasNoPath(){
        if(path.size<1){
            return true;
        }else {
            return false;
        }
    }

    public void addToPath(Vector2 point){
        this.path.addFirst(point);
    }

}
