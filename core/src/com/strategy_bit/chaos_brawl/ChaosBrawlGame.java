package com.strategy_bit.chaos_brawl;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.strategy_bit.chaos_brawl.controller.PlayerController;
import com.strategy_bit.chaos_brawl.screens.AbstractScreen;
import com.strategy_bit.chaos_brawl.screens.ScreenEnum;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import managers.AssetManager;
import managers.ScreenManager;
//TODO split into more screens, add assetManager

/**
 *
 */
public class ChaosBrawlGame extends Game {

	private AbstractScreen currentScreen;
	private ScreenManager screenManager;
	private AssetManager assetManager;
	private boolean loadGame;

	@Override
	public void create () {

		screenManager = ScreenManager.getInstance();
		screenManager.initialize(this);
		screenManager.showScreen(ScreenEnum.SPLASH_SCREEN);
		assetManager = AssetManager.getInstance();
		loadGame = true;
		Executor executor = Executors.newSingleThreadExecutor();
		executor.execute(loadAssets);
	}

	public void setScreen(AbstractScreen screen){
		super.setScreen(screen);
		this.currentScreen = screen;
	}

	public AbstractScreen getScreen(){
		return currentScreen;
	}



	@Override
	public void render () {
		currentScreen.render(Gdx.graphics.getDeltaTime());
		if(!loadGame){

			screenManager.showScreen(ScreenEnum.MAIN_MENU);
			loadGame = true;
			System.out.println("Loaded");
		}
	}

	@Override
	public void dispose () {
		currentScreen.dispose();
		assetManager.dispose();
	}


	private Runnable loadAssets = new Runnable() {
		@Override
		public void run() {
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					assetManager.loadAssets();
					loadGame = false;
				}
			});
		}
	};
}
