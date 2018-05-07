package com.strategy_bit.chaos_brawl.player_input_output.views;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.strategy_bit.chaos_brawl.types.UnitType;

public class BrawlButton extends TextButton {
    private UnitType unitType;
    private boolean activated;
    public BrawlButton(String text, Skin skin,UnitType unitType) {
        super(text, skin);
        activated=true;
        this.unitType=unitType;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
    public void update(float res){
        if (unitType.getCosts()>res){
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
