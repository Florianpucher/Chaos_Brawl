package com.strategy_bit.chaos_brawl.world;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.strategy_bit.chaos_brawl.ResourceSystem.Resource;
import com.strategy_bit.chaos_brawl.ResourceSystem.ResourceGold;
import com.strategy_bit.chaos_brawl.ashley.components.MovementComponent;
import com.strategy_bit.chaos_brawl.ashley.engine.MyEngine;
import com.strategy_bit.chaos_brawl.ashley.systems.BulletSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.CombatSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.DeleteSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.MovementSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.RenderSystem;

import com.strategy_bit.chaos_brawl.config.WorldSettings;
import com.strategy_bit.chaos_brawl.types.UnitType;
import com.strategy_bit.chaos_brawl.util.Boundary;
import com.strategy_bit.chaos_brawl.util.VectorMath;


import java.util.HashMap;

/**
 * Central manager for game
 *
 * @author AIsopp
 * @version 1.0
 * @since 15.03.2018
 */
public class World implements InputHandler {

    protected long lastID = 0;

    protected HashMap<Long, Entity> units;
    protected HashMap<Long, Resource> resources;

    protected SpawnerImpl spawner;
    protected MyEngine engine;
    protected Camera camera;
    protected Board board;

    public World(int map) {
        units = new HashMap<Long, Entity>();
        resources= new HashMap<Long, Resource>();
        spawner = new SpawnerImpl(this);
        createEngine();
        createWorld(map);
    }

    public World() {
        units = new HashMap<Long, Entity>();
        spawner = new SpawnerImpl(this);
        createEngine();
        createWorld(1);
    }

    public void createEntity(Entity entity){
        engine.addEntity(entity);
        units.put(lastID, entity);
        lastID++;
    }

    protected void createEngine(){
        engine = new MyEngine(units);
        //Add some logic
        engine.addSystem(new Resourcesystem());
        engine.addSystem(new DeleteSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new BulletSystem());
        engine.addSystem(new CombatSystem());
        //Renderer should be the last system to add
        RenderSystem renderSystem = new RenderSystem();
        engine.addSystem(renderSystem);
        camera = renderSystem.getCamera();
    }


    /**
     * creates Game board ({@link BoardA},{@link BoardB},{@link BoardC})
     */
    private void createWorld(int map){
        if(map == 1){
            board = new BoardA(engine);
        }
        if(map == 2){
            board = new BoardB(engine);
        }
        if(map == 3){
            board = new BoardC(engine);
        }
    }

    public void render(){
        engine.update(Gdx.graphics.getDeltaTime());
    }



    public void dispose(){
        engine.dispose();
    }


    @Override
    public void sendTouchInput(Vector2 screenCoordinates, long entityID) {
        Vector3 withZCoordinate = new Vector3(screenCoordinates, 0);
        Vector3 translated = camera.unproject(withZCoordinate);
        Vector2 targetLocation = new Vector2(translated.x,translated.y);
        moveEntity(targetLocation, entityID);
    }

    @Override
    public void createEntityScreenCoordinates(Vector2 screenCoordinates, UnitType entityType, int teamID) {
        Vector3 withZCoordinate = new Vector3(screenCoordinates, 0);
        Vector3 translated = camera.unproject(withZCoordinate);
        Vector2 targetLocation = new Vector2(translated.x,translated.y);
        createEntityWorldCoordinates(targetLocation, entityType, teamID);
    }

    @Override
    public void createEntityWorldCoordinates(Vector2 worldCoordinates, UnitType entityType, int teamID) {
        Entity entity = spawner.createNewUnit(entityType,teamID,worldCoordinates);
        if(entity==null){
            //not enough resources
            return;
        }
        createEntity(entity);
    }

    public void moveEntity(Vector2 worldCoordinates, long entityID){
        Entity entity = units.get(entityID);
        if(entity == null){
            return;
        }
        entity.getComponent(MovementComponent.class).setTargetLocation(worldCoordinates);
    }

    /**
     *
     * NOTE: current playerID can be only 1 or 2
     * @param playerID for which player the spawn area will be created
     * @return a 4x2 matrix where each column represents a position: the lower left, lower right, upper left and upper right corner in screen coordinates
     */
    public Boundary createSpawnAreaForPlayer(int playerID){
        //TODO add spawn Area boundaries and player base positions to board
        //Simple implementation for creating spawn area
        int spawnAreaWidth = 5;
        Boundary spawnArea;
        // current left player
        if(playerID == 1){
            Vector2 lowerLeftCorner = new Vector2(0.0f - WorldSettings.FRUSTUM_WIDTH/2f, WorldSettings.FRUSTUM_HEIGHT/2f);
            Vector2 upperLeftCorner = new Vector2(0.0f - WorldSettings.FRUSTUM_WIDTH/2f , 0.0f - WorldSettings.FRUSTUM_HEIGHT/2f);
            Vector2 lowerRightCorner = new Vector2( board.getWorldCoordinateOfTile(spawnAreaWidth,0).x - WorldSettings.FRUSTUM_WIDTH/2f, WorldSettings.FRUSTUM_HEIGHT/2f);
            Vector2 upperRightCorner = new Vector2(board.getWorldCoordinateOfTile(spawnAreaWidth,0).x - WorldSettings.FRUSTUM_WIDTH/2f,0.0f - WorldSettings.FRUSTUM_HEIGHT/2f);

            lowerLeftCorner = VectorMath.vector3ToVector2(camera.project(new Vector3(lowerLeftCorner,0)));
            upperLeftCorner = VectorMath.vector3ToVector2(camera.project(new Vector3(upperLeftCorner,0)));
            lowerRightCorner = VectorMath.vector3ToVector2(camera.project(new Vector3(lowerRightCorner,0)));
            upperRightCorner = VectorMath.vector3ToVector2(camera.project(new Vector3(upperRightCorner,0)));

            spawnArea = new Boundary(lowerLeftCorner, lowerRightCorner, upperLeftCorner, upperRightCorner);
            return spawnArea;
            //current right player
        }else if(playerID == 2){
            Vector2 lowerRightCorner = new Vector2(WorldSettings.FRUSTUM_WIDTH/2f, WorldSettings.FRUSTUM_HEIGHT/2f);
            Vector2 upperRightCorner = new Vector2(WorldSettings.FRUSTUM_WIDTH/2f, 0.0f - WorldSettings.FRUSTUM_HEIGHT/2f);
            Vector2 lowerLeftCorner = new Vector2( board.getWorldCoordinateOfTile(WorldSettings.BOARD_WIDTH - 5,0).x  - WorldSettings.FRUSTUM_WIDTH/2f, WorldSettings.FRUSTUM_HEIGHT/2f);
            Vector2 upperLeftCorner = new Vector2(board.getWorldCoordinateOfTile(WorldSettings.BOARD_WIDTH - 5,0).x  - WorldSettings.FRUSTUM_WIDTH/2f,0.0f - WorldSettings.FRUSTUM_HEIGHT/2f);


            lowerLeftCorner = VectorMath.vector3ToVector2(camera.project(new Vector3(lowerLeftCorner,0)));
            upperLeftCorner = VectorMath.vector3ToVector2(camera.project(new Vector3(upperLeftCorner,0)));
            lowerRightCorner = VectorMath.vector3ToVector2(camera.project(new Vector3(lowerRightCorner,0)));
            upperRightCorner = VectorMath.vector3ToVector2(camera.project(new Vector3(upperRightCorner,0)));

            spawnArea = new Boundary(lowerLeftCorner, lowerRightCorner, upperLeftCorner, upperRightCorner);
            return spawnArea;
        }
        throw new UnsupportedOperationException("Game only supports two player mode at the moment :)");
    }

    public Camera getCamera() {
        return camera;
    }

    public void createResource(int teamId){
        Resource resource=new ResourceGold(teamId);
        resources.put((long)teamId,resource);
    }
}
