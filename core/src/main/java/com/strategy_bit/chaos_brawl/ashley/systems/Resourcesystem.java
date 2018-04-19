package com.strategy_bit.chaos_brawl.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.strategy_bit.chaos_brawl.ashley.components.ResourceComponent;

public class Resourcesystem extends IteratingSystem {
    protected ComponentMapper<ResourceComponent> mResourceComponent;
    public Resourcesystem() {
        super(Family.all(ResourceComponent.class).get());
        mResourceComponent = ComponentMapper.getFor(ResourceComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ResourceComponent resourceComponent=mResourceComponent.get(entity);

        resourceComponent.add(1.0*deltaTime);

    }
}
