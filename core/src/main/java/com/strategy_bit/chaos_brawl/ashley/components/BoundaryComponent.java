package com.strategy_bit.chaos_brawl.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * @author AIsopp
 * @version 1.0
 * @since 13.05.2018
 */
public class BoundaryComponent implements Component{
    private Vector2 size;
    private TransformComponent transformComponent;

    public BoundaryComponent(Vector2 size, TransformComponent transformComponent) {
        this.size = size;
        this.transformComponent = transformComponent;
    }


    public boolean isWithinBorders(Vector2 position){
        // origin is always the middle of the entity
        Vector2 myPosition = transformComponent.getPosition();
        return position.x >= myPosition.x - size.x / 2f && position.x <= myPosition.x + size.x / 2f &&
                position.y >= myPosition.y - size.y / 2f && position.y <= myPosition.y + size.y / 2f;
    }
}
