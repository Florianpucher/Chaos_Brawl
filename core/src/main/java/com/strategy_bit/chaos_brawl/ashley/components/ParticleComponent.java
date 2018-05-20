package com.strategy_bit.chaos_brawl.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.managers.AssetManager;

public class ParticleComponent implements Component {

    ParticleEffect effect;

    public ParticleComponent() {

        TextureAtlas particleAtlas = AssetManager.getInstance().explosionSkin;
        effect = new

        ParticleEffect();

        effect.load(AssetManager.getInstance().particle, particleAtlas);
        effect.start();

    }

    public boolean isComplete() {
        return effect.isComplete();
    }

    public void start() {
        effect.start();

    }

    public void setPosition(Vector2 Vector) {

    // effect.setPosition();

    }

}
