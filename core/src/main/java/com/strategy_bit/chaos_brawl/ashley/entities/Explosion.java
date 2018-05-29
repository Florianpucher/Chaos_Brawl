package com.strategy_bit.chaos_brawl.ashley.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.components.ParticleComponent;

public class Explosion extends Entity{

    public Explosion(Vector2 position){

        String explosion = "explosion";

        ParticleComponent particleComponent = new ParticleComponent(explosion);
        add(particleComponent);

        particleComponent.setPosition(position);

    }
}
