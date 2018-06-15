package com.strategy_bit.chaos_brawl.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Queue;

/**
 * component that has fields for moving an entity
 *
 * @author AIsopp
 * @version 1.0
 * @since 17.03.2018
 */

public class MovementComponent implements Component,Pool.Poolable {



    private TransformComponent transformComponent;

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
        this.transformComponent = transformComponent;
        // initial target is current position
        path= new Queue<>();
    }

    public void setEverything(float speed, TransformComponent transformComponent) {
        this.speed = speed;
        this.transformComponent=transformComponent;
        path=new Queue<>();
    }

    public Vector2 getTargetLocation() {
        if(path.size<1) {
            return transformComponent.getPosition();
        }
        else{
            return path.last();
        }
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void popCurTarget(){
        if((path.size>0)) {
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

    public void setPath(Queue<Vector2> path){
        this.path = path;
    }

    public boolean hasNoPath(){
        return path.size < 1;
    }

    public void addToPath(Vector2 point){
        this.path.addFirst(point);
    }


    public MovementComponent() {
        this.transformComponent=null;
        speed=0;
        path=new Queue<>();
    }

    @Override
    public void reset() {
        this.transformComponent=null;
        speed=0;
        path.clear();

    }

    public void setTargetLocation(Vector2 targetLocation) {
        path.addFirst(targetLocation);
    }
}
