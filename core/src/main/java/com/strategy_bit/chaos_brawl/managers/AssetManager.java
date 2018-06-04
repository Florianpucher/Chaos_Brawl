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
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;

import java.awt.Font;
import java.io.File;
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
    private static final String ANIM_PATH = "animations/";

    private boolean playable;

    public BitmapFont font;
    public  Skin defaultSkin;
    public TextureRegion archerSkin;
    public TextureRegion swordFighterSkin;
    public TextureRegion knightSkin;
    public TextureRegion mageSkin;
    public TextureRegion berserkerSkin;
    public TextureRegion templarSkin;
    public TextureRegion projectileSkin;
    public TextureRegion fireballSkin;
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
    public TextureRegion ballistaTowerSkin;
    public TextureRegion mainTowerSkin;
    public Texture victoryScreen;
    public Texture defeatScreen;
    public ProgressBar.ProgressBarStyle progressHPbarStyle;
    public Sound victory;
    public Sound defeat;
    public Sound attackBow;
    public Sound attackSword;
    public Sound attackFireball;
    public Sound hitArrow;
    public Sound drawSword;
    public Sound drawKatana;
    public Sound explosionSound;
    public Sound upgradeExecuted;
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

        //font = new BitmapFont(Gdx.files.internal("fonts/default.otf"));

        // units
        archerSkin = new TextureRegion(new Texture(UNIT_PATH+ "unit_archer.png"));
        swordFighterSkin = new TextureRegion(new Texture(UNIT_PATH+ "unit_sword_fighter.png"));
        knightSkin = new TextureRegion(new Texture(UNIT_PATH+ "unit_knight.png"));
        mageSkin = new TextureRegion(new Texture(UNIT_PATH+ "unit_mage.png"));
        berserkerSkin = new TextureRegion(new Texture(UNIT_PATH+ "unit_berserker.png"));
        templarSkin = new TextureRegion(new Texture(UNIT_PATH+ "unit_templar.png"));
        projectileSkin=new TextureRegion(new Texture(UNIT_PATH+ "arrow.png"));
        fireballSkin=new TextureRegion(new Texture(UNIT_PATH+ "fireball.png"));


        // Environment
        defaultTile = new TextureRegion(new Texture(ENVIRONMENT_PATH+"default_tile.png"));
        waterTile = new TextureRegion(new Texture ( ENVIRONMENT_PATH+"water_tile.png"));
        dirtTile = new TextureRegion(new Texture ( ENVIRONMENT_PATH+"dirt_tile.png"));
        ballistaTowerSkin = new TextureRegion(new Texture(BUILDING_PATH+"ballista_tower.png"));
        mainTowerSkin = new TextureRegion(new Texture(BUILDING_PATH+"wall.png"));

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
        music.setVolume(0f);
        music.setLooping(true);
        maps = new Array<>();
        maps.add(Gdx.files.internal("maps/map1.txt"));
        maps.add(Gdx.files.internal("maps/map2.txt"));
        maps.add(Gdx.files.internal("maps/map3.txt"));

        // sounds
        victory =Gdx.audio.newSound(Gdx.files.internal("sounds/Victory.mp3"));
        defeat =Gdx.audio.newSound(Gdx.files.internal("sounds/Defeat.mp3"));
        upgradeExecuted =Gdx.audio.newSound(Gdx.files.internal("sounds/GameHUD/upgradeExecuted.wav"));
        attackSword =Gdx.audio.newSound(Gdx.files.internal("sounds/Weapon Whoosh/Sabre,Swing,Whoosh,Sharp.mp3"));
        attackBow =Gdx.audio.newSound(Gdx.files.internal("sounds/Bow, Crossbow/Bow,Recurve,Scythian,Arrow,Heavy,Fly,By,Whiz,Mid Tone,Two Tone - distant release.wav"));
        attackFireball =Gdx.audio.newSound(Gdx.files.internal("sounds/Bow, Crossbow/fireball.wav"));
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


    public void dispose() {
        defaultSkin.dispose();
        archerSkin.getTexture().dispose();
        swordFighterSkin.getTexture().dispose();
        knightSkin.getTexture().dispose();
        mageSkin.getTexture().dispose();
        berserkerSkin.getTexture().dispose();
        templarSkin.getTexture().dispose();
        projectileSkin.getTexture().dispose();
        fireballSkin.getTexture().dispose();
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
        attackFireball.dispose();
        hitArrow.dispose();
        drawSword.dispose();
        drawKatana.dispose();
        explosionSound.dispose();
        victory.dispose();
        defeat.dispose();
        upgradeExecuted.dispose();
        for (Sound s:
                drawKatanas) {
            s.dispose();
        }

    }

    public Sound getRandomDrawKatanaSound(){
        Random random = new Random();
        return drawKatanas.get(random.nextInt(drawKatanas.size));
    }

    public void setPlayable(boolean play){
        this.playable = play;
    }
    public boolean getPlayable(){
        return playable;
    }
}
