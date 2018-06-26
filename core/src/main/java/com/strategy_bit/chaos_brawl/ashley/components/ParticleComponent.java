package com.strategy_bit.chaos_brawl.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.managers.AssetManager;

public class ParticleComponent implements Component {

    private ParticleEffect effect;
    AssetManager assetManager;

    public ParticleComponent(String animation) {
        assetManager = AssetManager.getInstance();

        TextureAtlas particleAtlas;

        if (animation.equals("explosion")) {
            particleAtlas = assetManager.getExplosionSkin();
            effect = new ParticleEffect();
            effect.load(assetManager.getExplosionParticle(), particleAtlas);
        } else if (animation.equals("smoke")) {
            particleAtlas = assetManager.getSmokeSkin();
            effect = new ParticleEffect();
            effect.load(assetManager.getSmokeParticle(), particleAtlas);
        } else if (animation.equals("star")) {
            particleAtlas = assetManager.getStarSkin();
            effect = new ParticleEffect();
            effect.load(assetManager.getStarParticle(), particleAtlas);
        }
        effect.start();
    }

    public boolean isComplete() {
        return effect.isComplete();
    }

    public void draw(SpriteBatch batch, float delta) {
        effect.draw(batch, delta);
    }

    public void start() {
        effect.start();

    }

    public void setPosition(Vector2 vector) {
        effect.setPosition(vector.x, vector.y);
    }
}
