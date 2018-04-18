package com.strategy_bit.chaos_brawl.world;

import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.engine.MyEngine;
import com.strategy_bit.chaos_brawl.ashley.systems.RenderSystem;
import com.strategy_bit.chaos_brawl.config.WorldSettings;
import com.strategy_bit.chaos_brawl.util.MatrixNx2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author AIsopp
 * @version 1.0
 * @since 18.04.2018
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({World.class})
public class WorldTest {

    private World world;

    @Before
    public void initialize() throws Exception {

        RenderSystem renderSystem = Mockito.mock(RenderSystem.class);
        BoardA boardA = Mockito.mock(BoardA.class);
        Mockito.when(boardA.getWorldCoordinateOfTile(5,0)).thenReturn(new Vector2(5, 0));
        Mockito.when(boardA.getWorldCoordinateOfTile(WorldSettings.BOARD_WIDTH - 5, 0)).thenReturn(new Vector2(7.5f, 0));


        PowerMockito.whenNew(RenderSystem.class).withNoArguments().thenReturn(renderSystem);
        PowerMockito.whenNew(BoardA.class).withAnyArguments().thenReturn(boardA);
        world = new World(1);
    }

    @Test
    public void testCreateSpawnArea1(){
        MatrixNx2 spawnArea = world.createSpawnAreaForPlayer(1);
        Assert.assertArrayEquals(new Vector2[]{
                new Vector2(0.0f, WorldSettings.FRUSTUM_HEIGHT),
                new Vector2(5, WorldSettings.FRUSTUM_HEIGHT),
                new Vector2(0.0f, 0.0f),
                new Vector2(5, 0.0f)
        }, spawnArea.getMatrixArray());
    }


    @Test
    public void testCreateSpawnArea2(){
        MatrixNx2 spawnArea = world.createSpawnAreaForPlayer(2);
        Assert.assertArrayEquals(new Vector2[]{
                new Vector2(7.5f, WorldSettings.FRUSTUM_HEIGHT),
                new Vector2(WorldSettings.FRUSTUM_WIDTH, WorldSettings.FRUSTUM_HEIGHT),
                new Vector2(7.5f, 0.0f),
                new Vector2(WorldSettings.FRUSTUM_WIDTH, 0.0f)
        }, spawnArea.getMatrixArray());
    }

    @Test
    public void testCreateSpawnArea3(){
        try{
            MatrixNx2 spawnArea = world.createSpawnAreaForPlayer(3);
            Assert.fail();
        }catch (UnsupportedOperationException e){

        }

    }

}
