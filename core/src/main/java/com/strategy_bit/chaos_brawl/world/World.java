package com.strategy_bit.chaos_brawl.world;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.strategy_bit.chaos_brawl.ashley.components.MovementComponent;
import com.strategy_bit.chaos_brawl.ashley.engine.MyEngine;
import com.strategy_bit.chaos_brawl.ashley.entity.Player;
import com.strategy_bit.chaos_brawl.ashley.entity.PlayerClone;
import com.strategy_bit.chaos_brawl.ashley.entity.Tower;
import com.strategy_bit.chaos_brawl.ashley.entity.Base;
import com.strategy_bit.chaos_brawl.ashley.entity.TowerP;
import com.strategy_bit.chaos_brawl.ashley.systems.BulletSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.CombatSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.DeleteSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.MovementSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.RenderSystem;

import com.strategy_bit.chaos_brawl.types.UnitType;


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

    protected SpawnerImpl spawner;
    protected MyEngine engine;
    protected Camera camera;

    public World(int map) {
        units = new HashMap<Long, Entity>();
        spawner = new SpawnerImpl();
        createEngine();
        createWorld(map);
    }

    public World() {
        units = new HashMap<Long, Entity>();
        spawner = new SpawnerImpl();
        createEngine();
        createWorld(1);
    }

    public void createPlayer(){
        Player player = new Player();
        createEntity(player);
    }
    public void createTower(){
        Tower topTower = new Tower(new Vector2(17,12));
        createEntity(topTower);
        Tower botTower = new Tower(new Vector2(17, 5));
        createEntity(botTower);
        TowerP topTowerP = new TowerP(new Vector2(3,12));
        createEntity(topTowerP);
        TowerP botTowerP = new TowerP(new Vector2(3, 5));
        createEntity(botTowerP);
    }
    public void createBase() {
        Base base = new Base(new Vector2(19, 9));
        createEntity(base);
    }
    public void createDummy(){
        PlayerClone dummy = new PlayerClone(new Vector2((float) (Math.random()*10),(float) (Math.random()*10)));
        createEntity(dummy);
    }
    public void createEntity(Entity entity){
        engine.addEntity(entity);
        units.put(lastID, entity);
        lastID++;
    }

    protected void createEngine(){
        engine = new MyEngine(units);
        //Add some logic
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
     * creates Game board ({@link BoardA, BoardB, BoardC})
     */
    private void createWorld(int map){
        if(map == 1){
            BoardA board = new BoardA(engine);
        }
        if(map == 2){
            BoardB board = new BoardB(engine);
        }
        if(map == 3){
            BoardC board = new BoardC(engine);
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
    public void createEntity(Vector2 screenCoordinates, UnitType entityType, int teamID) {
        Vector3 withZCoordinate = new Vector3(screenCoordinates, 0);
        Vector3 translated = camera.unproject(withZCoordinate);
        Vector2 targetLocation = new Vector2(translated.x,translated.y);
        Entity entity = spawner.createNewUnit(entityType,teamID,targetLocation);
        createEntity(entity);
    }

    public void moveEntity(Vector2 worldCoordinates, long entityID){
        Entity entity = units.get(entityID);
        entity.getComponent(MovementComponent.class).setTargetLocation(worldCoordinates);
    }

    public Camera getCamera() {
        return camera;
    }
}
