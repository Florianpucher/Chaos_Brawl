package com.strategy_bit.chaos_brawl.player_input_output.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.config.WorldSettings;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.resource_system.Resource;
import com.strategy_bit.chaos_brawl.util.SpawnArea;
import com.strategy_bit.chaos_brawl.util.VectorMath;

/**
 * holds information of the gameHUD
 *
 * @author AIsopp
 * @version 1.0
 * @since 16.04.2018
 */
public class GameHUD extends Table {

    private static final String NEW_UNIT_1 = "Archer";
    private static final String NEW_UNIT_2 = "Fighter";
    private static final String NEW_UNIT_3 = "Knight";

    private static final String UPGRADED_UNIT_1 = "Mage";
    private static final String UPGRADED_UNIT_2 = "Berserker";
    private static final String UPGRADED_UNIT_3 = "Templar";

    private static final String UPGRADE_UNITS = "U UP!";
    private static final String UPGRADE_TOWER = "T UP!";

    private BrawlButton btnNewUnit1;
    private BrawlButton btnNewUnit2;
    private BrawlButton btnNewUnit3;

    private BrawlButton btnUpgradeUnits;
    private BrawlButton btnUpgradeTower;


    private boolean playedVictoryOnce = false;
    private boolean playedDefeatOnce = false;

    private int nextUnitType;
    private Texture nonSpawnAreaTexture;
    private ProgressBar manaBar;
    private Array<BrawlButton> brawlButtons;

    private OrthographicCamera camera;
    private SpriteBatch batch;

    public GameHUD() {
        super(AssetManager.getInstance().defaultSkin);
        nextUnitType=-1;
        AssetManager assetManager = AssetManager.getInstance();


        btnNewUnit1 = new BrawlButton(NEW_UNIT_1, assetManager.defaultSkin, 0);
        btnNewUnit1.setName(NEW_UNIT_1);
        setFillParent(true);
        btnNewUnit2 = new BrawlButton(NEW_UNIT_2, assetManager.defaultSkin, 1);
        btnNewUnit2.setName(NEW_UNIT_2);
        setFillParent(true);
        btnNewUnit3 = new BrawlButton(NEW_UNIT_3, assetManager.defaultSkin, 2);
        btnNewUnit3.setName(NEW_UNIT_3);
        btnUpgradeUnits = new BrawlButton(UPGRADE_UNITS, assetManager.defaultSkin, 20);
        btnUpgradeUnits.setName(UPGRADE_UNITS);
        btnUpgradeTower = new BrawlButton(UPGRADE_TOWER, assetManager.defaultSkin, 21);
        btnUpgradeTower.setName(UPGRADE_TOWER);


        brawlButtons = new Array<>();
        brawlButtons.add(btnNewUnit1);
        brawlButtons.add(btnNewUnit2);
        brawlButtons.add(btnNewUnit3);
        brawlButtons.add(btnUpgradeUnits);
        brawlButtons.add(btnUpgradeTower);
        setFillParent(true);

        NinePatchDrawable resourceBarOuter = new NinePatchDrawable(assetManager.resourceSkinOuter);
        NinePatchDrawable resourceBar = new NinePatchDrawable(assetManager.resourceSkinInner);
        ProgressBar.ProgressBarStyle progressBarStyle = new ProgressBar.ProgressBarStyle(resourceBarOuter, new NinePatchDrawable(assetManager.resourceSkinMiddle));
        progressBarStyle.knobBefore = resourceBar;
        manaBar = new ProgressBar(0f, 100f, 0.1f, false, progressBarStyle);

        manaBar.setValue(0);
        setBackground((Drawable) null);

        top();
        //add actors to UI
        add(manaBar).top().width(Gdx.graphics.getWidth() / 2f).height(Gdx.graphics.getHeight() / 9f);
        row().height(7 * Gdx.graphics.getHeight() / 9f);
        add();
        row().height(Gdx.graphics.getHeight() / 9f).width((float) Gdx.graphics.getWidth());

        // add own table for organizing buttons
        Table lowerUI = new Table(assetManager.defaultSkin);
        lowerUI.right();
        add(lowerUI).width((float) Gdx.graphics.getWidth());
        lowerUI.add(btnNewUnit1).right().height(lowerUI.getPrefHeight());
        lowerUI.add(btnNewUnit2).right().height(lowerUI.getPrefHeight());
        lowerUI.add(btnNewUnit3).right().height(lowerUI.getPrefHeight());


        lowerUI.add(btnUpgradeUnits).left().height(lowerUI.getPrefHeight());
        lowerUI.add(btnUpgradeTower).left().height(lowerUI.getPrefHeight());

        btnNewUnit1.addListener(listener);
        btnNewUnit2.addListener(listener);
        btnNewUnit3.addListener(listener);
        btnUpgradeUnits.addListener(listener);
        btnUpgradeTower.addListener(listener);
        initializeGameOverView();
    }

    private void switchButtons(boolean upgradeExecuted, String input){

        if (upgradeExecuted && input == UPGRADE_UNITS){

            if (AssetManager.getInstance().getPlayable()){
                AssetManager.getInstance().upgradeExecuted.play(1f);
            }

            btnNewUnit1.setName(UPGRADED_UNIT_1);
            btnNewUnit1.setText(UPGRADED_UNIT_1);

            btnNewUnit2.setName(UPGRADED_UNIT_2);
            btnNewUnit2.setText(UPGRADED_UNIT_2);

            btnNewUnit3.setName(UPGRADED_UNIT_3);
            btnNewUnit3.setText(UPGRADED_UNIT_3);

            btnUpgradeUnits.remove();
        } else if (upgradeExecuted && input == UPGRADE_TOWER){
            AssetManager.getInstance().upgradeExecutedTower.play(1f);
            btnUpgradeTower.remove();
        }


    }
    

    private ClickListener listener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            String name = event.getListenerActor().getName();

            if (name.equals(NEW_UNIT_1) && brawlButtons.get(0).isActivated()) {
                if (nextUnitType == 0) {
                    nextUnitType = -1;
                } else {
                    nextUnitType = 0;
                }
            }
            if (name.equals(NEW_UNIT_2) && brawlButtons.get(1).isActivated()) {
                if (nextUnitType == 1) {
                    nextUnitType = -1;
                } else {
                    nextUnitType = 1;
                }
            }
            if (name.equals(NEW_UNIT_3) && brawlButtons.get(2).isActivated()) {
                if (nextUnitType == 2) {
                    nextUnitType = -1;
                } else {
                    nextUnitType = 2;
                }
            }
            if (name.equals(UPGRADED_UNIT_1) && brawlButtons.get(0).isActivated()) {
                if (nextUnitType == 3) {
                    nextUnitType = -1;
                } else {
                    nextUnitType = 3;
                }
            }
            if (name.equals(UPGRADED_UNIT_2) && brawlButtons.get(1).isActivated()) {
                if (nextUnitType == 4) {
                    nextUnitType = -1;
                } else {
                    nextUnitType = 4;
                }
            }
            if (name.equals(UPGRADED_UNIT_3) && brawlButtons.get(2).isActivated()) {
                if (nextUnitType == 5) {
                    nextUnitType = -1;
                } else {
                    nextUnitType = 5;
                }
            }
            if (name.equals(UPGRADE_UNITS) && brawlButtons.get(3).isActivated()) {
                switchButtons(true, UPGRADE_UNITS);
            }

            if (name.equals(UPGRADE_TOWER) && brawlButtons.get(4).isActivated()) {
                switchButtons(true, UPGRADE_TOWER);
            }


            if (nextUnitType != -1) {
                setBackground(new TextureRegionDrawable(new TextureRegion(nonSpawnAreaTexture)));
            } else {
                setBackground((Drawable) null);
            }
        }
    };


    private void initializeGameOverView(){
        batch = new SpriteBatch();
        float w = (float) Gdx.graphics.getWidth();
        float h = (float) Gdx.graphics.getHeight();
        camera = new OrthographicCamera(w, h);
        camera.position.set(w / 2, h / 2, 0);
    }

    public void initializeNonSpawnAreaShadow(SpawnArea spawnArea, Camera camera) {
        Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
        Vector2 screenPosition = VectorMath.vector3ToVector2(camera.project(new Vector3(
                spawnArea.x - WorldSettings.FRUSTUM_WIDTH/2f,
                spawnArea.y - WorldSettings.FRUSTUM_HEIGHT/2f,0)));
        Vector2 screenSize = VectorMath.vector3ToVector2(camera.project(new Vector3(
                spawnArea.getWidth()- WorldSettings.FRUSTUM_WIDTH/2f,
                 spawnArea.getHeight() - WorldSettings.FRUSTUM_HEIGHT/2f,0)));
        pixmap.setBlending(Pixmap.Blending.None);
        pixmap.setColor(0, 0, 0, 150);

        pixmap.fillRectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        pixmap.setColor(Color.CLEAR);
        pixmap.fillRectangle((int) (screenPosition.x), (int) (screenPosition.y),
                (int) (screenSize.x),
                (int) (screenSize.y));
        nonSpawnAreaTexture = new Texture(pixmap);
        pixmap.dispose();
    }

    public int getUnitToSpawn() {
        setBackground((Drawable) null);
        int current = nextUnitType;
        nextUnitType = -1;
        return current;
    }

    public void dispose() {
        nonSpawnAreaTexture.dispose();
        batch.dispose();
    }

    public void updateBtns(float v) {
        for (BrawlButton brawlButton :
                brawlButtons) {
            brawlButton.update(v);
        }
    }

    public void showWinningScreen(boolean win){
        clear();
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);           // clear screen

        camera.update();

        AssetManager assetManager = AssetManager.getInstance();
        float tw;
        float th;
        Texture gameOverView;
        if (win) {
            gameOverView = assetManager.victoryScreen;
            if (!playedVictoryOnce){
                if (AssetManager.getInstance().getPlayable()) {
                    AssetManager.getInstance().victory.play(1f);
                }
                playedVictoryOnce = true;
            }

        }else{
            gameOverView = assetManager.defeatScreen;
            if(!playedDefeatOnce) {
                if (AssetManager.getInstance().getPlayable()) {
                    AssetManager.getInstance().defeat.play(1f);
                }
                playedDefeatOnce = true;
            }
        }

        tw = (float)gameOverView.getWidth();
        th = (float)gameOverView.getHeight();

        batch.begin();
        batch.draw(gameOverView, camera.position.x - (tw / 2), camera.position.y - (th / 2));
        batch.end();


    }

    public void updateManaBar(Resource r){
        manaBar.setValue((float) r.percentageFull() * manaBar.getMaxValue());
    }


}
