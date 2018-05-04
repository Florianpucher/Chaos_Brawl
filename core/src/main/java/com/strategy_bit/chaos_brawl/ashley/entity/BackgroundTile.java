package com.strategy_bit.chaos_brawl.ashley.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.types.TileType;
import com.strategy_bit.chaos_brawl.world.BoardA;
import com.strategy_bit.chaos_brawl.world.Tile;
import com.strategy_bit.chaos_brawl.ashley.components.TextureComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.config.ZIndex;

/**
 * Representing the tiles used by {@link BoardA}
 *
 * @author AIsopp
 * @version 1.0
 * @since 27.03.2018
 */
public class BackgroundTile extends Entity implements Tile{

    private TileType type;


    public BackgroundTile(TileType tileType){
        type = tileType;
        TextureComponent textureComponent = new TextureComponent();
        textureComponent.setTexture(new TextureRegion(tileType.getTexture()));
        TransformComponent transformComponent = new TransformComponent();
        transformComponent.setZ(ZIndex.BACKGROUND);
        add(textureComponent);
        add(transformComponent);
    }


    @Override
    public TileType getType() {
        return type;
    }

    @Override
    public void setType(TileType type) {
        this.type = type;
        getComponent(TextureComponent.class).setTexture(type.getTexture());
    }

    @Override
    public void setPosition(Vector2 position) {
        getComponent(TransformComponent.class).setPosition(position);
    }

    @Override
    public Vector2 getPosition() {
        return getComponent(TransformComponent.class).getPosition();
    }

    @Override
    public void setScale(Vector2 size) {
        getComponent(TransformComponent.class).setScale(new Vector2(size));
    }

    @Override
    public Vector2 getScale() {
        return getComponent(TransformComponent.class).getScale();
    }
}
