package com.strategy_bit.chaos_brawl.networking;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.BaseTest;
import com.strategy_bit.chaos_brawl.ChaosBrawlGame;
import com.strategy_bit.chaos_brawl.ashley.systems.RenderSystem;
import com.strategy_bit.chaos_brawl.managers.ScreenManager;
import com.strategy_bit.chaos_brawl.network.client.BrawlClientImpl;
import com.strategy_bit.chaos_brawl.network.messages.request.EntitySpawnMessage;
import com.strategy_bit.chaos_brawl.network.network_handlers.NetworkDiscoveryHandler;
import com.strategy_bit.chaos_brawl.network.server.BrawlServerImpl;
import com.strategy_bit.chaos_brawl.screens.ClientLobbyScreen;
import com.strategy_bit.chaos_brawl.screens.HostLobbyScreen;
import com.strategy_bit.chaos_brawl.screens.ScreenEnum;
import com.strategy_bit.chaos_brawl.types.UnitType;
import com.strategy_bit.chaos_brawl.world.MultiplayerInputHandler;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author AIsopp
 * @version 1.0
 * @since 21.04.2018
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RenderSystem.class, ScreenEnum.class})
public class NetworkingTest extends BaseTest {

    private BrawlClientImpl client;
    private BrawlClientImpl client2;
    private BrawlClientImpl client3;

    private BrawlServerImpl server;
    private ChaosBrawlGame game;

    private MultiplayerInputHandler inputHandler;

    @Before
    public void initialize() throws Exception {
        client = new BrawlClientImpl();
        client2 = new BrawlClientImpl();
        client3 = new BrawlClientImpl();
        server = new BrawlServerImpl();


        inputHandler = Mockito.mock(MultiplayerInputHandler.class);
        client.getBrawlConnector().setMultiplayerInputHandler(inputHandler);
        client2.getBrawlConnector().setMultiplayerInputHandler(inputHandler);
        client3.getBrawlConnector().setMultiplayerInputHandler(inputHandler);
        server.getBrawlConnector().setMultiplayerInputHandler(inputHandler);

        game = Mockito.mock(ChaosBrawlGame.class);

        ClientLobbyScreen clientLobbyScreen = Mockito.mock(ClientLobbyScreen.class);
        HostLobbyScreen hostLobbyScreen = Mockito.mock(HostLobbyScreen.class);
        Mockito.when(game.getScreen()).thenReturn(clientLobbyScreen);
        SpriteBatch spriteBatch = Mockito.mock(SpriteBatch.class);
        PowerMockito.whenNew(SpriteBatch.class).withNoArguments().thenReturn(spriteBatch);

        ScreenManager manager = ScreenManager.getInstance();
        manager.initialize(game);

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Mockito.when(game.getScreen()).thenReturn(hostLobbyScreen);
                return null;
            }
        }).when(game).setScreen(Mockito.any(HostLobbyScreen.class));

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Mockito.when(game.getScreen()).thenReturn(clientLobbyScreen);
                return null;
            }
        }).when(game).setScreen(Mockito.any(ClientLobbyScreen.class));
        server.startServer();
    }

    @After
    public void after() {
        server.closeServer();
    }

    @Test(timeout = 10000)
    public void testDiscovery() throws IOException {


        final CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        NetworkDiscoveryHandler handler = new NetworkDiscoveryHandler() {
            @Override
            public void receiveHosts(List<InetAddress> hostIPs) {
                if (hostIPs.size() > 0) {
                    cyclicBarrier.reset();
                }
            }
        };
        client.addNetworkDiscoveryHandler(handler);
        client.discoverServers();
        try {
            cyclicBarrier.await();
        } catch (InterruptedException ignored) {

        } catch (BrokenBarrierException ignored) {

        }

    }

    @Test(timeout = 10000)
    public void testConnect() throws IOException {
        client.connectToServer("127.0.0.1");
        Assert.assertEquals(1, server.getNetworkMembers().length);
        client.disconnect();
    }


    @Test(timeout = 10000)
    public void testSendSimpleMessage() throws IOException {
        client.connectToServer("127.0.0.1");
        EntitySpawnMessage spawnMessage = new EntitySpawnMessage(new Vector2(0, 0), 0, UnitType.SWORDFIGHTER, 0);
        client.sendData(spawnMessage);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Mockito.verify(inputHandler).createEntityWorldCoordinates(Mockito.any(Vector2.class), Mockito.eq(UnitType.SWORDFIGHTER), Mockito.eq(0));
        spawnMessage.entityTypeId = UnitType.MAINBUILDING;
        server.sendData(spawnMessage);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Mockito.verify(inputHandler).createEntityLocal(Mockito.any(Vector2.class), Mockito.eq(UnitType.MAINBUILDING), Mockito.eq(0), Mockito.eq(0L));

        client.disconnect();
    }

    @Test(timeout = 10000)
    public void testConnectMultipleClients() throws IOException {
        connectMultipleClients();
        Assert.assertEquals(3, server.getNetworkMembers().length);
        disconnectMultipleClients();
        Assert.assertEquals(0, server.getNetworkMembers().length);

    }


    @Test(timeout = 10000)
    public void testSendMultipleMessages() throws IOException {
        connectMultipleClients();
        EntitySpawnMessage spawnMessage = new EntitySpawnMessage(new Vector2(0, 0), 0, UnitType.SWORDFIGHTER, 0);
        server.sendData(spawnMessage);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Mockito.verify(inputHandler, Mockito.atLeast(3)).createEntityLocal(Mockito.any(Vector2.class), Mockito.eq(UnitType.SWORDFIGHTER), Mockito.eq(0), Mockito.eq(0L));
        spawnMessage.entityTypeId = UnitType.MAINBUILDING;
        client.sendData(spawnMessage);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Mockito.verify(inputHandler, Mockito.atLeast(1)).createEntityWorldCoordinates(Mockito.any(Vector2.class), Mockito.eq(UnitType.MAINBUILDING), Mockito.eq(0));

        disconnectMultipleClients();
    }
    //TODO add tests for every messages

/*--------------------------------------------------------------------------
--------------------------Helper Methods------------------------------------
 --------------------------------------------------------------------------*/

    private void connectMultipleClients() throws IOException {
        client.connectToServer("127.0.0.1");
        client2.connectToServer("127.0.0.1");
        client3.connectToServer("127.0.0.1");
    }

    private void disconnectMultipleClients() throws IOException {
        client.disconnect();
        client2.disconnect();
        client3.disconnect();
    }
}
