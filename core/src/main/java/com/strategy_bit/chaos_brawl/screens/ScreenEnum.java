package com.strategy_bit.chaos_brawl.screens;

import com.strategy_bit.chaos_brawl.network.BrawlMultiplayer;
import com.strategy_bit.chaos_brawl.network.server.BrawlServer;
import com.strategy_bit.chaos_brawl.screens.game_screens.GameScreen;
import com.strategy_bit.chaos_brawl.screens.game_screens.MultiplayerGameScreen;
import com.strategy_bit.chaos_brawl.screens.menu_screens.ClientConnectToScreen;
import com.strategy_bit.chaos_brawl.screens.menu_screens.ClientLobbyScreen;
import com.strategy_bit.chaos_brawl.screens.menu_screens.HostLobbyScreen;
import com.strategy_bit.chaos_brawl.screens.menu_screens.HostLoungeScreen;
import com.strategy_bit.chaos_brawl.screens.menu_screens.MainMenuScreen;
import com.strategy_bit.chaos_brawl.screens.menu_screens.MapMenuScreen;
import com.strategy_bit.chaos_brawl.screens.menu_screens.NetworkScreen;

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
    NETWORK_SCREEN {
        public AbstractScreen getScreen(Object... params) {
            return new NetworkScreen();
        }
    },
    CLIENT_CONNECT_SCREEN {
        public AbstractScreen getScreen(Object... params) {
            return new ClientConnectToScreen();
        }
    },
    HOST_LOUNGE_SCREEN {
        public AbstractScreen getScreen(Object... params) {
            return new HostLoungeScreen();
        }
    },
    HOST_LOBBY_SCREEN {
        public AbstractScreen getScreen(Object... params) {
            return new HostLobbyScreen((BrawlServer) params[0]);
        }
    },
    CLIENT_LOBBY_SCREEN {
        public AbstractScreen getScreen(Object... params) {
            return new ClientLobbyScreen();
        }
    },
    MULTIPLAYERGAME {
        public AbstractScreen getScreen(Object... params) {
            return new MultiplayerGameScreen((BrawlMultiplayer) params[0], (int[]) params[1]);
        }
    },
    GAME {
        public AbstractScreen getScreen(Object... params) {
            return new GameScreen((Integer) params[0]);
        }
    };

    public abstract AbstractScreen getScreen(Object... params);
}
