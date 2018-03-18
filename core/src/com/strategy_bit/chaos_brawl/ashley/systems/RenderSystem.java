package com.strategy_bit.chaos_brawl.ashley.systems;


import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.ashley.components.TextureComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.ashley.util.DisposeAble;

import java.util.Comparator;

/**
 * @author AIsopp
 * @version 1.0
 * @since 15.03.2018
 */
public class RenderSystem extends IteratingSystem implements DisposeAble {
    static final float FRUSTUM_WIDTH = 10;
    static final float FRUSTUM_HEIGHT = 15;
    static final float PIXELS_TO_METRES = 1.0f / 32.0f;

    private ComponentMapper<TextureComponent> textureMapper;
    private ComponentMapper<TransformComponent> transformMapper = ComponentMapper.getFor(TransformComponent.class);
    private SpriteBatch batch;
    private Array<Entity> renderQueue;
    private ZComparator comparator;
    private OrthographicCamera camera;

    public RenderSystem() {
        super(Family.all(TextureComponent.class, TransformComponent.class).get());
        textureMapper = ComponentMapper.getFor(TextureComponent.class);
        transformMapper = ComponentMapper.getFor(TransformComponent.class);
        batch = new SpriteBatch();
        renderQueue = new Array<Entity>();
        comparator = new ZComparator();

        camera = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        camera.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        renderQueue.sort(comparator);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(.135f, .206f, .235f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        for (Entity entity :
                renderQueue) {

            TextureRegion tex = textureMapper.get(entity).getTexture();
            if (tex == null) {
                continue;
            }
            TransformComponent transform = transformMapper.get(entity);

            float width = tex.getRegionWidth();
            float height = tex.getRegionHeight();
            float originX = width * 0.5f;
            float originY = height * 0.5f;
            batch.draw(tex,
                    transform.getPosition().x - originX, transform.getPosition().y - originY,
                    originX, originY,
                    width, height,
                    transform.getScale().x * PIXELS_TO_METRES, transform.getScale().y * PIXELS_TO_METRES,
                    MathUtils.radiansToDegrees * transform.getRotation());
        }
        batch.end();
        renderQueue.clear();
    }

    @Override
    public void dispose(){
        batch.dispose();
    }

    public Camera getCamera(){
        return camera;
    }


    private static class ZComparator implements Comparator<Entity> {
        private ComponentMapper<TransformComponent> pm = ComponentMapper.getFor(TransformComponent.class);

        @Override
        public int compare(Entity e1, Entity e2) {
            return (int)Math.signum(pm.get(e1).getZ() - pm.get(e2).getZ());
        }
    }
}
