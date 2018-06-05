package com.strategy_bit.chaos_brawl.types;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.strategy_bit.chaos_brawl.managers.AssetManager;

public enum AbilityType {
    ABILITY_1 {
        @Override
        public double getCosts() {
            return 10.0;
        }

        @Override
        public TextureRegion getAsset() {
            //TODO add Skin
            return AssetManager.getInstance().skins.get("swordFighterSkin");
        }
    }, ABILITY_2 {
        @Override
        public double getCosts() {
            return 0;
        }

        @Override
        public TextureRegion getAsset() {
            //TODO add Skin
            return AssetManager.getInstance().skins.get("archerSkin");
        }
    };



    public abstract double getCosts();
    public abstract TextureRegion getAsset();
}
