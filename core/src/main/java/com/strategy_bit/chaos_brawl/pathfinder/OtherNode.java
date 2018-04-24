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
        this.connections = new Array<Connection<OtherNode>>();
        this.index = index;
        this.position = new Vector2(position);
    }

    public void addSubSeccors(OtherNode node){
        OtherConnection connection = new OtherConnection(this, node);
        connections.add(connection);
    }


    public Array<Connection<OtherNode>> getConnections() {
        return connections;
    }


    public float calculateDistanceToOtherNode(OtherNode otherNode){
        return (float) VectorMath.distance(this.position, otherNode.position);
    }
}
