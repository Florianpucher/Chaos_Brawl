package com.strategy_bit.chaos_brawl.world;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.ashley.components.BaseComponent;
import com.strategy_bit.chaos_brawl.ashley.components.BoundaryComponent;
import com.strategy_bit.chaos_brawl.ashley.components.MovementComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TeamGameObjectComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
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
import com.strategy_bit.chaos_brawl.managers.SoundManager;
import com.strategy_bit.chaos_brawl.managers.UnitManager;
import com.strategy_bit.chaos_brawl.pathfinder.OtherPathfinder;
import com.strategy_bit.chaos_brawl.player_input_output.PawnController;
import com.strategy_bit.chaos_brawl.player_input_output.PlayerController;
import com.strategy_bit.chaos_brawl.types.EventType;
import com.strategy_bit.chaos_brawl.util.Boundary;
import com.strategy_bit.chaos_brawl.util.SpawnArea;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.strategy_bit.chaos_brawl.config.WorldSettings.FRUSTUM_HEIGHT;
import static com.strategy_bit.chaos_brawl.config.WorldSettings.FRUSTUM_WIDTH;

/**
 * Central class for game
 *
 * @author AIsopp
 * @version 1.0
 * @since 15.03.2018
 */
public class World implements InputHandler {

    protected long lastID = 0;

    protected HashMap<Long, Entity> units;


    public int playerTeamId=0;
    protected SpawnerImpl spawner;
    protected MyEngine engine;
    protected OrthographicCamera camera;
    public BoardInterface board;
    protected PawnController[] playerControllers;
    protected Entity[] bases;
    protected Entity[] tower;
    protected OtherPathfinder gdxPathFinder;
    protected DeleteSystem deleteSystem;
    protected UpgradeSystem upgradeSystem;
    protected RenderSystem renderSystem;

    protected Entity marker;

    boolean endGame = false;

    /**
     *
     * @param map with which map we are playing
     * @param players how much players are playing
     * @param containsDeleteSystem should the ashley engine delete units during update
     */
    public World(int map, int players, boolean containsDeleteSystem) {
        this(map,players,containsDeleteSystem,true);
    }

    /**
     * constructor for test to disable Rendersystem
     * @param map with which map we are playing
     * @param players how much players are playing
     * @param containsDeleteSystem should the ashley engine delete units during update
     * @param withRenderSystem should the world be rendered
     */
    public World(int map, int players, boolean containsDeleteSystem, boolean withRenderSystem)
    {
        camera = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        camera.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
        units = new HashMap<>();
        spawner = new SpawnerImpl();
        playerControllers = new PawnController[players];
        bases = new Entity[players];
        tower = new Entity[players];

        createEngine(containsDeleteSystem, withRenderSystem);
        marker=new CurrentTargetMarker(new Vector2(0,0), engine);
        engine.addEntity(marker);
        createWorld(map);
    }

    /**
     * default constructor
     */
    public World() {
        this(1,2,true);
    }

    /**
     * set player for an index
     * @param index internal index of the player
     * @param pawnController who is representing this player
     */
    public void setPlayerController(int index, PawnController pawnController){
        playerControllers[index] = pawnController;
        if (pawnController instanceof PlayerController){
            playerTeamId=pawnController.getTeamID();
            if (renderSystem!=null){
                renderSystem.setPlayerTeamId(pawnController.getTeamID());
            }

        }
    }

    /**
     * initialize buildings for players
     */
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


    /**
     * creates the engine for ashley and adds every system to it
     * @param containsDeleteSystem if false units will not be deleted by the engine.update method if they have no life points left
     * @param withRenderSystem if false
     */
    protected void createEngine(boolean containsDeleteSystem, boolean withRenderSystem){
        engine = new MyEngine();

        renderSystem = null;
        if(withRenderSystem)
        {
            renderSystem = new RenderSystem(camera);
        }
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
        if(withRenderSystem)
        {
            engine.addSystem(renderSystem);

            engine.addSystem(new ExplosionSystem(camera));
        }
    }


    /**
     * creates Game board ({@link Board}) and initializes pathfinder
     */
    private void createWorld(int map){
        board = new Board(engine, map);
        gdxPathFinder = new OtherPathfinder(board);
    }


    /**
     * call this method from a libgdx class method which name is also render or update
     */
    public void render(){
        updateResources(Gdx.graphics.getDeltaTime());
        engine.update(Gdx.graphics.getDeltaTime());
        endGame = checkWinningLosing();
    }


    /**
     * returns true if one player left, false otherwise and does inform players if they lose or win
     * @return true if one player left, false otherwise
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
            if (bases[i].getComponent(BaseComponent.class).isDestroyed()) {
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


    /**
     * releases every resource that has been allocated with this world
     */
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
    public void createEntityWorldCoordinates(Vector2 worldCoordinates, int unitId, int teamID) {
        Entity entity = createEntityInternal(unitId, lastID, worldCoordinates, teamID);
        lastID++;

        MovementComponent movementComponent = entity.getComponent(MovementComponent.class);
        //Move entity to enemy player
        if(movementComponent != null){
            Array<Vector2> path = gdxPathFinder.calculatePath(entity.getComponent(TransformComponent.class).getPosition(), bases[playerControllers[teamID].getCurrentTargetTeam()].getComponent(TransformComponent.class).getPosition());
            movementComponent.setPath(path);

        }
    }


    @Override
    public void updateTowersOrUnits(int teamID, int updateType) {
        HashMap<Long, Entity> map = new HashMap<>();
        Iterator<Map.Entry<Long, Entity>> iterator = units.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<Long,Entity> entry = iterator.next();
            Entity unit = entry.getValue();
            if(unit.getComponent(TeamGameObjectComponent.class).getUnitType() != updateType || unit.getComponent(TeamGameObjectComponent.class).getTeamId() != teamID)
            {
                continue;
            }
            Entity newEntity = upgradeSystem.upgradeToNextTier(unit, iterator);
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

    Entity createEntityInternal(int unitIdType, long unitID, Vector2 worldCoordinates, int teamID){
        Entity entity = spawner.createNewUnit(unitIdType,teamID,worldCoordinates, engine);
        engine.addEntity(entity);
        units.put(unitID, entity);
        if(unitIdType==6){
            bases[teamID] = entity;
        }
        else if (unitIdType==5||unitIdType==2){
            SoundManager.getInstance().playSound("drawSword");
        }
        else if (unitIdType==1||unitIdType==4){
            SoundManager.getInstance().playSound("drawKatana");
        }
        return entity;
    }

    @Override
    public void upgradeEntityInternal(Entity entity, int id){
        engine.addEntity(entity);
    }


    public void createBulletWorldCoordinates(Vector2 worldCoordinates, long targetId, float damage, int type) {

        Entity projectile = new Entity();
        UnitConfig unitConfig = UnitManager.getInstance().getUnitConfig(type);

        Projectiles.setComponents(projectile, unitConfig, worldCoordinates, targetId, damage, engine);

        if (type == 10) {
            SoundManager.getInstance().playSound("attackBow");
        } else if (type == 11) {
            SoundManager.getInstance().playSound("attackFireball");
        } else if (type == 12) {
            SoundManager.getInstance().playSound("attackCannonBall");
        } else if (type == 13) {
            SoundManager.getInstance().playSound("attackLaser");
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
     * @return a spawn area for a player
     */
    public SpawnArea createSpawnAreaForPlayer(int playerID, int players){
        Boundary result = board.createSpawnAreaForPlayer(playerID, players);
        SpawnArea spawnArea = new SpawnArea(result.getLowerLeft().x, result.getLowerLeft().y,
                result.getUpperRight().x-result.getUpperLeft().x,
                result.getUpperRight().y - result.getLowerRight().y);
        return spawnArea;
    }

    public void cheatFunctionDisposer(){
        if (playerControllers[0].isCheatFunctionActive() && playerControllers[1].isCheatFunctionActive()){
                playerControllers[0].setCheatFunctionActive(false);
        }
        if (playerControllers[1].isCheatFunctionActive() && playerControllers[0].isCheatFunctionActive()){
                playerControllers[1].setCheatFunctionActive(false);
        }
    }

    public Vector2 getUnitPosition(long unitID){
        Entity entity=getUnit(unitID);
        if (entity!=null){
            return entity.getComponent(TransformComponent.class).getPosition();
        }
        else return null;
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
