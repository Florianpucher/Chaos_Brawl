package com.strategy_bit.chaos_brawl.player_input_output;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.network.BrawlMultiplayer;
import com.strategy_bit.chaos_brawl.types.UnitType;
import com.strategy_bit.chaos_brawl.util.Boundary;
import com.strategy_bit.chaos_brawl.world.InputHandler;

/**
 *
 * dummy class that tells us that this player plays on a remote device
 * @author AIsopp
 * @version 1.0
 * @since 19.04.2018
 */
public class OtherPlayerController extends PawnController {
    private BrawlMultiplayer brawlMultiplayer;
    public OtherPlayerController(int teamID, InputHandler inputHandler, Boundary spawnArea, BrawlMultiplayer brawlMultiplayer) {
        super(teamID, inputHandler, spawnArea);
        this.brawlMultiplayer = brawlMultiplayer;
    }


    @Override
    public void tick() {
        brawlMultiplayer.sendTick();
    }

    @Override
    public void notifyAboutAttacking(long unitID_Attacker, long unitID_Victim) {

    }

    @Override
    public void notifyAboutDeletingUnit(long unitID) {
        brawlMultiplayer.sendEntityDeleteMsg(unitID);
    }

    @Override
    public void notifyAboutMoving(long unitID, Array<Vector2> movingPositions) {
        brawlMultiplayer.sendEntityMovingMessage(unitID,movingPositions);
    }

    @Override
    public void notifyAboutSpawning(Vector2 worldPosition, UnitType unitType, int teamID) {
        brawlMultiplayer.sendEntitySpawnMsg(worldPosition, unitType,teamID);
    }
}
