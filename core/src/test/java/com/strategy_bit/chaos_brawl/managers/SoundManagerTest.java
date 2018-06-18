package com.strategy_bit.chaos_brawl.managers;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.strategy_bit.chaos_brawl.BaseTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;

/**
 * Created by Florian on 17.06.2018.
 */

public class SoundManagerTest extends BaseTest{
    private SoundManager soundManager;

    @Before
    public void initialize(){
        soundManager = SoundManager.getInstance();
    }

    @Test
    public void testLoadSoundsMusic(){
        soundManager.loadSounds();
        soundManager.loadMusic();

        Assert.assertNotNull(soundManager.getSoundHashMap());
        Assert.assertNotNull(soundManager.getMusicHashMap());

    }

    @Test
    public void testDispose(){
        soundManager.dispose();

        Assert.assertEquals(0, soundManager.getSoundHashMap().size());
        Assert.assertEquals(0, soundManager.getSoundHashMap().size());
    }

    @Test
    public void testVolume(){

        soundManager.loadSounds();
        soundManager.loadMusic();

        Assert.assertEquals(1f, soundManager.getMasterVolume(), 0.1f);
        Assert.assertEquals(1f, soundManager.getVolumeSounds(),0.1f);
        Assert.assertEquals(1f, soundManager.getVolumeMusic(),0.1f);

        soundManager.playMusic("mainSoundtrack");

        soundManager.setMasterVolume(0.5f);
        soundManager.setVolumeSounds(0.5f);
        soundManager.setVolumeMusic(0.5f);

        Assert.assertEquals(0.5f, soundManager.getMasterVolume(), 0.1f);
        Assert.assertEquals(0.25f, soundManager.getVolumeSounds(), 0.1f);
        Assert.assertEquals(0.25f, soundManager.getVolumeMusic(), 0.1f);

    }



}
