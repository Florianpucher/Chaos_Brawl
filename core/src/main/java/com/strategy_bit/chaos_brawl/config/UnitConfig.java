package com.strategy_bit.chaos_brawl.config;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class UnitConfig {
    private String name;
    private float cost;
    private Vector2 position;
    private TextureRegion skin;
    private float speed;
    private float attackRadius;
    private float attackSpeed;
    private float attackDamage;
    private boolean ranged;
    private int rangedAttackType;
    private float hitPoints;
    private int teamId;
    private Sound sound;
    private boolean hasMovementComponent;
    private boolean hasBoundaryComponent;
    private boolean hasExplosionComponent;
    private boolean hasUpgradeComponent;

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
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
