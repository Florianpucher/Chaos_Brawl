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

    GAME {
        public AbstractScreen getScreen(Object... params) {
            return new GameScreen();
        }
    };

    public abstract AbstractScreen getScreen(Object... params);
}
