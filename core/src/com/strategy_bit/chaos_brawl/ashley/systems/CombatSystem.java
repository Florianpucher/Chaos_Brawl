package com.strategy_bit.chaos_brawl.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.strategy_bit.chaos_brawl.ashley.components.CombatComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.ashley.entity.PlayerClone;
import com.strategy_bit.chaos_brawl.ashley.entity.Projectile;
import com.strategy_bit.chaos_brawl.util.VectorMath;

/**
 * Created by A_329_09 on 22/03/2018.
 */

public class CombatSystem extends IteratingSystem {
    private ComponentMapper<CombatComponent> mCombatComponent;
    private ComponentMapper<TransformComponent> mTransformComponent;

    public CombatSystem() {
        super(Family.all(CombatComponent.class).get());
        mCombatComponent = ComponentMapper.getFor(CombatComponent.class);
        mTransformComponent = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CombatComponent combatComponent=mCombatComponent.get(entity);
        if(combatComponent.getHitPotins()<=0.0){
            getEngine().removeEntity(entity);
            PlayerClone playerClone=new PlayerClone(new Vector2((float) (Math.random()*20),(float) (Math.random()*10)));
            getEngine().addEntity(playerClone);
            return;
        }
        TransformComponent transformComponent=mTransformComponent.get(entity);
        double closest=combatComponent.getAttackRadius()+1.0;
        CombatComponent closestEnemy=null;
        TransformComponent closestEnemyPosition=null;
        for (Entity enemy : getEntities()) {
            CombatComponent eCombatComponent=mCombatComponent.get(enemy);
            if(combatComponent.getTeamId()!=eCombatComponent.getTeamId()) {
                Vector2 mPos=transformComponent.getPosition();
                TransformComponent eTransformComponent=mTransformComponent.get(enemy);
                Vector2 ePos=eTransformComponent.getPosition();
                double range=combatComponent.getAttackRadius();
                if(Math.abs(mPos.x-ePos.x)<range){
                    if(Math.abs(mPos.y-ePos.y)<range){
                        double dist=VectorMath.distance(mPos,ePos);
                        if(dist<range){
                            if (dist<closest){
                                closest=dist;
                                closestEnemy=eCombatComponent;
                                closestEnemyPosition=eTransformComponent;
                            }
                        }
                    }
                }
            }
        }
        if(closestEnemy!=null) {
            combatComponent.setEngagedInCombat(true);
            attack(combatComponent, closestEnemy,transformComponent,closestEnemyPosition);
        }
        else {
            combatComponent.setEngagedInCombat(false);
        }
    }

    private void attack(CombatComponent c1,CombatComponent c2,TransformComponent t1,TransformComponent t2){
        if(c1.attack()){
            c2.setHitPotins(c2.getHitPotins()-c1.getAttackDamage());
            Projectile projectile=new Projectile(t1.getPosition(),t2.getPosition());
            getEngine().addEntity(projectile);
        }
    }
}
