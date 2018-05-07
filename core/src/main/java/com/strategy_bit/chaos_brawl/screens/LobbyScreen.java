package com.strategy_bit.chaos_brawl.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.config.Network;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.managers.ScreenManager;
import com.strategy_bit.chaos_brawl.network.Server.BrawlServer;

/**
 * @author AIsopp
 * @version 1.0
 * @since 02.04.2018
 */
abstract class LobbyScreen extends AbstractScreen {

    protected final static String PLAYER_1 = "1";
    protected final static String PLAYER_2 = "2";
    protected final static String PLAYER_3 = "3";
    protected final static String PLAYER_4 = "4";

    protected AssetManager assetManager;
    protected ScreenManager screenManager;
    protected OrthographicCamera camera;
    protected Array<TextButton> textButtons;
    protected Array<String> playerNames;
    protected Array<Integer> playerIds;
    protected int c=0;

    public LobbyScreen() {

    }

    @Override
    public void buildStage() {
        super.buildStage();

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        act();
        draw();
        camera.update();
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void hide() {
        super.hide();
        Gdx.input.setInputProcessor(null);
    }


    @Override
    protected void handleBackKey() {
        super.handleBackKey();
    }

    public void addClient(String ip,int id){
        if (playerNames==null){
            playerNames=new Array<>();
        }
        if (playerIds==null){
            playerIds=new Array<>();
        }
        playerIds.add(id);
        System.out.println(id+"\n"+ip);
        playerNames.add(ip);
        textButtons.get(0).setText(PLAYER_1);
        textButtons.get(1).setText(PLAYER_2);
        textButtons.get(2).setText(PLAYER_3);
        textButtons.get(3).setText(PLAYER_4);
        for (int i = 0; i < playerNames.size; i++) {
            textButtons.get(i).setText(playerNames.get(i));
        }
    }
    public void removeClient(int id){
        String ip=(playerNames.get(id-c++));
        if (playerNames==null){
            return;
        }else{
            System.out.println(ip);
            playerNames.removeValue(ip,true);
        }
        textButtons.get(0).setText(PLAYER_1);
        textButtons.get(1).setText(PLAYER_2);
        textButtons.get(2).setText(PLAYER_3);
        textButtons.get(3).setText(PLAYER_4);
        for (int i = 0; i < playerNames.size; i++) {
            textButtons.get(i).setText(playerNames.get(i));
        }
    }

}
