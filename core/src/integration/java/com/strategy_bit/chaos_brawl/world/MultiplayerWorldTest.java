package com.strategy_bit.chaos_brawl.world;

import com.strategy_bit.chaos_brawl.BaseTest;
import com.strategy_bit.chaos_brawl.network.BrawlMultiplayer;
import com.strategy_bit.chaos_brawl.network.server.BrawlServerImpl;
import com.strategy_bit.chaos_brawl.player_input_output.PawnController;

import org.junit.Before;

/**
 * @author AIsopp
 * @version 1.0
 * @since 10.05.2018
 */
public class MultiplayerWorldTest extends BaseTest{


    private MultiplayerWorld[] worlds;

    private PawnController[][] playersPerWorld;

    private BrawlMultiplayer[] brawlMultiplayer;

    @Before
    public void initialize(){
        // world 1 is the world of the server
        worlds = new MultiplayerWorld[4];

        BrawlServerImpl brawlServer = new BrawlServerImpl();

        brawlMultiplayer[0] = brawlServer;
    }

}
