package com.strategy_bit.chaos_brawl.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.ChaosBrawlGame;
import com.strategy_bit.chaos_brawl.screens.AbstractScreen;
import com.strategy_bit.chaos_brawl.screens.ScreenEnum;

import java.util.ArrayList;
import java.util.Stack;
/*
Updated on 11.04.2018 by Alexander Isopp
added:
- More methods for switching screens
 */
/**
 * <p>manager for screen selecting</p>
 * <p>Implementation: Singleton</p>
 * @author AIsopp
 * @version 1.1
 * @since 22.03.2018
 */
public class ScreenManager {
    // Singleton: unique instance
    private static ScreenManager instance;

    // Reference to game
    private ChaosBrawlGame game;
    private Stack<ScreenEnum> lastScreens;
    //private ScreenEnum[] screenThatShouldNotBeAddedToStack;
    private ScreenEnum currentScreenEnum;

    // Singleton: private constructor
    private ScreenManager() {
        super();
        lastScreens = new Stack<ScreenEnum>();
        //screenThatShouldNotBeAddedToStack = new ScreenEnum[]{ScreenEnum.SPLASH_SCREEN,ScreenEnum.GAME};
    }

    /**
     * Singleton implementation for ScreenManager
     * @return ScreenManager instance
     */
    public static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    /**
     * initializing within the games class
     * @param game the game that holds this class
     */
    public void initialize(ChaosBrawlGame game) {
        this.game = game;
    }

    /**
     * changes the screen and added currentScreen to screen stack
     * @param screenEnum the type of screen that will be shown
     * @param params optional parameters that needs to be passed to new screen
     */
    public void showScreen(ScreenEnum screenEnum, Object... params) {
        if(currentScreenEnum != null){
            lastScreens.add(currentScreenEnum);
        }
        switchScreen(screenEnum,params);
    }

    /**
     * <p>switches to the last added screen on the Stack</p>
     * <p>If the last screen stack is empty the manager will close the game</p>
     */
    public void switchToLastScreen(){
        if(lastScreens.isEmpty()){
            // Just for android and desktop
            Gdx.app.exit();
            return;
        }
        ScreenEnum screenEnum = lastScreens.pop();
        switchScreen(screenEnum);
    }

    /**
     * changes the screen and clears the screen enum
     * @param screenEnum the type of screen that will be shown
     * @param params optional parameters that needs to be passed to new screen
     */
    public void showScreenAndClearScreenStack(ScreenEnum screenEnum, Object... params){
        lastScreens.clear();
        switchScreen(screenEnum,params);
    }

    /**
     *
     * @param screenEnum the type of screen that will be shown
     * @param params optional parameters that needs to be passed to new screen
     */
    public void showScreenWithoutAddingOldOneToStack(ScreenEnum screenEnum, Object... params){
        switchScreen(screenEnum,params);
    }

    /**
     * switches screen
     * @param screenEnum the type of screen that will be shown
     * @param params optional parameters that needs to be passed to new screen
     */
    private void switchScreen(ScreenEnum screenEnum, Object... params){
        // Get current screen to dispose it
        Screen currentScreen = game.getScreen();

        // Show new screen
        AbstractScreen newScreen = screenEnum.getScreen(params);
        newScreen.buildStage();


        // Dispose previous screen
        if (currentScreen != null) {
            currentScreen.dispose();
        }
        game.setScreen(newScreen);
        currentScreenEnum = screenEnum;
    }
}
