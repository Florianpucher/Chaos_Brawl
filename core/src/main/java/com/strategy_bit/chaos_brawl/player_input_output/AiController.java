package com.strategy_bit.chaos_brawl.player_input_output;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.config.UnitConfig;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.managers.UnitManager;
import com.strategy_bit.chaos_brawl.util.SpawnArea;
import com.strategy_bit.chaos_brawl.world.InputHandler;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author AIsopp
 * @version 1.0
 * @since 19.04.2018
 */
public class AiController extends PawnController implements Runnable{

    private boolean isRunning;
    private ReentrantLock lock;
    private ReentrantLock resource_lock;
    private boolean goIntoPause;
    private float current_cost;

    public AiController(int teamID, InputHandler inputHandler, SpawnArea spawnArea, Camera camera) {
        super(teamID, inputHandler, spawnArea, camera);
        isRunning = false;
        goIntoPause = false;
        lock = new ReentrantLock();
        resource_lock=new ReentrantLock();
        current_cost=0f;

    }




    public void startAI() {
        isRunning = true;
        Executor aiExecutor = Executors.newSingleThreadExecutor();
        aiExecutor.execute(this);
    }

    public void resumeAI() {
        goIntoPause = false;
        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }


    public void pauseAI() {
        lock.lock();
        goIntoPause = true;
    }

    public void stopAI() {
        goIntoPause = false;
        isRunning = false;
    }

    @Override
    public void gameOver(boolean win) {
        isRunning = false;
    }

    @Override
    public void run() {
        Array<Integer> set = new Array<>();
        for (int i = 0; i < 6; i++) {
            set.add(i);
        }
        set.add(18);
        set.add(19);
            while (isRunning) {


                float x = MathUtils.random(spawnArea.getX(),spawnArea.getX() + spawnArea.getWidth());
                float y =  MathUtils.random(spawnArea.getY(), spawnArea.getY() + spawnArea.getHeight());
                final Vector2 spawnPosition = new Vector2(x, y);

                int unitId= set.get(MathUtils.random(set.size - 1));
                while (!spawnUnit(unitId)) {

                }
                if(!isRunning)
                {
                    break;
                }
                Gdx.app.postRunnable(() -> {

                        inputHandler.createEntityWorldCoordinates(spawnPosition, unitId, teamID);

                });




                if (goIntoPause) {

                    lock.lock();
                    lock.unlock();
                }
            }
    }

    @Override
    public void tick(float deltaTime) {
        super.tick(deltaTime);
        if (resource_lock.isHeldByCurrentThread()&&gold.getResourceAmount()>=current_cost){
            resource_lock.unlock();
        }
    }

    @Override
    public boolean spawnUnit(int unitId) {
        UnitConfig unitConfig= UnitManager.getInstance().getUnitConfig(unitId);
        float cost= unitConfig.getCost();
        boolean paid = checkAndSubtract(cost);
        if (!paid){
            current_cost=cost;
            resource_lock.lock();
        }
        return paid;
    }
}
