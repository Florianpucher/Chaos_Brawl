package com.strategy_bit.chaos_brawl.cheat_function;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.player_input_output.PlayerController;

/**
 * Created by Florian on 27.04.2018.
 */

public class SensorReader {

    private static final float MIN = 1.0f;
    private Array<Float> accelerations = new Array<>();
    private float time = 0;
    private float duration;
    private PlayerController playerController;
    private boolean active;
    private boolean used = false;

    public SensorReader(PlayerController playerController) {
        this.playerController = playerController;
    }

    public void update(float dt){
        float accelY;
        time+=dt;

        if (active){
            tick(dt);
        }

        accelY = Gdx.input.getAccelerometerY();
        if (Math.abs(accelY) > MIN) {
            accelerations.add(accelY);
            float average = 0;
            for (float acc:
                    accelerations) {
                average += acc;
            }
            average = average / accelerations.size;
            if (accelerations.size > 50 && average < 0.5f && time < 5000 && !used) {
                startCheatfunction();
            }
            else if (time > 5000){
                accelerations.clear();
                time = 0;
                playerController.setCheatFunctionActive(false);
            }
        }
    }

    private void startCheatfunction(){
        Gdx.app.log("CHEAT", "Cheat Function active!");
        playerController.setNewRate(5);
        playerController.setCheatFunctionActive(true);
        duration = 10;
        active = true;
        used = true;
    }
    private void clearCheatfunction(){
        Gdx.app.log("CHEAT_CLEAR", "Cheat Function not active!");
        accelerations.clear();
        time = 0;
        playerController.setNewRate(1);
        active = false;
    }

    private void tick(float dt){
        duration -= dt;
        if (duration < 0){
            clearCheatfunction();
        }
    }
}
