package com.strategy_bit.chaos_brawl.pathfinder;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.util.VectorMath;

/**
 * @author AIsopp
 * @version 1.0
 * @since 24.04.2018
 */
public class OtherNode {
    private Array<Connection<OtherNode>> connections;
    private int index;
    private Vector2 position;

    public int getIndex() {
        return index;
    }

    public OtherNode(int index, Vector2 position) {
        this.connections = new Array<>();
        this.index = index;
        this.position = new Vector2(position);
    }

    public void addSuccessor(OtherNode node){
        OtherConnection connection = new OtherConnection(this, node);
        connections.add(connection);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Array<Connection<OtherNode>> getConnections() {
        return connections;
    }


    public float calculateDistanceToOtherNode(OtherNode otherNode){
        return (float) VectorMath.distance(this.position, otherNode.position);
    }

    /**
     *
     * @param otherNode the node to check if it is a neighbour
     * @return true if the otherNode is a neighbour of the current one
     */
    public boolean isAdjacentTo(OtherNode otherNode){
        float xDistance = Math.abs(otherNode.position.x - position.x);
        float yDistance = Math.abs(otherNode.position.y - position.y);
        return (xDistance <= 1 && yDistance <= 1);
    }

    @Override
    public boolean equals(Object o) {
        if(o == null){
            return false;
        }
        OtherNode otherNode = (OtherNode) o;
        if(index == otherNode.index && position.equals(otherNode.getPosition())){
            if(otherNode.connections.size == connections.size){
                for (int i = 0; i < connections.size; i++) {
                    if(!connections.get(i).equals(otherNode.connections.get(i))){
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Index: " + index +", Position: " + position;
    }
}
