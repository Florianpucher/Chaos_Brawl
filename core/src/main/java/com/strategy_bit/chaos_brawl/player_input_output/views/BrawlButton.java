package com.strategy_bit.chaos_brawl.player_input_output.views;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.strategy_bit.chaos_brawl.managers.AssetManager;

public class BrawlButton extends ImageButton {
    private int unitId;
    private boolean activated;
    private Cell<Actor> cell;
    public BrawlButton(String text, Skin skin, int unitId, TextureRegion imageSkin) {
        super(skin);
        setName(text);
        debug();

        cell= getImageCell();
        cell.setActor(new Image(imageSkin));
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

    public void setSizeImage(float width, float height, float percentageOf)
    {
        cell.width(width* percentageOf);
        cell.height(height * percentageOf);
    }
}
