package com.strategy_bit.chaos_brawl.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Florian on 08.06.2018.
 */

public class SoundManager {

    private static SoundManager instance;
    private HashMap<String, Sound> soundHashMap;
    private HashMap<String, Music> musicHashMap;
    private float masterVolume;
    private float volumeSounds;
    private float volumeMusic;
    private Music current;

    public SoundManager() {
        soundHashMap = new HashMap<>();
        musicHashMap = new HashMap<>();
        masterVolume = 1f;
        volumeMusic = 1f;
        volumeSounds = 1f;
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }
    public void loadSounds(){
        addSound("victory", "sounds/Victory.mp3");
        addSound("defeat", "sounds/Defeat.mp3");
        addSound("upgradeExecuted", "sounds/GameHUD/upgradeExecuted.wav");
        addSound("upgradeTowerExecuted", "sounds/GameHUD/upgradeExecutedTower.wav");
        addSound("critHit", "sounds/Weapon Whoosh/critHit.wav");
        addSound("attackSword", "sounds/Weapon Whoosh/Sabre,Swing,Whoosh,Sharp.mp3");
        addSound("attackBow", "sounds/Projectiles/Bow,Recurve,Scythian,Arrow,Heavy,Fly,By,Whiz,Mid Tone,Two Tone - distant release.wav");
        addSound("attackFireball", "sounds/Projectiles/fireball.wav");
        addSound("attackCannonBall", "sounds/Projectiles/cannonball.wav");
        addSound("attackLaser", "sounds/Projectiles/laser2.wav");
        addSound("hitArrow", "sounds/Hits/Mace,Hit,Spear,Haft,Lazy,Messy.mp3");
        addSound("drawSword", "sounds/Draw and Replace Weapon/Sabre,Draw,Scabbard,Fast,Loose,Rough.mp3");
        addSound("explosionSound", "sounds/Animations/explosion.mp3");
        addSound("drawKatana", "sounds/Draw and Replace Weapon/Katana,Draw,Scabbard,Fast,Strong.mp3");
        addSound("click", "sounds/GameHUD/Click.wav");
    }
    public void loadMusic(){
        musicHashMap.put("mainSoundtrack", Gdx.audio.newMusic(Gdx.files.internal("sounds/The Empire.ogg")));
    }

    public void addSound(String key, String sound){
        soundHashMap.put(key, Gdx.audio.newSound(Gdx.files.internal(sound)));
    }

    public void dispose(){
        Iterator it = soundHashMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            ((Sound)(pair.getValue())).dispose();
            it.remove();
        }
        it = musicHashMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            ((Music)(pair.getValue())).dispose();
            it.remove();
        }
    }

    public void playSound(String key){
        if(!soundHashMap.containsKey(key)){
            return;
        }
        soundHashMap.get(key).play(getVolumeSounds());
    }
    public void playMusic(String key){
        if (current != null && current.isPlaying()){
            current.stop();
        }
        if(!musicHashMap.containsKey(key)){
            return;
        }
        current = musicHashMap.get(key);
        current.setVolume(getVolumeMusic());
        current.setLooping(true);
        current.play();
    }

    public float getMasterVolume() {
        return masterVolume;
    }

    public void setMasterVolume(float masterVolume) {
        this.masterVolume = masterVolume;
        updateVolume();
    }

    public float getVolumeSounds() {
        return volumeSounds * masterVolume;
    }

    public void setVolumeSounds(float volumeSounds) {
        this.volumeSounds = volumeSounds;
    }

    public float getVolumeMusic() {
        return volumeMusic * masterVolume;
    }

    public void setVolumeMusic(float volumeMusic) {
        this.volumeMusic = volumeMusic;
        updateVolume();
    }

    private void updateVolume(){
        current.setVolume(getVolumeMusic());
    }

    public HashMap<String, Sound> getSoundHashMap() {
        return soundHashMap;
    }

    public HashMap<String, Music> getMusicHashMap() {
        return musicHashMap;
    }
}
