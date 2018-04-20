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

}
