package com.strategy_bit.chaos_brawl.player_input_output.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.resource_system.Resource;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.types.UnitType;
import com.strategy_bit.chaos_brawl.util.Boundary;

/**
 * holds information of the gameHUD
 * @author AIsopp
 * @version 1.0
 * @since 16.04.2018
 */
public class GameHUD extends Table{

    private static final String NEW_UNIT_1 = "Archer";
    private static final String NEW_UNIT_2 = "Fighter";
    private static final String NEW_UNIT_3 = "Knight";

    private UnitType nextUnitType;
    private Texture nonSpawnAreaTexture;
    public ProgressBar manaBar;
    private Array<BrawlButton> brawlButtons;

    public GameHUD(Boundary spawnArea) {
        super(AssetManager.getInstance().defaultSkin);
        AssetManager assetManager = AssetManager.getInstance();
        initializeNonSpawnAreaShadow(spawnArea);

        final BrawlButton btnNewUnit1 = new BrawlButton(NEW_UNIT_1, assetManager.defaultSkin,UnitType.RANGED);
        btnNewUnit1.setName(NEW_UNIT_1);
        setFillParent(true);
        final BrawlButton btnNewUnit2 = new BrawlButton(NEW_UNIT_2, assetManager.defaultSkin,UnitType.SWORDFIGHTER);
        btnNewUnit2.setName(NEW_UNIT_2);
        setFillParent(true);
        final BrawlButton btnNewUnit3 = new BrawlButton(NEW_UNIT_3, assetManager.defaultSkin,UnitType.KNIGHT);
        btnNewUnit3.setName(NEW_UNIT_3);
        brawlButtons=new Array<>();
        brawlButtons.add(btnNewUnit1);
        brawlButtons.add(btnNewUnit2);
        brawlButtons.add(btnNewUnit3);
        setFillParent(true);

        NinePatchDrawable resourceBarOuter = new NinePatchDrawable(assetManager.resourceSkinOuter);
        NinePatchDrawable resourceBar = new NinePatchDrawable(assetManager.resourceSkinInner);
        ProgressBar.ProgressBarStyle progressBarStyle = new ProgressBar.ProgressBarStyle(resourceBarOuter, new NinePatchDrawable(assetManager.resourceSkinMiddle));
        progressBarStyle.knobBefore=resourceBar;
        manaBar = new ProgressBar(0, 100, 0.1f, false, progressBarStyle);

        manaBar.setValue(0);
        setBackground((Drawable) null);

        //debugCell();
        top();
        //add actors to UI
        add(manaBar).top().width(Gdx.graphics.getWidth()/2).height(Gdx.graphics.getHeight()/9);
        row().height(7 * Gdx.graphics.getHeight()/9 );
        add();
        row().height(Gdx.graphics.getHeight()/9 ).width(Gdx.graphics.getWidth());
        // add own table for organizing buttons
        Table lowerUI = new Table(assetManager.defaultSkin);
        lowerUI.right();
        add(lowerUI).width(Gdx.graphics.getWidth());
        lowerUI.add(btnNewUnit1).right().height(lowerUI.getPrefHeight());
        lowerUI.add(btnNewUnit2).right().height(lowerUI.getPrefHeight());
        lowerUI.add(btnNewUnit3).right().height(lowerUI.getPrefHeight());


        ClickListener listener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                String name = event.getListenerActor().getName();
                if(name.equals(NEW_UNIT_1)&&brawlButtons.get(0).isActivated()){
                    if(nextUnitType == UnitType.RANGED){
                        nextUnitType = null;
                    }else{
                        nextUnitType = UnitType.RANGED;
                    }
                }
                if(name.equals( NEW_UNIT_2)&&brawlButtons.get(1).isActivated()){
                    if(nextUnitType == UnitType.SWORDFIGHTER){
                        nextUnitType = null;
                    }else{
                        nextUnitType = UnitType.SWORDFIGHTER;
                    }
                }
                if(name.equals( NEW_UNIT_3)&&brawlButtons.get(2).isActivated()){
                    if(nextUnitType == UnitType.KNIGHT){
                        nextUnitType = null;
                    }else{
                        nextUnitType = UnitType.KNIGHT;
                    }
                }



                if(nextUnitType != null){
                    setBackground(new TextureRegionDrawable(new TextureRegion(nonSpawnAreaTexture)));
                }else{
                    setBackground((Drawable) null);
                }

            }
        };
        btnNewUnit1.addListener(listener);
        btnNewUnit2.addListener(listener);
        btnNewUnit3.addListener(listener);
    }

    private void initializeNonSpawnAreaShadow(Boundary spawnArea){
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
public void updateBtns(float v){
    for (BrawlButton brawlButton:
         brawlButtons) {
        brawlButton.update(v);
    }
}


}
