package com.strategy_bit.chaos_brawl.pathfinder;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.PathFinderRequest;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.config.WalkAbleAreas;
import com.strategy_bit.chaos_brawl.world.BoardInterface;

import java.util.Iterator;

/**
 * @author AIsopp
 * @version 1.0
 * @since 24.04.2018
 */
public class OtherPathfinder {

    private BoardInterface boardInterface;
    IndexedGraph<OtherNode> nodeIndexedGraph;
    private IndexedAStarPathFinder<OtherNode> indexedAStarPathFinder;
    private OtherHeuristic heuristic;
    private Array<OtherNode> graphNodes;

    public OtherPathfinder(BoardInterface boardInterface){
        this.boardInterface = boardInterface;
        initializeGraph();
    }

    public Array<OtherNode> getGraphNodes() {
        return graphNodes;
    }

    private void initializeGraph(){
        graphNodes = new Array<>();

        int[][] walkablePort = boardInterface.boardToMatrix();
        int index = 0;
        //Add nodes to graphNodes
        for (int i = 0; i < walkablePort.length; i++) {
            for (int j = 0; j < walkablePort[i].length; j++) {

                if(walkablePort[i][j] == WalkAbleAreas.BLOCKING){
                    continue;
                }
                OtherNode otherNode = new OtherNode(index, new Vector2(i,j));
                index++;
                graphNodes.add(otherNode);
            }
        }

        //Connect nodes to each other
        for (int i = 0; i < graphNodes.size; i++) {
            OtherNode otherNode = graphNodes.get(i);
            for (int j = 0; j < graphNodes.size; j++) {
                if(j == i){
                    continue;
                }
                OtherNode possibleSuccessor = graphNodes.get(j);
                if(otherNode.isAdjacentTo(possibleSuccessor)){
                    otherNode.addSuccessor(possibleSuccessor);
                }
            }
        }

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

    /**
     *
     * @param start Vector2 in world coordinates
     * @param goal Vector2 in world coordinates
     * @return a Vector2 array containing all way points from start to goal in world coordinates
     */
    public Array<Vector2> calculatePath(Vector2 start, Vector2 goal){
        System.out.println(start);
        System.out.println(goal);
        OtherNode startNode = getNode(start);
        OtherNode endNode = getNode(goal);
        ResultPath path = new ResultPath();
        //TODO check if this solution is working
        PathFinderRequest<OtherNode> otherNodePathFinderRequest = new PathFinderRequest<OtherNode>(startNode, endNode, heuristic, path);
        otherNodePathFinderRequest.statusChanged = true;
        boolean found = indexedAStarPathFinder.search(otherNodePathFinderRequest, 1000000000);
        if(!found) {
            System.out.println("Could not find");
        }
        //otherNodePathFinderRequest.search(indexedAStarPathFinder, 1000000000);
        Array<Vector2> pathToReturn = new Array<>();
        Iterator<OtherNode> nodeIterator = path.iterator();
        while (nodeIterator.hasNext()){
            OtherNode node = nodeIterator.next();
            Vector2 worldPosition = getWorldCoordinateOfNode(node);
            pathToReturn.add(worldPosition);
        }
        System.out.println(pathToReturn);
        return pathToReturn;
    }

    /**
     *
     * @param worldCoordinate the world coordinates to check
     * @return a Node representing the tile that includes the worldCoordinate
     * @throws IllegalStateException if no node could be found
     */
    private OtherNode getNode(Vector2 worldCoordinate) throws IllegalStateException{
        Vector2 indexVector = boardInterface.getTileBoardPositionDependingOnWorldCoordinates(worldCoordinate);
        for (OtherNode node :
                graphNodes) {
            if (indexVector.equals(node.getPosition())){
                return node;
            }
        }
        throw new IllegalStateException("Could not find a Node for Coordinates: " + worldCoordinate);
    }

    private Vector2 getWorldCoordinateOfNode(OtherNode node){
        return boardInterface.getWorldCoordinateOfTile((int)node.getPosition().y,(int) node.getPosition().x);
    }
}
