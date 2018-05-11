package com.strategy_bit.chaos_brawl.player_input_output;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.strategy_bit.chaos_brawl.cheat_function.SensorReader;
import com.strategy_bit.chaos_brawl.player_input_output.views.GameHUD;
import com.strategy_bit.chaos_brawl.resource_system.Resource;
import com.strategy_bit.chaos_brawl.types.UnitType;
import com.strategy_bit.chaos_brawl.util.Boundary;
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



    private SensorReader sensorReader;

    public PlayerController(int teamID, InputHandler inputHandler, Boundary spawnArea) {
        super(teamID, inputHandler, spawnArea);
        sensorReader = new SensorReader(this);
    }

    public void render(float dt) {
        gameHUD.updateBtns(resources.get(0).getResourceAmount());
        sensorReader.update(dt);
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
                if (spawnUnit(current)) {
                    inputHandler.createEntityScreenCoordinates(screenCoordinates, current, teamID);
                }
            }
        }
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
        super.tick();
        if (gameHUD != null) {
            for (Resource r :
                    resources) {
                gameHUD.updateManaBar(r);
            }
        }
    }

    public void gameOver(boolean win) {
        if(gameHUD != null){
            gameHUD.showWinningScreen(win);
        }

    }
}
