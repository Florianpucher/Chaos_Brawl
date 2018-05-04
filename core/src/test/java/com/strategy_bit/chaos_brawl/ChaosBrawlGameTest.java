package com.strategy_bit.chaos_brawl;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.managers.ScreenManager;
import com.strategy_bit.chaos_brawl.screens.AbstractScreen;
import com.strategy_bit.chaos_brawl.screens.ScreenEnum;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


/**
 * @author AIsopp
 * @version 1.0
 * @since 28.03.2018
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({AssetManager.class, ScreenManager.class, Gdx.class})
public class ChaosBrawlGameTest {

    private ChaosBrawlGame chaosBrawlGame;
    private AssetManager assetManager;
    private ScreenManager screenManager;

    private ScreenEnum screenType;
    private ScreenEnum screenTypeMainMenu;
    private AbstractScreen splashScreen;
    private AbstractScreen gameScreen;

    private float deltaTime = 1f;

    @Before
    public void initialize() {
        chaosBrawlGame = new ChaosBrawlGame();
        assetManager = Mockito.mock(AssetManager.class);
        screenManager = Mockito.mock(ScreenManager.class);
        Graphics graphics = Mockito.mock(Graphics.class);
        Application app = Mockito.mock(Application.class);
        Gdx.graphics = graphics;
        Gdx.app = app;
        Gdx.input = Mockito.mock(Input.class);
        PowerMockito.mockStatic(AssetManager.class);
        PowerMockito.mockStatic(ScreenManager.class);
        screenType = Mockito.mock(ScreenEnum.class);
        screenTypeMainMenu = Mockito.mock(ScreenEnum.class);
        splashScreen = Mockito.mock(AbstractScreen.class);
        gameScreen = Mockito.mock(AbstractScreen.class);
        Mockito.when(AssetManager.getInstance()).thenReturn(assetManager);
        Mockito.when(ScreenManager.getInstance()).thenReturn(screenManager);
        Mockito.when(screenType.getScreen()).thenReturn(splashScreen);
        Mockito.when(screenTypeMainMenu.getScreen()).thenReturn(gameScreen);
        Mockito.when(graphics.getDeltaTime()).thenReturn(deltaTime);
        Mockito.when(graphics.getWidth()).thenReturn(10);
        Mockito.when(graphics.getHeight()).thenReturn(5);
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                chaosBrawlGame.setScreen(screenType.getScreen());
                return null;
            }
        }).when(screenManager).showScreen(ScreenEnum.SPLASH_SCREEN);
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                chaosBrawlGame.setScreen(screenTypeMainMenu.getScreen());
                return null;
            }
        }).when(screenManager).showScreen(ScreenEnum.MAIN_MENU);
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Runnable runnable = invocation.getArgument(0);
                Thread thread = new Thread(runnable);
                thread.start();
                thread.join();
                return null;
            }
        }).when(app).postRunnable(Mockito.any(Runnable.class));
    }

    @Test
    public void testCreate() {
        chaosBrawlGame.create();
        AbstractScreen screen = chaosBrawlGame.getScreen();
        Assert.assertEquals(screen, splashScreen);
    }


    @Test(timeout = 5000)
    public void testRender(){
        /*chaosBrawlGame.create();
        while(chaosBrawlGame.getScreen() != gameScreen){
            chaosBrawlGame.render();
        }*/
    }

    @Test(timeout = 5000)
    public void testDispose(){
        /*chaosBrawlGame.create();
        while(chaosBrawlGame.getScreen() != gameScreen){
            chaosBrawlGame.render();
        }
        chaosBrawlGame.dispose();
        Mockito.verify(assetManager, Mockito.times(1)).dispose();
        Mockito.verify(gameScreen, Mockito.times(1)).dispose();*/
        //chaosBrawlGame.dispose();
    }


}
