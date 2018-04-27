package com.strategy_bit.chaos_brawl;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new ChaosBrawlGame(), config);

		//enable accelerometer and compass in app
		config.useAccelerometer = true;
		config.useCompass = true;
	}
}
