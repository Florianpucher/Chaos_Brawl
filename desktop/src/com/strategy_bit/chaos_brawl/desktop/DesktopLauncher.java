package com.strategy_bit.chaos_brawl.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.strategy_bit.chaos_brawl.ChaosBrawlGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1920;
		config.height = 1080;
		config.resizable=true;
		config.vSyncEnabled=false;
		config.foregroundFPS=60;
		new LwjglApplication(new ChaosBrawlGame(), config);
	}
}
