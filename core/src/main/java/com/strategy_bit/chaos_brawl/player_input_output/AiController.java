package com.strategy_bit.chaos_brawl.player_input_output;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.util.SpawnArea;
import com.strategy_bit.chaos_brawl.world.InputHandler;

import java.util.HashSet;
import java.util.Set;
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
    private boolean goIntoPause;

    public AiController(int teamID, InputHandler inputHandler, SpawnArea spawnArea, Camera camera) {
        super(teamID, inputHandler, spawnArea, camera);
        isRunning = false;
        goIntoPause = false;
        lock = new ReentrantLock();

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
        isRunning = false;

    }

    @Override
    public void gameOver(boolean win) {
        isRunning = false;
    }

    @Override
    public void run() {
            while (isRunning) {
                float xFrom;
                float xTo;
                xFrom =  spawnArea.getX();
                xTo =  (spawnArea.getX() + spawnArea.getWidth());

                float x = MathUtils.random(xFrom, xTo);
                float y =  MathUtils.random(spawnArea.getY(), spawnArea.getY() + spawnArea.getHeight());
                final Vector2 spawnPosition = new Vector2(x, y);
                Set<Integer> set=new HashSet<>();
                for (int i = 0; i < 6; i++) {
                    set.add(i);
                }
                set.add(18);
                set.add(19);
                int unitId=(Integer) set.toArray()[(int)(Math.random()*set.size())];
                if (spawnUnit(unitId)) {
                Gdx.app.postRunnable(() -> {

                        inputHandler.createEntityWorldCoordinates(spawnPosition, unitId, teamID);

                });}




                if (goIntoPause) {

                    lock.lock();
                    lock.unlock();
                }
            }
    }
}
