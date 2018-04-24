package com.strategy_bit.chaos_brawl.pathfinder;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.PathFinderRequest;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.world.Board;

/**
 * @author AIsopp
 * @version 1.0
 * @since 24.04.2018
 */
public class OtherPathfinder {

    private Board board;
    IndexedGraph<OtherNode> nodeIndexedGraph;
    private int nodeAmount;

    public OtherPathfinder(Board board){
        this.board = board;


        this.nodeAmount = 0;

    }


    private void initializeGraph(){






        nodeIndexedGraph = new IndexedGraph<OtherNode>() {
            @Override
            public int getIndex(OtherNode node) {
                return node.getIndex();
            }

            @Override
            public int getNodeCount() {
                return nodeAmount;
            }

            @Override
            public Array<Connection<OtherNode>> getConnections(OtherNode fromNode) {
                return fromNode.getConnections();
            }
        };

        IndexedAStarPathFinder<OtherNode> indexedGraphIndexedAStarPathFinder = new IndexedAStarPathFinder<>(nodeIndexedGraph);
        PathFinderRequest<OtherNode> otherNodePathFinderRequest = new PathFinderRequest<OtherNode>();
        //otherNodePathFinderRequest.
    }

    public void calculatePath(Vector2 start, Vector2 goal){

    }
}
