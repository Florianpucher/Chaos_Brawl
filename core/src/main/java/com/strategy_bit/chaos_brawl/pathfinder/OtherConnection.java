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
        return fromNode.calculateDistanceToOtherNode(toNode);
    }

    @Override
    public OtherNode getFromNode() {
        return fromNode;
    }

    @Override
    public OtherNode getToNode() {
        return toNode;
    }


    @Override
    public boolean equals(Object o) {
        if(!(o instanceof OtherConnection)){
            return false;
        }

        OtherConnection otherConnection = (OtherConnection) o;


        return toNode.getIndex() == otherConnection.getToNode().getIndex() &&
                fromNode.getIndex() == otherConnection.getFromNode().getIndex();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
