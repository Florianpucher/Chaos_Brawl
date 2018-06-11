package com.strategy_bit.chaos_brawl.screens.game_screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.managers.ScreenManager;
import com.strategy_bit.chaos_brawl.player_input_output.AiController;
import com.strategy_bit.chaos_brawl.player_input_output.PawnController;
import com.strategy_bit.chaos_brawl.player_input_output.PlayerController;
import com.strategy_bit.chaos_brawl.screens.AbstractScreen;
import com.strategy_bit.chaos_brawl.screens.ScreenEnum;
import com.strategy_bit.chaos_brawl.world.World;

/**
 * gamescreen implementation
 * @author AIsopp
 * @version 1.0
 * @since 22.03.2018
 */

public class GameScreen extends AbstractScreen {

    protected World manager;
    protected PlayerController playerController;
    private int map;
    protected PawnController[] controllers;
    public GameScreen(int map) {
        this.map = map;
    }
    private static final String EXIT = "Exit Game";


    @Override
    public void buildStage() {
        //ImageButton button = new ImageButton(AssetManager.getInstance().defaultSkin);
        //button.getImage().setDrawable(new TextureRegionDrawable(AssetManager.getInstance().skins.get("archerSkin")));
        super.buildStage();
        initializeGame();
        final TextButton btnOptions = new TextButton(EXIT, assetManager.defaultSkin);
        btnOptions.setName(EXIT);

        final Table root = new Table(assetManager.defaultSkin);
        root.setFillParent(true);
        float height = Gdx.graphics.getHeight()/24f;
        root.top();
        root.right();
        root.add(btnOptions).width(Gdx.graphics.getWidth()/10f).height(height);
        //root.add(button).width(Gdx.graphics.getWidth()/10f).height(height);
        addActor(root);

        ClickListener listener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String name = event.getListenerActor().getName();
                if (name.equals(EXIT)){
                    ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
                }
                super.clicked(event, x, y);
            }
        };

        btnOptions.addListener(listener);
    }


    protected void initializeGame(){
        manager = new World(map,2);
        controllers = new PawnController[2];
        playerController = new PlayerController(0, manager, manager.createSpawnAreaForPlayer(0, 2), manager.getCamera());
        controllers[0] = playerController;
        AiController otherPlayerController = new AiController(1,manager, manager.createSpawnAreaForPlayer(1, 2), manager.getCamera());
        controllers[1] = otherPlayerController;
        manager.setPlayerController(0, playerController);

        manager.setPlayerController(1,otherPlayerController);

        manager.initializeGameForPlayers();
        setInitialTargets();
    }

    void setInitialTargets(){
        for (int i = 0; i < controllers.length; i++) {
            controllers[i].setCurrentTargetTeam((i + 1) % controllers.length);
        }
    }

    @Override
    public void show() {
        super.show();
        //TODO input needs to be changed
        // The order of adding the input processors plays an important role!!!
        playerController.addGameHUD(this);
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(playerController);
        Gdx.input.setInputProcessor(inputMultiplexer);
        for (PawnController controller :
             controllers) {
            if(controller instanceof AiController){
                ((AiController) controller).startAI();
            }
        }
    }

    @Override
    public void pause() {
        super.pause();
        for (PawnController controller :
                controllers) {
            if(controller instanceof AiController){
                ((AiController) controller).pauseAI();
            }
        }
    }


    @Override
    public void resume() {
        super.resume();
        for (PawnController controller :
                controllers) {
            if(controller instanceof AiController){
                ((AiController) controller).resumeAI();
            }
        }
    }

    @Override
    public void render(float delta) {
        playerController.render(delta);
        manager.render();
        super.render(delta);
    }

    @Override
    public void hide() {
        super.hide();
        for (PawnController controller :
                controllers) {
            if(controller instanceof AiController){
                ((AiController) controller).stopAI();
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        manager.dispose();
        playerController.dispose();
    }


}
