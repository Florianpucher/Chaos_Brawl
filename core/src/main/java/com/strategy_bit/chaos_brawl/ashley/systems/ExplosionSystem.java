package com.strategy_bit.chaos_brawl.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.ashley.components.ExplosionComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.ashley.util.DisposeAble;
import com.strategy_bit.chaos_brawl.util.VectorMath;

public class ExplosionSystem extends IteratingSystem implements DisposeAble {

    private ComponentMapper<TransformComponent> transformMapper;
    private SpriteBatch batch;
    private Array<Entity> renderQueue;
    private RenderSystem.ZComparator comparator;
    private Camera camera;

    ParticleEffect effect;


    public ExplosionSystem(Camera camera) {
        super(Family.all(ExplosionComponent.class).get());
        this.camera = camera;
        //initialize transformMapper
        transformMapper = ComponentMapper.getFor(TransformComponent.class);

        //initialize additional used components
        batch = new SpriteBatch();
        renderQueue = new Array<>();
        comparator = new RenderSystem.ZComparator();

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // add entities to list
        renderQueue.add(entity);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        renderQueue.sort(comparator);
        //update camera
        camera.update();
        batch.setProjectionMatrix(camera.combined);


        batch.begin();
        for (Entity entity :
                renderQueue) {
            // draw all explosions, till all entities are done
            //TODO edit lines
            TransformComponent transform = transformMapper.get(entity);


            Vector3 position = new Vector3(transform.getPosition().x, transform.getPosition().y, 0.0f);

            // Get position of unit in screenCoordinates
            Vector2 screenPosition = VectorMath.vector3ToVector2(camera.project(position));

            //Setting the position of the ParticleEffect
            effect.setPosition(screenPosition.x, screenPosition.y);

        }
        batch.end();

        // explosions.iscomplete
        // remove from engine

        if (effect.isComplete()){

        // the engine in the world class?

        }
        //clear render queue
        renderQueue.clear();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
