package com.strategy_bit.chaos_brawl.screens.menu_screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.network.network_handlers.NetworkConnectionHandler;

/**
 * @author AIsopp
 * @version 1.0
 * @since 02.04.2018
 */
abstract class LobbyScreen extends MenuScreen implements NetworkConnectionHandler {

    protected static final String PLAYER_1 = "1";
    protected static final String PLAYER_2 = "2";
    protected static final String PLAYER_3 = "3";
    protected static final String PLAYER_4 = "4";

    protected Array<TextButton> textButtons;
    protected Array<String> playerNames;
    protected Array<Integer> playerIds;
    protected int c = 0;

    protected TextButton btnPlayer1;
    protected TextButton btnPlayer2;
    protected TextButton btnPlayer3;
    protected TextButton btnPlayer4;
    protected Table root;
    protected float height;
    protected float width;


    public LobbyScreen() {
        playerNames = new Array<>();
        playerIds = new Array<>();
    }

    @Override
    public void buildStage() {
        super.buildStage();
        btnPlayer1 = new TextButton(PLAYER_1, assetManager.getDefaultSkin());
        btnPlayer1.setName(PLAYER_1);
        btnPlayer2 = new TextButton(PLAYER_2, assetManager.getDefaultSkin());
        btnPlayer2.setName(PLAYER_2);
        btnPlayer3 = new TextButton(PLAYER_3, assetManager.getDefaultSkin());
        btnPlayer3.setName(PLAYER_3);
        btnPlayer4 = new TextButton(PLAYER_4, assetManager.getDefaultSkin());
        btnPlayer4.setName(PLAYER_4);
        textButtons = new Array<>();
        textButtons.add(btnPlayer1);
        textButtons.add(btnPlayer2);
        textButtons.add(btnPlayer3);
        textButtons.add(btnPlayer4);

        root = new Table(assetManager.getDefaultSkin());
        root.setBackground(new NinePatchDrawable(assetManager.getDefaultSkin().getPatch(AssetManager.MENU_BACKGROUND)));
        root.setFillParent(true);
        height = Gdx.graphics.getHeight() / 6f;
        width=Gdx.graphics.getWidth()/1.2f;
        root.top();
        root.add(btnPlayer1).width(width).height(height);
        root.row();
        root.add(btnPlayer2).width(width).height(height);
        root.row();
        root.add(btnPlayer3).width(width).height(height);
        root.row();
        root.add(btnPlayer4).width(width).height(height);
        root.row();
    }

    public void addClient(String ip, int id) {
        playerIds.add(id);

        playerNames.add(ip);
        textButtons.get(0).setText(PLAYER_1);
        textButtons.get(1).setText(PLAYER_2);
        textButtons.get(2).setText(PLAYER_3);
        textButtons.get(3).setText(PLAYER_4);
        for (int i = 0; i < playerNames.size; i++) {
            textButtons.get(i).setText(playerNames.get(i));
        }
    }

    public void removeClient(int id) {
        String ip = (playerNames.get(id - c++));

        playerNames.removeValue(ip, true);

        textButtons.get(0).setText(PLAYER_1);
        textButtons.get(1).setText(PLAYER_2);
        textButtons.get(2).setText(PLAYER_3);
        textButtons.get(3).setText(PLAYER_4);
        for (int i = 0; i < playerNames.size; i++) {
            textButtons.get(i).setText(playerNames.get(i));
        }
    }

}
