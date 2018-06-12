package com.strategy_bit.chaos_brawl;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.strategy_bit.chaos_brawl.config.UnitConfig;
import com.strategy_bit.chaos_brawl.managers.AssetManager;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.mockito.Mockito;

import java.util.HashMap;

/**
 *
 * depends on GameTest from <a href="http://manabreak.eu/java/2016/10/21/unittesting-libgdx.html">http://manabreak.eu/java/2016/10/21/unittesting-libgdx.html</a>
 * @author AIsopp
 * @version 1.0
 * @since 19.04.2018
 */
public class BaseTest {
    // This is our "test" application
    private static Application application;

    // Before running any tests, initialize the application with the headless backend
    @BeforeClass
    public static void init() {
        // Note that we don't need to implement any of the listener's methods
        application = new HeadlessApplication(new ApplicationListener() {
            @Override public void create() {}
            @Override public void resize(int width, int height) {}
            @Override public void render() {}
            @Override public void pause() {}
            @Override public void resume() {}
            @Override public void dispose() {}
        });
        // Use Mockito to mock the OpenGL methods since we are running headlessly
        Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl = Gdx.gl20;
        Gdx.graphics = Mockito.mock(Graphics.class);
        Mockito.when(Gdx.graphics.getGL20()).thenReturn(Gdx.gl20);
        Mockito.when(Gdx.graphics.getHeight()).thenReturn(480);
        Mockito.when(Gdx.graphics.getWidth()).thenReturn(840);

        AssetManager assetManager = AssetManager.getInstance();
        Pixmap pixmap = new Pixmap(100,100, Pixmap.Format.RGB888);
        Texture texture = new Texture(pixmap);
        assetManager.skins.put("mainTowerSkin", new TextureRegion(texture));
        UnitConfig config = new UnitConfig();
        UnitConfig buildingConfig = new UnitConfig();
        HashMap<Integer, UnitConfig> unitConfigHashMap = Mockito.mock(HashMap.class);
        Mockito.when(unitConfigHashMap.get(6)).thenReturn(buildingConfig);
        Mockito.when(unitConfigHashMap.get(7)).thenReturn(buildingConfig);
        for (int i = 0; i < 6; i++) {
            Mockito.when(unitConfigHashMap.get(i)).thenReturn(config);
        }
        buildingConfig.setSkin(assetManager.skins.get("mainTowerSkin"));
        config.setHitPoints(10f);
        config.setMovementComponent(true);
        buildingConfig.setHitPoints(10f);
        buildingConfig.setBoundaryComponent(true);

        assetManager.unitManager.unitConfigHashMap = unitConfigHashMap;
        pixmap.dispose();
        assetManager.explosionParticle = Mockito.mock(FileHandle.class);
    }

    // After we are done, clean up the application
    @AfterClass
    public static void cleanUp() {
        // Exit the application first
        application.exit();
        application = null;
        AssetManager.getInstance().skins.get("mainTowerSkin").getTexture().dispose();
    }
}
