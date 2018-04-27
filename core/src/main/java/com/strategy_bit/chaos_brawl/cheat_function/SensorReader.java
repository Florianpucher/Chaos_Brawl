package com.strategy_bit.chaos_brawl.cheat_function;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Florian on 27.04.2018.
 */

public class SensorReader {

    private static final float min = 1.0f;
    Array<Float> accelerations = new Array<Float>();
    float time = 0;

    public void update(float dt){
        float accelY;
        time+=dt;
        accelY = Gdx.input.getAccelerometerY();
        if (Math.abs(accelY) > min) {
            accelerations.add(accelY);
            float average = 0;
            for (float acc:
                    accelerations) {
                average += acc;
            }
            average = average / accelerations.size;
            if (accelerations.size > 50 && average < 0.5f && time < 5000) {
                String message = "Cheat Function active!";
                System.out.println(message);
                accelerations.clear();
                time = 0;
            }
            else if (time > 5000){
                accelerations.clear();
                time = 0;
            }
        }
    }
}
