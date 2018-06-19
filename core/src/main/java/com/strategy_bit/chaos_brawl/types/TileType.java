package com.strategy_bit.chaos_brawl.types;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.strategy_bit.chaos_brawl.managers.AssetManager;

import static com.strategy_bit.chaos_brawl.config.WalkAbleAreas.*;

/**
 * @author AIsopp
 * @version 1.0
 * @since 27.03.2018
 */

public enum TileType {
    GRASS{
       public TextureRegion getTexture(){
           AssetManager assetManager = null;
           TextureRegion defaultTile = assetManager.getDefaultTile();
           return defaultTile;
       }

        @Override
        public int getMoveAble() {
            return WALKABLE;
        }
    },WATER{
        public TextureRegion getTexture(){
            AssetManager assetManager = null;
            TextureRegion waterTile = assetManager.getWaterTile();
            return waterTile;
        }

        @Override
        public int getMoveAble() {
            return BLOCKING;
        }
    },
    DIRT{
        public TextureRegion getTexture(){
            AssetManager assetManager = null;
            TextureRegion dirtTile = assetManager.getDirtTile();
            return dirtTile;
        }
        @Override
        public int getMoveAble() {
            return WALKABLE;
        }
    };


    public abstract TextureRegion getTexture();
    public abstract int getMoveAble();
}
