package com.strategy_bit.chaos_brawl.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.ashley.components.ExplosionComponent;
import com.strategy_bit.chaos_brawl.ashley.components.ParticleComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.ashley.util.DisposeAble;
import com.strategy_bit.chaos_brawl.util.VectorMath;

public class ExplosionSystem extends IteratingSystem implements DisposeAble {


    private ComponentMapper<ParticleComponent> particleMapper;
    private SpriteBatch batch;
    private Array<Entity> renderQueue;
    private Camera camera;

    public ExplosionSystem(Camera camera) {
        super(Family.all(ParticleComponent.class).get());
        this.camera = camera;
        //initialize transformMapper
        particleMapper = ComponentMapper.getFor(ParticleComponent.class);

        //initialize additional used components
        batch = new SpriteBatch();
        renderQueue = new Array<>();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // add entities to list
        renderQueue.add(entity);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        //update camera
        camera.update();
        batch.setProjectionMatrix(camera.combined);


        batch.begin();
        for (Entity entity :
                renderQueue) {

            ParticleComponent component = particleMapper.get(entity);
            component.draw(batch);


            if (component.isComplete()){
                getEngine().removeEntity(entity);
            }
        }
        batch.end();



        //clear render queue
        renderQueue.clear();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
