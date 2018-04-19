package com.strategy_bit.chaos_brawl;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.mockito.Mockito;

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
    }

    // After we are done, clean up the application
    @AfterClass
    public static void cleanUp() {
        // Exit the application first
        application.exit();
        application = null;
    }
}
