package com.strategy_bit.chaos_brawl.world;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.entity.BackgroundTile;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.types.TileType;

import static com.strategy_bit.chaos_brawl.config.WorldSettings.BOARD_HEIGHT;
import static com.strategy_bit.chaos_brawl.config.WorldSettings.BOARD_WIDTH;
import static com.strategy_bit.chaos_brawl.config.WorldSettings.FRUSTUM_HEIGHT;
import static com.strategy_bit.chaos_brawl.config.WorldSettings.FRUSTUM_WIDTH;
import static com.strategy_bit.chaos_brawl.config.WorldSettings.PIXELS_TO_METRES;

/**
 * Created by Florian on 11.04.2018.
 *
 * Game BoardA with only one big bridge in the middle
 */

public class BoardC implements Board {
    private Tile[][] tileBoard;
    private int[][] intBoard;

    public BoardC(Engine engine){
        // TODO add Custom board option
        tileBoard = new Tile[BOARD_HEIGHT][BOARD_WIDTH];
        float tileHeight;
        float tileWidth;
        // application origin(0,0) is the upper right corner
        Vector2 origin = new Vector2(0,0);
        float multiplicandX = FRUSTUM_WIDTH/ BOARD_WIDTH;
        float multiplicandY = FRUSTUM_HEIGHT/ BOARD_HEIGHT;

        /*
        0 ----------------- WIDTH
        -
        -
        -
        -
        -
        -
        HEIGHT
        RenderedWidth = tex.getRegionWidth
         */
        TextureRegion defaultTile = AssetManager.getInstance().defaultTile;
        //width = texWidth * scale;
        // scale = width/texWidth
        Vector2 size = new Vector2(multiplicandX/(defaultTile.getRegionWidth() * PIXELS_TO_METRES),multiplicandY/(defaultTile.getRegionHeight() * PIXELS_TO_METRES));
        System.out.println(size);
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < 4; j++) {
                Tile tile = new BackgroundTile(TileType.GRASS);
                tileBoard[i][j] = tile;
                tile.setPosition(new Vector2(multiplicandX/2 + multiplicandX* j, multiplicandY/2 + multiplicandY *i));
                tile.setScale(size);
                engine.addEntity((BackgroundTile)tile);
            }
            for (int j = 11; j < BOARD_HEIGHT; j++) {
                Tile tile = new BackgroundTile(TileType.GRASS);
                tileBoard[i][j] = tile;
                tile.setPosition(new Vector2(multiplicandX/2 + multiplicandX* j, multiplicandY/2 + multiplicandY *i));
                tile.setScale(size);
                engine.addEntity((BackgroundTile)tile);
            }
        }
        for (int i = 0; i < BOARD_HEIGHT; i++){
            for(int j = 4; j < 11; j++){
                Tile tile = new BackgroundTile(TileType.WATER);
                tileBoard[i][j] = tile;
                tile.setPosition(new Vector2(multiplicandX/2 + multiplicandX* j, multiplicandY/2 + multiplicandY *i));
                tile.setScale(size);
                engine.addEntity((BackgroundTile)tile);
            }

        }
        for(int i = 4; i < 6; i++) {
            for (int j = 4; j < 5; j++) {
                Tile tile = new BackgroundTile(TileType.DIRT);
                tileBoard[i][j] = tile;
                tile.setPosition(new Vector2(multiplicandX / 2 + multiplicandX * j, multiplicandY / 2 + multiplicandY * i));
                tile.setScale(size);
                engine.addEntity((BackgroundTile) tile);
            }
            for (int j = 10; j < 11; j++) {
                Tile tile = new BackgroundTile(TileType.DIRT);
                tileBoard[i][j] = tile;
                tile.setPosition(new Vector2(multiplicandX / 2 + multiplicandX * j, multiplicandY / 2 + multiplicandY * i));
                tile.setScale(size);
                engine.addEntity((BackgroundTile) tile);
            }
        }
        for(int i = 1; i < 8; i++) {
            for (int j = 5; j < 10; j++) {
                Tile tile = new BackgroundTile(TileType.DIRT);
                tileBoard[i][j] = tile;
                tile.setPosition(new Vector2(multiplicandX / 2 + multiplicandX * j, multiplicandY / 2 + multiplicandY * i));
                tile.setScale(size);
                engine.addEntity((BackgroundTile) tile);
            }
        }
    }

    public Tile[][] getTileBoard(){
        return tileBoard;
    }

    public int[][] boardToMatrix(BoardA board){
        for(int i = 0; i < BOARD_HEIGHT; i++){
            for (int j = 0; j < BOARD_WIDTH; j++){
                intBoard[i][j] = tileBoard[i][j].getType().getMoveAble();
            }
        }
        return intBoard;
    }

    @Override
    public Vector2 getWorldCoordinateOfTile(int x, int y) {
        Tile tile = tileBoard[x][y];
        return new Vector2(tile.getPosition());
    }


}
