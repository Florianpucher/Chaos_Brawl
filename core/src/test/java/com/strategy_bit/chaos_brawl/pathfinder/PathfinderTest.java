package com.strategy_bit.chaos_brawl.pathfinder;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.world.Board;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;



/**
 * @author AIsopp
 * @version 1.0
 * @since 24.04.2018
 */
public class PathfinderTest {


    private Board board;
    private OtherPathfinder otherPathfinder;
    private Vector2 start, end;
    @Before
    public void initialize(){

        board = Mockito.mock(Board.class);
        Mockito.when(board.boardToMatrix()).thenReturn(new int[][]{
                {0,0,0},
                {1,1,1},
                {0,1,1}});
        otherPathfinder = new OtherPathfinder(board);

        start = new Vector2(1,0);
        end = new Vector2(1,2);
        Mockito.when(board.getTileBoardPositionDependingOnWorldCoordinates(start)).thenReturn(new Vector2(1,0));
        Mockito.when(board.getTileBoardPositionDependingOnWorldCoordinates(end)).thenReturn(new Vector2(1,2));
        Mockito.when(board.getWorldCoordinateOfTile((int)1, (int)0)).thenReturn(start);
        Mockito.when(board.getWorldCoordinateOfTile(1,1)).thenReturn(new Vector2(1,1));
        Mockito.when(board.getWorldCoordinateOfTile((int)1, (int)2)).thenReturn(end);
        otherPathfinder = new OtherPathfinder(board);
    }


    @Test
    public void testInitializeBoard(){
        /*Array<OtherNode> nodes= otherPathfinder.getGraphNodes();
        Array<OtherNode> expectedResult = new Array<>();
        OtherNode otherNode1 = new OtherNode(0, new Vector2(1,0));
        OtherNode otherNode2 = new OtherNode(1, new Vector2(1,1));
        OtherNode otherNode3 = new OtherNode(2, new Vector2(1,2));
        OtherNode otherNode4 = new OtherNode(3, new Vector2(2,1));
        OtherNode otherNode5 = new OtherNode(4, new Vector2(2,2));

        otherNode1.addSuccessor(otherNode2);
        otherNode1.addSuccessor(otherNode4);

        otherNode2.addSuccessor(otherNode1);
        otherNode2.addSuccessor(otherNode3);
        otherNode2.addSuccessor(otherNode4);
        otherNode2.addSuccessor(otherNode5);


        otherNode3.addSuccessor(otherNode2);
        otherNode3.addSuccessor(otherNode4);
        otherNode3.addSuccessor(otherNode5);

        otherNode4.addSuccessor(otherNode1);
        otherNode4.addSuccessor(otherNode2);
        otherNode4.addSuccessor(otherNode3);
        otherNode4.addSuccessor(otherNode5);

        otherNode2.addSuccessor(otherNode2);
        otherNode5.addSuccessor(otherNode3);
        otherNode5.addSuccessor(otherNode4);
        expectedResult.add(otherNode1);
        expectedResult.add(otherNode2);
        expectedResult.add(otherNode3);
        expectedResult.add(otherNode4);
        expectedResult.add(otherNode5);

        Assert.assertEquals(expectedResult.size, nodes.size);
        for (int i = 0; i < expectedResult.size; i++) {
            Assert.assertEquals(expectedResult.get(i), nodes.get(i));
        }*/
    }

    @Test
    public void testCalculatePath(){
        /*Array<Vector2> finalArray = otherPathfinder.calculatePath(start,end);
        finalArray = otherPathfinder.calculatePath(start,end);
        Array<Vector2> expectedArray = new Array<>(new Vector2[]{start, new Vector2(1,1), new Vector2(1,2)});
        Assert.assertEquals(expectedArray,finalArray);*/
    }

}
