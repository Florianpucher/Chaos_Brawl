package com.strategy_bit.chaos_brawl.ashley.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.components.ResourceComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TextureComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.managers.AssetManager;

public class Resource extends Entity {
    public Resource() {
    }
    public Resource(int teamId) {
        ResourceComponent resourceComponent=new ResourceComponent(teamId);
        TextureComponent textureComponent=new TextureComponent();
        textureComponent.setTexture(AssetManager.getInstance().resourceSkin);
        TransformComponent transformComponent=new TransformComponent();
        //TODO adjust position, but not here
        transformComponent.setPosition(new Vector2(10,5));
        add(resourceComponent);
        add(textureComponent);
        add(transformComponent);
    }
}
