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
public class GameHUD extends Table{

    private static final String NEW_UNIT_1 = "UNIT_1";

    private boolean catchNextUserInput;
    private UnitType nextUnitType;

    public GameHUD() {
        super(AssetManager.getInstance().defaultSkin);
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

    public UnitType getUnitToSpawn(){
        UnitType current = nextUnitType;
        nextUnitType = null;
        return current;
    }


}
