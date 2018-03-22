package managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * manger for holding references to assets
 *
 * @author AIsopp
 * @version 1.0
 * @since 22.03.2018
 */

public class AssetManager {


    public  Skin defaultSkin;
    public TextureRegion playerSkin;


    private static AssetManager instance;


    public static AssetManager getInstance(){
        if(instance == null){
            instance = new AssetManager();
        }
        return instance;
    }

    private AssetManager(){

    }

    public  void loadAssets(){
        defaultSkin = new Skin(Gdx.files.internal("default/skin.json"));
        playerSkin = new TextureRegion(new Texture("character.png"));
    }


    public void dispose(){
        defaultSkin.dispose();
        playerSkin.getTexture().dispose();
    }
}
