package com.strategy_bit.chaos_brawl.ashley.components;

import com.badlogic.ashley.core.Component;
import com.sun.org.apache.xpath.internal.SourceTree;

public class UpgradeComponent implements Component{

    public UpgradeComponent(){

    }

    public void Units(){
        System.out.println("All Units got upgraded!");
    }

    public void Buildings(){
        System.out.println("All Buildings got upgraded!");
    }

}
