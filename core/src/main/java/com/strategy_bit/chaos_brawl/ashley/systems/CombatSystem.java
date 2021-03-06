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
import com.strategy_bit.chaos_brawl.managers.SoundManager;
import com.strategy_bit.chaos_brawl.util.VectorMath;
import com.strategy_bit.chaos_brawl.world.World;

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
        CombatComponent combatComponent = mCombatComponent.get(entity);
        TeamGameObjectComponent teamGameObjectComponent = mTeamGameObjectComponentMapper.get(entity);


        TransformComponent transformComponent = mTransformComponent.get(entity);
        double closest = combatComponent.getAttackRadius();
        TeamGameObjectComponent closestEnemy = null;

        Entity targetEnemy = null;
        ImmutableArray<Entity> unitsOnTheMap = getEngine().getEntitiesFor(Family.all(TeamGameObjectComponent.class).get());
        for (Entity enemy : unitsOnTheMap) {
            TeamGameObjectComponent eTeamGameObjectComponent = mTeamGameObjectComponentMapper.get(enemy);
            if (teamGameObjectComponent.getTeamId() != eTeamGameObjectComponent.getTeamId()) {
                Vector2 mPos = transformComponent.getPosition();
                TransformComponent eTransformComponent = mTransformComponent.get(enemy);
                Vector2 ePos = eTransformComponent.getPosition();
                double distance = VectorMath.distance(mPos, ePos);
                if (distance <= closest) {
                    closest = distance;
                    closestEnemy = eTeamGameObjectComponent;
                    targetEnemy = enemy;
                }
            }
        }
        if (closestEnemy != null) {
            combatComponent.setAttacking(true);
            CombatComponent enemyCombatComponent=targetEnemy.getComponent(CombatComponent.class);
            if (!enemyCombatComponent.isAttacking()) {
                enemyCombatComponent.setGetsAttacked(true);
                enemyCombatComponent.setAttacker(transformComponent.getPosition());
            }
            attack(combatComponent, closestEnemy, transformComponent, targetEnemy, teamGameObjectComponent);
        } else {
            combatComponent.setAttacking(false);
        }
    }


    private void attack(CombatComponent c1, TeamGameObjectComponent c2, TransformComponent t1, Entity targetEnemy, TeamGameObjectComponent h1) {
        if (c1.isRanged()) {
            for (int i = 13; i > 9; i--) {
                if (c1.isRangedAttackType() == i && c1.attack()) {
                        //ready to fire
                        world.createBulletWorldCoordinates(t1.getPosition(), world.getIdOfUnit(targetEnemy), (float) c1.getAttackDamage(), i);
                }
            }
        } else {
            if (c1.attack()) {
                if (c1.isRangedAttackType() == 99 && (h1.getHitPoints() <= h1.getMaxHP() / 2f)) {
                    SoundManager.getInstance().playSound("critHit");
                    c2.setHitPoints(c2.getHitPoints() - (c1.getAttackDamage() * 2f));
                } else {
                    SoundManager.getInstance().playSound("attackSword");
                    c2.setHitPoints(c2.getHitPoints() - c1.getAttackDamage());
                }

            }
        }
    }

    public void addWorld(World world) {
        this.world = world;
    }
}
