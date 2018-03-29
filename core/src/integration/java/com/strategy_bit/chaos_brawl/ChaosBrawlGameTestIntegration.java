package com.strategy_bit.chaos_brawl;

import com.strategy_bit.chaos_brawl.screens.AbstractScreen;

import org.junit.Before;
import org.junit.Test;


/**
 * @author AIsopp
 * @version 1.0
 * @since 28.03.2018
 */
public class ChaosBrawlGameTestIntegration {

    private ChaosBrawlGame chaosBrawlGame;
    //Todo run integration test with GdxTestRunner

    @Before
    public void initialize() {
        chaosBrawlGame = new ChaosBrawlGame();

    }

    @Test
    public void testCreate() {
        //chaosBrawlGame.create();
        //AbstractScreen screen = chaosBrawlGame.getScreen();
    }


    @Test(timeout = 5000)
    public void testRender(){
        //chaosBrawlGame.create();
    }

    @Test(timeout = 5000)
    public void testDispose(){
        //chaosBrawlGame.create();
    }


}
