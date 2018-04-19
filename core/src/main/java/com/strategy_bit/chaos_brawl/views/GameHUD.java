package com.strategy_bit.chaos_brawl.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.types.UnitType;
import com.strategy_bit.chaos_brawl.util.Boundary;
import com.strategy_bit.chaos_brawl.world.InputHandler;

/**
 * holds information of the gameHUD
 * @author AIsopp
 * @version 1.0
 * @since 16.04.2018
 */
public class GameHUD extends Table{

    private static final String NEW_UNIT_1 = "UNIT_1";

    private UnitType nextUnitType;
    private Texture nonSpawnAreaTexture;

    public GameHUD(Boundary spawnArea) {
        super(AssetManager.getInstance().defaultSkin);
        AssetManager assetManager = AssetManager.getInstance();
        initializeNonSpawnAreaShadow(spawnArea);

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
                if(name.equals(NEW_UNIT_1)){
                    if(nextUnitType == UnitType.MELEE){
                        setBackground((Drawable) null);
                        nextUnitType = null;
                    }else{
                        setBackground(new TextureRegionDrawable(new TextureRegion(nonSpawnAreaTexture)));
                        nextUnitType = UnitType.MELEE;
                    }
                }

            }
        };
        btnNewUnit1.addListener(listener);
    }

    public void initializeNonSpawnAreaShadow(Boundary spawnArea){
        Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
        pixmap.setBlending(Pixmap.Blending.None);
        pixmap.setColor(0,0,0,150);
        //pixmap.drawRectangle(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        pixmap.fillRectangle(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        pixmap.setColor(Color.CLEAR);
        pixmap.fillRectangle((int)(spawnArea.getUpperLeft().x), (int)(spawnArea.getUpperLeft().y),
                (int)(spawnArea.getUpperRight().x - spawnArea.getUpperLeft().x),
                (int) (spawnArea.getLowerRight().y - spawnArea.getUpperRight().y));
        System.out.println((int)(spawnArea.getUpperLeft().x) +","+ (int)(spawnArea.getUpperLeft().y)+","+
                (int)(spawnArea.getUpperRight().x - spawnArea.getUpperLeft().x)+","+
                (int) (spawnArea.getLowerLeft().y - spawnArea.getUpperRight().y));
        nonSpawnAreaTexture = new Texture(pixmap);
        pixmap.dispose();
    }

    public UnitType getUnitToSpawn(){
        setBackground((Drawable) null);
        UnitType current = nextUnitType;
        nextUnitType = null;
        return current;
    }

    public void dispose(){
        nonSpawnAreaTexture.dispose();
    }


}
