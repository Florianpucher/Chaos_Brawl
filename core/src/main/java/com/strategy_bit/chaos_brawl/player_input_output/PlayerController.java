package com.strategy_bit.chaos_brawl.player_input_output;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.strategy_bit.chaos_brawl.cheat_function.SensorReader;
import com.strategy_bit.chaos_brawl.config.WorldSettings;
import com.strategy_bit.chaos_brawl.config.UnitConfig;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.player_input_output.views.GameHUD;
import com.strategy_bit.chaos_brawl.resource_system.Resource;
import com.strategy_bit.chaos_brawl.types.EventType;
import com.strategy_bit.chaos_brawl.util.SpawnArea;
import com.strategy_bit.chaos_brawl.util.VectorMath;
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

    public PlayerController(int teamID, InputHandler inputHandler, SpawnArea spawnArea, Camera camera) {
        super(teamID, inputHandler, spawnArea, camera);
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

        int current = -1;
        if (gameHUD != null) {
            current = gameHUD.getUnitToSpawn();
            Vector2 worldCoordinates = VectorMath.vector3ToVector2(camera.unproject(new Vector3(screenCoordinates,0)));
            worldCoordinates.y = WorldSettings.FRUSTUM_HEIGHT - worldCoordinates.y;
            if (current != -1 && spawnArea.contains(worldCoordinates)) {
                if (spawnUnit(current)) {
                    worldCoordinates.y = WorldSettings.FRUSTUM_HEIGHT - worldCoordinates.y;
                    inputHandler.createEntityWorldCoordinates(worldCoordinates, current, teamID);
                }
                return true;
            }
        }
        if(current == -1){
            inputHandler.sendTouchInput(screenCoordinates, this);
            return true;
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
            gameHUD = new GameHUD( this);
            gameHUD.initializeNonSpawnAreaShadow(spawnArea, camera);

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
    public void tick(float deltaTime) {
        super.tick(deltaTime);
        if (gameHUD != null) {
            for (Resource r :
                    resources) {
                gameHUD.updateManaBar(r);
            }
        }
    }

    @Override
    public void gameOver(boolean win) {
        if(gameHUD != null){
            gameHUD.showWinningScreen(win);
        }

    }

    @Override
    public void triggeredEvent(EventType type, Object... params) {
        if(type == EventType.CLICKED_ON_ENEMY_BASE){
            int targetIndex = (Integer) params[0];
            setCurrentTargetTeam(targetIndex);
            Gdx.app.log("PLAYER_CONTROLLER", String.valueOf(targetIndex));
        }
    }

    @Override
    public void setCurrentTargetTeam(int currentTargetTeam) {
        super.setCurrentTargetTeam(currentTargetTeam);
        inputHandler.updateMarker(currentTargetTeam);
    }

    public boolean updateTowersOrUnits(int upgradeType) {

        UnitConfig unitConfig = null;
        int updateType = -1;
        if (upgradeType == 20) {
            unitConfig = AssetManager.getInstance().unitManager.unitConfigHashMap.get(20);
            updateType = 1;
        } else if (upgradeType == 21) {
            unitConfig = AssetManager.getInstance().unitManager.unitConfigHashMap.get(21);
            updateType = 11;
        }
        inputHandler.updateTowersOrUnits(teamID, updateType);
        float cost = unitConfig.getCost();
        return checkAndSubtract(cost, "Gold");

    }
}
