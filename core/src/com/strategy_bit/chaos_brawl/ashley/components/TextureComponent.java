package com.strategy_bit.chaos_brawl.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * component that holds the sprite that needs to tbe rendered
 * @author AIsopp
 * @version 1.0
 * @since 15.03.2018
 */

public class TextureComponent implements Component {
    private TextureRegion texture;

    public TextureRegion getTexture() {
        return texture;
    }

    public void setTexture(TextureRegion texture) {
        this.texture = texture;
    }
}
