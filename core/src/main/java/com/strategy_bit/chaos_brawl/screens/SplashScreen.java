package com.strategy_bit.chaos_brawl.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * @author AIsopp
 * @version 1.0
 * @since 22.03.2018
 */

public class SplashScreen extends AbstractScreen {

    private SpriteBatch batch;
    private Texture splashImage;
    private OrthographicCamera camera;
    private Vector2 position;
    private float size;
    private float splashWidth, splashHeight, splashX, splashY;

    @Override
    public void buildStage() {
        super.buildStage();
        batch = new SpriteBatch();
        splashImage = new Texture(Gdx.files.internal("user_interface/chaos_brawl_logo.png"));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        size = Gdx.graphics.getWidth() > Gdx.graphics.getHeight() ? Gdx.graphics.getHeight() : Gdx.graphics.getWidth();
        position = new Vector2();
        position.x = Gdx.graphics.getWidth() / 2f - size / 2f;
        position.y = Gdx.graphics.getHeight() / 2f - size / 2f;
        float scaleY=1f;
        if (splashImage.getHeight()>Gdx.graphics.getHeight()){
            splashHeight =Gdx.graphics.getHeight();
            scaleY=1-Gdx.graphics.getHeight()/splashImage.getHeight();
        }else {
            splashHeight =splashImage.getHeight();
        }
        float scaleX=1f;
        if (splashImage.getWidth()*scaleY>Gdx.graphics.getWidth()){
            splashWidth =Gdx.graphics.getWidth();
            scaleX=1-Gdx.graphics.getWidth()/splashImage.getWidth()*scaleY;
        }else {
            splashWidth =splashImage.getWidth()*scaleY;
        }
        splashHeight *=scaleX;
        splashX =(Gdx.graphics.getWidth()- splashWidth)/2f;
        splashY =(Gdx.graphics.getHeight()- splashHeight)/2f;

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(.135f, .206f, .235f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(splashImage, splashX, splashY, splashWidth, splashHeight);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        splashImage.dispose();
    }
}
