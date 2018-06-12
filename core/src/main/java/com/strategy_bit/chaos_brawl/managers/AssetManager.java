package com.strategy_bit.chaos_brawl.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * manager for holding references to assets
 *
 * @author AIsopp
 * @version 1.0
 * @since 22.03.2018
 */

public class AssetManager {
    private static final String UNIT_PATH = "units/";
    private static final String UI_PATH = "user_interface/";
    private static final String ENVIRONMENT_PATH = "environment/";
    private static final String ANIM_PATH = "animations/";
    private static final String MARKER_PATH = "markers/";

    public BitmapFont font;
    public UnitManager unitManager;
    public Map<String, TextureRegion> markers;
    public Map<String, TextureRegion> skins;
    public Map<String, Sound> sounds;
    public  Skin defaultSkin;
    public TextureAtlas explosionSkin;
    public TextureAtlas smokeSkin;
    public TextureAtlas starSkin;
    public FileHandle starParticle;
    public FileHandle explosionParticle;
    public FileHandle smokeParticle;
    public NinePatch resourceSkinOuter;
    public NinePatch resourceSkinInner;
    public NinePatch resourceSkinMiddle;
    public NinePatch hpSkinOuter;
    public NinePatch hpSkinInner;
    public Slider.SliderStyle sliderStyle;
    public TextureRegion defaultTile;
    public TextureRegion waterTile;
    public TextureRegion dirtTile;
    public Array<FileHandle> maps;
    public Array<Float> config;
    public Array<Float> config2;
    public Array<Float> spawn;
    public Array<Float> spawn4;
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
        skins = new HashMap<>();
        unitManager=new UnitManager();
        sounds = new HashMap<>();
        markers=new HashMap<>();
    }

    public  void loadAssets(){
        markers.put("default",new TextureRegion(new Texture(MARKER_PATH+"default.png")));


        //unit stats
        unitManager.readFile(UNIT_PATH+"units.json");

        // Environment
        defaultTile = new TextureRegion(new Texture(ENVIRONMENT_PATH+"default_tile.png"));
        waterTile = new TextureRegion(new Texture ( ENVIRONMENT_PATH+"water_tile.png"));
        dirtTile = new TextureRegion(new Texture ( ENVIRONMENT_PATH+"dirt_tile.png"));

        // animations
        explosionSkin = new TextureAtlas(ANIM_PATH+"explosions.atlas");
        smokeSkin = new TextureAtlas(ANIM_PATH+"smoke.atlas");
        starSkin = new TextureAtlas(ANIM_PATH+"star.atlas");
        starParticle = Gdx.files.internal(ANIM_PATH+"star.p");
        explosionParticle = Gdx.files.internal(ANIM_PATH+"explosions.p");
        smokeParticle = Gdx.files.internal(ANIM_PATH+"smoke.p");

        // user interface & other
        defaultSkin = new Skin(Gdx.files.internal(UI_PATH+"default/skin.json"));
        resourceSkinOuter = new NinePatch(new Texture(UI_PATH+"resourceBarOuterBorder.png"),12,12,12,12);
        resourceSkinInner = new NinePatch(new Texture(UI_PATH+"resourceBarInner.png"),0,16,7,7);
        resourceSkinMiddle= new NinePatch(new Texture(UI_PATH+"resourceBarMiddle.png"),0,0,21,23);
        hpSkinOuter = new NinePatch(new Texture("hpBarbackgroundRed.png"));
        hpSkinInner = new NinePatch(new Texture("hpBarInner.png"));
        victoryScreen = new Texture("victory.png");
        defeatScreen = new Texture("defeat.png");
        progressHPbarStyle = new ProgressBar.ProgressBarStyle(new NinePatchDrawable(hpSkinOuter), new NinePatchDrawable(hpSkinInner));
        progressHPbarStyle.knobBefore = progressHPbarStyle.knob;
        sliderStyle = new Slider.SliderStyle(new NinePatchDrawable(hpSkinOuter), new NinePatchDrawable(hpSkinInner));
        sliderStyle.knobBefore = sliderStyle.knob;
        maps = new Array<>();
        maps.add(Gdx.files.internal("maps/map1.txt"));
        maps.add(Gdx.files.internal("maps/map2.txt"));
        maps.add(Gdx.files.internal("maps/map3.txt"));
        maps.add(Gdx.files.internal("maps/4playermap.txt"));

        FileHandle handle = Gdx.files.internal("maps/Config2Players.json");
        FileHandle handle1 = Gdx.files.internal("maps/SpawnAreas2.json");
        FileHandle handle2 = Gdx.files.internal("maps/Config4Players.json");
        FileHandle handle3 = Gdx.files.internal("maps/SpawnAreas4.json");
        config = new Array<>();
        config2 = new Array<>();
        spawn = new Array<>();
        spawn4 = new Array<>();
        setArray(config, handle);
        setArray(config2, handle2);
        setArray(spawn, handle1);
        setArray(spawn4, handle3);
    }

    private void setArray(Array<Float> arr, FileHandle handle) {
        JsonReader jsonReader = new JsonReader();
        JsonValue value = jsonReader.parse(handle);
        JsonValue child = value.child();
        while (child != null) {
            arr.add(child.getFloat("x"));
            arr.add(child.getFloat("y"));
            child = child.next();
        }
    }

    public void dispose() {
        defaultSkin.dispose();
        Iterator it = skins.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            ((TextureRegion)(pair.getValue())).getTexture().dispose();
            it.remove();
        }
        it=markers.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            ((TextureRegion)(pair.getValue())).getTexture().dispose();
            it.remove();
        }
        defaultTile.getTexture().dispose();
        waterTile.getTexture().dispose();
        dirtTile.getTexture().dispose();
        resourceSkinOuter.getTexture().dispose();
        resourceSkinInner.getTexture().dispose();
        resourceSkinMiddle.getTexture().dispose();
        hpSkinOuter.getTexture().dispose();
        hpSkinInner.getTexture().dispose();
        victoryScreen.dispose();
        defeatScreen.dispose();

    }

    public void addSkin(String name,String path){
        skins.put(name,new TextureRegion(new Texture(path)));
    }
}
