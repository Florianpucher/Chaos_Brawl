package com.strategy_bit.chaos_brawl.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

/**
 * manager for holding references to assets
 *
 * @author AIsopp
 * @version 1.0
 * @since 22.03.2018
 */

public class AssetManager {


    public Skin defaultSkin;
    public TextureRegion playerSkin;
    public TextureRegion projectileSkin;
    public NinePatch resourceSkinOuter;
    public NinePatch resourceSkinInner;
    public NinePatch hpSkinOuter;
    public NinePatch hpSkinInner;
    public TextureRegion defaultTile;
    public TextureRegion waterTile;
    public TextureRegion dirtTile;
    public TextureRegion TowerSkin;
    public TextureRegion TowerSkinP;
    public TextureRegion BaseSkin;
    public TextureRegion ballistaTowerSkin;
    public TextureRegion wallSkin;
    public Texture victoryScreen;
    public Texture defeatScreen;
    public ProgressBar.ProgressBarStyle progressHPbarStyle;

    private static AssetManager instance;


    public static AssetManager getInstance() {
        if (instance == null) {
            instance = new AssetManager();
        }
        return instance;
    }

    private AssetManager() {

    }

    public void loadAssets() {
        defaultSkin = new Skin(Gdx.files.internal("default/skin.json"));
        playerSkin = new TextureRegion(new Texture("unit.png"));
        projectileSkin = new TextureRegion(new Texture("projectile.png"));
        defaultTile = new TextureRegion(new Texture("default_tile.png"));
        waterTile = new TextureRegion(new Texture("water_tile.png"));
        dirtTile = new TextureRegion(new Texture("dirt_tile.png"));
        TowerSkin = new TextureRegion(new Texture("Tower.png"));
        TowerSkinP = new TextureRegion(new Texture("Towerp.png"));
        BaseSkin = new TextureRegion(new Texture("Base.png"));
        ballistaTowerSkin = new TextureRegion(new Texture("ballista_tower.png"));
        wallSkin = new TextureRegion(new Texture("wall.png"));
        victoryScreen = new Texture("victory.png");
        defeatScreen = new Texture("defeat.png");
        resourceSkinOuter = new NinePatch(new Texture("resourceBarOuterBorder.9.png"));
        resourceSkinInner = new NinePatch(new Texture("resourceBarInner.9.png"));
        hpSkinOuter = new NinePatch(new Texture("hpBarbackgroundRed.png"));
        hpSkinInner = new NinePatch(new Texture("hpBarInner.png"));
        progressHPbarStyle = new ProgressBar.ProgressBarStyle(new NinePatchDrawable(hpSkinOuter), new NinePatchDrawable(hpSkinInner));
        progressHPbarStyle.knobBefore = progressHPbarStyle.knob;
    }


    public void dispose() {
        defaultSkin.dispose();
        playerSkin.getTexture().dispose();
        projectileSkin.getTexture().dispose();
        defaultTile.getTexture().dispose();
        waterTile.getTexture().dispose();
        dirtTile.getTexture().dispose();
        TowerSkin.getTexture().dispose();
        BaseSkin.getTexture().dispose();
        ballistaTowerSkin.getTexture().dispose();
        wallSkin.getTexture().dispose();
        resourceSkinOuter.getTexture().dispose();
        resourceSkinInner.getTexture().dispose();
        hpSkinOuter.getTexture().dispose();
        hpSkinInner.getTexture().dispose();
        //victoryScreen.getTexture().dispose();
        //victoryScreen.getTexture().dispose();

    }
}
