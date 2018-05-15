package com.strategy_bit.chaos_brawl.screens.menu_screens;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.network.network_handlers.NetworkConnectionHandler;

/**
 * @author AIsopp
 * @version 1.0
 * @since 02.04.2018
 */
abstract class LobbyScreen extends MenuScreen implements NetworkConnectionHandler{

    protected static final String PLAYER_1 = "1";
    protected static final String PLAYER_2 = "2";
    protected static final String PLAYER_3 = "3";
    protected static final String PLAYER_4 = "4";

    protected Array<TextButton> textButtons;
    protected Array<String> playerNames;
    protected Array<Integer> playerIds;
    protected int c=0;

    public LobbyScreen(){
        playerNames=new Array<>();
        playerIds=new Array<>();
    }

    public void addClient(String ip,int id){
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
    public void removeClient(int id){
        String ip=(playerNames.get(id-c++));
        if (playerNames==null){
            return;
        }else{

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
