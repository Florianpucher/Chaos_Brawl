package com.strategy_bit.chaos_brawl.player_input_output.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * @author AIsopp
 * @version 1.0
 * @since 14.06.2018
 */
public class Manabar extends ProgressBar {
    private boolean disabled = false;
    private boolean round;
    private ProgressBarStyle style;

    public Manabar(float min, float max, float stepSize, boolean vertical, Skin skin) {
        super(min, max, stepSize, vertical, skin.get("default-" + (vertical ? "vertical" : "horizontal"), ProgressBarStyle.class));
        this.round = true;
        this.style = getStyle();
    }

    @Override
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        ProgressBarStyle style = this.style;
        boolean disabled = this.disabled;
        final Drawable knob = getKnobDrawable();
        final Drawable bg = (disabled && style.disabledBackground != null) ? style.disabledBackground : style.background;
        final Drawable knobBefore = (disabled && style.disabledKnobBefore != null) ? style.disabledKnobBefore : style.knobBefore;

        Color color = getColor();
        float x = getX();
        float y = getY();
        float width = getWidth();
        float height = getHeight();
        float knobWidth = knob == null ? 0 : knob.getMinWidth();
        float percent = getVisualPercent();
        float position = getKnobPosition();

        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);


        float positionWidth = width;

        float bgLeftWidth = 0;
        if (bg != null) {
            if (round)
                bg.draw(batch, x, Math.round(y + (height - bg.getMinHeight()) * 0.5f), width, height);
            else
                bg.draw(batch, x, y + (height - bg.getMinHeight()) * 0.5f, width, height);
            bgLeftWidth = bg.getLeftWidth();
            positionWidth -= bgLeftWidth + bg.getRightWidth();
        }

        float knobWidthHalf = 0;
        if (getMinValue() != getMaxValue()) {
            if (knob == null) {
                knobWidthHalf = knobBefore == null ? 0 : knobBefore.getMinWidth() * 0.5f;
                position = (positionWidth - knobWidthHalf) * percent;
                position = Math.min(positionWidth - knobWidthHalf, position);
            } else {
                knobWidthHalf = knobWidth * 0.5f;
                position = (positionWidth - knobWidth) * percent;
                position = Math.min(positionWidth - knobWidth, position) + bgLeftWidth;
            }
            position = Math.max(0, position);
        }

        if (knobBefore != null) {
            float offset = 0;
            if (bg != null) {
                offset = bgLeftWidth;
                if (round)
                    knobBefore.draw(batch, Math.round(x + offset), Math.round(y + (height - knobBefore.getMinHeight()) * 0.5f),
                            Math.round(position + knobWidthHalf), height - (bg.getMinHeight() - knobBefore.getMinHeight()));
                else
                    knobBefore.draw(batch, x + offset, y + (height - knobBefore.getMinHeight()) * 0.5f,
                            position + knobWidthHalf, knobBefore.getMinHeight());
            }
        }
    }


}
