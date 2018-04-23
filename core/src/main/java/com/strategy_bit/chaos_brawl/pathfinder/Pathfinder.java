package com.strategy_bit.chaos_brawl.pathfinder;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.util.VectorMath;
import com.strategy_bit.chaos_brawl.world.Board;
import com.strategy_bit.chaos_brawl.world.Tile;
import com.strategy_bit.chaos_brawl.world.World;
import com.strategy_bit.chaos_brawl.screens.GameScreen;

import java.util.PriorityQueue;


/**
 * Created by A_329_09 on 29/03/2018.
 */

public class Pathfinder {
    private static Array<Array<Node>> board;
    private static PriorityQueue<Node> closedSet;
    private static PriorityQueue<Node> openSet;
    private static float x=1.0f;
    private static float y=1.0f;

    public static Array<Vector2> findPath(Vector2 start,Vector2 goal) {
        Node startNode=board.get(Math.round(start.x/x)).get(Math.round(start.y/y));
        Node goalNode=board.get(Math.round(goal.x/x)).get(Math.round(goal.y/y));
        // The set of nodes already evaluated
        closedSet = new PriorityQueue<Node>();
        // The set of currently discovered nodes that are not evaluated yet.
        openSet = new PriorityQueue<Node>();
        // Initially, only the start node is known.
        openSet.add(startNode);

        // The cost of going from start to start is zero.
        startNode.setgScore(0);


        // For the first node, that value is completely heuristic.
        startNode.setfScore(VectorMath.distance(start,goal));

        while (!openSet.isEmpty()){
            Node current=getLowestFScore();
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
        total_path.add(current.getVector());
        while (current.getCameFrom()!=null){
            current=current.getCameFrom();
            total_path.add(current.getVector());
        }
        total_path.reverse();
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
                    }
                } catch (IndexOutOfBoundsException e){
                    e.printStackTrace();
                }
            }
        }
        return neighbors;
    }

    private static Node getLowestFScore(){
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
        Tile[][] arr=b.getTileBoard();
        Vector2 v=arr[arr.length-1][arr[0].length-1].getPosition();
        x=v.x;
        y=v.y;
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
