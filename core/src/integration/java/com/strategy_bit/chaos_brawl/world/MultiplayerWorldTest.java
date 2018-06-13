package com.strategy_bit.chaos_brawl.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Queue;
import com.strategy_bit.chaos_brawl.BaseTest;
import com.strategy_bit.chaos_brawl.ashley.components.MovementComponent;
import com.strategy_bit.chaos_brawl.ashley.components.ParticleComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TeamGameObjectComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.network.BrawlMultiplayer;
import com.strategy_bit.chaos_brawl.network.client.BrawlClientImpl;
import com.strategy_bit.chaos_brawl.network.server.BrawlServerImpl;
import com.strategy_bit.chaos_brawl.player_input_output.OtherPlayerController;
import com.strategy_bit.chaos_brawl.player_input_output.PawnController;
import com.strategy_bit.chaos_brawl.player_input_output.PlayerController;
import com.strategy_bit.chaos_brawl.util.SpawnArea;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.InitializationError;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static com.strategy_bit.chaos_brawl.config.WorldSettings.FRUSTUM_HEIGHT;
import static org.junit.Assert.assertEquals;

/**
 * @author AIsopp
 * @version 1.0
 * @since 10.05.2018
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ParticleComponent.class})
public class MultiplayerWorldTest extends BaseTest {
    private static final int PLAYERS = 4;

    private static final int UNITS_AFTER_INITIALIZATION = PLAYERS * 3;

    private MultiplayerWorld[] worlds;

    private PawnController[][] playersPerWorld;

    private BrawlMultiplayer[] brawlMultiplayer;


    @Before
    public void initialize() throws Exception {
        // world 0 is the world of the server
        worlds = new MultiplayerWorld[PLAYERS];
        brawlMultiplayer = new BrawlMultiplayer[PLAYERS];
        playersPerWorld = new PawnController[PLAYERS][PLAYERS];

        // initialize server
        BrawlServerImpl brawlServer = new BrawlServerImpl();
        MultiplayerWorld serverWorld = new MultiplayerWorld( brawlServer, PLAYERS,4,false);
        brawlServer.getBrawlConnector().setMultiplayerInputHandler(serverWorld);
        brawlMultiplayer[0] = brawlServer;
        worlds[0] = serverWorld;
        initializePlayersForWorld(0);
        brawlServer.startServer();

        // initialize clients
        for (int i = 1; i < PLAYERS; i++) {
            BrawlClientImpl brawlClient = new BrawlClientImpl();

            MultiplayerWorld clientWorld = new MultiplayerWorld(brawlClient, PLAYERS,4, false);
            brawlClient.getBrawlConnector().setMultiplayerInputHandler(clientWorld);
            brawlMultiplayer[i] = brawlClient;
            worlds[i] = clientWorld;
            initializePlayersForWorld(i);
            brawlClient.connectToServer("127.0.0.1");
        }
        Thread.sleep(500);
        serverWorld.initializeGameForPlayers();

        long currentTime = System.currentTimeMillis();

        for (MultiplayerWorld world :
                worlds) {
            while (world.units.size() < UNITS_AFTER_INITIALIZATION) {
                if (System.currentTimeMillis() - currentTime > 5000) {
                    throw new InitializationError(new Throwable("Could not initialize world for every player"));
                }
            }
        }
        for (MultiplayerWorld world :
                worlds) {
            world.getCamera().update();
        }
    }

    private void initializePlayersForWorld(int worldIndex) {
        MultiplayerWorld world = worlds[worldIndex];
        SpawnArea spawnArea = new SpawnArea(0, 0, 1, 1);
        for (int i = 0; i < PLAYERS; i++) {
            if (i == worldIndex) {
                PlayerController playerController = new PlayerController(i, world, spawnArea, world.getCamera());
                playersPerWorld[worldIndex][i] = playerController;
                world.setPlayerController(i, playerController);
            } else {
                OtherPlayerController otherPlayerController = new OtherPlayerController(i, world, spawnArea);
                playersPerWorld[worldIndex][i] = otherPlayerController;
                world.setPlayerController(i, otherPlayerController);
            }
        }
    }


    @After
    public void after() {
        for (int i = PLAYERS - 1; i >= 0; i--) {
            brawlMultiplayer[i].dispose();
            worlds[i].dispose();
        }
    }

    @Test
    public void testInitialization() {
        //TODO update test because at the moment just two bases are initialized
        // Every world should have 6 units after initialization
        for (MultiplayerWorld multiplayerWorld :
                worlds) {
            assertEquals(UNITS_AFTER_INITIALIZATION, multiplayerWorld.units.size());
        }
    }

    @Test
    public void testIncreaseResources() throws InterruptedException {
        // Resources should not be incremented by client worlds
        for (int i = 1; i < worlds.length; i++) {
            worlds[i].render();
            for (int j = 0; j < playersPerWorld[i].length; j++) {
                assertEquals(false, playersPerWorld[i][j].getMana().getResourceAmount() > 0);
            }
        }
        worlds[0].render();


        // Message should arrive in the next 2 seconds
        Thread.sleep(2000);
        for (PawnController[] aPlayersPerWorld : playersPerWorld) {
            for (PawnController anAPlayersPerWorld : aPlayersPerWorld) {
                assertEquals(true, anAPlayersPerWorld.getMana().getResourceAmount() > 0);
            }
        }
    }

    @Test
    public void testSpawnUnitOnClient() throws InterruptedException {
        for (int i = 1; i < worlds.length; i++) {

            worlds[i].createEntityWorldCoordinates(new Vector2(worlds[i].bases[i].getComponent(TransformComponent.class).getPosition()), 0, i);
        }
        // Message should arrive in the next 2 seconds
        Thread.sleep(2000);

        for (int i = 0; i < PLAYERS; i++) {
            assertEquals(UNITS_AFTER_INITIALIZATION + PLAYERS - 1, worlds[i].units.size());
        }
    }


    @Test
    public void testSpawnUnitOnServer() throws InterruptedException {
        int unitsToSpawn = 3;
        for (int i = 0; i < unitsToSpawn; i++) {
            worlds[0].createEntityWorldCoordinates(new Vector2(worlds[i].bases[i].getComponent(TransformComponent.class).getPosition()), 0, i % PLAYERS);
        }
        // Message should arrive in the next 2 seconds
        Thread.sleep(2000);
        for (int i = 0; i < PLAYERS; i++) {
            assertEquals(UNITS_AFTER_INITIALIZATION + unitsToSpawn, worlds[i].units.size());
        }
    }

    @Test
    public void testUnitDied() throws InterruptedException {
        worlds[0].createEntityWorldCoordinates(new Vector2(worlds[0].bases[0].getComponent(TransformComponent.class).getPosition()), 0, 0);
        Thread.sleep(2000);
        //Check if unit got spawned remotely
        for (int i = 0; i < PLAYERS; i++) {
            assertEquals(UNITS_AFTER_INITIALIZATION + 1, worlds[i].units.size());
        }
        Entity unitThatDied = worlds[0].units.get(worlds[0].lastID - 1);
        unitThatDied.getComponent(TeamGameObjectComponent.class).setHitPoints(0.0f);
        worlds[0].render();
        Thread.sleep(2000);
        // Now the inserted unit should be removed on every world
        for (int i = 0; i < PLAYERS; i++) {
            assertEquals(UNITS_AFTER_INITIALIZATION, worlds[i].units.size());
        }
    }

    @Test
    public void testUnitGotWayPoints() throws InterruptedException {
        worlds[0].createEntityWorldCoordinates(new Vector2(worlds[0].bases[0].getComponent(TransformComponent.class).getPosition()), 0, 0);
        Thread.sleep(2000);
        Entity unitThatMovesServer = worlds[0].units.get(worlds[0].lastID - 1);
        Queue<Vector2> targetPath = unitThatMovesServer.getComponent(MovementComponent.class).getPath();
        for (int i = 0; i < PLAYERS; i++) {
            Entity unitThatMoves = worlds[i].units.get(worlds[0].lastID - 1);
            Queue<Vector2> path = unitThatMoves.getComponent(MovementComponent.class).getPath();
            Assert.assertEquals(targetPath, path);
        }

    }

    @Test
    public void testWinningLosing() throws InterruptedException {

        for (int i = 0; i < PLAYERS - 1; i++) {
            worlds[0].bases[i].getComponent(TeamGameObjectComponent.class).setHitPoints(0.0f);
            worlds[0].render();
            Thread.sleep(500);
            for (int j = 0; j < PLAYERS; j++) {
                if (i == PLAYERS - 2) {
                    assertEquals(true, worlds[j].checkWinningLosing());
                    break;
                } else {
                    assertEquals(false, worlds[j].checkWinningLosing());
                }

            }
        }
    }

    @Test
    public void testPlayerChangesTarget() throws InterruptedException {
        for (int i = 0; i < PLAYERS; i++) {
            for (int j = 0; j < PLAYERS; j++) {
                playersPerWorld[i][j].setCurrentTargetTeam(-1);
            }
        }
        Thread.sleep(1000);

        for (int i = 0; i < PLAYERS; i++) {
            worlds[i].getCamera().update();
            Entity base = worlds[i].bases[(i + 1) % PLAYERS];
            TransformComponent transformComponent = base.getComponent(TransformComponent.class);
            Vector3 worldPosition = new Vector3(transformComponent.getPosition().x, FRUSTUM_HEIGHT - transformComponent.getPosition().y, 0);
            Vector3 screenPosition = worlds[i].getCamera().project(worldPosition);
            ((PlayerController) playersPerWorld[i][i]).touchDown((int) screenPosition.x, (int) screenPosition.y, 0, 0);
            Thread.sleep(1000);
            Assert.assertEquals((i + 1) % PLAYERS, playersPerWorld[0][i].getCurrentTargetTeam());

        }
    }
}
