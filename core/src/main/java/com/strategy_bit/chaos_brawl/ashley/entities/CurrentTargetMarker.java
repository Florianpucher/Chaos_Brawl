package com.strategy_bit.chaos_brawl.ashley.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.components.TextureComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.ashley.engine.MyEngine;
import com.strategy_bit.chaos_brawl.managers.AssetManager;

public class CurrentTargetMarker extends Entity{

    public CurrentTargetMarker(Vector2 position, MyEngine engine){
        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        transformComponent.setPosition(position);
        transformComponent.setZ(10);

        TextureComponent textureComponent =engine.createComponent(TextureComponent.class);
        textureComponent.setTexture(AssetManager.getInstance().markers.get("default"));
        this.add(transformComponent);
        this.add(textureComponent);
    }
}
