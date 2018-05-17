package com.strategy_bit.chaos_brawl.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;

/**
 * component that holds the sprite that needs to tbe rendered
 * @author AIsopp
 * @version 1.0
 * @since 15.03.2018
 */

public class TextureComponent implements Component,Pool.Poolable {
    private TextureRegion texture;

    public TextureRegion getTexture() {
        return texture;
    }

    public void setTexture(TextureRegion texture) {
        this.texture = texture;
    }

    public TextureComponent() {
        texture=new TextureRegion();
    }

    @Override
    public void reset() {

    }
}
