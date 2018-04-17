package com.strategy_bit.chaos_brawl.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.types.UnitType;
import com.strategy_bit.chaos_brawl.world.InputHandler;

/**
 * holds information of the gameHUD
 * @author AIsopp
 * @version 1.0
 * @since 16.04.2018
 */
public class GameHUD extends Table implements InputProcessor{

    private InputHandler inputHandler;
    private static final String NEW_UNIT_1 = "UNIT_1";

    private boolean catchNextUserInput;
    private UnitType nextUnitType;
    private int teamID;

    /**
     * Implementation for a classic gameHud
     * @param inputHandler on what world do you want to spawn the units
     * @param teamID used for spawning units for the given team
     */
    public GameHUD(InputHandler inputHandler, int teamID) {
        super(AssetManager.getInstance().defaultSkin);
        this.inputHandler = inputHandler;
        this.teamID = teamID;
        catchNextUserInput = false;
        AssetManager assetManager = AssetManager.getInstance();
        final TextButton btnNewUnit1 = new TextButton(NEW_UNIT_1, assetManager.defaultSkin);
        btnNewUnit1.setName(NEW_UNIT_1);
        setFillParent(true);
        bottom();
        right();
        setBackground((Drawable) null);
        float height = Gdx.graphics.getHeight()/8;
        add(btnNewUnit1).width(Gdx.graphics.getWidth()/4).height(height);
        ClickListener listener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                String name = event.getListenerActor().getName();
                System.out.println("Spawn unit on next click");
                if(name.equals(NEW_UNIT_1)){
                    if(nextUnitType == UnitType.RANGED){
                        nextUnitType = null;
                    }else{
                        System.out.println("Spawn unit on next click");
                        nextUnitType = UnitType.RANGED;
                    }
                }

            }
        };
        System.out.println("init game hud");
        btnNewUnit1.addListener(listener);
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
        System.out.println("Catched");
        if(nextUnitType != null){
            inputHandler.createEntityScreenCoordinates(new Vector2(screenX,screenY),  nextUnitType,teamID);
            nextUnitType = null;
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
}
