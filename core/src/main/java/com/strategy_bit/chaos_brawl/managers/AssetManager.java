package com.strategy_bit.chaos_brawl.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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

    public UnitManager unitManager;
    public Map<String, TextureRegion> skins;
    public Map<String, Sound> sounds;
    public  Skin defaultSkin;
    public TextureAtlas explosionSkin;
    public TextureAtlas smokeSkin;
    public FileHandle explosionParticle;
    public FileHandle smokeParticle;
    public NinePatch resourceSkinOuter;
    public NinePatch resourceSkinInner;
    public NinePatch resourceSkinMiddle;
    public NinePatch hpSkinOuter;
    public NinePatch hpSkinInner;
    public TextureRegion defaultTile;
    public TextureRegion waterTile;
    public Music music;
    public TextureRegion dirtTile;
    public Array<FileHandle> maps;
    public Array<Float> config;
    public Array<Float> config2;
    public Array<Float> spawn;
    public Texture victoryScreen;
    public Texture defeatScreen;
    public ProgressBar.ProgressBarStyle progressHPbarStyle;
    public Sound victory;
    public Sound defeat;
    public Sound attackBow;
    public Sound attackSword;
    public Sound attackFireball;
    public Sound attackCannonBall;
    public Sound hitArrow;
    public Sound drawSword;
    public Sound drawKatana;
    public Sound explosionSound;
    public Sound upgradeExecuted;
    public Sound upgradeExecutedTower;
    private Array<Sound> drawKatanas;

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
    }

    public  void loadAssets(){


        //unit stats
        unitManager.readFile(UNIT_PATH+"units.json");

        // Environment
        defaultTile = new TextureRegion(new Texture(ENVIRONMENT_PATH+"default_tile.png"));
        waterTile = new TextureRegion(new Texture ( ENVIRONMENT_PATH+"water_tile.png"));
        dirtTile = new TextureRegion(new Texture ( ENVIRONMENT_PATH+"dirt_tile.png"));

        // animations
        explosionSkin = new TextureAtlas(ANIM_PATH+"explosions.atlas");
        smokeSkin = new TextureAtlas(ANIM_PATH+"smoke.atlas");
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
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/mainSoundTrack.mp3"));
        music.setVolume(1.0f);
        music.setLooping(true);
        maps = new Array<>();
        maps.add(Gdx.files.internal("maps/map1.txt"));
        maps.add(Gdx.files.internal("maps/map2.txt"));
        maps.add(Gdx.files.internal("maps/map3.txt"));
        maps.add(Gdx.files.internal("maps/4playermap.txt"));

        FileHandle handle = Gdx.files.internal("maps/Config2Players.json");
        FileHandle handle1 = Gdx.files.internal("maps/SpawnAreas2.json");
        FileHandle handle2 = Gdx.files.internal("maps/Config4Players.json");
        config = new Array<>();
        config2 = new Array<>();
        spawn = new Array<>();
        setArray(config, handle);
        setArray(config2, handle2);
        setArray(spawn, handle1);


        // sounds
        victory =Gdx.audio.newSound(Gdx.files.internal("sounds/Victory.mp3"));
        defeat =Gdx.audio.newSound(Gdx.files.internal("sounds/Defeat.mp3"));
        upgradeExecuted =Gdx.audio.newSound(Gdx.files.internal("sounds/GameHUD/upgradeExecuted.wav"));
        upgradeExecutedTower =Gdx.audio.newSound(Gdx.files.internal("sounds/GameHUD/upgradeExecutedTower.wav"));
        attackSword =Gdx.audio.newSound(Gdx.files.internal("sounds/Weapon Whoosh/Sabre,Swing,Whoosh,Sharp.mp3"));
        attackBow =Gdx.audio.newSound(Gdx.files.internal("sounds/Projectiles/Bow,Recurve,Scythian,Arrow,Heavy,Fly,By,Whiz,Mid Tone,Two Tone - distant release.wav"));
        attackFireball =Gdx.audio.newSound(Gdx.files.internal("sounds/Projectiles/fireball.wav"));
        attackCannonBall = Gdx.audio.newSound(Gdx.files.internal("sounds/Projectiles/cannonball.wav"));
        hitArrow =Gdx.audio.newSound(Gdx.files.internal("sounds/Hits/Mace,Hit,Spear,Haft,Lazy,Messy.mp3"));
        drawSword =Gdx.audio.newSound(Gdx.files.internal("sounds/Draw and Replace Weapon/Sabre,Draw,Scabbard,Fast,Loose,Rough.mp3"));
        drawKatana =Gdx.audio.newSound(Gdx.files.internal("sounds/Draw and Replace Weapon/Katana,Replace,Scabbard,Fast,Ripple.mp3"));
        drawKatanas =new Array<>();
        drawKatanas.add(Gdx.audio.newSound(Gdx.files.internal("sounds/Draw and Replace Weapon/Katana,Draw,Scabbard,Fast,Deep.mp3")));
        drawKatanas.add(Gdx.audio.newSound(Gdx.files.internal("sounds/Draw and Replace Weapon/Katana,Draw,Scabbard,Fast,Deep.mp3")));
        drawKatanas.add(Gdx.audio.newSound(Gdx.files.internal("sounds/Draw and Replace Weapon/Katana,Draw,Scabbard,Fast,Determined.mp3")));
        drawKatanas.add(Gdx.audio.newSound(Gdx.files.internal("sounds/Draw and Replace Weapon/Katana,Draw,Scabbard,Fast,Shuffle.mp3")));
        drawKatanas.add(Gdx.audio.newSound(Gdx.files.internal("sounds/Draw and Replace Weapon/Katana,Draw,Scabbard,Fast,Steady.mp3")));
        drawKatanas.add(Gdx.audio.newSound(Gdx.files.internal("sounds/Draw and Replace Weapon/Katana,Draw,Scabbard,Fast,Strong.mp3")));
        drawKatanas.add(Gdx.audio.newSound(Gdx.files.internal("sounds/Draw and Replace Weapon/Katana,Draw,Scabbard,Fast.mp3")));
        drawKatanas.add(Gdx.audio.newSound(Gdx.files.internal("sounds/Draw and Replace Weapon/Katana,Draw,Scabbard,Slow,Complex.mp3")));
        drawKatanas.add(Gdx.audio.newSound(Gdx.files.internal("sounds/Draw and Replace Weapon/Katana,Draw,Scabbard,Slow,Methodical.mp3")));
        drawKatanas.add(Gdx.audio.newSound(Gdx.files.internal("sounds/Draw and Replace Weapon/Katana,Draw,Scabbard,Slow,Steady.mp3")));

        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/Animations/explosion.mp3"));



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
        music.dispose();
        attackBow.dispose();
        attackSword.dispose();
        attackFireball.dispose();
        attackCannonBall.dispose();
        hitArrow.dispose();
        drawSword.dispose();
        drawKatana.dispose();
        explosionSound.dispose();
        victory.dispose();
        defeat.dispose();
        upgradeExecuted.dispose();
        upgradeExecutedTower.dispose();
        for (Sound s:
                drawKatanas) {
            s.dispose();
        }

    }

    public Sound getRandomDrawKatanaSound(){
        Random random = new Random();
        return drawKatanas.get(random.nextInt(drawKatanas.size));
    }

    public void addSkin(String name,String path){
        skins.put(name,new TextureRegion(new Texture(path)));
    }

    public void addSound(String name,String path){
        sounds.put(name, Gdx.audio.newSound(Gdx.files.internal(path)));
    }

}
