package com.strategy_bit.chaos_brawl.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.components.CombatComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TeamGameObjectComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.ashley.engine.MyEngine;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.util.VectorMath;
import com.strategy_bit.chaos_brawl.world.World;
/*
 updated by Alisopp on 24.03.2018
  */
/**
 * Created by A_329_09 on 22/03/2018.
 */

public class CombatSystem extends IteratingSystem {
    private ComponentMapper<CombatComponent> mCombatComponent;
    private ComponentMapper<TransformComponent> mTransformComponent;
    private ComponentMapper<TeamGameObjectComponent> mTeamGameObjectComponentMapper;
    private World world;

    public CombatSystem() {
        super(Family.all(CombatComponent.class, TransformComponent.class, TeamGameObjectComponent.class).get());
        mCombatComponent = ComponentMapper.getFor(CombatComponent.class);
        mTransformComponent = ComponentMapper.getFor(TransformComponent.class);
        mTeamGameObjectComponentMapper = ComponentMapper.getFor(TeamGameObjectComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CombatComponent combatComponent=mCombatComponent.get(entity);
        TeamGameObjectComponent teamGameObjectComponent = mTeamGameObjectComponentMapper.get(entity);

        TransformComponent transformComponent=mTransformComponent.get(entity);
        double closest=combatComponent.getAttackRadius();
        TeamGameObjectComponent closestEnemy=null;

        Entity targetEnemy=null;
        ImmutableArray<Entity> unitsOnTheMap = getEngine().getEntitiesFor(Family.all(TeamGameObjectComponent.class).get());
        for (Entity enemy : unitsOnTheMap) {
            TeamGameObjectComponent eTeamGameObjectComponent= mTeamGameObjectComponentMapper.get(enemy);
            if(teamGameObjectComponent.getTeamId()!=eTeamGameObjectComponent.getTeamId())
            {
                Vector2 mPos=transformComponent.getPosition();
                TransformComponent eTransformComponent=mTransformComponent.get(enemy);
                Vector2 ePos=eTransformComponent.getPosition();
                double distance = VectorMath.distance(mPos, ePos);
                if(distance <= closest){
                    closest=distance;
                    closestEnemy=eTeamGameObjectComponent;
                    targetEnemy=enemy;
                }
            }
        }
        if(closestEnemy!=null) {
            combatComponent.setEngagedInCombat(true);
            attack(combatComponent, closestEnemy,transformComponent,targetEnemy);
        }
        else {
            combatComponent.setEngagedInCombat(false);
        }
    }


    private void attack(CombatComponent c1, TeamGameObjectComponent c2, TransformComponent t1, Entity targetEnemy){
        //TODO add here attack logic for different types
        if(c1.isRanged() && c1.isMage()){
            if(c1.attack()){
                //ready to fire
                AssetManager.getInstance().attackFireball.play(0.6f);
                world.createFireballWorldCoordinates(t1.getPosition(),world.getIdOfUnit(targetEnemy),(float) c1.getAttackDamage());

            }
        }else if(c1.isRanged()){
            if(c1.attack()){
                //ready to fire
                AssetManager.getInstance().attackBow.play(0.6f);
                world.createBulletWorldCoordinates(t1.getPosition(),world.getIdOfUnit(targetEnemy),(float) c1.getAttackDamage());

            }
        }else {
        if(c1.attack()){
            AssetManager.getInstance().attackSword.play(1f);
            c2.setHitPoints(c2.getHitPoints()-c1.getAttackDamage());
        }
        }
    }

    public void addWorld(World world){
        this.world=world;
    }
}
