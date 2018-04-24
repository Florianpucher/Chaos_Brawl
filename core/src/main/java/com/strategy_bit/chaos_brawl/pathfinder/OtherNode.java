package com.strategy_bit.chaos_brawl.pathfinder;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.utils.Array;

/**
 * @author AIsopp
 * @version 1.0
 * @since 24.04.2018
 */
public class OtherNode {
    private Array<Connection<OtherNode>> connections;
    private int index;

    public int getIndex() {
        return index;
    }

    public OtherNode(int index) {
        this.connections = new Array<Connection<OtherNode>>();
        this.index = index;

    }

    public void addSubSeccors(OtherNode node){
        OtherConnection connection = new OtherConnection(this, node);
        connections.add(connection);
    }


    public Array<Connection<OtherNode>> getConnections() {
        return connections;
    }
}
