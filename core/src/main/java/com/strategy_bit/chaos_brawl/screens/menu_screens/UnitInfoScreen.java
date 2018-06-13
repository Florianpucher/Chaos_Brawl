package com.strategy_bit.chaos_brawl.screens.menu_screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.managers.AssetManager;

/**
 * Created by Florian on 04.06.2018.
 */

public class UnitInfoScreen extends MenuScreen{

    private List<String> previewList;
    private String first = "UNIT STATS:";
    private String empty = "";
    private static final String BACK = "Return to Main Menu";

    @Override
    public void buildStage() {
        super.buildStage();
        final TextButton btnBack = new TextButton(BACK, assetManager.defaultSkin);
        btnBack.setName(BACK);

        float height = Gdx.graphics.getHeight()/8f;

        final Table root = new Table(assetManager.defaultSkin);

        root.setBackground(new NinePatchDrawable(assetManager.defaultSkin.getPatch(AssetManager.MENU_BACKGROUND)));
        root.setFillParent(true);

        previewList = new List<>(assetManager.defaultSkin);
        ScrollPane scrollPane = new ScrollPane(previewList);
        root.add(scrollPane).width(Gdx.graphics.getHeight());

        Array<String> array = new Array<>();
        array.add(first);
        previewList.setItems(array);
        previewList.getItems().add(empty);

        addStats("ARCHER", 50f, 2f, 3f, 5f);
        addStats("FIGHTER", 75f, 1f, 2.5f, 7f);
        addStats("KNIGHT", 120f, 1f, 1f, 5f);

        addStats("MAGE", 60f, 1f, 3f, 12f);
        addStats("BERSERKER", 95f, 2f, 2.5f, 5f);
        addStats("TEMPLAR", 150f, 1f, 1f, 6f);

        root.add(btnBack).width(Gdx.graphics.getWidth()/3f).height(height);

        addActor(root);

        ClickListener listener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                String name = event.getListenerActor().getName();
                if (name.equals(BACK)) {
                    screenManager.switchToLastScreen();
                }
            }
        };

        btnBack.addListener(listener);
    }
    private void addStats(String name, float hitPoints, float attackSpeed, float attackRadius, float attackDamage){
        float dps = attackDamage*attackSpeed;
        previewList.getItems().add(name + ":");
        previewList.getItems().add("Hitpoints: " + hitPoints);
        previewList.getItems().add("DPS: " + Math.round(dps));
        previewList.getItems().add("AttackRange: " + attackRadius);
        previewList.getItems().add(empty);
    }
}
