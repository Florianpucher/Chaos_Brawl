package com.strategy_bit.chaos_brawl.managers;

import com.strategy_bit.chaos_brawl.ChaosBrawlGame;
import com.strategy_bit.chaos_brawl.screens.GameScreen;
import com.strategy_bit.chaos_brawl.screens.MainMenuScreen;
import com.strategy_bit.chaos_brawl.screens.ScreenEnum;
import com.strategy_bit.chaos_brawl.screens.SplashScreen;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertEquals;

/**
 * @author AIsopp
 * @version 1.0
 * @since 05.04.2018
 */
public class ScreenManagerTest{

    private ScreenManager screenManager;
    private ChaosBrawlGame chaosBrawlGame;
    private SplashScreen splashScreen;
    private MainMenuScreen mainMenuScreen;
    private GameScreen gameScreen;
    private ScreenEnum splash, main,  game;
    @Before
    public void initialize(){
        screenManager = ScreenManager.getInstance();
        chaosBrawlGame = Mockito.mock(ChaosBrawlGame.class);
        screenManager.initialize(chaosBrawlGame);
        splashScreen = Mockito.mock(SplashScreen.class);
        mainMenuScreen = Mockito.mock(MainMenuScreen.class);

        gameScreen = Mockito.mock(GameScreen.class);
        main = Mockito.mock(ScreenEnum.class);
        splash = Mockito.mock(ScreenEnum.class);
        game = Mockito.mock(ScreenEnum.class);
        Mockito.when(splash.getScreen()).thenReturn(splashScreen);
        Mockito.when(main.getScreen()).thenReturn(mainMenuScreen);
        Mockito.when(game.getScreen()).thenReturn(gameScreen);




        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Mockito.when(chaosBrawlGame.getScreen()).thenReturn(splashScreen);
                return null;
            }
        }).when(chaosBrawlGame).setScreen(Mockito.any(SplashScreen.class));
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Mockito.when(chaosBrawlGame.getScreen()).thenReturn(mainMenuScreen);
                return null;
            }
        }).when(chaosBrawlGame).setScreen(Mockito.any(MainMenuScreen.class));
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Mockito.when(chaosBrawlGame.getScreen()).thenReturn(gameScreen);
                return null;
            }
        }).when(chaosBrawlGame).setScreen(Mockito.any(GameScreen.class));
    }

    @Test
    public void testShowScreen(){
        screenManager.showScreen(splash);
        assertEquals(splashScreen, chaosBrawlGame.getScreen());
        screenManager.showScreen(main);
        assertEquals(mainMenuScreen, chaosBrawlGame.getScreen());
        Mockito.verify(splashScreen).dispose();
    }

    @Test
    public void testSwitchToLastScreenSimple(){
        screenManager.showScreen(splash);
        screenManager.showScreen(main);
        screenManager.switchToLastScreen();
        Mockito.verify(mainMenuScreen).dispose();
        assertEquals(splashScreen, chaosBrawlGame.getScreen());
    }


    @Test
    public void testSwitchToLastScreenAdvanced(){
        screenManager.showScreen(splash);
        screenManager.showScreen(main);
        screenManager.switchToLastScreen();
        Mockito.verify(mainMenuScreen).dispose();
        assertEquals(splashScreen, chaosBrawlGame.getScreen());


    }

}
