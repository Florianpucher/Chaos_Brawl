package com.strategy_bit.chaos_brawl.screens.menu_screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.config.UnitConfig;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.managers.UnitManager;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by Florian on 04.06.2018.
 */

public class UnitInfoScreen extends MenuScreen{

    private List<String> previewList;
    private String first = "UNIT STATS:";
    private String empty = "";

    @Override
    public void buildStage() {
        super.buildStage();

        final Table root = new Table(assetManager.defaultSkin);

        root.setBackground(new NinePatchDrawable(assetManager.defaultSkin.getPatch(AssetManager.MENU_BACKGROUND)));
        root.setFillParent(true);

        previewList = new List<>(assetManager.defaultSkin);
        ScrollPane scrollPane = new ScrollPane(previewList);
        root.add(scrollPane).width(Gdx.graphics.getWidth());

        Array<String> array = new Array<>();
        array.add(first);
        previewList.setItems(array);
        previewList.getItems().add(empty);


        Iterator it = UnitManager.getInstance().getUnitConfigHashMap().entrySet().iterator();
        while (it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            UnitConfig unitConfig=((UnitConfig)(pair.getValue()));
            if (unitConfig.getId()>5&&unitConfig.getId()!=18&&unitConfig.getId()!=19){
                continue;
            }
            addStats(unitConfig.getName(),unitConfig.getHitPoints(),unitConfig.getAttackSpeed(),unitConfig.getAttackRadius(),unitConfig.getAttackDamage(),unitConfig.getCost());
        }
        addActor(root);
    }
    private void addStats(String name, float hitPoints, float attackSpeed, float attackRadius, float attackDamage,float cost){
        float dps = attackDamage*attackSpeed;
        previewList.getItems().add(name + ":");
        previewList.getItems().add("Cost: " + cost);
        previewList.getItems().add("Hitpoints: " + hitPoints);
        previewList.getItems().add("DPS: " + Math.round(dps));
        previewList.getItems().add("AttackRange: " + attackRadius);
        previewList.getItems().add(empty);
    }
}
