package managers;

import com.badlogic.gdx.Screen;
import com.strategy_bit.chaos_brawl.ChaosBrawlGame;
import com.strategy_bit.chaos_brawl.screens.AbstractScreen;
import com.strategy_bit.chaos_brawl.screens.ScreenEnum;

/**
 * manager for screen selecting
 *
 * @author AIsopp
 * @version 1.0
 * @since 22.03.2018
 */
public class ScreenManager {
    // Singleton: unique instance
    private static ScreenManager instance;

    // Reference to game
    private ChaosBrawlGame game;

    // Singleton: private constructor
    private ScreenManager() {
        super();
    }

    // Singleton: retrieve instance
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
     * changes the screen
     * @param screenEnum the type of screen that will be selected
     * @param params optional parameters that needs to be passed to new screen
     */
    public void showScreen(ScreenEnum screenEnum, Object... params) {

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
    }
}
