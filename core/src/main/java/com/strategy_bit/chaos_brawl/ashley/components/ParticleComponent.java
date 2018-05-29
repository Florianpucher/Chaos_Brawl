package com.strategy_bit.chaos_brawl.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.managers.AssetManager;

public class ParticleComponent implements Component {

    private ParticleEffect effect;

    public ParticleComponent(String animation) {

        TextureAtlas particleAtlas = null;

        if (animation.equals("explosion")) {
            particleAtlas = AssetManager.getInstance().explosionSkin;
            effect = new ParticleEffect();
            effect.load(AssetManager.getInstance().explosionParticle, particleAtlas);
            effect.start();

        } else if (animation.equals("smoke")) {
            particleAtlas = AssetManager.getInstance().smokeSkin;
            effect = new ParticleEffect();
            effect.load(AssetManager.getInstance().smokeParticle, particleAtlas);
            effect.start();
        }
    }

    public boolean isComplete() {
        return effect.isComplete();
    }

    public void draw(SpriteBatch batch, float delta){
        effect.draw(batch, delta);
    }

    public void start() {
        effect.start();

    }

    public void setPosition(Vector2 vector) {
        effect.setPosition(vector.x, vector.y);
    }
}
