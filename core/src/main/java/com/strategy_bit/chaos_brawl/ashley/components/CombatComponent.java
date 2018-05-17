package com.strategy_bit.chaos_brawl.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class CombatComponent implements Component,Pool.Poolable {
    private static final double MAX_ATTACK_SPEED=2.5;
    private double attackRadius;
    /**
     * Attacks per second
     */
    private double attackSpeed;
    private double attackDamage;
    private long lastAttackTimeStamp;
    private boolean isRanged;
    private boolean isEngagedInCombat;

    public CombatComponent(double radius, double attackRadius, double attackSpeed, double attackDamage, boolean ranged) {
        setAttackDamage(attackDamage);
        setAttackRadius(attackRadius);
        setAttackSpeed(attackSpeed);
        setRanged(ranged);
        setEngagedInCombat(false);
        lastAttackTimeStamp=System.currentTimeMillis()- millisBetweenAttacks();
    }
    public void setRadiusAndAttackRadiusAndAttackSpeedAndAttackDamageAndRanged(double radius, double attackRadius, double attackSpeed, double attackDamage, boolean ranged) {
        setAttackDamage(attackDamage);
        setAttackRadius(attackRadius);
        setAttackSpeed(attackSpeed);
        setRanged(ranged);
        setEngagedInCombat(false);
        lastAttackTimeStamp=System.currentTimeMillis()- millisBetweenAttacks();
    }

    public double getAttackRadius() {
        return attackRadius;
    }

    public void setAttackRadius(double attackRadius) {
        this.attackRadius = attackRadius;
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(double attackSpeed) {
        if(attackSpeed>MAX_ATTACK_SPEED){
            this.attackSpeed=MAX_ATTACK_SPEED;
        } else {
            this.attackSpeed = attackSpeed;
        }
    }

    public double getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(double attackDamage) {
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

    public CombatComponent() {
        attackRadius=0;
        attackSpeed=0;
        attackDamage=0;
        lastAttackTimeStamp=0;
        isRanged=false;
        isEngagedInCombat=false;
    }

    @Override
    public void reset() {
        attackRadius=0;
        attackSpeed=0;
        attackDamage=0;
        lastAttackTimeStamp=0;
        isRanged=false;
        isEngagedInCombat=false;
    }
}
