package com.strategy_bit.chaos_brawl.ashley.components;

import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.engine.MyEngine;
import com.strategy_bit.chaos_brawl.util.Boundary;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author AIsopp
 * @version 1.0
 * @since 13.05.2018
 */
@RunWith(Parameterized.class)
public class BoundaryComponentTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { new Vector2(1,1), new Vector2(0,0), new Vector2(0.5f,0.5f),true },
                { new Vector2(1,1), new Vector2(0,0), new Vector2(0.7f,0.5f),false },
                { new Vector2(1,1), new Vector2(0,0), new Vector2(0.2f,-0.3f),true }
        });
    }

    private BoundaryComponent boundaryComponent;

    private Vector2 clickPosition;
    private boolean result;

    public BoundaryComponentTest(Vector2 size, Vector2 position,Vector2 clickPosition,boolean result) {
        this.result = result;
        this.clickPosition = clickPosition;
        TransformComponent transformComponent = new TransformComponent();
        transformComponent.setPosition(position);
        boundaryComponent = new BoundaryComponent(size,transformComponent);
    }


    @Test
    public void testInsideBoundary(){
        Assert.assertEquals(result,boundaryComponent.isWithinBorders(clickPosition));
    }


}
