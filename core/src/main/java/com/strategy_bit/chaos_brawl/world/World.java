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
import com.strategy_bit.chaos_brawl.ashley.entities.Fireball;
import com.strategy_bit.chaos_brawl.ashley.entities.Projectile;
import com.strategy_bit.chaos_brawl.ashley.systems.BulletDeleteSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.BulletSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.CombatSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.DeleteSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.ExplosionSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.MovementSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.RenderSystem;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.pathfinder.OtherPathfinder;
import com.strategy_bit.chaos_brawl.player_input_output.PawnController;
import com.strategy_bit.chaos_brawl.types.EventType;
import com.strategy_bit.chaos_brawl.util.Boundary;
import com.strategy_bit.chaos_brawl.util.VectorMath;

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

    protected HashMap<Long, Entity> units;

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

    public World(int map, int players) {
        units = new HashMap<>();
        spawner = new SpawnerImpl();
        playerControllers = new PawnController[players];
        bases = new Entity[players];
        tower = new Entity[players];

        createEngine();
        createWorld(map);
    }

    public World() {
        units = new HashMap<>();
        spawner = new SpawnerImpl();
        playerControllers = new PawnController[2];
        createEngine();
        createWorld(1);
    }

    public void setPlayerController(int index, PawnController pawnController){
        playerControllers[index] = pawnController;
    }

    public void initializeGameForPlayers(){
        int configMap;
        if (playerControllers.length < 3) {
            configMap = 0;

        } else if (playerControllers.length == 4){
            configMap = 1;
        }
        else {
            configMap = -1;
        }

        setEntityWorldCoordinates(board.getAsset(configMap), playerControllers.length);
        resourceTimeStamp = System.currentTimeMillis();
    }

    private void setEntityWorldCoordinates(Array<Float> spawn, int players){
        int offset = 0;
        for (int j = 0; j < players; j++){
            createEntityWorldCoordinates(new Vector2(spawn.get(offset), spawn.get(offset+1)), 7,  playerControllers[j].getTeamID());
            createEntityWorldCoordinates(new Vector2(spawn.get(offset+2),spawn.get(offset+3)), 7,  playerControllers[j].getTeamID());
            createEntityWorldCoordinates(new Vector2(spawn.get(offset+4),spawn.get(offset+5)), 6,  playerControllers[j].getTeamID());
            offset += 6;
        }
    }


    public void createProjectile(Entity entity){
        engine.addEntity(entity);
    }

    public void createFireball(Entity entity){
        engine.addEntity(entity);
    }

    protected void createEngine(){
        engine = MyEngine.createEngine(units);
        //Add some logic
        RenderSystem renderSystem = new RenderSystem();
        camera = renderSystem.getCamera();
        deleteSystem = new DeleteSystem();
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
    }


    /**
     * returns true if one players one, false otherwise and does inform players if they lose or win
     * @return true if one players one, false otherwise
     */
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
            Gdx.app.error("DRAW","A draw happens suddenly");
            return true;
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
    public void createEntityScreenCoordinates(Vector2 screenCoordinates, int unitId, int teamID) {
        Vector3 withZCoordinate = new Vector3(screenCoordinates, 0);
        Vector3 translated = camera.unproject(withZCoordinate);
        Vector2 targetLocation = new Vector2(translated.x,translated.y);
        createEntityWorldCoordinates(targetLocation, unitId, teamID);
    }

    @Override
    public void createEntityWorldCoordinates(Vector2 worldCoordinates, int unitId, int teamID) {
        Entity entity = createEntityInternal(unitId, lastID, worldCoordinates, teamID);
        lastID++;

        MovementComponent movementComponent = entity.getComponent(MovementComponent.class);
        //Move entity to enemy player
        if(movementComponent != null){
            //TODO add pathfinding here Florian but maybe with ThreadPool implementation!!!
            Array<Vector2> path = gdxPathFinder.calculatePath(entity.getComponent(TransformComponent.class).getPosition(), bases[playerControllers[teamID].getCurrentTargetTeam()].getComponent(TransformComponent.class).getPosition());
            movementComponent.setPath(path);

        }


    }

    Entity createEntityInternal(int unitId, long unitID, Vector2 worldCoordinates, int teamID){
        Entity entity = spawner.createNewUnit(unitId,teamID,worldCoordinates);
        engine.addEntity(entity);
        units.put(unitID, entity);
        if(unitId==6){
            bases[teamID] = entity;
        }
        else if (unitId==5||unitId==2){
            AssetManager.getInstance().drawSword.play(1f);
        }
        else if (unitId==1||unitId==4){
            AssetManager.getInstance().getRandomDrawKatanaSound().play(1f);
        }

        return entity;
    }


    public void createBulletWorldCoordinates(Vector2 worldCoordinates, long targetId, float damage, int type) {
        Entity projectile = new Entity();

        if (type == 0) {
            Projectile.setComponents(projectile, worldCoordinates, targetId, damage);
        } else if (type == 1) {
            Fireball.setComponents(projectile, worldCoordinates, targetId, damage);
        }

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
        Boundary result = board.createSpawnAreaForPlayer(playerID);

        Vector2 left = VectorMath.vector3ToVector2(camera.project(new Vector3(result.getLowerLeft(), 0)));
        Vector2 right = VectorMath.vector3ToVector2(camera.project(new Vector3(result.getLowerRight(), 0)));
        Vector2 left2 = VectorMath.vector3ToVector2(camera.project(new Vector3(result.getUpperLeft(), 0)));
        Vector2 right2 = VectorMath.vector3ToVector2(camera.project(new Vector3(result.getUpperRight(), 0)));
        return new Boundary(left, right, left2, right2);
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
