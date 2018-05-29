package com.strategy_bit.chaos_brawl.player_input_output;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.util.Boundary;
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
    private boolean goIntoPause;

    public AiController(int teamID, InputHandler inputHandler, Boundary spawnArea) {
        super(teamID, inputHandler, spawnArea);
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
        try {
            while (isRunning) {
                int xFrom;
                int xTo;
                if (spawnArea.getLowerLeft().x < spawnArea.getLowerRight().x) {
                    xFrom = (int) spawnArea.getLowerLeft().x;
                    xTo = (int) spawnArea.getLowerRight().x;
                } else {
                    xTo = (int) spawnArea.getLowerLeft().x;
                    xFrom = (int) spawnArea.getLowerRight().x;
                }

                int x = MathUtils.random(xFrom, xTo);
                int y = (int) MathUtils.random(spawnArea.getLowerLeft().y, spawnArea.getUpperLeft().y);
                final Vector2 spawnPosition = new Vector2(x, y);
                Gdx.app.postRunnable(() -> {
                    if (spawnUnit(0)) {
                        inputHandler.createEntityScreenCoordinates(spawnPosition, 0, teamID);
                    }
                });


                Thread.sleep(3000);

                if (goIntoPause) {

                    lock.lock();
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
