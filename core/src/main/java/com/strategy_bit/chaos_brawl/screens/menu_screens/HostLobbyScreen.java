package com.strategy_bit.chaos_brawl.screens.menu_screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.network.server.BrawlServer;

/**
 * @author AIsopp
 * @version 1.0
 * @since 02.04.2018
 */
public class HostLobbyScreen extends LobbyScreen {

    private static final String START_GAME = "Start Game";


    private BrawlServer brawlServer;


    public HostLobbyScreen(BrawlServer brawlServer) {
        this.brawlServer = brawlServer;
        brawlServer.setNetworkConnectionHandler(this);
    }

    @Override
    public void buildStage() {
        super.buildStage();
        final TextButton btnStartGame = new TextButton(START_GAME, assetManager.defaultSkin);
        btnStartGame.setName(START_GAME);
        final TextButton btnPlayer1 = new TextButton(PLAYER_1, assetManager.defaultSkin);
        btnPlayer1.setName(PLAYER_1);
        final TextButton btnPlayer2 = new TextButton(PLAYER_2, assetManager.defaultSkin);
        btnPlayer2.setName(PLAYER_2);
        final TextButton btnPlayer3 = new TextButton(PLAYER_3, assetManager.defaultSkin);
        btnPlayer3.setName(PLAYER_3);
        final TextButton btnPlayer4 = new TextButton(PLAYER_4, assetManager.defaultSkin);
        btnPlayer4.setName(PLAYER_4);
        textButtons = new Array<>();
        textButtons.add(btnPlayer1);
        textButtons.add(btnPlayer2);
        textButtons.add(btnPlayer3);
        textButtons.add(btnPlayer4);

        final Table root = new Table(assetManager.defaultSkin);
        root.setBackground(new NinePatchDrawable(assetManager.defaultSkin.getPatch("default-window")));
        root.setFillParent(true);
        float height = Gdx.graphics.getHeight() / 8f;
        root.top();
        root.add(btnPlayer1).width(Gdx.graphics.getWidth() / 4f).height(height);
        root.row();
        root.add(btnPlayer2).width(Gdx.graphics.getWidth() / 4f).height(height);
        root.row();
        root.add(btnPlayer3).width(Gdx.graphics.getWidth() / 4f).height(height);
        root.row();
        root.add(btnPlayer4).width(Gdx.graphics.getWidth() / 4f).height(height);
        root.row();
        root.add(btnStartGame).width(Gdx.graphics.getWidth() / 4f).height(height);
        addActor(root);

        ClickListener listener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                String name = event.getListenerActor().getName();
                if (name.equals(START_GAME)) {
                    startGame();
                } else if (name.equals(PLAYER_2) && playerIds.size > 1) {
                    brawlServer.closeConnectionTo(0);
                } else if (name.equals(PLAYER_3) && playerIds.size > 2) {
                    brawlServer.closeConnectionTo(1);
                } else if (name.equals(PLAYER_4) && playerIds.size > 3) {
                    brawlServer.closeConnectionTo(2);
                }
            }
        };
        btnStartGame.addListener(listener);
        btnPlayer2.addListener(listener);
        btnPlayer3.addListener(listener);
        btnPlayer4.addListener(listener);

        addClient(brawlServer.getName(),0);
    }


    private void startGame() {
        //dont start game if only the host or more than 4 are players are in the lobby
        if (playerNames.size < 2||playerNames.size>4) return;
        brawlServer.sendGameInitializingMessage();

    }

    @Override
    public void connected() {
        // Not needed host
    }

    @Override
    public void disconnected() {
        // Not needed host
    }

    @Override
    public void anotherClientConnected(String clientName, int id){
        if (playerNames.size>3){
            throw new IllegalStateException("Client limit reached (only a maximum of 4 players are allowed)");
        }
        addClient(clientName, id);
    }

    @Override
    public void anotherClientDisconnected(int id){
        //if id >=4 the client has never entered the lobby
        if (id>3){
            throw new IllegalStateException("Client wasn't in the lobby (only a maximum of 4 players are allowed)");
        }
        removeClient(id);
    }

    @Override
    protected void handleBackKey() {
        brawlServer.closeServer();
        super.handleBackKey();
    }
}
