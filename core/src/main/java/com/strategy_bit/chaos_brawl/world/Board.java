package com.strategy_bit.chaos_brawl.world;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.ashley.engine.MyEngine;
import com.strategy_bit.chaos_brawl.ashley.entities.BackgroundTile;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.types.TileType;
import com.strategy_bit.chaos_brawl.util.Boundary;
import com.strategy_bit.chaos_brawl.util.VectorMath;

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

    AssetManager assetManager;

    private float multiplicandX = FRUSTUM_WIDTH / BOARD_WIDTH;
    private float multiplicandY = FRUSTUM_HEIGHT / BOARD_HEIGHT;

    public Board(MyEngine engine, int map) {
        tileBoard = new Tile[BOARD_HEIGHT][BOARD_WIDTH];

        int[][] mapArray = new int[BOARD_HEIGHT][BOARD_WIDTH];

        TextureRegion defaultTile = assetManager.getDefaultTile();
        Vector2 size = new Vector2(multiplicandX / (defaultTile.getRegionWidth() * PIXELS_TO_METRES), multiplicandY / (defaultTile.getRegionHeight() * PIXELS_TO_METRES));

        Array<FileHandle> maps = assetManager.getMaps();
        map = map-1;
        mapArray = fileReader(maps.get(map).readString(), mapArray);
        matrixToBoard(mapArray, size, engine);
    }


    private int[][] fileReader(String file, int[][] mapArray) {
        Scanner scanner = null;
        try {
            scanner = new Scanner((file));
            for (int i = 0; i < BOARD_HEIGHT; i++) {
                for (int j = 0; j < BOARD_WIDTH; j++) {
                    mapArray[i][j] = scanner.nextInt();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(scanner != null){
                scanner.close();
            }
        }
        return mapArray;
    }

    private void matrixToBoard(int[][] map, Vector2 size, MyEngine engine) {
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (map[i][j] == 0) {
                    Tile tile = new BackgroundTile(TileType.GRASS, engine);
                    tileBoard[i][j] = tile;
                    tile.setPosition(new Vector2(multiplicandX / 2 + multiplicandX * j, multiplicandY / 2 + multiplicandY * i));
                    tile.setScale(size);
                    engine.addEntity((BackgroundTile)tile);
                }
                if (map[i][j] == 1) {
                    Tile tile = new BackgroundTile(TileType.WATER, engine);
                    tileBoard[i][j] = tile;
                    tile.setPosition(new Vector2(multiplicandX / 2 + multiplicandX * j, multiplicandY / 2 + multiplicandY * i));
                    tile.setScale(size);
                    engine.addEntity((BackgroundTile)tile);
                }
                if (map[i][j] == 2) {
                    Tile tile = new BackgroundTile(TileType.DIRT, engine);
                    tileBoard[i][j] = tile;
                    tile.setPosition(new Vector2(multiplicandX / 2 + multiplicandX * j, multiplicandY / 2 + multiplicandY * i));
                    tile.setScale(size);
                    engine.addEntity((BackgroundTile)tile);
                }
            }
        }
    }

    @Override
    public Tile[][] getTileBoard() {
        return tileBoard;
    }

    public int[][] boardToMatrix() {
        int[][] intBoard = new int[BOARD_HEIGHT][BOARD_WIDTH];
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
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
                if (distance < closedDistance) {
                    closedDistance = (float) distance;
                    tilePosition = new Vector2(i, j);
                }
            }
        }

        return tilePosition;
    }

    public Boundary createSpawnAreaForPlayer(int playerID, int players){
        Boundary spawnArea0 = null;
        Boundary spawnArea1 = null;
        Boundary spawnArea2 = null;
        Boundary spawnArea3 = null;
        Array<Vector2> vectorList = new Array<>();

        if (players == 2){
            for (int i = 0; i < assetManager.getSpawn().size - 1; i = i + 2){
                vectorList.add(getWorldCoordinateOfTile(assetManager.getSpawn().get(i).intValue(),
                        assetManager.getSpawn().get(i+1).intValue()));
            }
            spawnArea0 = new Boundary(vectorList.get(0), vectorList.get(1), vectorList.get(2), vectorList.get(3));
            spawnArea1 = new Boundary(vectorList.get(4), vectorList.get(5), vectorList.get(6), vectorList.get(7));
        }
        if (players == 4){
            for (int i = 0; i < assetManager.getSpawn4().size - 1; i = i + 2){
                vectorList.add(getWorldCoordinateOfTile(assetManager.getSpawn4().get(i).intValue(),
                        assetManager.getSpawn4().get(i+1).intValue()));
            }
            spawnArea2 = new Boundary(vectorList.get(0), vectorList.get(1), vectorList.get(2), vectorList.get(3));
            spawnArea3 = new Boundary(vectorList.get(4), vectorList.get(5), vectorList.get(6), vectorList.get(7));
            spawnArea1 = new Boundary(vectorList.get(8), vectorList.get(9), vectorList.get(10), vectorList.get(11));
            // player0 host
            spawnArea0 = new Boundary(vectorList.get(12), vectorList.get(13), vectorList.get(14), vectorList.get(15));
        }


        if (playerID == 0){
            return spawnArea0;
        }
        if (playerID == 1){
            return spawnArea1;
        }
        if (playerID == 2){
            return spawnArea3;
        }
        else if (playerID == 3){
            return spawnArea2;
        }
        throw new UnsupportedOperationException("Game only supports up to 4 players");
    }


    public Array<Vector2> getConfig(int asset){
        Array<Vector2> array = new Array<>();
        switch (asset){

            case 0:
                for (int i = 0; i < assetManager.getConfig().size - 1; i = i + 2) {
                    array.add( new Vector2(assetManager.getConfig().get(i), assetManager.getConfig().get(i + 1)));
                }
                return array;

            case 1:
                for (int i = 0; i < assetManager.getConfig2().size - 1; i = i + 2) {
                    array.add( new Vector2(assetManager.getConfig2().get(i), assetManager.getConfig2().get(i + 1)));
                }
                return array;

        default: throw new UnsupportedOperationException("Can't play with this amount of players");
        }
    }
}
