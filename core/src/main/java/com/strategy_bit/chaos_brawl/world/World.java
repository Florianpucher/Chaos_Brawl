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
import com.strategy_bit.chaos_brawl.ashley.components.UpgradeComponent;
import com.strategy_bit.chaos_brawl.ashley.engine.MyEngine;
import com.strategy_bit.chaos_brawl.ashley.entities.CurrentTargetMarker;
import com.strategy_bit.chaos_brawl.ashley.entities.Projectiles;
import com.strategy_bit.chaos_brawl.ashley.systems.BulletDeleteSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.BulletSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.CombatSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.DeleteSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.ExplosionSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.MovementSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.ReRouteSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.RenderSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.UpgradeSystem;
import com.strategy_bit.chaos_brawl.config.UnitConfig;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.managers.SoundManager;
import com.strategy_bit.chaos_brawl.pathfinder.OtherPathfinder;
import com.strategy_bit.chaos_brawl.player_input_output.PawnController;
import com.strategy_bit.chaos_brawl.types.EventType;
import com.strategy_bit.chaos_brawl.util.Boundary;
import com.strategy_bit.chaos_brawl.util.SpawnArea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
    protected OtherPathfinder gdxPathFinder;
    protected DeleteSystem deleteSystem;
    protected UpgradeSystem upgradeSystem;

    protected Entity marker;

    boolean endGame = false;
    private int players;

    public World(int map, int players, boolean containsDeleteSystem) {
        units = new HashMap<>();
        spawner = new SpawnerImpl();
        playerControllers = new PawnController[players];
        bases = new Entity[players];
        tower = new Entity[players];
        this.players = players;

        createEngine(containsDeleteSystem);
        marker=new CurrentTargetMarker(new Vector2(0,0));
        engine.addEntity(marker);
        createWorld(map);
    }

    public World() {
        units = new HashMap<>();
        spawner = new SpawnerImpl();
        playerControllers = new PawnController[2];
        createEngine(true);
        marker=new CurrentTargetMarker(new Vector2(0,0));
        engine.addEntity(marker);
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

        setEntityWorldCoordinates(board.getConfig(configMap), playerControllers.length);

    }

    private void setEntityWorldCoordinates(Array<Vector2> spawn, int players){
        int offset = 0;
        for (int j = 0; j < players; j++){
            createEntityWorldCoordinates(board.getWorldCoordinateOfTile((int) spawn.get(offset).x,
                    (int) spawn.get(offset).y), 7,  playerControllers[j].getTeamID());
            createEntityWorldCoordinates(board.getWorldCoordinateOfTile((int) spawn.get(offset+1).x,
                    (int) spawn.get(offset+1).y), 7,  playerControllers[j].getTeamID());
            createEntityWorldCoordinates(board.getWorldCoordinateOfTile((int) spawn.get(offset+2).x,
                    (int) spawn.get(offset+2).y), 6,  playerControllers[j].getTeamID());
            offset += 3;
        }
    }


    public void createProjectile(Entity entity){
        engine.addEntity(entity);
    }


    protected void createEngine(boolean containsDeleteSystem){
        engine = MyEngine.createEngine();
        //Add some logic
        RenderSystem renderSystem = new RenderSystem();
        camera = renderSystem.getCamera();
        deleteSystem = new DeleteSystem(units);
        if(containsDeleteSystem){
            engine.addSystem(deleteSystem);
        } else
        {
            deleteSystem.addedToEngine(engine);
        }


        upgradeSystem = new UpgradeSystem(this);

        engine.addSystem(upgradeSystem);
        engine.addSystem(new MovementSystem());
        BulletSystem bulletSystem=new BulletSystem();
        engine.addSystem(bulletSystem);
        bulletSystem.addWorld(this);
        CombatSystem combatSystem=new CombatSystem();
        combatSystem.addWorld(this);
        engine.addSystem(combatSystem);
        engine.addSystem(new BulletDeleteSystem());
        ReRouteSystem reRouteSystem=new ReRouteSystem(this);
        engine.addSystem(reRouteSystem);
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
        updateResources(Gdx.graphics.getDeltaTime());
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
    protected void updateResources(float deltaTime){
            for (PawnController controller :
                    playerControllers) {
                controller.tick(deltaTime);
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


    @Override
    public void updateTowersOrUnits(int playerID, int updateType) {
        HashMap<Long, Entity> map = new HashMap<>();
        Iterator<Map.Entry<Long, Entity>> iterator = units.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<Long,Entity> entry = iterator.next();
            Entity unit = entry.getValue();
            TeamGameObjectComponent component = unit.getComponent(TeamGameObjectComponent.class);
            System.out.println(component.getUnitType());
            if((int)unit.getComponent(TeamGameObjectComponent.class).getUnitType() != updateType)
            {
                continue;
            }
            Entity newEntity = upgradeSystem.UpgradeToNextTier(unit, iterator);
            if(newEntity != null)
            {
                map.put(entry.getKey(), newEntity);
            }
        }
        Iterator<Map.Entry<Long, Entity>> iterator2 = map.entrySet().iterator();
        while (iterator2.hasNext())
        {
            Map.Entry<Long,Entity> entry = iterator2.next();
            units.put(entry.getKey(),entry.getValue());
        }
    }



    public Array<Vector2> getPath(Vector2 start, Vector2 dest){
            return gdxPathFinder.calculatePath(start, dest);
    }

    Entity createEntityInternal(int unitId, long unitID, Vector2 worldCoordinates, int teamID){
        Entity entity = spawner.createNewUnit(unitId,teamID,worldCoordinates);
        engine.addEntity(entity);
        units.put(unitID, entity);
        if(unitId==6){
            bases[teamID] = entity;
        }
        else if (unitId==5||unitId==2){
            SoundManager.getInstance().playSound("drawSword");
        }
        else if (unitId==1||unitId==4){
            SoundManager.getInstance().playSound("drawKatana");
        }
        return entity;
    }

    @Override
    public void upgradeEntityInternal(Entity entity, int ID){
        engine.addEntity(entity);
        //units.put(lastID++, entity);
    }


    public void createBulletWorldCoordinates(Vector2 worldCoordinates, long targetId, float damage, int type) {

        Entity projectile = new Entity();
        UnitConfig unitConfig = AssetManager.getInstance().unitManager.unitConfigHashMap.get(type);

        Projectiles.setComponents(projectile, unitConfig, worldCoordinates, targetId, damage);

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
    public SpawnArea createSpawnAreaForPlayer(int playerID, int players){
        Boundary result = board.createSpawnAreaForPlayer(playerID, players);
        SpawnArea spawnArea = new SpawnArea(result.getLowerLeft().x, result.getLowerLeft().y,
                result.getUpperRight().x-result.getUpperLeft().x,
                result.getUpperRight().y - result.getLowerRight().y);
        return spawnArea;
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

    public int getPlayers(){
        return players;
    }

    public Entity getUnit(long unitID){
        return units.get(unitID);
    }
    public Camera getCamera() {
        return camera;
    }

    @Override
    public void updateMarker(int t){
        for (Entity base :
                bases) {
            if(base!=null&&base.getComponent(TeamGameObjectComponent.class).getTeamId()==t){
                marker.getComponent(TransformComponent.class).setPosition(base.getComponent(TransformComponent.class).getPosition());
            }
        }
    }
}
