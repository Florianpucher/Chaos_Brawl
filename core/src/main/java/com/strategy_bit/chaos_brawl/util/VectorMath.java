package com.strategy_bit.chaos_brawl.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * @author AIsopp
 * @version 1.0
 * @since 18.03.2018
 */

public class VectorMath {


    public static Vector2 add(Vector2 v, Vector2 v2) {
        Vector2 vector2 = new Vector2(v);
        vector2.add(v2);
        return vector2;
    }


    public static Vector2 sub(Vector2 v, Vector2 v2) {
        float x1 = Math.round(v.x *10) /10f;
        float y1 = Math.round(v.y *10) /10f;

        float x2 = Math.round(v2.x *10) /10f;
        float y2 = Math.round(v2.y *10) /10f;

        return new Vector2(x1 -x2,y1 - y2);
    }


    public static Vector2 scl(Vector2 v, float scalar) {
        Vector2 vector2 = new Vector2(v);
        vector2.scl(scalar);
        return vector2;
    }

    public static Vector2 nor(Vector2 v) {
        Vector2 vector2 = new Vector2(v);
        vector2.nor();
        return vector2;
    }

    public static double distance(Vector2 v,Vector2 v2){
        return Math.sqrt(Math.pow(v.x-v2.x,2)+Math.pow(v.y-v2.y,2));
    }

    public static Vector2 vector3ToVector2(Vector3 vector3){
        Vector2 vector2 = new Vector2(vector3.x,vector3.y);
        return vector2;
    }
}
