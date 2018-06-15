package com.strategy_bit.chaos_brawl.cheat_function;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.strategy_bit.chaos_brawl.BaseTest;
import com.strategy_bit.chaos_brawl.player_input_output.PlayerController;
import com.strategy_bit.chaos_brawl.util.SpawnArea;
import com.strategy_bit.chaos_brawl.world.InputHandler;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by Florian on 15.06.2018.
 */

public class SensorReaderTest extends BaseTest{

    private SensorReader sensorReader;
    private PlayerController playerController;

    @Before
    public void initialize(){
        InputHandler handler = Mockito.mock(InputHandler.class);
        SpawnArea spawnArea = Mockito.mock(SpawnArea.class);

        playerController = new PlayerController(0, handler, spawnArea, null);
        sensorReader = new SensorReader(playerController);

    }

    @Test
    public void testShakeCheatFunctionActive()
    {
        Gdx.input = Mockito.mock(Input.class);

        for (int i = 0; i < 50; i++){
            Mockito.when(Gdx.input.getAccelerometerY()).thenReturn(-2f);
            sensorReader.update(0.1f);
            Mockito.when(Gdx.input.getAccelerometerY()).thenReturn(2f);
            sensorReader.update(0.1f);
        }

        Assert.assertEquals(true, playerController.isCheatFunctionActive());
    }

    @Test
    public void testShakeCheatFunctionNotActive(){
        Gdx.input = Mockito.mock(Input.class);

        for (int i = 0; i < 100; i++){
            Mockito.when(Gdx.input.getAccelerometerY()).thenReturn(-2f);
            sensorReader.update(0.1f);
            Mockito.when(Gdx.input.getAccelerometerY()).thenReturn(2f);
            sensorReader.update(0.1f);
        }

        for (int i = 0; i <= 10; i++){
            sensorReader.update(1f);
        }

        Assert.assertEquals(false, playerController.isCheatFunctionActive());
    }

    @Test
    public void testTimeTooHigh(){
        Gdx.input = Mockito.mock(Input.class);


        for (int i = 0; i < 50; i++){
            Mockito.when(Gdx.input.getAccelerometerY()).thenReturn(-2f);
            sensorReader.update(500f);
            Mockito.when(Gdx.input.getAccelerometerY()).thenReturn(2f);
            sensorReader.update(0.1f);
        }

        Assert.assertEquals(false, playerController.isCheatFunctionActive());
    }
}
