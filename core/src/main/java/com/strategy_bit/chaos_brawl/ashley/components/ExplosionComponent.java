package com.strategy_bit.chaos_brawl.ashley.components;

import com.badlogic.ashley.core.Component;

public class ExplosionComponent implements Component{

    public ExplosionComponent() {
    }

        public void explode() {

        System.out.println("explosion! A building got destroyed!");

    }

    public void death() {

        System.out.println("smoke! A unit got killed!");

    }

}