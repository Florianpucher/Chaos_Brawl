package com.strategy_bit.chaos_brawl.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.ResourceSystem.Resource;
import com.strategy_bit.chaos_brawl.types.UnitType;
import com.strategy_bit.chaos_brawl.util.Boundary;
import com.strategy_bit.chaos_brawl.views.GameHUD;
import com.strategy_bit.chaos_brawl.world.InputHandler;

/**
 * interface of user interface and other user input
 *
 * @author AIsopp
 * @version 1.0
 * @since 15.03.2018
 */
public class PlayerController extends PawnController implements InputProcessor {

    private GameHUD gameHUD;
    private static final float min = 1.0f;
    Array<Float> accelerations = new Array<Float>();
    float time = 0;


    public PlayerController(int teamID, InputHandler inputHandler, Boundary spawnArea) {
        super(teamID, inputHandler, spawnArea);

    }

    public void render(float dt) {
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


    @Override
    public boolean keyDown(int keycode) {

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 screenCoordinates = new Vector2(screenX, screenY);
        if (gameHUD != null) {
            UnitType current = gameHUD.getUnitToSpawn();

            if (current != null && spawnArea.checkIfVectorIsInside(screenCoordinates)) {
                System.out.println("Click");

                inputHandler.createEntityScreenCoordinates(screenCoordinates, current, teamID);
                return false;
            }
        }
        //inputHandler.sendTouchInput(screenCoordinates,0);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    /**
     * adds the game hud to a stage
     *
     * @param stage the stage where the gameHUD will be added
     */
    public void addGameHUD(Stage stage) {
        if (gameHUD == null) {
            gameHUD = new GameHUD(spawnArea);
        }
        if (!stage.getActors().contains(gameHUD, false)) {
            stage.addActor(gameHUD);
        } else {
            throw new IllegalStateException("GameHUD was already added to current Stage: " + stage.getClass().getName());
        }
    }


    public void dispose() {
        if (gameHUD != null) {
            gameHUD.dispose();
        }
    }

    @Override
    public void tick() {
        if (gameHUD != null) {
            super.tick();
            for (Resource r :
                    resources) {
                gameHUD.manaBar.setValue((float) r.percentageFull() * gameHUD.manaBar.getMaxValue());
            }
        }
    }
}