package com.strategy_bit.chaos_brawl.ashley.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.components.BuildingComponent;
import com.strategy_bit.chaos_brawl.ashley.components.NewCombatComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TeamGameObjectComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TextureComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.managers.AssetManager;


public class Tower extends Entity {

    public Tower(){

        TextureComponent textureComponent = new TextureComponent();
        textureComponent.setTexture(AssetManager.getInstance().TowerSkin);
        NewCombatComponent newCombatComponent=new NewCombatComponent(100000.0,13,1,20,true);
        TeamGameObjectComponent teamGameObjectComponent = new TeamGameObjectComponent(30000.0,0);
        add(textureComponent);
        add(newCombatComponent);
        add(teamGameObjectComponent);
        add(new BuildingComponent(true));
    }

    /*
    private SpriteBatch batch;

    public void create () {
        batch = new SpriteBatch();
        towerSkin = new Texture("TowerSkin.png");
    }

    public void render () {
        batch.begin();
        batch.draw(towerSkin, Gdx.graphics.getWidth()/2 - towerSkin.getWidth()/2, Gdx.graphics.getHeight()/2 - towerSkin.getHeight()/2);
        batch.end();
    }
    */
}
