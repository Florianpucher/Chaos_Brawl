package com.strategy_bit.chaos_brawl.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
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

/**
 * manager for holding references to assets
 *
 * @author AIsopp
 * @version 1.0
 * @since 22.03.2018
 */

public class AssetManager {
    public static final String MENU_BACKGROUND = "background";

    private static final String UI_PATH = "user_interface/";
    private static final String ENVIRONMENT_PATH = "environment/";
    private static final String ANIM_PATH = "animations/";
    private static final String MARKER_PATH = "markers/";
    private static final String UI_SWORD_IMAGE = "sword_image_ui";

    private Map<String, TextureRegion> markers;
    private Map<String, TextureRegion> unitMarkers;
    private Map<String, TextureRegion> skins;
    private Map<String, Sound> sounds;
    private Map<Integer, NinePatch> hpSkinsInner;
    private Map<Integer, ProgressBar.ProgressBarStyle> hpBarStyles;

    private Array<FileHandle> maps;
    private Array<Float> config;
    private Array<Float> config2;
    private Array<Float> spawn;
    private Array<Float> spawn4;

    private TextureRegion defaultTile;
    private TextureRegion waterTile;
    private TextureRegion dirtTile;

    private TextureAtlas explosionSkin;
    private TextureAtlas smokeSkin;
    private TextureAtlas starSkin;

    private Texture victoryScreen;
    private Texture defeatScreen;

    private NinePatch resourceSkinOuter;
    private NinePatch resourceSkinInner;
    private NinePatch resourceSkinMiddle;
    private NinePatch hpSkinOuter;

    private FileHandle starParticle;
    private FileHandle explosionParticle;
    private FileHandle smokeParticle;

    private Skin defaultSkin;

    private Slider.SliderStyle sliderStyle;

    private static AssetManager instance;

    public static AssetManager getInstance() {
        if (instance == null) {
            instance = new AssetManager();
        }
        return instance;
    }

    private AssetManager() {
        skins = new HashMap<>();
        sounds = new HashMap<>();
        markers = new HashMap<>();
        unitMarkers = new HashMap<>();
        hpBarStyles = new HashMap<>();
        hpSkinsInner = new HashMap<>();
    }

    public void loadAssets() {
        markers.put("default", new TextureRegion(new Texture(MARKER_PATH + "default.png")));
        unitMarkers.put("triangle", new TextureRegion(new Texture(MARKER_PATH + "triangle.png")));
        unitMarkers.put("square", new TextureRegion(new Texture(MARKER_PATH + "square.png")));
        unitMarkers.put("square2", new TextureRegion(new Texture(MARKER_PATH + "square2.png")));
        unitMarkers.put("star", new TextureRegion(new Texture(MARKER_PATH + "star.png")));
        skins.put(UI_SWORD_IMAGE, new TextureRegion(new Texture(UI_PATH + "sword.png")));


        // Environment
        defaultTile = new TextureRegion(new Texture(ENVIRONMENT_PATH + "default_tile.png"));
        waterTile = new TextureRegion(new Texture(ENVIRONMENT_PATH + "water_tile.png"));
        dirtTile = new TextureRegion(new Texture(ENVIRONMENT_PATH + "dirt_tile.png"));

        // animations
        explosionSkin = new TextureAtlas(ANIM_PATH + "explosions.atlas");
        smokeSkin = new TextureAtlas(ANIM_PATH + "smoke.atlas");
        starSkin = new TextureAtlas(ANIM_PATH + "star.atlas");
        starParticle = Gdx.files.internal(ANIM_PATH + "star.p");
        explosionParticle = Gdx.files.internal(ANIM_PATH + "explosions.p");
        smokeParticle = Gdx.files.internal(ANIM_PATH + "smoke.p");

        // user interface & other
        defaultSkin = new Skin(Gdx.files.internal(UI_PATH + "skin/skin.json"));
        resourceSkinOuter = new NinePatch(new Texture(UI_PATH + "resourceBarOuterBorder.png"), 12, 12, 12, 12);
        resourceSkinInner = new NinePatch(new Texture(UI_PATH + "resourceBarInner.png"), 0, 16, 7, 7);
        resourceSkinMiddle = new NinePatch(new Texture(UI_PATH + "resourceBarMiddle.png"), 0, 0, 21, 23);
        hpSkinOuter = new NinePatch(new Texture("hpBarbackgroundRed.png"));
        NinePatch hpSkinInner = new NinePatch(new Texture("hpBarInner.png"));
        NinePatch hpSkinInner2 = new NinePatch(new Texture("hpBarInner2.png"));
        NinePatch hpSkinInner3 = new NinePatch(new Texture("hpBarInner3.png"));
        NinePatch hpSkinInner4 = new NinePatch(new Texture("hpBarInner4.png"));
        hpSkinsInner.put(0, hpSkinInner);
        hpSkinsInner.put(1, hpSkinInner2);
        hpSkinsInner.put(2, hpSkinInner3);
        hpSkinsInner.put(3, hpSkinInner4);
        victoryScreen = new Texture("victory.png");
        defeatScreen = new Texture("defeat.png");
        for (int i = 0; i < 4; i++) {
            ProgressBar.ProgressBarStyle progressHPbarStyle = new ProgressBar.ProgressBarStyle(new NinePatchDrawable(hpSkinOuter), new NinePatchDrawable(hpSkinsInner.get(i)));
            progressHPbarStyle.knobBefore = progressHPbarStyle.knob;
            hpBarStyles.put(i, progressHPbarStyle);
        }

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
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            ((TextureRegion) (pair.getValue())).getTexture().dispose();
            it.remove();
        }
        it = markers.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            ((TextureRegion) (pair.getValue())).getTexture().dispose();
            it.remove();
        }
        it = hpSkinsInner.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            ((NinePatch) (pair.getValue())).getTexture().dispose();
            it.remove();
        }
        defaultTile.getTexture().dispose();
        waterTile.getTexture().dispose();
        dirtTile.getTexture().dispose();
        resourceSkinOuter.getTexture().dispose();
        resourceSkinInner.getTexture().dispose();
        resourceSkinMiddle.getTexture().dispose();
        hpSkinOuter.getTexture().dispose();
        victoryScreen.dispose();
        defeatScreen.dispose();

    }

    public Map<String, TextureRegion> getMarkers() {
        return markers;
    }

    public Map<String, TextureRegion> getSkins() {
        return skins;
    }

    public Map<String, Sound> getSounds() {
        return sounds;
    }

    public void addSkin(String name, String path) {
        skins.put(name, new TextureRegion(new Texture(path)));
    }

    public Skin getDefaultSkin() {
        return defaultSkin;
    }

    public TextureAtlas getExplosionSkin() {
        return explosionSkin;
    }

    public TextureAtlas getSmokeSkin() {
        return smokeSkin;
    }

    public TextureAtlas getStarSkin() {
        return starSkin;
    }

    public FileHandle getStarParticle() {
        return starParticle;
    }

    public FileHandle getExplosionParticle() {
        return explosionParticle;
    }

    public FileHandle getSmokeParticle() {
        return smokeParticle;
    }

    public NinePatch getResourceSkinOuter() {
        return resourceSkinOuter;
    }

    public NinePatch getResourceSkinInner() {
        return resourceSkinInner;
    }

    public NinePatch getResourceSkinMiddle() {
        return resourceSkinMiddle;
    }

    public NinePatch getHpSkinOuter() {
        return hpSkinOuter;
    }

    public Map<Integer, NinePatch> getHpSkinsInner() {
        return hpSkinsInner;
    }

    public Map<String, TextureRegion> getUnitMarkers() {
        return unitMarkers;
    }

    public Slider.SliderStyle getSliderStyle() {
        return sliderStyle;
    }

    public TextureRegion getDefaultTile() {
        return defaultTile;
    }

    public TextureRegion getWaterTile() {
        return waterTile;
    }

    public TextureRegion getDirtTile() {
        return dirtTile;
    }

    public Array<FileHandle> getMaps() {
        return maps;
    }

    public Array<Float> getConfig() {
        return config;
    }

    public Array<Float> getConfig2() {
        return config2;
    }

    public Array<Float> getSpawn() {
        return spawn;
    }

    public Array<Float> getSpawn4() {
        return spawn4;
    }

    public Texture getVictoryScreen() {
        return victoryScreen;
    }

    public Texture getDefeatScreen() {
        return defeatScreen;
    }

    public Map<Integer, ProgressBar.ProgressBarStyle> getHpBarStyles() {
        return hpBarStyles;
    }


}
