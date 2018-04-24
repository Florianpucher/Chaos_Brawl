package com.strategy_bit.chaos_brawl.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * manager for holding references to assets
 *
 * @author AIsopp
 * @version 1.0
 * @since 22.03.2018
 */

public class AssetManager {
    private static final String UNIT_PATH = "units/";
    private static final String BUILDING_PATH = "buildings/";
    private static final String UI_PATH = "user_interface/";
    private static final String ENVIRONMENT_PATH = "environment/";


    public  Skin defaultSkin;
    public TextureRegion archerSkin;
    public TextureRegion swordFighterSkin;
    public TextureRegion projectileSkin;
    public NinePatch resourceSkinOuter;
    public NinePatch resourceSkinInner;
    public NinePatch resourceSkinMiddle;
    public TextureRegion defaultTile;
    public TextureRegion waterTile;
    public TextureRegion dirtTile;
    //public TextureRegion TowerSkin;
    //public TextureRegion TowerSkinP;
    //public TextureRegion BaseSkin;
    public TextureRegion ballistaTowerSkin;
    public TextureRegion mainTowerSkin;

    private static AssetManager instance;


    public static AssetManager getInstance(){
        if(instance == null){
            instance = new AssetManager();
        }
        return instance;
    }

    private AssetManager(){

    }

    public  void loadAssets(){

        archerSkin = new TextureRegion(new Texture(UNIT_PATH+ "unit.png"));
        swordFighterSkin = new TextureRegion(new Texture(UNIT_PATH+ "unit_sword_fighter.png"));
        projectileSkin=new TextureRegion(new Texture(UNIT_PATH+ "arrow.png"));


        defaultTile = new TextureRegion(new Texture(ENVIRONMENT_PATH+"default_tile.png"));
        waterTile = new TextureRegion(new Texture ( ENVIRONMENT_PATH+"water_tile.png"));
        dirtTile = new TextureRegion(new Texture ( ENVIRONMENT_PATH+"dirt_tile.png"));
        //TowerSkin = new TextureRegion(new Texture("Tower.png"));
        //TowerSkinP = new TextureRegion(new Texture("Towerp.png"));
        //BaseSkin = new TextureRegion(new Texture("Base.png"));
        ballistaTowerSkin = new TextureRegion(new Texture(BUILDING_PATH+"ballista_tower.png"));
        mainTowerSkin = new TextureRegion(new Texture(BUILDING_PATH+"wall.png"));

        defaultSkin = new Skin(Gdx.files.internal(UI_PATH+"default/skin.json"));
        resourceSkinOuter = new NinePatch(new Texture(UI_PATH+"resourceBarOuterBorder.png"),12,12,12,12);
        resourceSkinInner = new NinePatch(new Texture(UI_PATH+"resourceBarInner.png"),0,16,7,7);
        resourceSkinMiddle= new NinePatch(new Texture(UI_PATH+"resourceBarMiddle.png"),0,0,21,23);
    }


    public void dispose(){
        defaultSkin.dispose();
        archerSkin.getTexture().dispose();
        swordFighterSkin.getTexture().dispose();
        projectileSkin.getTexture().dispose();
        defaultTile.getTexture().dispose();
        waterTile.getTexture().dispose();
        dirtTile.getTexture().dispose();
        //TowerSkin.getTexture().dispose();
        //BaseSkin.getTexture().dispose();
        ballistaTowerSkin.getTexture().dispose();
        mainTowerSkin.getTexture().dispose();
        resourceSkinOuter.getTexture().dispose();
        resourceSkinInner.getTexture().dispose();
        resourceSkinMiddle.getTexture().dispose();
    }
}
