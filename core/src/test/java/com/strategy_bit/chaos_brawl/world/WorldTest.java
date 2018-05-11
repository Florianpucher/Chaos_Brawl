package com.strategy_bit.chaos_brawl.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.BaseTest;
import com.strategy_bit.chaos_brawl.ashley.components.TeamGameObjectComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.ashley.entity.Knight;
import com.strategy_bit.chaos_brawl.ashley.systems.BulletSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.CombatSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.DeleteSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.MovementSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.RenderSystem;
import com.strategy_bit.chaos_brawl.config.WorldSettings;
import com.strategy_bit.chaos_brawl.pathfinder.OtherPathfinder;
import com.strategy_bit.chaos_brawl.player_input_output.PawnController;
import com.strategy_bit.chaos_brawl.player_input_output.PlayerController;
import com.strategy_bit.chaos_brawl.types.UnitType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static com.strategy_bit.chaos_brawl.config.WorldSettings.FRUSTUM_HEIGHT;
import static com.strategy_bit.chaos_brawl.config.WorldSettings.FRUSTUM_WIDTH;
import static org.junit.Assert.assertEquals;

/**
 * @author AIsopp
 * @version 1.0
 * @since 18.04.2018
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({World.class})
public class WorldTest extends BaseTest {

    private World world;
    private Camera camera;

    private PlayerController player1;
    private PawnController player2;
    private PawnController player3;
    private PawnController player4;

    private RenderSystem renderSystem;

    @Before
    public void initialize() throws Exception {


        renderSystem = Mockito.mock(RenderSystem.class);
        OtherPathfinder pathfinder = Mockito.mock(OtherPathfinder.class);
        camera = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        camera.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
        Mockito.when(renderSystem.getCamera()).thenReturn(camera);
        BoardA boardA = Mockito.mock(BoardA.class);
        Mockito.when(boardA.getWorldCoordinateOfTile(5,0)).thenReturn(new Vector2(5, 0));
        Mockito.when(boardA.getWorldCoordinateOfTile(WorldSettings.BOARD_WIDTH - 5, 0)).thenReturn(new Vector2(15f, 0));
        Mockito.when(boardA.boardToMatrix()).thenReturn(new int[][]{
                {0,0,0},
                {1,1,1},
                {0,1,1}});
        Array<Vector2> defaultPath = new Array<>();
        defaultPath.add(new Vector2());
        defaultPath.add(new Vector2(1,1));
        Mockito.when(pathfinder.calculatePath(Mockito.any(Vector2.class), Mockito.any(Vector2.class))).thenReturn(defaultPath);

        PowerMockito.whenNew(RenderSystem.class).withNoArguments().thenReturn(renderSystem);
        PowerMockito.whenNew(BoardA.class).withAnyArguments().thenReturn(boardA);
        PowerMockito.whenNew(OtherPathfinder.class).withArguments(boardA).thenReturn(pathfinder);
        world = new World(1,4);

        player1 = Mockito.mock(PlayerController.class);
        player2 = Mockito.mock(PawnController.class);
        player3 = Mockito.mock(PawnController.class);
        player4 = Mockito.mock(PawnController.class);

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
        world.createEntityInternal(UnitType.KNIGHT,100,new Vector2(WorldSettings.FRUSTUM_WIDTH/2, WorldSettings.FRUSTUM_HEIGHT/2), 0);

        Entity insertedUnit = world.units.get(100L);
        assertEquals(true, insertedUnit instanceof Knight);
        assertEquals(0, insertedUnit.getComponent(TeamGameObjectComponent.class).getTeamId());

        assertEquals(true,new Vector2(WorldSettings.FRUSTUM_WIDTH/2, WorldSettings.FRUSTUM_HEIGHT/2).epsilonEquals(insertedUnit.getComponent(TransformComponent.class).getPosition(), 0.5f) );

    }

    @Test
    public void createEngine() {
        ImmutableArray<EntitySystem> systems = world.engine.getSystems();
        boolean containsAnySystem = true;
        Class[] classes = new Class[]{DeleteSystem.class,RenderSystem.class,MovementSystem.class, CombatSystem.class, BulletSystem.class};
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
        Mockito.verify(renderSystem).dispose();
    }

    @Test
    public void createEntityScreenCoordinates() {
        camera.update();
        world.createEntityScreenCoordinates(new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2), UnitType.KNIGHT,0);

        Entity insertedUnit = world.units.get((long)world.units.size()-1);
        assertEquals(true, insertedUnit instanceof Knight);
        assertEquals(0, insertedUnit.getComponent(TeamGameObjectComponent.class).getTeamId());

        assertEquals(true,new Vector2(WorldSettings.FRUSTUM_WIDTH/2, WorldSettings.FRUSTUM_HEIGHT/2).epsilonEquals(insertedUnit.getComponent(TransformComponent.class).getPosition(), 0.5f) );
    }

    @Test
    public void createEntityWorldCoordinates() {
        world.createEntityWorldCoordinates(new Vector2(WorldSettings.FRUSTUM_WIDTH/2, WorldSettings.FRUSTUM_HEIGHT/2), UnitType.KNIGHT,0);
        Entity insertedUnit = world.units.get((long)world.units.size()-1);
        assertEquals(true, insertedUnit instanceof Knight);
        assertEquals(0, insertedUnit.getComponent(TeamGameObjectComponent.class).getTeamId());

        assertEquals(true,new Vector2(WorldSettings.FRUSTUM_WIDTH/2, WorldSettings.FRUSTUM_HEIGHT/2).epsilonEquals(insertedUnit.getComponent(TransformComponent.class).getPosition(), 0.5f) );

    }

    @Test
    public void getCamera() {
        assertEquals(camera, world.getCamera());
    }


    // Test for multiple players
    @Test
    public void testGetCurrentPlayerTarget(){

    }

    @Test
    public void testWinningLosing(){
        //TODO Engin implement here test for winning losing with four players
        // Get first bases of players by looping through units hashmap

        //Then set the health points of one to 0 and use world.checkWinningLosing
        // after that check if the gameOver method of the pawnController to which this base belonged to had been used
    }
}
