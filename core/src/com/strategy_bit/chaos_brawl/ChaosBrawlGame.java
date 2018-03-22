package com.strategy_bit.chaos_brawl;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.strategy_bit.chaos_brawl.controller.PlayerController;
import com.strategy_bit.chaos_brawl.screens.AbstractScreen;
import com.strategy_bit.chaos_brawl.screens.ScreenEnum;

import managers.AssetManager;
import managers.ScreenManager;
//TODO split into more screens, add assetManager

/**
 *
 */
public class ChaosBrawlGame extends ApplicationAdapter {

	private AbstractScreen currentScreen;
	private ScreenManager screenManager;
	private AssetManager assetManager;

	@Override
	public void create () {
		assetManager = AssetManager.getInstance();
		assetManager.loadAssets();
		screenManager = ScreenManager.getInstance();
		screenManager.initialize(this);
		screenManager.showScreen(ScreenEnum.MAIN_MENU);
	}

	public void setScreen(AbstractScreen screen){
		this.currentScreen = screen;
	}

	public AbstractScreen getScreen(){
		return currentScreen;
	}



	@Override
	public void render () {
		currentScreen.render(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void dispose () {
		currentScreen.dispose();
		assetManager.dispose();
	}
}
