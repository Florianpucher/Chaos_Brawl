package com.strategy_bit.chaos_brawl.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;

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
    private static final String BUILDING_PATH = "buildings/";
    private static final String UI_PATH = "user_interface/";
    private static final String ENVIRONMENT_PATH = "environment/";


    public  Skin defaultSkin;
    public TextureRegion archerSkin;
    public TextureRegion swordFighterSkin;
    public TextureRegion knightSkin;
    public TextureRegion projectileSkin;
    public TextureRegion explosionSkin;
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
    public TextureRegion ballistaTowerSkin;
    public TextureRegion mainTowerSkin;
    public Texture victoryScreen;
    public Texture defeatScreen;
    public ProgressBar.ProgressBarStyle progressHPbarStyle;
    public Sound attackBow;
    public Sound attackSword;
    public Sound hitArrow;
    public Sound drawSword;
    public Sound drawKatana;
    private Array<Sound> drawKatanas;


    private static AssetManager instance;


    public static AssetManager getInstance() {
        if (instance == null) {
            instance = new AssetManager();
        }
        return instance;
    }

    private AssetManager() {

    }

    public  void loadAssets(){

        archerSkin = new TextureRegion(new Texture(UNIT_PATH+ "unit_archer.png"));
        swordFighterSkin = new TextureRegion(new Texture(UNIT_PATH+ "unit_sword_fighter.png"));
        knightSkin = new TextureRegion(new Texture(UNIT_PATH+ "unit_knight.png"));     // need a new Knightskin
        projectileSkin=new TextureRegion(new Texture(UNIT_PATH+ "arrow.png"));


        defaultTile = new TextureRegion(new Texture(ENVIRONMENT_PATH+"default_tile.png"));
        waterTile = new TextureRegion(new Texture ( ENVIRONMENT_PATH+"water_tile.png"));
        dirtTile = new TextureRegion(new Texture ( ENVIRONMENT_PATH+"dirt_tile.png"));
        ballistaTowerSkin = new TextureRegion(new Texture(BUILDING_PATH+"ballista_tower.png"));
        mainTowerSkin = new TextureRegion(new Texture(BUILDING_PATH+"wall.png"));
        explosionSkin = new TextureRegion(new Texture(BUILDING_PATH+ "explosion.png"));

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
        music.setVolume(0f);
        music.setLooping(true);
        maps = new Array<>();
        maps.add(Gdx.files.internal("maps/map1.txt"));
        maps.add(Gdx.files.internal("maps/map2.txt"));
        maps.add(Gdx.files.internal("maps/map3.txt"));

        attackSword =Gdx.audio.newSound(Gdx.files.internal("sounds/Weapon Whoosh/Sabre,Swing,Whoosh,Sharp.mp3"));
        attackBow =Gdx.audio.newSound(Gdx.files.internal("sounds/Bow, Crossbow/Bow,Recurve,Scythian,Arrow,Heavy,Fly,By,Whiz,Mid Tone,Two Tone - distant release.mp3"));
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


    }


    public void dispose() {
        defaultSkin.dispose();
        archerSkin.getTexture().dispose();
        swordFighterSkin.getTexture().dispose();
        knightSkin.getTexture().dispose();
        projectileSkin.getTexture().dispose();
        explosionSkin.getTexture().dispose();
        defaultTile.getTexture().dispose();
        waterTile.getTexture().dispose();
        dirtTile.getTexture().dispose();
        ballistaTowerSkin.getTexture().dispose();
        mainTowerSkin.getTexture().dispose();
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
        hitArrow.dispose();
        drawSword.dispose();
        drawKatana.dispose();
        for (Sound s:
                drawKatanas) {
            s.dispose();
        }

    }

    public Sound getRandomDrawKatanaSound(){
        Random random = new Random();
        return drawKatanas.get(random.nextInt(drawKatanas.size));
    }
}
