package com.strategy_bit.chaos_brawl.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;
import com.strategy_bit.chaos_brawl.SpawnerImpl;
import com.strategy_bit.chaos_brawl.ashley.engine.MyEngine;
import com.strategy_bit.chaos_brawl.ashley.entity.PlayerClone;
import com.strategy_bit.chaos_brawl.network.BrawlMultiplayer;
import com.strategy_bit.chaos_brawl.network.Server.BrawlServerImpl;

import java.util.HashMap;

public class MultiplayerWorld extends World {
    public long lastID = 0;

    private HashMap<Long, Entity> units;

    private SpawnerImpl spawner;
    private MyEngine engine;
    private Camera camera;
    private BrawlMultiplayer brawlMultiplayer;

    public MultiplayerWorld(BrawlMultiplayer brawlMultiplayer) {
        units = new HashMap<Long, Entity>();
        spawner = new SpawnerImpl();
        createEngine();
        createWorld();
        this.brawlMultiplayer=brawlMultiplayer;
        if(brawlMultiplayer instanceof BrawlServerImpl){
            //spawn own starting units
            createPlayer();
            for (Connection connection : ((BrawlServerImpl) brawlMultiplayer).getNetworkMembers()) {
                //spawn starting units for other players
                createEntity(new PlayerClone(new Vector2(15,7.5f)));
            }
        }
    }

    @Override
    public void createPlayer() {
        super.createPlayer();
    }

    @Override
    public void createDummy() {
        super.createDummy();
    }

    public  void createEntity(Entity entity){
        super.createEntity(entity);
        brawlMultiplayer.spawnEntity(entity);
    }


    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void sendTouchInput(Vector2 screenCoordinates, long entityID) {
        super.sendTouchInput(screenCoordinates, entityID);
        brawlMultiplayer.moveEntity(screenCoordinates,entityID);
    }

    //creates entity without notifying other Players
    public  void createEntityLocal(Entity entity){
        super.createEntity(entity);
    }

    //sets target location without notifying other Players
    public void sendTouchInputLocal(Vector2 screenCoordinates, long entityID) {
        super.sendTouchInput(screenCoordinates, entityID);
    }
}
