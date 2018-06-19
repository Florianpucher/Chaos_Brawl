package com.strategy_bit.chaos_brawl.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.strategy_bit.chaos_brawl.BaseTest;
import com.strategy_bit.chaos_brawl.ashley.components.CombatComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TeamGameObjectComponent;
import com.strategy_bit.chaos_brawl.ashley.components.TransformComponent;
import com.strategy_bit.chaos_brawl.ashley.engine.MyEngine;
import com.strategy_bit.chaos_brawl.ashley.entities.Unit;
import com.strategy_bit.chaos_brawl.managers.AssetManager;
import com.strategy_bit.chaos_brawl.managers.UnitManager;
import com.strategy_bit.chaos_brawl.world.World;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class CombatSystemTest extends BaseTest {

    private CombatSystem combatSystem;
    private Array<Entity> attackers;
    private Array<Entity> victims;
    private Array<Integer> attackTypes;
    private Entity attacker;
    private Entity victim;
    private MyEngine engine;
    private World world;

    @Before
    public void initialize() {

        attackers = new Array<>();
        victims = new Array<>();
        attackTypes = new Array<>();

        combatSystem = new CombatSystem();
        world = Mockito.mock(World.class);

        engine = new MyEngine();
        engine.addSystem(combatSystem);
        combatSystem.addWorld(world);
        for (int i = 0; i < 6; i++) {
            attacker = new Entity();
            victim = new Entity();

            attackers.add(attacker);
            victims.add(victim);

            Unit.setComponents(attacker, UnitManager.getInstance().getUnitConfig(i), 0, new Vector2(i,i), engine);
            Unit.setComponents(victim, UnitManager.getInstance().getUnitConfig(i), 1, new Vector2(i,i), engine);
            engine.addEntity(attacker);
            engine.addEntity(victim);
            int attackType = -1;
            if (attacker.getComponent(CombatComponent.class).isRanged()) {
                attackType = attacker.getComponent(CombatComponent.class).isRangedAttackType();
            }
            attackTypes.add(attackType);
        }

    }




    @Test
    public void testCombat() {
        engine.update(10f);
        for (int i = 0; i < attackers.size; i++) {
            Entity victim = victims.get(i);
            Entity attacker = attackers.get(i);
            int attackResult = attackTypes.get(i);
            double maxHP = victim.getComponent(TeamGameObjectComponent.class).getMaxHP();
            double currentHP = victim.getComponent(TeamGameObjectComponent.class).getHitPoints();
            double attackerDamage = attacker.getComponent(CombatComponent.class).getAttackDamage();
            if (attackResult > 0) {
                Mockito.verify(world, Mockito.atLeast(1)).createBulletWorldCoordinates(
                        attacker.getComponent(TransformComponent.class).getPosition(),
                        0, (float) attacker.getComponent(CombatComponent.class).getAttackDamage(),
                        attackResult);
            } else {
                Assert.assertEquals(maxHP - attackerDamage, currentHP, 0);
            }
        }
    }


}
