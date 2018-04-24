package com.strategy_bit.chaos_brawl.pathfinder;

import com.badlogic.gdx.ai.pfa.Connection;

/**
 * @author AIsopp
 * @version 1.0
 * @since 24.04.2018
 */
public class OtherConnection implements Connection<OtherNode> {
    private OtherNode fromNode;
    private OtherNode toNode;

    public OtherConnection(OtherNode fromNode, OtherNode toNode) {
        this.fromNode = fromNode;
        this.toNode = toNode;
    }

    @Override
    public float getCost() {
        return 1;
    }

    @Override
    public OtherNode getFromNode() {
        return fromNode;
    }

    @Override
    public OtherNode getToNode() {
        return toNode;
    }
}
