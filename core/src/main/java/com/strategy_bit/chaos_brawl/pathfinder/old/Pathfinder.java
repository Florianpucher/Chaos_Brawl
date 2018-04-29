package com.strategy_bit.chaos_brawl.pathfinder.old;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.util.VectorMath;
import com.strategy_bit.chaos_brawl.world.Board;

import java.util.PriorityQueue;


/**
 * Created by A_329_09 on 29/03/2018.
 */

public class Pathfinder {
    static Array<Array<Node>> board;
    static Board gameBoard;
    //private static PriorityQueue<Node> closedSet;
    //private static PriorityQueue<Node> openSet;

    public static Array<Vector2> findPath(Vector2 start,Vector2 goal) {
        //x = multiplicandX/2 + multiplicandX* j => (x - mulX/2)/mulX
        Vector2 startVector = gameBoard.getTileBoardPositionDependingOnWorldCoordinates(start);
        Vector2 endVector = gameBoard.getTileBoardPositionDependingOnWorldCoordinates(goal);
        Node startNode=board.get((int) startVector.x).get((int) startVector.y);
        Node goalNode=board.get((int) endVector.x).get((int) endVector.y);
        // The set of nodes already evaluated
        PriorityQueue<Node> closedSet = new PriorityQueue<Node>();
        // The set of currently discovered nodes that are not evaluated yet.
        PriorityQueue<Node>openSet = new PriorityQueue<Node>();
        // Initially, only the start node is known.
        openSet.add(startNode);

        // The cost of going from start to start is zero.
        startNode.setgScore(0);


        // For the first node, that value is completely heuristic.
        startNode.setfScore(VectorMath.distance(start,goal));

        while (!openSet.isEmpty()){
            Node current=getLowestFScore(openSet);
            if(current==goalNode){
                return reconstruct_path(current);
            }
            openSet.remove(current);
            closedSet.add(current);
            for (Node neighbor :
                    getNeighbors(current)) {
                if (closedSet.contains(neighbor)) {
                    continue;
                }
                if (!openSet.contains(neighbor)){
                    openSet.add(neighbor);
                }
                double tentative_gSCore=current.getgScore()+VectorMath.distance(current.getVector(),neighbor.getVector());
                if (tentative_gSCore>=neighbor.getgScore()){
                    continue;
                }
                neighbor.setCameFrom(current);
                neighbor.setgScore(tentative_gSCore);
                neighbor.setfScore(neighbor.getgScore()+VectorMath.distance(neighbor.getVector(),goalNode.getVector()));
            }

        }
        return null;
    }

    private static Array<Vector2> reconstruct_path(Node current){
        Array<Vector2> total_path=new Array<Vector2>();
        //total_path.add(current.getVector());
        total_path.add(gameBoard.getWorldCoordinateOfTile((int)current.getVector().y,(int) current.getVector().x));
        while (current.getCameFrom()!=null){
            current=current.getCameFrom();
            total_path.add(gameBoard.getWorldCoordinateOfTile((int)current.getVector().y,(int) current.getVector().x));
            //total_path.add(current.getVector());
        }
        total_path.reverse();
        for (Array<Node> nodeArray : board){
            for (Node node :
                    nodeArray) {
                node.setfScore(Double.POSITIVE_INFINITY);
                node.setgScore(Double.POSITIVE_INFINITY);
            }
        }


        return total_path;
    }

    private static Array<Node> getNeighbors(Node node){
        Array<Node> neighbors=new Array<Node>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i==0&&j==0){
                    continue;
                }
                try {
                    Node n=board.get(node.getX()+i).get(node.getY()+j);
                    if(node.getMoveable()) {
                        neighbors.add(n);

                    }else{
                        //System.out.println("Not move able");
                    }
                } catch (IndexOutOfBoundsException ignore){

                }
            }
        }
        return neighbors;
    }

    private static Node getLowestFScore(PriorityQueue<Node>openSet){
        double min=Double.POSITIVE_INFINITY;
        Node lowest=null;
            for (Node node :
                    openSet) {
                if(min>node.getfScore()){
                    min=node.getfScore();
                    lowest=node;
                }
            }


        return lowest;
    }

    public static void initBoard(int lines, int coloms){
        board=new Array<Array<Node>>();
        for (int i = 0; i < coloms; i++) {
            Array<Node> coloumn=new Array<Node>();
            for (int j = 0; j < lines; j++) {
                coloumn.add(new Node(i,j));
            }
            board.add(coloumn);
        }
    }

    public static void setMoveable(int[][] matrix, Board b){
        //Tile[][] arr=b.getTileBoard();
        gameBoard = b;
        //Vector2 v=arr[arr.length-1][arr[0].length-1].getPosition();
        //float x=v.x;
        //float y=v.y;
        initBoard(matrix[0].length,matrix.length);
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[0].length; j++){
                if (matrix[i][j] == 0){
                    board.get(i).get(j).setMoveable(false);
                }
                else{
                    board.get(i).get(j).setMoveable(true);
                }
            }
        }
    }

}
