package com.strategy_bit.chaos_brawl.pathfinder;

import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.world.Board;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;



/**
 * @author AIsopp
 * @version 1.0
 * @since 24.04.2018
 */
@RunWith(PowerMockRunner.class)
public class PathfinderTest {


    private Board board;
    @Before
    public void initialize(){

        board = Mockito.mock(Board.class);
        Mockito.when(board.boardToMatrix()).thenReturn(new int[][]{
                {0,0,0,0},
                {1,1,1,1},
                {0,1,1,0},
                {0,1,1,1}});

    }


    @Test
    public void testInitializeBoard(){
        Pathfinder.setMoveable(board.boardToMatrix(), board);
        Array<Array<Node>> arrayArray = Pathfinder.board;
        System.out.println();
    }



}
