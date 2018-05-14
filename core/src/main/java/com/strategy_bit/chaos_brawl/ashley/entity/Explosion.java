package com.strategy_bit.chaos_brawl.ashley.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.strategy_bit.chaos_brawl.managers.AssetManager;

public class Explosion {

    public static final float FRAME_LENGTH = 0.8f; // time between each frame of the animation
    public static final int OFFSET = 8;
    public static final int SIZE = 32;

    public static Animation anim = null;
    float x, y;
    float statetime;

    public boolean remove = false;                  // check if object needs to be removed

    public Explosion (float x, float y){
        this.x = x - OFFSET;
        this.y = y - OFFSET;
        statetime = 0;


        if (anim == null){
            anim = new Animation(FRAME_LENGTH, TextureRegion.split(new Texture("explosion.png"), SIZE, SIZE)[0]);
        }
    }

    public void update (float deltatime){
        statetime += deltatime;

        if (anim.isAnimationFinished(statetime)) {
            remove = true;
        }
    }

    public void render (SpriteBatch batch){
        batch.draw((Texture) anim.getKeyFrame(statetime),x, y);
    }
}
