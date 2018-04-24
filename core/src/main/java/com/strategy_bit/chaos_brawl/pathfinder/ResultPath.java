package com.strategy_bit.chaos_brawl.pathfinder;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

/**
 * @author AIsopp
 * @version 1.0
 * @since 24.04.2018
 */
public class ResultPath implements GraphPath<OtherNode>{
    private Array<OtherNode> innerPath;

    public ResultPath() {
        this.innerPath = new Array<>();
    }


    @Override
    public int getCount() {
        return innerPath.size;
    }

    @Override
    public OtherNode get(int index) {
        return innerPath.get(index);
    }

    @Override
    public void add(OtherNode node) {
        innerPath.add(node);
    }

    @Override
    public void clear() {
        innerPath.clear();
    }

    @Override
    public void reverse() {
        innerPath.reverse();
    }

    @Override
    public Iterator<OtherNode> iterator() {
        return innerPath.iterator();
    }
}
