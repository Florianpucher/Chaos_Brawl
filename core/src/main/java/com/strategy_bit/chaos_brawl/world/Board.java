package com.strategy_bit.chaos_brawl.world;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.entity.BackgroundTile;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.types.TileType;
import com.strategy_bit.chaos_brawl.util.VectorMath;

import java.lang.String;
import java.util.Scanner;

import static com.strategy_bit.chaos_brawl.config.WorldSettings.BOARD_HEIGHT;
import static com.strategy_bit.chaos_brawl.config.WorldSettings.BOARD_WIDTH;
import static com.strategy_bit.chaos_brawl.config.WorldSettings.FRUSTUM_HEIGHT;
import static com.strategy_bit.chaos_brawl.config.WorldSettings.FRUSTUM_WIDTH;
import static com.strategy_bit.chaos_brawl.config.WorldSettings.PIXELS_TO_METRES;

/**
 * Created by Florian on 07.05.2018.
 */

public class Board implements BoardInterface {

    private Tile[][] tileBoard;
    private Vector2 tileSize;
    float multiplicandX = FRUSTUM_WIDTH/ BOARD_WIDTH;
    float multiplicandY = FRUSTUM_HEIGHT/ BOARD_HEIGHT;

    public Board(Engine engine, int map){
        tileBoard = new Tile[BOARD_HEIGHT][BOARD_WIDTH];
        Vector2 origin = new Vector2(0,0);

        int[][] mapArray = new int[BOARD_HEIGHT][BOARD_WIDTH];

        TextureRegion defaultTile = AssetManager.getInstance().defaultTile;
        Vector2 size = new Vector2(multiplicandX/(defaultTile.getRegionWidth() * PIXELS_TO_METRES),multiplicandY/(defaultTile.getRegionHeight() * PIXELS_TO_METRES));
        System.out.println(size);
        tileSize = new Vector2(multiplicandX, multiplicandY);

        switch (map){
            case 1:
                    mapArray = fileReader(AssetManager.getInstance().map1.readString(), mapArray);
                    matrixToBoard(mapArray, size);
                    createTileBoard(engine);

            case 2:
                    mapArray = fileReader(AssetManager.getInstance().map2.readString(), mapArray);
                    matrixToBoard(mapArray, size);
                    createTileBoard(engine);

            case 3:
                    mapArray = fileReader(AssetManager.getInstance().map3.readString(), mapArray);
                    matrixToBoard(mapArray, size);
                    createTileBoard(engine);
        }
    }

    private void createTileBoard(Engine engine){
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                engine.addEntity((BackgroundTile) tileBoard[i][j]);
            }
        }
    }

    private int[][] fileReader(String file, int[][] mapArray){
        try {
            //BufferedReader reader = new BufferedReader(new FileReader(file));
            //String line;
            Scanner scanner = new Scanner((file));
            for (int i = 0; i < BOARD_HEIGHT; i++){
                for (int j = 0; j < BOARD_WIDTH; j++){
                    mapArray[i][j] = scanner.nextInt();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapArray;
    }

    private void matrixToBoard(int[][] map, Vector2 size){
        for (int i = 0; i < BOARD_HEIGHT; i++){
            for (int j = 0; j < BOARD_WIDTH; j++){
                if (map[i][j] == 0){
                    Tile tile = new BackgroundTile(TileType.GRASS);
                    tileBoard[i][j] = tile;
                    tile.setPosition(new Vector2(multiplicandX/2 + multiplicandX* j, multiplicandY/2 + multiplicandY *i));
                    tile.setScale(size);
                }
                if (map[i][j] == 1){
                    Tile tile = new BackgroundTile(TileType.WATER);
                    tileBoard[i][j] = tile;
                    tile.setPosition(new Vector2(multiplicandX/2 + multiplicandX* j, multiplicandY/2 + multiplicandY *i));
                    tile.setScale(size);
                }
                if (map[i][j] == 2){
                    Tile tile = new BackgroundTile(TileType.DIRT);
                    tileBoard[i][j] = tile;
                    tile.setPosition(new Vector2(multiplicandX/2 + multiplicandX* j, multiplicandY/2 + multiplicandY *i));
                    tile.setScale(size);
                }
            }
        }
    }

    @Override
    public Tile[][] getTileBoard() {
        return tileBoard;
    }

    public int[][] boardToMatrix(){
        int[][] intBoard=new int[BOARD_HEIGHT][BOARD_WIDTH];
        for(int i = 0; i < BOARD_HEIGHT; i++){
            for (int j = 0; j < BOARD_WIDTH; j++){
                intBoard[i][j] = tileBoard[i][j].getType().getMoveAble();
            }
        }
        return intBoard;
    }

    @Override
    public Vector2 getWorldCoordinateOfTile(int x, int y) {
        Tile tile = tileBoard[y][x];
        return new Vector2(tile.getPosition());
    }

    @Override
    public Vector2 getTileBoardPositionDependingOnWorldCoordinates(Vector2 worldCoordinates) {
        Vector2 tilePosition = new Vector2();
        float closedDistance = 10000;
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                double distance = VectorMath.distance(worldCoordinates, tileBoard[i][j].getPosition());
                if(distance < closedDistance){
                    closedDistance = (float) distance;
                    tilePosition = new Vector2(i,j);
                }
            }
        }

        return tilePosition;
    }
}
