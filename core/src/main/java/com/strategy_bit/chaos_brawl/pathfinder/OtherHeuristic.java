package com.strategy_bit.chaos_brawl.pathfinder;

import com.badlogic.gdx.ai.pfa.Heuristic;

/**
 * @author AIsopp
 * @version 1.0
 * @since 24.04.2018
 */
public class OtherHeuristic implements Heuristic<OtherNode>{

    @Override
    public float estimate(OtherNode node, OtherNode endNode) {
        //TODO check if this calculation does no errors
        return node.calculateDistanceToOtherNode(endNode);
    }
}
