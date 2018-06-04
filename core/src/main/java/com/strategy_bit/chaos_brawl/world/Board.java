package com.strategy_bit.chaos_brawl.world;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.ashley.entities.BackgroundTile;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.types.TileType;
import com.strategy_bit.chaos_brawl.types.UnitType;
import com.strategy_bit.chaos_brawl.util.Boundary;
import com.strategy_bit.chaos_brawl.util.VectorMath;
import com.badlogic.gdx.graphics.Camera;

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

    private float multiplicandX = FRUSTUM_WIDTH / BOARD_WIDTH;
    private float multiplicandY = FRUSTUM_HEIGHT / BOARD_HEIGHT;

    public Board(Engine engine, int map) {
        tileBoard = new Tile[BOARD_HEIGHT][BOARD_WIDTH];

        int[][] mapArray = new int[BOARD_HEIGHT][BOARD_WIDTH];

        TextureRegion defaultTile = AssetManager.getInstance().defaultTile;
        Vector2 size = new Vector2(multiplicandX / (defaultTile.getRegionWidth() * PIXELS_TO_METRES), multiplicandY / (defaultTile.getRegionHeight() * PIXELS_TO_METRES));

        Array<FileHandle> maps = AssetManager.getInstance().maps;
        //TODO fix this
        map = map-1;
        mapArray = fileReader(maps.get(map).readString(), mapArray);
        matrixToBoard(mapArray, size);
        createTileBoard(engine);
    }

    private void createTileBoard(Engine engine) {
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                engine.addEntity((BackgroundTile) tileBoard[i][j]);
            }
        }
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

    private void matrixToBoard(int[][] map, Vector2 size) {
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (map[i][j] == 0) {
                    Tile tile = new BackgroundTile(TileType.GRASS);
                    tileBoard[i][j] = tile;
                    tile.setPosition(new Vector2(multiplicandX / 2 + multiplicandX * j, multiplicandY / 2 + multiplicandY * i));
                    tile.setScale(size);
                }
                if (map[i][j] == 1) {
                    Tile tile = new BackgroundTile(TileType.WATER);
                    tileBoard[i][j] = tile;
                    tile.setPosition(new Vector2(multiplicandX / 2 + multiplicandX * j, multiplicandY / 2 + multiplicandY * i));
                    tile.setScale(size);
                }
                if (map[i][j] == 2) {
                    Tile tile = new BackgroundTile(TileType.DIRT);
                    tileBoard[i][j] = tile;
                    tile.setPosition(new Vector2(multiplicandX / 2 + multiplicandX * j, multiplicandY / 2 + multiplicandY * i));
                    tile.setScale(size);
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
        Boundary spawnArea0, spawnArea1, spawnArea2, spawnArea3;
        Array<Vector2> vectorList = new Array<>();

        if (players == 2){
            for (int i = 0; i < AssetManager.getInstance().spawn.size - 1; i = i + 2){
                vectorList.add(new Vector2(AssetManager.getInstance().spawn.get(i), AssetManager.getInstance().spawn.get(i+1)));
            }
            spawnArea0 = new Boundary(vectorList.get(0), vectorList.get(1), vectorList.get(2), vectorList.get(3));
            spawnArea1 = new Boundary(vectorList.get(4), vectorList.get(5), vectorList.get(6), vectorList.get(7));
        }
        if (players == 4){
            for (int i = 0; i < AssetManager.getInstance().spawn4.size - 1; i = i + 2){
                vectorList.add(new Vector2(AssetManager.getInstance().spawn4.get(i), AssetManager.getInstance().spawn4.get(i+1)));
            }
            spawnArea0 = new Boundary(vectorList.get(0), vectorList.get(1), vectorList.get(2), vectorList.get(3));
            spawnArea1 = new Boundary(vectorList.get(4), vectorList.get(5), vectorList.get(6), vectorList.get(7));
            spawnArea2 = new Boundary(vectorList.get(8), vectorList.get(9), vectorList.get(10), vectorList.get(11));
            spawnArea3 = new Boundary(vectorList.get(12), vectorList.get(13), vectorList.get(14), vectorList.get(15));
        }
        else{
            throw new UnsupportedOperationException("Game only supports two player mode at the moment :)");
        }

        if (playerID == 0){
            return spawnArea0;
        }
        if (playerID == 1){
            return spawnArea1;
        }
        if (playerID == 2){
            return spawnArea2;
        }
        else if (playerID == 4){
            return spawnArea3;
        }
        throw new UnsupportedOperationException("Game only supports two player mode at the moment :)");
    }

    public Array<Float> getAsset(int asset){
        switch (asset){
            case 0: return AssetManager.getInstance().config;

            case 1: return AssetManager.getInstance().config2;

        default: throw new UnsupportedOperationException("Can't play with this amount of players");
        }
    }
}
