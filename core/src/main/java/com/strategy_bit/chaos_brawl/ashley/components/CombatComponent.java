package com.strategy_bit.chaos_brawl.ashley.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by A_329_09 on 22/03/2018.
 */

public class CombatComponent implements Component {
    private static final double MAX_ATTACK_SPEED=2.5;
    private double hitPoints;
    private double attackRadius;
    /**
     * Attacks per second
     */
    private double attackSpeed;
    private double attackDamage;
    private int teamId;
    private long lastAttackTimeStamp;
    private boolean isRanged;
    private boolean isEngagedInCombat;

    public CombatComponent(double hitPoints, double attackRadius, double attackSpeed, double attackDamage, int teamId, boolean ranged) {
        setHitPoints(hitPoints);
        setAttackDamage(attackDamage);
        setAttackRadius(attackRadius);
        setAttackSpeed(attackSpeed);
        setTeamId(teamId);
        setRanged(ranged);
        setEngagedInCombat(false);
        lastAttackTimeStamp=System.currentTimeMillis()- millisBetweenAttacks();
    }

    public double getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(double hitPoints) {
        this.hitPoints = hitPoints;
        System.out.println("HitPoints: "+ hitPoints);
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
        }else {
            this.attackSpeed = attackSpeed;
        }
    }

    public double getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(double attackDamage) {
        this.attackDamage = attackDamage;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
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
}
