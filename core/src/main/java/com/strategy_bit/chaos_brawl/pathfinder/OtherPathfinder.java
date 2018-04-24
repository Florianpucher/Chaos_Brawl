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
    private IndexedAStarPathFinder<OtherNode> indexedAStarPathFinder;
    private OtherHeuristic heuristic;
    private Array<OtherNode> graphNodes;

    public OtherPathfinder(Board board){
        this.board = board;


        this.nodeAmount = 0;
        initializeGraph();
    }


    private void initializeGraph(){
        graphNodes = new Array<>();
        //TODO add to graphNodes nodes

        nodeIndexedGraph = new IndexedGraph<OtherNode>() {
            @Override
            public int getIndex(OtherNode node) {
                return node.getIndex();
            }

            @Override
            public int getNodeCount() {
                return graphNodes.size;
            }

            @Override
            public Array<Connection<OtherNode>> getConnections(OtherNode fromNode) {
                return fromNode.getConnections();
            }
        };


        indexedAStarPathFinder = new IndexedAStarPathFinder<>(nodeIndexedGraph);
        heuristic = new OtherHeuristic();
        //otherNodePathFinderRequest.
    }

    public Array<Vector2> calculatePath(Vector2 start, Vector2 goal){
        OtherNode startNode = getNode(start);
        OtherNode endNode = getNode(goal);
        ResultPath path = new ResultPath();
        PathFinderRequest<OtherNode> otherNodePathFinderRequest = new PathFinderRequest<OtherNode>(startNode, endNode, heuristic, path);
        otherNodePathFinderRequest.search(indexedAStarPathFinder, 10000);
        Array<Vector2> pathToReturn = new Array<>();

        return pathToReturn;
    }


    private OtherNode getNode(Vector2 worldCoordinate){
        //TODO get graphNode from graphNodes here
        return null;
    }
}
