package com.strategy_bit.chaos_brawl.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.BaseTest;
import com.strategy_bit.chaos_brawl.ashley.components.ParticleComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TeamGameObjectComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.ashley.systems.BulletSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.CombatSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.DeleteSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.ExplosionSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.MovementSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.RenderSystem;
import com.strategy_bit.chaos_brawl.config.WorldSettings;
import com.strategy_bit.chaos_brawl.pathfinder.OtherPathfinder;
import com.strategy_bit.chaos_brawl.player_input_output.PawnController;
import com.strategy_bit.chaos_brawl.player_input_output.PlayerController;
import com.strategy_bit.chaos_brawl.util.Boundary;
import com.strategy_bit.chaos_brawl.util.VectorMath;

import org.junit.After;
import org.junit.Assert;
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
@PrepareForTest({World.class, ParticleComponent.class})
public class WorldTest extends BaseTest {

    private World world;
    private Camera camera;

    private PlayerController player1;
    private PawnController player2;
    private PawnController player3;
    private PawnController player4;

    private RenderSystem renderSystem;

    private static final int UNITS_AFTER_INITIALIZATION = 4 * 3;

    @Before
    public void initialize() throws Exception {


        renderSystem = Mockito.mock(RenderSystem.class);
        ExplosionSystem explosionSystem = Mockito.mock(ExplosionSystem.class);
        OtherPathfinder pathfinder = Mockito.mock(OtherPathfinder.class);
        camera = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        camera.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
        camera.update();
        Mockito.when(renderSystem.getCamera()).thenReturn(camera);
        Board boardA = Mockito.mock(Board.class);
        Mockito.when(boardA.getWorldCoordinateOfTile(5,0)).thenReturn(new Vector2(5, 0));
        Mockito.when(boardA.getWorldCoordinateOfTile(WorldSettings.BOARD_WIDTH - 5, 0)).thenReturn(new Vector2(15f, 0));
        Mockito.when(boardA.boardToMatrix()).thenReturn(new int[][]{
                {0,0,0},
                {1,1,1},
                {0,1,1}});
        Array<Float> positions = new Array<>();
        for (int i = 0; i < UNITS_AFTER_INITIALIZATION * 2; i++) {
            positions.add((float) i);
        }
        Mockito.when(boardA.getConfig(Mockito.anyInt())).thenReturn(positions);
        Array<Vector2> defaultPath = new Array<>();
        defaultPath.add(new Vector2());
        defaultPath.add(new Vector2(1,1));
        Mockito.when(pathfinder.calculatePath(Mockito.any(Vector2.class), Mockito.any(Vector2.class))).thenReturn(defaultPath);
        PowerMockito.whenNew(ExplosionSystem.class).withAnyArguments().thenReturn(explosionSystem);
        PowerMockito.whenNew(RenderSystem.class).withNoArguments().thenReturn(renderSystem);
        PowerMockito.whenNew(Board.class).withAnyArguments().thenReturn(boardA);
        PowerMockito.whenNew(OtherPathfinder.class).withArguments(boardA).thenReturn(pathfinder);
        PowerMockito.whenNew(ParticleEffect.class).withNoArguments().thenReturn(Mockito.mock(ParticleEffect.class));
        world = new World(1,4);
        Boundary boundary = new Boundary(new Vector2(), new Vector2(), new Vector2(), new Vector2());
        player1 = new PlayerController(0,world,boundary);
        player2 = new PlayerController(1,world,boundary);
        player3 = new PlayerController(2,world,boundary);
        player4 = new PlayerController(3,world,boundary);

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
        world.createEntityScreenCoordinates(new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2), 2,0);

        Entity insertedUnit = world.units.get((long)world.units.size()-1);
        assertEquals(0, insertedUnit.getComponent(TeamGameObjectComponent.class).getTeamId());

        assertEquals(true,new Vector2(WorldSettings.FRUSTUM_WIDTH/2, WorldSettings.FRUSTUM_HEIGHT/2).epsilonEquals(insertedUnit.getComponent(TransformComponent.class).getPosition(), 0.5f) );
    }

    @Test
    public void createEntityWorldCoordinates() {
        world.createEntityWorldCoordinates(new Vector2(WorldSettings.FRUSTUM_WIDTH/2, WorldSettings.FRUSTUM_HEIGHT/2), 2,0);
        Entity insertedUnit = world.units.get((long)world.units.size()-1);
        assertEquals(0, insertedUnit.getComponent(TeamGameObjectComponent.class).getTeamId());

        assertEquals(true,new Vector2(WorldSettings.FRUSTUM_WIDTH/2, WorldSettings.FRUSTUM_HEIGHT/2).epsilonEquals(insertedUnit.getComponent(TransformComponent.class).getPosition(), 0.5f) );

    }

    @Test
    public void getCamera() {
        assertEquals(camera, world.getCamera());
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
        TransformComponent transformComponent1 = world.bases[0].getComponent(TransformComponent.class);
        Vector3 worldPosition1 = new Vector3(transformComponent1.getPosition().x, WorldSettings.FRUSTUM_HEIGHT - transformComponent1.getPosition().y,0);

        Vector3 screenPosition = camera.project(worldPosition1);
        TransformComponent transformComponent2 = world.bases[1].getComponent(TransformComponent.class);
        Vector3 worldPosition2 = new Vector3(transformComponent2.getPosition().x, WorldSettings.FRUSTUM_HEIGHT - transformComponent2.getPosition().y,0);

        Vector3 screenPosition2 = camera.project(worldPosition2);
        world.sendTouchInput(VectorMath.vector3ToVector2(screenPosition), player3);
        world.sendTouchInput(VectorMath.vector3ToVector2(screenPosition2), player4);
        Assert.assertEquals(player1.getTeamID(), player3.getCurrentTargetTeam());
        Assert.assertEquals(player2.getTeamID(), player4.getCurrentTargetTeam());
    }
}
