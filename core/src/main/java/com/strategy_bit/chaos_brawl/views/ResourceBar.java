package com.strategy_bit.chaos_brawl.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.strategy_bit.chaos_brawl.managers.AssetManager;

public class ResourceBar {
    private NinePatchDrawable resourceBar;
    Color color;
    public ResourceBar() {
        resourceBar=new NinePatchDrawable();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        resourceBar=resourceBar.tint(color);
        this.color = color;
    }

    public void draw(SpriteBatch spriteBatch,float x,float y,float width,float height){
        AssetManager assetManager = AssetManager.getInstance();
        Texture texture = assetManager.resourceSkin.getTexture();
        NinePatch patch = new NinePatch(texture, 15, 15, 15, 15);
        NinePatchDrawable ninePatch = new NinePatchDrawable(patch);
        ninePatch.draw(spriteBatch, x, y, width, height);
    }

}
