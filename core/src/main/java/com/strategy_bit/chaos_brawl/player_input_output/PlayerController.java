package com.strategy_bit.chaos_brawl.player_input_output;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.strategy_bit.chaos_brawl.resource_system.Resource;
import com.strategy_bit.chaos_brawl.types.UnitType;
import com.strategy_bit.chaos_brawl.util.Boundary;
import com.strategy_bit.chaos_brawl.player_input_output.views.GameHUD;
import com.strategy_bit.chaos_brawl.world.InputHandler;

import org.w3c.dom.events.UIEvent;

/**
 * interface of user interface and other user input
 *
 * @author AIsopp
 * @version 1.0
 * @since 15.03.2018
 */
public class PlayerController extends PawnController implements InputProcessor {

    private GameHUD gameHUD;

    int w = 0;
    int h = 0;
    int tw = 0;
    int th = 0;
    OrthographicCamera camera = null;
    private SpriteBatch batch = null;


    public PlayerController(int teamID, InputHandler inputHandler, Boundary spawnArea) {
        super(teamID,inputHandler, spawnArea);
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
        Vector2 screenCoordinates = new Vector2(screenX,screenY);
        if(gameHUD != null){
            UnitType current = gameHUD.getUnitToSpawn();

            if(current != null && spawnArea.checkIfVectorIsInside(screenCoordinates)){
                System.out.println("Click");

                inputHandler.createEntityScreenCoordinates(screenCoordinates,current, teamID);
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
     * @param stage the stage where the gameHUD will be added
     */
    public void addGameHUD(Stage stage){
        if(gameHUD == null){
            gameHUD = new GameHUD(spawnArea);
        }
        if(!stage.getActors().contains(gameHUD, false)){
            stage.addActor(gameHUD);
        }else{
            throw new IllegalStateException("GameHUD was already added to current Stage: " + stage.getClass().getName());
        }
    }


    public void dispose(){
        if(gameHUD != null){
            gameHUD.dispose();
        }
    }

    @Override
    public void tick() {
        if(gameHUD!=null){
        super.tick();
        for (Resource r :
                resources) {
            gameHUD.manaBar.setValue((float)r.percentageFull()*gameHUD.manaBar.getMaxValue());
        }
        }
    }

    public void gameOver(boolean win) {
        batch = new SpriteBatch();

        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        OrthographicCamera camera = new OrthographicCamera(w, h);
        camera.position.set(w / 2, h / 2, 0);
        camera.update();

        if (win == true){
            Texture victory = new Texture(Gdx.files.internal("victory.png"));
            tw = victory.getWidth();
            th = victory.getHeight();

            batch.begin();
            batch.draw(victory, camera.position.x - (tw / 2), camera.position.y - (th / 2));
            batch.end();
        }
        else if (win == false){
            Texture defeat = new Texture(Gdx.files.internal("defeat.png"));
            tw = defeat.getWidth();
            th = defeat.getHeight();

            batch.begin();
            batch.draw(defeat, camera.position.x - (tw / 2), camera.position.y - (th / 2));
            batch.end();
        }
        System.out.println(win);
    }
}
