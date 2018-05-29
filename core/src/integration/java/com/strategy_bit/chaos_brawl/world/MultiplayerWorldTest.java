package com.strategy_bit.chaos_brawl.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.strategy_bit.chaos_brawl.BaseTest;
import com.strategy_bit.chaos_brawl.ChaosBrawlGame;
import com.strategy_bit.chaos_brawl.ashley.components.MovementComponent;
import com.strategy_bit.chaos_brawl.ashley.components.ParticleComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TeamGameObjectComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.ashley.systems.ExplosionSystem;
import com.strategy_bit.chaos_brawl.ashley.systems.RenderSystem;
import com.strategy_bit.chaos_brawl.managers.ScreenManager;
import com.strategy_bit.chaos_brawl.network.BrawlMultiplayer;
import com.strategy_bit.chaos_brawl.network.client.BrawlClientImpl;
import com.strategy_bit.chaos_brawl.network.server.BrawlServerImpl;
import com.strategy_bit.chaos_brawl.pathfinder.OtherPathfinder;
import com.strategy_bit.chaos_brawl.player_input_output.OtherPlayerController;
import com.strategy_bit.chaos_brawl.player_input_output.PawnController;
import com.strategy_bit.chaos_brawl.player_input_output.PlayerController;
import com.strategy_bit.chaos_brawl.util.Boundary;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.InitializationError;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static com.strategy_bit.chaos_brawl.config.WorldSettings.FRUSTUM_HEIGHT;
import static com.strategy_bit.chaos_brawl.config.WorldSettings.FRUSTUM_WIDTH;
import static org.junit.Assert.assertEquals;

/**
 * @author AIsopp
 * @version 1.0
 * @since 10.05.2018
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MultiplayerWorld.class, ParticleComponent.class})
public class MultiplayerWorldTest extends BaseTest {
    //TODO make this test parameterized but for that you cannot use PowerMockRunner
    //TODO Change amount of players if more players are getting a base
    private static final int PLAYERS = 4;

    private static final int UNITS_AFTER_INITIALIZATION = PLAYERS * 3;

    private MultiplayerWorld[] worlds;

    private PawnController[][] playersPerWorld;

    private BrawlMultiplayer[] brawlMultiplayer;

    private Queue<Vector2> targetPath;

    private Camera camera;

    @Before
    public void initialize() throws Exception {
        // world 0 is the world of the server
        worlds = new MultiplayerWorld[PLAYERS];
        brawlMultiplayer = new BrawlMultiplayer[PLAYERS];
        playersPerWorld = new PawnController[PLAYERS][PLAYERS];
        RenderSystem renderSystem = Mockito.mock(RenderSystem.class);
        ExplosionSystem explosionSystem = Mockito.mock(ExplosionSystem.class);
        OtherPathfinder pathfinder = Mockito.mock(OtherPathfinder.class);

        camera = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        camera.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
        Mockito.when(renderSystem.getCamera()).thenReturn(camera);

        Array<Vector2> defaultPath = new Array<>();
        defaultPath.add(new Vector2());
        defaultPath.add(new Vector2(1, 1));
        Mockito.when(pathfinder.calculatePath(Mockito.any(Vector2.class), Mockito.any(Vector2.class))).thenReturn(defaultPath);
        targetPath = new Queue<>();
        targetPath.addFirst(defaultPath.get(0));
        targetPath.addFirst(defaultPath.get(1));

        PowerMockito.whenNew(ParticleEffect.class).withNoArguments().thenReturn(Mockito.mock(ParticleEffect.class));


        PowerMockito.whenNew(RenderSystem.class).withNoArguments().thenReturn(renderSystem);
        PowerMockito.whenNew(ExplosionSystem.class).withAnyArguments().thenReturn(explosionSystem);
        Board board = Mockito.mock(Board.class);
        Mockito.when(board.boardToMatrix()).thenReturn(new int[][]{
                {0, 0, 0},
                {1, 1, 1},
                {0, 1, 1}});
        Array<Float> positions = new Array<>();
        for (int i = 0; i < UNITS_AFTER_INITIALIZATION * 2; i++) {
            positions.add((float) i);
        }
        Mockito.when(board.getAsset(Mockito.anyInt())).thenReturn(positions);

        ChaosBrawlGame game = Mockito.mock(ChaosBrawlGame.class);
        SpriteBatch spriteBatch = Mockito.mock(SpriteBatch.class);
        PowerMockito.whenNew(SpriteBatch.class).withNoArguments().thenReturn(spriteBatch);
        PowerMockito.whenNew(Board.class).withAnyArguments().thenReturn(board);
        PowerMockito.whenNew(OtherPathfinder.class).withAnyArguments().thenReturn(pathfinder);
        ScreenManager manager = ScreenManager.getInstance();
        manager.initialize(game);

        // initialize server
        BrawlServerImpl brawlServer = new BrawlServerImpl();
        MultiplayerWorld serverWorld = new MultiplayerWorld(true, brawlServer, PLAYERS);

        brawlServer.getBrawlConnector().setMultiplayerInputHandler(serverWorld);
        brawlMultiplayer[0] = brawlServer;
        worlds[0] = serverWorld;
        initializePlayersForWorld(0);
        brawlServer.startServer();

        // initialize clients
        for (int i = 1; i < PLAYERS; i++) {
            BrawlClientImpl brawlClient = new BrawlClientImpl();

            MultiplayerWorld clientWorld = new MultiplayerWorld(false, brawlClient, PLAYERS);
            brawlClient.getBrawlConnector().setMultiplayerInputHandler(clientWorld);
            brawlMultiplayer[i] = brawlClient;
            worlds[i] = clientWorld;
            initializePlayersForWorld(i);
            brawlClient.connectToServer("127.0.0.1");
        }
        serverWorld.initializeGameForPlayers(1, PLAYERS);

        long currentTime = System.currentTimeMillis();

        for (MultiplayerWorld world :
                worlds) {
            while (world.units.size() < UNITS_AFTER_INITIALIZATION) {
                if (System.currentTimeMillis() - currentTime > 3000) {
                    throw new InitializationError(new Throwable("Could not initialize world for every player"));
                }
            }
        }
        camera.update();
    }

    private void initializePlayersForWorld(int worldIndex) {
        MultiplayerWorld world = worlds[worldIndex];
        Boundary spawnArea = new Boundary(new Vector2(0, 1), new Vector2(1, 1), new Vector2(0, 0), new Vector2(1, 0));
        for (int i = 0; i < PLAYERS; i++) {
            if (i == worldIndex) {
                PlayerController playerController = new PlayerController(i, world, spawnArea);
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
            worlds[i].createEntityWorldCoordinates(new Vector2(0, 0), 0, i);
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
            worlds[0].createEntityWorldCoordinates(new Vector2(0, 0), 0, i % PLAYERS);
        }
        // Message should arrive in the next 2 seconds
        Thread.sleep(2000);
        for (int i = 0; i < PLAYERS; i++) {
            assertEquals(UNITS_AFTER_INITIALIZATION + unitsToSpawn, worlds[i].units.size());
        }
    }

    @Test
    public void testUnitDied() throws InterruptedException {
        worlds[0].createEntityWorldCoordinates(new Vector2(0, 0), 0, 0);
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
        worlds[0].createEntityWorldCoordinates(new Vector2(0, 0), 0, 0);
        Thread.sleep(2000);
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
        camera.update();

        for (int i = 0; i < PLAYERS; i++) {
            Entity base = worlds[i].bases[(i + 1) % PLAYERS];
            TransformComponent transformComponent = base.getComponent(TransformComponent.class);
            Vector3 worldPosition = new Vector3(transformComponent.getPosition().x, FRUSTUM_HEIGHT - transformComponent.getPosition().y, 0);
            Vector3 screenPosition = camera.project(worldPosition);
            ((PlayerController) playersPerWorld[i][i]).touchDown((int) screenPosition.x, (int) screenPosition.y, 0, 0);
            Thread.sleep(1000);
            Assert.assertEquals((i + 1) % PLAYERS, playersPerWorld[0][i].getCurrentTargetTeam());

        }
    }
}
