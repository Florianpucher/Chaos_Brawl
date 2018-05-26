package com.strategy_bit.chaos_brawl.ashley.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.components.ParticleComponent;

public class Smoke extends Entity{

    public Smoke (Vector2 position){

        String smoke = "smoke";

        ParticleComponent particleComponent = new ParticleComponent(smoke);
        add(particleComponent);

        particleComponent.setPosition(position);

    }
}
