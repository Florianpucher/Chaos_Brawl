package com.strategy_bit.chaos_brawl.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;
import com.strategy_bit.chaos_brawl.ashley.entity.Player;
import com.strategy_bit.chaos_brawl.ashley.entity.PlayerClone;
import com.strategy_bit.chaos_brawl.network.BrawlMultiplayer;
import com.strategy_bit.chaos_brawl.network.Server.BrawlServerImpl;

import java.util.HashMap;

public class MultiplayerWorld extends World {

    private BrawlMultiplayer brawlMultiplayer;

    public MultiplayerWorld(BrawlMultiplayer brawlMultiplayer) {
        this.brawlMultiplayer = brawlMultiplayer;
        if (brawlMultiplayer instanceof BrawlServerImpl) {
            //spawn own starting units
            createPlayer();
            for (Connection connection : ((BrawlServerImpl) brawlMultiplayer).getNetworkMembers()) {
                //spawn starting units for other players
                createEntity(new PlayerClone(new Vector2(15, 7.5f)));
            }
        }
    }

    @Override
    public void createPlayer() {
        Player player = new Player();
        createEntity(player);
    }

    @Override
    public void createDummy() {
        PlayerClone dummy = new PlayerClone(new Vector2((float) (Math.random()*10),(float) (Math.random()*10)));
        createEntity(dummy);
    }

    public void createEntity(Entity entity) {
        super.createEntity(entity);
        //Check if brawlMultiplayer has been set
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
        brawlMultiplayer.moveEntity(screenCoordinates, entityID);
    }

    //creates entity without notifying other Players
    public void createEntityLocal(Entity entity) {
        super.createEntity(entity);
    }

    //sets target location without notifying other Players
    public void sendTouchInputLocal(Vector2 screenCoordinates, long entityID) {
        super.sendTouchInput(screenCoordinates, entityID);
    }
}
