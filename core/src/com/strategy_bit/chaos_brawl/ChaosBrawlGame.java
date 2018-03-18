package com.strategy_bit.chaos_brawl;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.strategy_bit.chaos_brawl.controller.PlayerController;

public class ChaosBrawlGame extends ApplicationAdapter {
	private GameManager manager;

	@Override
	public void create () {
		manager = new GameManager();
		//add User input
		PlayerController controller = new PlayerController();
		controller.setInputHandler(manager);
	}




	@Override
	public void render () {
		manager.render();
	}

	@Override
	public void dispose () {
		manager.dispose();
	}
}
