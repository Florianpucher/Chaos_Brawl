package com.strategy_bit.chaos_brawl;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.strategy_bit.chaos_brawl.ashley.entities.Unit;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.managers.ScreenManager;
import com.strategy_bit.chaos_brawl.managers.SoundManager;
import com.strategy_bit.chaos_brawl.managers.UnitManager;
import com.strategy_bit.chaos_brawl.screens.AbstractScreen;
import com.strategy_bit.chaos_brawl.screens.ScreenEnum;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * entry point for this application
 */
public class ChaosBrawlGame extends Game {

	private AbstractScreen currentScreen;
	private ScreenManager screenManager;
	private AssetManager assetManager;
	private UnitManager unitManager;
	private boolean loadGame;
	private FPSLogger logger;

	@Override
	public void create () {
		logger = new FPSLogger();
		Gdx.input.setCatchBackKey(true);
		// Entry point for application
		screenManager = ScreenManager.getInstance();
		screenManager.initialize(this);
		screenManager.showScreen(ScreenEnum.SPLASH_SCREEN);
		assetManager = AssetManager.getInstance();
		unitManager=UnitManager.getInstance();
		loadGame = true;
		// Load assets here
		Executor executor = Executors.newSingleThreadExecutor();
		executor.execute(() -> Gdx.app.postRunnable(() -> {
            assetManager.loadAssets();
            unitManager.initialize();
            SoundManager.getInstance().loadMusic();
            SoundManager.getInstance().loadSounds();
            loadGame = false;
        }));
	}

	public void setScreen(AbstractScreen screen){
		super.setScreen(screen);
		this.currentScreen = screen;
	}

	@Override
	public AbstractScreen getScreen(){
		return currentScreen;
	}



	@Override
	public void render () {
		currentScreen.render(Gdx.graphics.getDeltaTime());
		logger.log();
		if(!loadGame){
			SoundManager.getInstance().playMusic("mainSoundtrack");
			screenManager.showScreenWithoutAddingOldOneToStack(ScreenEnum.MAIN_MENU);
			loadGame = true;
		}
	}

	@Override
	public void dispose () {
		currentScreen.dispose();
		SoundManager.getInstance().dispose();
		assetManager.dispose();
	}
}
