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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.ashley.components.TeamGameObjectComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TextureComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.ashley.util.DisposeAble;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.util.VectorMath;

import java.util.Comparator;

import static com.strategy_bit.chaos_brawl.config.WorldSettings.FRUSTUM_HEIGHT;
import static com.strategy_bit.chaos_brawl.config.WorldSettings.FRUSTUM_WIDTH;
import static com.strategy_bit.chaos_brawl.config.WorldSettings.PIXELS_TO_METRES;

/**
 * System for rendering images/sprites
 * <br>
 * handled components
 * <ul>
 * <li>TextureComponent</li>
 * <li>TransformComponent</li>
 * </ul>
 *
 * @author AIsopp
 * @version 1.0
 * @since 15.03.2018
 */
public class RenderSystem extends IteratingSystem implements DisposeAble {

    private ComponentMapper<TextureComponent> textureMapper;
    private ComponentMapper<TransformComponent> transformMapper;
    private ComponentMapper<TeamGameObjectComponent> teamGameObjectMapper;
    private SpriteBatch batch;
    private Array<Entity> renderQueue;
    private ZComparator comparator;
    private OrthographicCamera camera;

    private Stage hpBarStage;

    public RenderSystem() {
        //set used entities by components
        super(Family.all(TextureComponent.class, TransformComponent.class).get());
        //initialize component mapper
        textureMapper = ComponentMapper.getFor(TextureComponent.class);
        transformMapper = ComponentMapper.getFor(TransformComponent.class);
        teamGameObjectMapper = ComponentMapper.getFor(TeamGameObjectComponent.class);
        //initialize additional used components
        batch = new SpriteBatch();
        renderQueue = new Array<Entity>();
        comparator = new ZComparator();
        hpBarStage = new Stage();
        //initialize camera with size
        camera = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        camera.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // add entity with transform and texture component to render queue
        renderQueue.add(entity);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);


        //Stage stage = new Stage();
        //sort entity by z-index
        //entities with lower z-index will be rendered before entities with higher z-index
        renderQueue.sort(comparator);
        //update camera
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        hpBarStage.clear();
        //draw over old scene
        Gdx.gl.glClearColor(.135f, .206f, .235f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //begin new draw
        batch.begin();
        for (Entity entity :
                renderQueue) {

            TextureRegion tex = textureMapper.get(entity).getTexture();
            // if an entity has a texture component but there is no texture set skip it
            if (tex == null) {
                continue;
            }
            TransformComponent transform = transformMapper.get(entity);
            //calculate real draw size
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

            TeamGameObjectComponent unitHP = teamGameObjectMapper.get(entity);
            if (unitHP != null) {
                ProgressBar hpBar = new ProgressBar(0, 140, 1, false, AssetManager.getInstance().progressHPbarStyle);
                hpBar.setValue((float) (unitHP.getHitPoints() / unitHP.getMaxHP() * hpBar.getWidth()));


                Vector3 position = new Vector3(transform.getPosition().x ,transform.getPosition().y ,0.0f);
                // Get position of unit in screenCoordinates
                Vector2 screenPosition = VectorMath.vector3ToVector2(camera.project(position));
                // Set position of hp bar above unit
                hpBar.setPosition((screenPosition.x - width/4)-(width/2), (screenPosition.y + height/1.5f)+50);
                hpBar.setSize(width*1.5f, 1);
                hpBarStage.addActor(hpBar);
            }
        }

        batch.end();
        hpBarStage.draw();
        //hpBarStage.act();

        //clear render queue
        renderQueue.clear();
    }

    @Override
    public void dispose() {
        batch.dispose();
        hpBarStage.dispose();
    }

    /**
     * @return the used camera
     */
    public Camera getCamera() {
        return camera;
    }

    /**
     * comparator for sorting entities by z-index
     */
    private static class ZComparator implements Comparator<Entity> {
        private ComponentMapper<TransformComponent> pm = ComponentMapper.getFor(TransformComponent.class);

        @Override
        public int compare(Entity e1, Entity e2) {
            return (int) Math.signum(pm.get(e1).getZ() - pm.get(e2).getZ());
        }
    }
}