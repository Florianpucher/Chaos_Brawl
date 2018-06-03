package com.strategy_bit.chaos_brawl.player_input_output.views;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.strategy_bit.chaos_brawl.managers.AssetManager;

public class BrawlButton extends TextButton {
    private int unitId;
    private boolean activated;
    public BrawlButton(String text, Skin skin,int unitId) {
        super(text, skin);
        activated=true;
        this.unitId=unitId;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
    public void update(float res){
        if (AssetManager.getInstance().unitManager.unitConfigHashMap.get(unitId).getCost()>res){
            setDisabled(true);
            setTouchable(Touchable.disabled);
            setActivated(false);
        }else {
            setTouchable(Touchable.enabled);
            setDisabled(false);
            setActivated(true);
        }
    }
}
