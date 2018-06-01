package com.strategy_bit.chaos_brawl.ashley.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.components.ParticleComponent;
import com.strategy_bit.chaos_brawl.config.UnitConfig;

public class Particle extends Entity{

    public Particle(Vector2 position, String type){


        ParticleComponent particleComponent = new ParticleComponent(type);
        add(particleComponent);

        particleComponent.setPosition(position);

    }
}
