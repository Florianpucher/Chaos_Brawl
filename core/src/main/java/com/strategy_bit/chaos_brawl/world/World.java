package com.strategy_bit.chaos_brawl.world;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.ashley.components.BoundaryComponent;
import com.strategy_bit.chaos_brawl.ashley.components.MovementComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TeamGameObjectComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.ashley.engine.MyEngine;
import com.strategy_bit.chaos_brawl.ashley.entities.Projectile;
import com.strategy_bit.chaos_brawl.ashley.entities.Explosion;
import com.strategy_bit.chaos_brawl.ashley.systems.BulletDeleteSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.BulletSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.CombatSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.DeleteSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.ExplosionSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.MovementSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.RenderSystem;
import com.strategy_bit.chaos_brawl.config.WorldSettings;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.pathfinder.OtherPathfinder;
import com.strategy_bit.chaos_brawl.player_input_output.PawnController;
import com.strategy_bit.chaos_brawl.types.EventType;
import com.strategy_bit.chaos_brawl.types.UnitType;
import com.strategy_bit.chaos_brawl.util.Boundary;
import com.strategy_bit.chaos_brawl.util.VectorMath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Central manager for game
 *
 * @author AIsopp
 * @version 1.0
 * @since 15.03.2018
 */
public class World implements InputHandler {

    protected long lastID = 0;
    protected long lastProjectileID = 0;

    protected HashMap<Long, Entity> units;
    protected HashMap<Long, Entity> projectiles;

    ArrayList<Explosion> explosions;

    protected SpawnerImpl spawner;
    protected MyEngine engine;
    protected Camera camera;
    public BoardInterface board;
    protected PawnController[] playerControllers;
    protected Entity[] bases;
    protected Entity[] tower;
    protected long resourceTimeStamp;
    protected OtherPathfinder gdxPathFinder;
    protected DeleteSystem deleteSystem;

    boolean endGame = false;
    boolean buildingDestroyed = false;

    public World(int map, int players) {
        units = new HashMap<>();
        projectiles = new HashMap<>();
        spawner = new SpawnerImpl();
        playerControllers = new PawnController[players];
        bases = new Entity[players];
        tower = new Entity[players];
        explosions = new ArrayList<>();
        createEngine();
        createWorld(map);
    }

    public World() {
        units = new HashMap<>();
        projectiles = new HashMap<>();
        spawner = new SpawnerImpl();
        playerControllers = new PawnController[2];
        createEngine();
        createWorld(1);
    }

    public void setPlayerController(int index, PawnController pawnController){
        playerControllers[index] = pawnController;
    }

    public void initializeGameForPlayers(){
        //TODO support variable player length
        createEntityWorldCoordinates(new Vector2(6,12), UnitType.TOWER,  playerControllers[0].getTeamID());
        createEntityWorldCoordinates(new Vector2(6,3), UnitType.TOWER,  playerControllers[0].getTeamID());
        createEntityWorldCoordinates(new Vector2(2,7.5f), UnitType.MAINBUILDING,  playerControllers[0].getTeamID());

        createEntityWorldCoordinates(new Vector2(14,12), UnitType.TOWER,  playerControllers[1].getTeamID());
        createEntityWorldCoordinates(new Vector2(14,3), UnitType.TOWER,  playerControllers[1].getTeamID());
        createEntityWorldCoordinates(new Vector2(19,7.5f), UnitType.MAINBUILDING,  playerControllers[1].getTeamID());
        resourceTimeStamp = System.currentTimeMillis();
    }


    public void createProjectile(Entity entity){
        engine.addEntity(entity);
        //projectiles.put(lastProjectileID, entity);
        //lastProjectileID++;
    }

    protected void createEngine(){
        engine = MyEngine.createEngine(units);
        //Add some logic
        RenderSystem renderSystem = new RenderSystem();
        camera = renderSystem.getCamera();
        deleteSystem = new DeleteSystem(camera);
        engine.addSystem(deleteSystem);
        engine.addSystem(new MovementSystem());
        BulletSystem bulletSystem=new BulletSystem();
        engine.addSystem(bulletSystem);
        bulletSystem.addWorld(this);
        CombatSystem combatSystem=new CombatSystem();
        combatSystem.addWorld(this);
        engine.addSystem(combatSystem);
        engine.addSystem(new BulletDeleteSystem());
        //Renderer should be the last system to add

        engine.addSystem(renderSystem);

        engine.addSystem(new ExplosionSystem(camera));

    }


    /**
     * creates Game board ({@link Board})
     */
    private void createWorld(int map){
        board = new Board(engine, map);
        gdxPathFinder = new OtherPathfinder(board);
    }


    public void render(){
        updateResources();
        engine.update(Gdx.graphics.getDeltaTime());
        endGame = checkWinningLosing();
        buildingDestroyed = checkBuildingDestroyed();
    }


    public boolean checkWinningLosing(){

        for (Entity base: bases){
            if(base == null){
                return false;
            }
        }

        // Send losing messages
        int aliveCounter = 0;
        int lastAlive = -1;
        for (int i = 0; i < bases.length; i++) {
            if (bases[i].getComponent(TeamGameObjectComponent.class).getHitPoints() <= 0) {
                playerControllers[i].gameOver(false);
            } else {
                aliveCounter++;
                lastAlive = i;
            }
        }
        if (aliveCounter == 1) {
            playerControllers[lastAlive].gameOver(true);
            engine.removeAllEntities();
            return true;
        } else if (aliveCounter == 0) {
            System.err.println("A draw happens suddenly");
            return true;
        }
        return false;
    }

    public boolean checkBuildingDestroyed() {
        for (Entity base : bases) {
            if (base == null) {
                return false;
            }
        }
        for (Entity tower : tower) {
            if (tower == null) {
                return false;
            }
        }
        for (int i = 0; i < bases.length; i++) {
            if (bases[i].getComponent(TeamGameObjectComponent.class).getHitPoints() <= 0) {
                return true;
            }
        }
        for (int i = 0; i < tower.length; i++) {
            if (tower[i].getComponent(TeamGameObjectComponent.class).getHitPoints() <= 0) {
                return true;
            }
        }
        return false;
    }

    // update recources
    protected void updateResources(){
        if(System.currentTimeMillis() - resourceTimeStamp > 1){
            for (PawnController controller :
                    playerControllers) {
                controller.tick();
                resourceTimeStamp = System.currentTimeMillis();
            }
        }
    }


    public void dispose() {
        engine.dispose();
    }


    @Override
    public void sendTouchInput(Vector2 screenCoordinates, PawnController controller) {

        Vector3 withZCoordinate = new Vector3(screenCoordinates, 0);
        Vector3 translated = camera.unproject(withZCoordinate);
        Vector2 targetLocation = new Vector2(translated.x,translated.y);
        int baseIndex = checkIfClickHappensOnBase(targetLocation, controller);
        if(baseIndex >= 0){
            controller.triggeredEvent(EventType.CLICKED_ON_ENEMY_BASE, baseIndex);
        }
    }


    protected int checkIfClickHappensOnBase(Vector2 targetLocation, PawnController controller){
        for (int i = 0; i < bases.length; i++) {
            Entity base = bases[i];
            if(base == null || i == controller.getTeamID() ||base.getComponent(TeamGameObjectComponent.class).getHitPoints() <= 0f){
                continue;
            }

            if (base.getComponent(BoundaryComponent.class).isWithinBorders(targetLocation)) {
                return i;
            }
        }
        return -1;
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
        Entity entity = createEntityInternal(entityType, lastID, worldCoordinates, teamID);
        lastID++;

        MovementComponent movementComponent = entity.getComponent(MovementComponent.class);
        //Move entity to enemy player
        if(movementComponent != null){
            //TODO add pathfinding here Florian but maybe with ThreadPool implementation!!!
            Array<Vector2> path = gdxPathFinder.calculatePath(entity.getComponent(TransformComponent.class).getPosition(), bases[playerControllers[teamID].getCurrentTargetTeam()].getComponent(TransformComponent.class).getPosition());
            movementComponent.setPath(path);

        }


    }

    Entity createEntityInternal(UnitType entityType, long unitID, Vector2 worldCoordinates, int teamID){
        Entity entity = spawner.createNewUnit(entityType,teamID,worldCoordinates);
        engine.addEntity(entity);
        units.put(unitID, entity);
        if(entityType.equals(UnitType.MAINBUILDING)){
            bases[teamID] = entity;
        }else if (entityType.equals(UnitType.KNIGHT)) {
            AssetManager.getInstance().drawSword.play(1f);
        } else if (entityType.equals(UnitType.SWORDFIGHTER)) {
            AssetManager.getInstance().getRandomDrawKatanaSound().play(1f);
        }

        return entity;
    }


    public void createBulletWorldCoordinates(Vector2 worldCoordinates, long targetId,float damage) {
        Entity projectile=new Entity();

        Projectile.setComponents(projectile,worldCoordinates,targetId,damage);

        createProjectile(projectile);

    }
    public long getIdOfUnit(Entity unit){
        for (Map.Entry<Long, Entity> entry : units.entrySet()) {
            if (entry.getValue().equals(unit)) {
                return entry.getKey();
            }
        }
        return -1;
    }



    /**
     *
     * NOTE: current playerID can be only 1 or 2
     *
     * @param playerID for which player the spawn area will be created
     * @return a 4x2 matrix where each column represents a position: the lower left, lower right, upper left and upper right corner in screen coordinates
     */
    public Boundary createSpawnAreaForPlayer(int playerID){
        //TODO: modulo 2 for experimental 3-4players
        playerID=playerID%2;
        //TODO move spawn Area boundaries and player base positions to board
        //Simple implementation for creating spawn area
        int spawnAreaWidth = 5;
        Boundary spawnArea;
        // current left player
        if(playerID == 0){
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
        }else if(playerID == 1){
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

    public void cheatFunctionDisposer(){
        if (playerControllers[0].isCheatFunctionActive()){
            if (playerControllers[1].isCheatFunctionActive()){
                playerControllers[0].setCheatFunctionActive(false);
            }
        }
        if (playerControllers[1].isCheatFunctionActive()){
            if (playerControllers[0].isCheatFunctionActive()){
                playerControllers[1].setCheatFunctionActive(false);
            }
        }
    }

    public Vector2 getUnitPosition(long unitID){
        Entity entity=getUnit(unitID);
        if (entity!=null)
        return entity.getComponent(TransformComponent.class).getPosition();
        else return null;
    }
    public Entity getUnit(long unitID){
        return units.get(unitID);
    }
    public Camera getCamera() {
        return camera;
    }
}
