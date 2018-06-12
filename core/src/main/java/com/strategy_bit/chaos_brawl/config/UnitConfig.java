package com.strategy_bit.chaos_brawl.config;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class UnitConfig {


    //default values
    public static final String DEFAULT_NAME="NA";
    public static final float DEFAULT_COST=1f;
    public static final float DEFAULT_SPEED=1f;
    public static final float DEFAULT_ATTACK_RADIUS=1f;
    public static final float DEFAULT_ATTACK_SPEED=1f;
    public static final float DEFAULT_ATTACK_DAMAGE=1f;
    public static final float DEFAULT_HIT_POINTS=1f;
    public static final boolean DEFAULT_RANGED=false;
    public static final boolean DEFAULT_MOVEMENT_COMPONENT =false;
    public static final boolean DEFAULT_BOUNDARY_COMPONENT =false;
    public static final boolean DEFAULT_EXPLOSION_COMPONENT =false;
    public static final boolean DEFAULT_UPGRADE_COMPONENT =false;
    public static final int DEFAULT_RANGED_ATTACK_TYPE =0;

    //private fields
    private int id;
    private int unitType;
    private String name;
    private float cost;
    private float speed;
    private float attackRadius;
    private float attackSpeed;
    private float attackDamage;
    private float hitPoints;
    private int teamId;
    private int unitId;
    private Sound sound;

    private boolean ranged;
    private boolean hasMovementComponent;
    private boolean hasBoundaryComponent;
    private boolean hasExplosionComponent;
    private boolean hasUpgradeComponent;

    private int rangedAttackType;

    private TextureRegion skin;

    public void setId(int id) { this.id = id;}

    public int getId () { return id; }



    public UnitConfig() {
        setName(DEFAULT_NAME);
        setCost(DEFAULT_COST);
        setSpeed(DEFAULT_SPEED);
        setAttackRadius(DEFAULT_ATTACK_RADIUS);
        setAttackSpeed(DEFAULT_ATTACK_SPEED);
        setAttackDamage(DEFAULT_ATTACK_DAMAGE);
        setHitPoints(DEFAULT_HIT_POINTS);
        setRanged(DEFAULT_RANGED);
        setMovementComponent(DEFAULT_MOVEMENT_COMPONENT);
        setBoundaryComponent(DEFAULT_BOUNDARY_COMPONENT);
        setExplosionComponent(DEFAULT_EXPLOSION_COMPONENT);
        setUpgradeComponent(DEFAULT_UPGRADE_COMPONENT);
        setRangedAttackType(DEFAULT_RANGED_ATTACK_TYPE);
        setSkin(null);
    }

    public TextureRegion getSkin() {
        return skin;
    }

    public void setSkin(TextureRegion skin) {
        this.skin = skin;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getAttackRadius() {
        return attackRadius;
    }

    public void setAttackRadius(float attackRadius) {
        this.attackRadius = attackRadius;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(float attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public float getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(float attackDamage) {
        this.attackDamage = attackDamage;
    }

    public boolean isRanged() {
        return ranged;
    }

    public void setRanged(boolean ranged) {
        this.ranged = ranged;
    }

    public int isRangedAttackType() {
        return rangedAttackType;
    }

    public void setRangedAttackType(int rangedAttackType) {
        this.rangedAttackType = rangedAttackType;
    }

    public float getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(float hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public Sound getSound() {
        return sound;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public int getUnitType() {
        return unitType;
    }

    public void setUnitType(int uniType) {
        this.unitType = uniType;
    }

    public boolean hasMovementComponent() {
        return hasMovementComponent;
    }

    public void setMovementComponent(boolean hasMovementComponent) {
        this.hasMovementComponent = hasMovementComponent;
    }

    public boolean hasBoundaryComponent() {
        return hasBoundaryComponent;
    }

    public void setBoundaryComponent(boolean hasBoundaryComponent) {
        this.hasBoundaryComponent = hasBoundaryComponent;
    }

    public boolean hasExplosionComponent() {
        return hasExplosionComponent;
    }

    public void setExplosionComponent(boolean hasExplosionComponent) {
        this.hasExplosionComponent = hasExplosionComponent;
    }

    public boolean hasUpgradeComponent() {
        return hasUpgradeComponent;
    }

    public void setUpgradeComponent(boolean hasUpgradeComponent) {
        this.hasUpgradeComponent = hasUpgradeComponent;
    }
}
