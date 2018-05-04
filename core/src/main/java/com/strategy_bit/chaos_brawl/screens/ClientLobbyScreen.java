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
public class ClientLobbyScreen extends AbstractScreen {

    private final static String PLAYER_1 = "1";
    private final static String PLAYER_2 = "2";
    private final static String PLAYER_3 = "3";
    private final static String PLAYER_4 = "4";

    private AssetManager assetManager;
    private ScreenManager screenManager;
    private OrthographicCamera camera;
    private BrawlServer brawlServer;
    private Array<TextButton> textButtons;
    private int players=0;

    public ClientLobbyScreen() {

    }

    @Override
    public void buildStage() {
        super.buildStage();
        assetManager = AssetManager.getInstance();
        screenManager = ScreenManager.getInstance();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        final TextButton btnPlayer1 = new TextButton(PLAYER_1, assetManager.defaultSkin);
        btnPlayer1.setName(PLAYER_1);
        final TextButton btnPlayer2 = new TextButton(PLAYER_2, assetManager.defaultSkin);
        btnPlayer2.setName(PLAYER_2);
        final TextButton btnPlayer3 = new TextButton(PLAYER_3, assetManager.defaultSkin);
        btnPlayer3.setName(PLAYER_3);
        final TextButton btnPlayer4 = new TextButton(PLAYER_4, assetManager.defaultSkin);
        btnPlayer4.setName(PLAYER_4);
        textButtons=new Array<>();
        textButtons.add(btnPlayer1);
        textButtons.add(btnPlayer2);
        textButtons.add(btnPlayer3);
        textButtons.add(btnPlayer4);

        final Table root = new Table(assetManager.defaultSkin);
        root.setBackground(new NinePatchDrawable(assetManager.defaultSkin.getPatch("default-window")));
        root.setFillParent(true);
        float height = Gdx.graphics.getHeight()/8;
        root.top();
        root.add(btnPlayer1).width(Gdx.graphics.getWidth()/4).height(height);
        root.row();
        root.add(btnPlayer2).width(Gdx.graphics.getWidth()/4).height(height);
        root.row();
        root.add(btnPlayer3).width(Gdx.graphics.getWidth()/4).height(height);
        root.row();
        root.add(btnPlayer4).width(Gdx.graphics.getWidth()/4).height(height);
        addActor(root);

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
        System.out.println("SHOW LOBBY");
        Gdx.input.setInputProcessor(this);
        addClient("self");
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

    public void addClient(String ip){
        System.out.println(ip);
        textButtons.get(players++).setText(ip);
    }
}
