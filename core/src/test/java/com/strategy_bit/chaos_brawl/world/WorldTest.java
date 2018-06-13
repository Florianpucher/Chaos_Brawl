package com.strategy_bit.chaos_brawl.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.strategy_bit.chaos_brawl.BaseTest;
import com.strategy_bit.chaos_brawl.ashley.components.TeamGameObjectComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.ashley.systems.BulletSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.CombatSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.DeleteSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.MovementSystem;
import com.strategy_bit.chaos_brawl.config.WorldSettings;
import com.strategy_bit.chaos_brawl.player_input_output.PawnController;
import com.strategy_bit.chaos_brawl.player_input_output.PlayerController;
import com.strategy_bit.chaos_brawl.util.SpawnArea;
import com.strategy_bit.chaos_brawl.util.VectorMath;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author AIsopp
 * @version 1.0
 * @since 18.04.2018
 */

public class WorldTest extends BaseTest {

    private World world;

    private PlayerController player1;
    private PawnController player2;
    private PawnController player3;
    private PawnController player4;


    private static final int UNITS_AFTER_INITIALIZATION = 4 * 3;

    @Before
    public void initialize() throws Exception {

        world = new World(1,4, true,false);
        SpawnArea boundary = new SpawnArea(0,0,0,0);
        player1 = new PlayerController(0,world,boundary, world.getCamera());
        player2 = new PlayerController(1,world,boundary, world.getCamera());
        player3 = new PlayerController(2,world,boundary, world.getCamera());
        player4 = new PlayerController(3,world,boundary, world.getCamera());

        world.setPlayerController(0, player1);
        world.setPlayerController(1,player2);
        world.setPlayerController(2, player3);
        world.setPlayerController(3,player4);
        world.initializeGameForPlayers();
    }

    @After
    public void after(){
        player1.dispose();
        world.dispose();

    }

    // Does not work
    /*@Test
    public void testCreateSpawnArea1(){
        Boundary spawnArea = world.createSpawnAreaForPlayer(1);
        Assert.assertArrayEquals(new Vector2[]{
                new Vector2(0.0f, 480),
                new Vector2(210, 480),
                new Vector2(0, 0),
                new Vector2(210, 0.0f)
        }, spawnArea.getMatrixArray());
    }*/


    @Test
    public void testCreateSpawnArea2(){
        /*Boundary spawnArea = world.createSpawnAreaForPlayer(2);
        Assert.assertArrayEquals(new Vector2[]{
                new Vector2(630, 480),
                new Vector2(840, 480),
                new Vector2(630, 0.0f),
                new Vector2(840, 0.0f)
        }, spawnArea.getMatrixArray());*/
    }

    @Test
    public void testCreateSpawnArea3(){
        /*try{
            Boundary spawnArea = world.createSpawnAreaForPlayer(3);
            Assert.fail();
        }catch (UnsupportedOperationException e){

        }
        */
    }

    @Test
    public void createEntityInternal() {
        world.createEntityInternal(2,100,new Vector2(WorldSettings.FRUSTUM_WIDTH/2, WorldSettings.FRUSTUM_HEIGHT/2), 0);

        Entity insertedUnit = world.units.get(100L);
        assertEquals(0, insertedUnit.getComponent(TeamGameObjectComponent.class).getTeamId());

        assertEquals(true,new Vector2(WorldSettings.FRUSTUM_WIDTH/2, WorldSettings.FRUSTUM_HEIGHT/2).epsilonEquals(insertedUnit.getComponent(TransformComponent.class).getPosition(), 0.5f) );

    }

    @Test
    public void createEngine() {
        ImmutableArray<EntitySystem> systems = world.engine.getSystems();
        boolean containsAnySystem = true;
        Class[] classes = new Class[]{DeleteSystem.class,MovementSystem.class, CombatSystem.class, BulletSystem.class};
        for (int i = 0; i < classes.length; i++) {
            containsAnySystem = false;
            for (EntitySystem system :
                    systems) {
                if(classes[i].isInstance(system)){
                    containsAnySystem = true;
                    break;
                }
            }
            if(!containsAnySystem){
                break;
            }
        }
        assertEquals(true, containsAnySystem);
    }

    @Test
    public void render() {
        //Check if there occur no null pointer exception
        world.render();
    }

    @Test
    public void dispose() {
        world.dispose();
    }

    @Test
    public void createEntityWorldCoordinates() {
        world.createEntityWorldCoordinates(world.bases[0].getComponent(TransformComponent.class).getPosition(), 2,0);
        Entity insertedUnit = world.units.get((long)world.units.size()-1);
        assertEquals(0, insertedUnit.getComponent(TeamGameObjectComponent.class).getTeamId());

        assertEquals(true,world.bases[0].getComponent(TransformComponent.class).getPosition().epsilonEquals(insertedUnit.getComponent(TransformComponent.class).getPosition(), 0.5f) );

    }


    // Test for multiple players

    @Test
    public void testWinningLosing(){
        //TODO Engin implement here test for winning losing with four players
        // Get first bases of players by looping through units hashmap

        //Then set the health points of one to 0 and use world.checkWinningLosing
        // after that check if the gameOver method of the pawnController to which this base belonged to had been used
    }

    @Test
    public void testChangePlayerTarget(){
        world.render();
        TransformComponent transformComponent1 = world.bases[0].getComponent(TransformComponent.class);
        Vector3 worldPosition1 = new Vector3(transformComponent1.getPosition().x, -transformComponent1.getPosition().y,0);
        Vector3 screenPosition = world.getCamera().project(worldPosition1);
        TransformComponent transformComponent2 = world.bases[1].getComponent(TransformComponent.class);
        Vector3 worldPosition2 = new Vector3(transformComponent2.getPosition().x, -transformComponent2.getPosition().y,0);
        Vector3 screenPosition2 = world.getCamera().project(worldPosition2);
        world.sendTouchInput(VectorMath.vector3ToVector2(screenPosition), player3);
        world.sendTouchInput(VectorMath.vector3ToVector2(screenPosition2), player4);
        Assert.assertEquals(player1.getTeamID(), player3.getCurrentTargetTeam());
        Assert.assertEquals(player2.getTeamID(), player4.getCurrentTargetTeam());
    }
}
