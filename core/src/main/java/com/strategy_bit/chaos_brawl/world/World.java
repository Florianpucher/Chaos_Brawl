package com.strategy_bit.chaos_brawl.world;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.strategy_bit.chaos_brawl.InputHandler;
import com.strategy_bit.chaos_brawl.SpawnerImpl;
import com.strategy_bit.chaos_brawl.ashley.components.MovementComponent;
import com.strategy_bit.chaos_brawl.ashley.engine.MyEngine;
import com.strategy_bit.chaos_brawl.ashley.entity.Player;
import com.strategy_bit.chaos_brawl.ashley.entity.PlayerClone;
import com.strategy_bit.chaos_brawl.ashley.systems.BulletSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.CombatSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.MovementSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.RenderSystem;
import com.strategy_bit.chaos_brawl.world.Board;

import java.util.HashMap;

/**
 * Central manager for game
 *
 * @author AIsopp
 * @version 1.0
 * @since 15.03.2018
 */
public class World implements InputHandler {

    public long lastID = 0;

    protected HashMap<Long, Entity> units;

    private SpawnerImpl spawner;
    private MyEngine engine;
    private Camera camera;

    public World() {
        units = new HashMap<Long, Entity>();
        spawner = new SpawnerImpl();
        createEngine();
        createWorld();

    }

    public void createPlayer(){
        Player player = new Player();
        createEntity(player);
    }
    public void createDummy(){
        PlayerClone dummy = new PlayerClone(new Vector2((float) (Math.random()*10),(float) (Math.random()*10)));
        createEntity(dummy);
    }
    public  void createEntity(Entity entity){
        engine.addEntity(entity);
        units.put(lastID, entity);
        lastID++;
    }

    protected void createEngine(){
        engine = new MyEngine();
        //Add some logic

        engine.addSystem(new MovementSystem());
        engine.addSystem(new BulletSystem());
        engine.addSystem(new CombatSystem());
        //Renderer should be the last system to add
        RenderSystem renderSystem = new RenderSystem();
        engine.addSystem(renderSystem);
        camera = renderSystem.getCamera();
    }


    /**
     * creates Game board ({@link Board})
     */
    protected void createWorld(){
        Board board = new Board(engine);
    }

    public void render(){
        engine.update(Gdx.graphics.getDeltaTime());
    }



    public void dispose(){
        engine.dispose();
    }


    @Override
    public void sendTouchInput(Vector2 screenCoordinates, long entityID) {
        Entity entity = units.get(entityID);
        Vector3 withZCoordinate = new Vector3(screenCoordinates, 0);
        Vector3 translated = camera.unproject(withZCoordinate);
        Vector2 targetLocation = new Vector2(translated.x,translated.y);
        System.out.println("Hello");
        entity.getComponent(MovementComponent.class).setTargetLocation(targetLocation);
    }
}
