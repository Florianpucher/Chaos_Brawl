package com.strategy_bit.chaos_brawl.world;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.BaseTest;
import com.strategy_bit.chaos_brawl.ashley.systems.RenderSystem;
import com.strategy_bit.chaos_brawl.config.WorldSettings;
import com.strategy_bit.chaos_brawl.util.Boundary;

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

/**
 * @author AIsopp
 * @version 1.0
 * @since 18.04.2018
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({World.class})
public class WorldTest extends BaseTest {

    private World world;


    @Before
    public void initialize() throws Exception {
        RenderSystem renderSystem = Mockito.mock(RenderSystem.class);
        Camera camera = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        camera.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
        Mockito.when(renderSystem.getCamera()).thenReturn(camera);
        BoardA boardA = Mockito.mock(BoardA.class);
        Mockito.when(boardA.getWorldCoordinateOfTile(5,0)).thenReturn(new Vector2(5, 0));
        Mockito.when(boardA.getWorldCoordinateOfTile(WorldSettings.BOARD_WIDTH - 5, 0)).thenReturn(new Vector2(15f, 0));


        PowerMockito.whenNew(RenderSystem.class).withNoArguments().thenReturn(renderSystem);
        PowerMockito.whenNew(BoardA.class).withAnyArguments().thenReturn(boardA);
        world = new World(1);


    }

    @Test
    public void testCreateSpawnArea1(){
        Boundary spawnArea = world.createSpawnAreaForPlayer(1);
        Assert.assertArrayEquals(new Vector2[]{
                new Vector2(0.0f, 480),
                new Vector2(210, 480),
                new Vector2(0, 0),
                new Vector2(210, 0.0f)
        }, spawnArea.getMatrixArray());
    }


    @Test
    public void testCreateSpawnArea2(){
        Boundary spawnArea = world.createSpawnAreaForPlayer(2);
        Assert.assertArrayEquals(new Vector2[]{
                new Vector2(630, 480),
                new Vector2(840, 480),
                new Vector2(630, 0.0f),
                new Vector2(840, 0.0f)
        }, spawnArea.getMatrixArray());
    }

    @Test
    public void testCreateSpawnArea3(){
        try{
            Boundary spawnArea = world.createSpawnAreaForPlayer(3);
            Assert.fail();
        }catch (UnsupportedOperationException e){

        }

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createEntity() {
    }

    @Test
    public void createEngine() {
    }

    @Test
    public void render() {
    }

    @Test
    public void dispose() {
    }

    @Test
    public void sendTouchInput() {
    }

    @Test
    public void createEntityScreenCoordinates() {
    }

    @Test
    public void createEntityWorldCoordinates() {
    }

    @Test
    public void moveEntity() {
    }

    @Test
    public void createSpawnAreaForPlayer() {
    }

    @Test
    public void getCamera() {
    }
}
