package com.strategy_bit.chaos_brawl.pathfinder;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by A_329_09 on 30/03/2018.
 */

public class Node implements Comparable<Node> {
    private int x;
    private int y;
    private double gScore;
    private double fScore;
    private Node cameFrom;
    private boolean moveable;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.gScore=Double.POSITIVE_INFINITY;
        this.fScore=Double.POSITIVE_INFINITY;
        this.cameFrom=null;
    }

    public void setMoveable(boolean moveable){
        this.moveable = moveable;
    }

    public boolean getMoveable(){return moveable;}

    public double getgScore() {
        return gScore;
    }

    public void setgScore(double gScore) {
        this.gScore = gScore;
    }

    public double getfScore() {
        return fScore;
    }

    public void setfScore(double fScore) {
        this.fScore = fScore;
    }

    public Node getCameFrom() {
        return cameFrom;
    }

    public void setCameFrom(Node cameFrom) {
        this.cameFrom = cameFrom;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Vector2 getVector(){
        return new Vector2(x,y);
    }

    @Override
    public boolean equals(Object o) {
        try {
            return ((Node)o).getVector().equals(this.getVector());
        }catch (Exception e){
            e.printStackTrace();
        }
        return super.equals(o);
    }

    @Override
    public int compareTo(Node node) {
        double t=this.getfScore()-node.getfScore();
        if (t<0){
            return -1;
        }
        else if(t>0){
            return 1;
        }else {
            return 0;
        }

    }
}
