package com.strategy_bit.chaos_brawl.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.strategy_bit.chaos_brawl.ashley.components.TeamGameObjectComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.ashley.entity.Player;
import com.strategy_bit.chaos_brawl.ashley.entity.PlayerClone;
import com.strategy_bit.chaos_brawl.ashley.entity.Tower;
import com.strategy_bit.chaos_brawl.ashley.entity.Base;
import com.strategy_bit.chaos_brawl.network.BrawlMultiplayer;
import com.strategy_bit.chaos_brawl.network.Server.BrawlServer;
import com.strategy_bit.chaos_brawl.network.Server.BrawlServerImpl;
import com.strategy_bit.chaos_brawl.network.messages.Request.EntitySpawnMessage;

import java.util.HashMap;

public class MultiplayerWorld extends World {

    private BrawlMultiplayer brawlMultiplayer;

    public MultiplayerWorld(BrawlMultiplayer brawlMultiplayer) {
        this.brawlMultiplayer = brawlMultiplayer;
        if (brawlMultiplayer instanceof BrawlServerImpl) {
            //spawn own starting units
            //createPlayer();
            /*for (Connection connection : ((BrawlServerImpl) brawlMultiplayer).getNetworkMembers()) {
                //spawn starting units for other players
                //createEntity(new PlayerClone(new Vector2(15, 7.5f)));
            }*/
        }
    }

    @Override
    public void createPlayer() {
        Player player = new Player();
        createEntity(player);
    }
    public void createTower(){
        Tower topTower = new Tower(new Vector2(17,12));
        createEntity(topTower);
        Tower botTower = new Tower(new Vector2(17, 5));
        createEntity(botTower);
    }
    public void createBase() {
        Base base = new Base(new Vector2(19, 9));
        createEntity(base);
    }

    @Override
    public void createDummy() {
        PlayerClone dummy = new PlayerClone(new Vector2((float) (Math.random()*10),(float) (Math.random()*10)));
        createEntity(dummy);
    }

    public void createEntity(Entity entity) {
        super.createEntity(entity);
        //Check if brawlMultiplayer has been set
        if(brawlMultiplayer instanceof BrawlServer){
            brawlMultiplayer.spawnEntity(entity);
        }
    }

    public void createEntity(Vector2 position, int teamId, int entityTypeId) {
        createEntity(toEntity(position,teamId,entityTypeId));
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

        Vector3 withZCoordinate = new Vector3(screenCoordinates, 0);
        Vector3 translated = camera.unproject(withZCoordinate);
        Vector2 targetLocation = new Vector2(translated.x,translated.y);
        super.moveEntity(targetLocation, entityID);
        brawlMultiplayer.moveEntity(targetLocation, entityID);
    }

    //creates entity without notifying other Players
    public void createEntityLocal(Entity entity) {
        super.createEntity(entity);
    }
    public void createEntityLocal(Vector2 position, int teamId, int entityTypeId) {
        createEntityLocal(toEntity(position, teamId, entityTypeId));
    }

    //sets target location without notifying other Players
    public void sendTouchInputLocal(Vector2 worldCoordinates, long entityID) {
        super.moveEntity(worldCoordinates, entityID);
    }

    private Entity toEntity(Vector2 position, int teamId, int entityTypeId){
        Entity entity;
        //TODO add other cases
        switch (entityTypeId){
            case 1:
                entity=new Player(position,teamId);
                break;
            case 2:
                entity=new PlayerClone(position,teamId);
                break;
            default:
                entity=new Player(position,teamId);
                break;
        }
        return entity;
    }

    public EntitySpawnMessage createEntitySpawnMsg(Entity entity){
        TransformComponent transformComponent=entity.getComponent(TransformComponent.class);
        TeamGameObjectComponent teamGameObjectComponent = entity.getComponent(TeamGameObjectComponent.class);
        //CombatComponent combatComponent=entity.getComponent(CombatComponent.class);
        int entityTypeId=0;
        //TODO add other types
        if (entity instanceof Player){
            entityTypeId=1;
        }else if(entity instanceof  PlayerClone){
            entityTypeId=2;
        }
        return new EntitySpawnMessage(transformComponent.getPosition(),teamGameObjectComponent.getTeamId(),entityTypeId);
    }
}
