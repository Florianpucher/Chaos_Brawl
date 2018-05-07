package com.strategy_bit.chaos_brawl.networking;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.strategy_bit.chaos_brawl.BaseTest;
import com.strategy_bit.chaos_brawl.ChaosBrawlGame;
import com.strategy_bit.chaos_brawl.ashley.systems.RenderSystem;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.managers.ScreenManager;
import com.strategy_bit.chaos_brawl.network.client.BrawlClientImpl;
import com.strategy_bit.chaos_brawl.network.client.BrawlClientListener;
import com.strategy_bit.chaos_brawl.network.messages.request.EntitySpawnMessage;
import com.strategy_bit.chaos_brawl.network.messages.response.NetworkMemberResponseMessage;
import com.strategy_bit.chaos_brawl.network.network_handlers.NetworkDiscoveryHandler;
import com.strategy_bit.chaos_brawl.network.server.BrawlServerImpl;
import com.strategy_bit.chaos_brawl.network.server.BrawlServerListener;
import com.strategy_bit.chaos_brawl.screens.ScreenEnum;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;

/**
 * @author AIsopp
 * @version 1.0
 * @since 21.04.2018
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RenderSystem.class, ScreenEnum.class})
public class NetworkingIntegrationTest extends BaseTest{

    private BrawlClientImpl client;
    private BrawlServerImpl server;
    private ChaosBrawlGame game;

    @Before
    public void initialize() throws Exception {
        client = new BrawlClientImpl();
        server = new BrawlServerImpl();

        //game = new ChaosBrawlGame();
        SpriteBatch spriteBatch = Mockito.mock(SpriteBatch.class);
        PowerMockito.whenNew(SpriteBatch.class).withNoArguments().thenReturn(spriteBatch);

        AssetManager assetManager = AssetManager.getInstance();
        //assetManager.loadAssets();
        //ScreenManager manager = ScreenManager.getInstance();
        //manager.initialize(game);
    }

    @After
    public void after(){
        AssetManager assetManager = AssetManager.getInstance();
        //assetManager.dispose();
        server.closeServer();
    }

    @Test(timeout = 10000)
    public void testDiscovery() throws IOException {
        server.startServer();



        /*final CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        NetworkDiscoveryHandler handler = new NetworkDiscoveryHandler() {
            @Override
            public void receiveHosts(List<InetAddress> hostIPs) {
                if(hostIPs.size() > 0){
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

        }*/

    }

    @Test
    public void testConnect() throws IOException {
        server.startServer();
        //client.connectToServer("127.0.0.1");
        //Assert.assertEquals(1,server.getNetworkMembers().length);
        //client.disconnect();
        //server.closeServer();
    }


    @Test
    public void testSendSimpleMessage() throws IOException {
        server.startServer();
        //client.connectToServer("127.0.0.1");
        //EntitySpawnMessage spawnMessage = new EntitySpawnMessage();
        //client.sendData(new EntitySpawnMessage());





        //client.disconnect();
        //server.closeServer();
    }


    @Test
    public void testWorld() throws IOException {
        server.startServer();
        client.connectToServer("127.0.0.1");
        BrawlClientListener listener = new BrawlClientListener( client);
        BrawlServerListener brawlServerListener = new BrawlServerListener(server);

        //MultiplayerWorld worldClient = new MultiplayerWorld(client);
        //MultiplayerWorld worldServer = new MultiplayerWorld(server);
        client.disconnect();
        server.closeServer();

    }

    @Test
    public void testNetworkMemberMessage() throws IOException {
        server.startServer();
        client.connectToServer("127.0.0.1");
        //NetworkMembersRequestMessage networkMembersRequestMessage = new NetworkMembersRequestMessage();
        //client.sendData(networkMembersRequestMessage);
        server.sendData(new NetworkMemberResponseMessage(server.getNetworkMembers()));

        client.disconnect();
        server.closeServer();
    }

}
