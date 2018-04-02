package com.strategy_bit.chaos_brawl.pathfinder;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.util.VectorMath;

import java.util.PriorityQueue;


/**
 * Created by A_329_09 on 29/03/2018.
 */

public class Pathfinder {
    private static Array<Array<Node>> board;
    private static PriorityQueue<Node> closedSet;
    private static PriorityQueue<Node> openSet;

    public static Array<Vector2> findPath(Vector2 start,Vector2 goal) {
        Node startNode=board.get(Math.round(start.x)).get(Math.round(start.y));
        Node goalNode=board.get(Math.round(goal.x)).get(Math.round(goal.y));
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
                double tentatice_gSCore=current.getgScore()+VectorMath.distance(current.getVector(),neighbor.getVector());
                if (tentatice_gSCore>=neighbor.getgScore()){
                    continue;
                }
                neighbor.setCameFrom(current);
                neighbor.setgScore(tentatice_gSCore);
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
                    neighbors.add(board.get(node.getX()+i).get(node.getY()+j));
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

    public static void initBoard(){
        board=new Array<Array<Node>>();
        for (int i = 0; i < 10; i++) {
            Array<Node> coloumn=new Array<Node>();
            for (int j = 0; j < 10; j++) {
                coloumn.add(new Node(i,j));
            }
            board.add(coloumn);
        }
    }
}
