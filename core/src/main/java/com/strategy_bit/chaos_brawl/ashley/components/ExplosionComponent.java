package com.strategy_bit.chaos_brawl.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.strategy_bit.chaos_brawl.ashley.systems.ExplosionSystem;
import com.strategy_bit.chaos_brawl.managers.AssetManager;

public class ExplosionComponent implements Component {

    public ExplosionComponent() {

    }

    public void explode() {
        // ExplosionSystem explosionSystem = new ExplosionSystem();

        AssetManager.getInstance().explosionSound.play(1f);
        System.out.println("explosion!");

    }

}