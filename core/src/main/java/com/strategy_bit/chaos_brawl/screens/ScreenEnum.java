package com.strategy_bit.chaos_brawl.screens;

/**
 * @author AIsopp
 * @version 1.0
 * @since 22.03.2018
 */

public enum ScreenEnum {
    MAIN_MENU {
        public AbstractScreen getScreen(Object... params) {
            return new MainMenuScreen();
        }
    },
    MAP_MENU {
        public AbstractScreen getScreen(Object... params) {
            return new MapMenuScreen();
        }
    },
    SPLASH_SCREEN {
        public AbstractScreen getScreen(Object... params) {
            return new SplashScreen();
        }
    },
    GAME1 {
        public AbstractScreen getScreen(Object... params) {
            return new GameScreen(1);
        }
    },
    GAME2 {
        public AbstractScreen getScreen(Object... params) {
            return new GameScreen(2);
        }
    },
    GAME3 {
        public AbstractScreen getScreen(Object... params) {
            return new GameScreen(3);
        }
    };

    public abstract AbstractScreen getScreen(Object... params);
}
