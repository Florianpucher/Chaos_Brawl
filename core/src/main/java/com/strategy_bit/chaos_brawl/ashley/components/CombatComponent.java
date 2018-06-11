package com.strategy_bit.chaos_brawl.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class CombatComponent implements Component,Pool.Poolable {
    private static final float MAX_ATTACK_SPEED=2.5f;
    private float attackRadius;
    /**
     * Attacks per second
     */
    private float attackSpeed;
    private float attackDamage;
    private long lastAttackTimeStamp;
    private boolean isRanged;
    private int isRangedAttackType;
    private boolean isEngagedInCombat;

    public void setEverything(float attackRadius, float attackSpeed, float attackDamage, boolean ranged, int rangedAttackType) {
        setAttackDamage(attackDamage);
        setAttackRadius(attackRadius);
        setAttackSpeed(attackSpeed);
        setRanged(ranged);
        setEngagedInCombat(false);
        setRangedAttackType(rangedAttackType);
        lastAttackTimeStamp=System.currentTimeMillis()- millisBetweenAttacks();
    }

    public double getAttackRadius() {
        return attackRadius;
    }

    public void setAttackRadius(float attackRadius) {
        this.attackRadius = attackRadius;
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(float attackSpeed) {
        if(attackSpeed>MAX_ATTACK_SPEED){
            this.attackSpeed=MAX_ATTACK_SPEED;
        } else {
            this.attackSpeed = attackSpeed;
        }
    }

    public double getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(float attackDamage) {
        this.attackDamage = attackDamage;
    }

    public boolean isEngagedInCombat() {
        return isEngagedInCombat;
    }

    public void setEngagedInCombat(boolean engagedInCombat) {
        isEngagedInCombat = engagedInCombat;
    }

    private long millisBetweenAttacks(){
        return (long)((1.0/attackSpeed)*1000.0);
    }

    public boolean attack(){
        if (System.currentTimeMillis()-lastAttackTimeStamp> millisBetweenAttacks()){
            lastAttackTimeStamp=System.currentTimeMillis();
            return true;
        }else{
            return false;
        }
    }

    public boolean isRanged() {
        return isRanged;
    }

    public void setRanged(boolean ranged) {
        isRanged = ranged;
    }


    public int isRangedAttackType() {
        return isRangedAttackType;
    }

    public void setRangedAttackType(int rangedAttackType) {
        isRangedAttackType = rangedAttackType;
    }

    public CombatComponent() {
        attackRadius = 0;
        attackSpeed = 0;
        attackDamage = 0;
        lastAttackTimeStamp = 0;
        isRanged = false;
        isRangedAttackType = 0;
        isEngagedInCombat = false;
    }

    @Override
    public void reset() {
        attackRadius = 0;
        attackSpeed = 0;
        attackDamage = 0;
        lastAttackTimeStamp = 0;
        isRanged = false;
        isRangedAttackType = 0;
        isEngagedInCombat = false;
    }
}
