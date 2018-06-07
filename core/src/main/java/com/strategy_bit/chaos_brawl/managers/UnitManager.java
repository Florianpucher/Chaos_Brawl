package com.strategy_bit.chaos_brawl.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.strategy_bit.chaos_brawl.config.UnitConfig;

import java.util.HashMap;
import java.util.Map;

public class UnitManager {
    public Map<Integer, UnitConfig> unitConfigHashMap;

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String COST = "cost";
    private static final String MOVEMENT_COMPONENT = "MovementComponent";
    private static final String COMBAT_COMPONENT = "CombatComponent";
    private static final String TEXTURE_COMPONENT = "TextureComponent";
    private static final String TEAM_GAME_OBJECT_COMPONENT = "TeamGameObjectComponent";
    private static final String BOUNDARY_COMPONENT = "BoundaryComponent";
    private static final String EXPLOSION_COMPONENT = "ExplosionComponent";
    private static final String UPGRADE_COMPONENT = "UpgradeComponent";
    private static final String SKIN_NAME = "skinName";
    private static final String SKIN_PATH = "skinPath";
    private static final String SPEED = "speed";
    private static final String ATTACK_RADIUS = "attackRadius";
    private static final String ATTACK_SPEED = "attackSpeed";
    private static final String ATTACK_DAMAGE = "attackDamage";
    private static final String RANGED = "ranged";
    private static final String RANGED_ATTACK_TYPE = "rangedAttackType";
    private static final String HIT_POINTS = "hitPoints";
    private static final String SOUND_PATH = "soundPath";
    private static final String SOUND_NAME = "soundName";

    public void readFile(String file) {
        //TODO Hellmuth reduce cognitive complexity of method
        unitConfigHashMap = new HashMap<>();
        FileHandle fileHandle = Gdx.files.internal(file);
        JsonReader jsonReader = new JsonReader();
        JsonValue jsonValue = jsonReader.parse(fileHandle);
        JsonValue.JsonIterator iterator = jsonValue.iterator();
        while (iterator.hasNext()) {
            JsonValue unitConfig = iterator.next();
            addToConfigs(unitConfig);
        }
    }

    private void addToConfigs(JsonValue unitConfig) {
        if (!unitConfig.has(ID)) return;
        UnitConfig config = new UnitConfig();
        if (unitConfig.has(NAME)) {
            config.setName(unitConfig.getString(NAME));
        }
        if (unitConfig.has(COST)) {
            config.setCost(unitConfig.getFloat(COST));
        }
        if (unitConfig.has(SOUND_PATH) && unitConfig.has(SOUND_NAME)) {
            AssetManager.getInstance().addSound(unitConfig.getString(SOUND_NAME), unitConfig.getString(SOUND_PATH));
            config.setSound(AssetManager.getInstance().sounds.get(unitConfig.getString(SOUND_NAME)));
        }
        if (unitConfig.has(TEXTURE_COMPONENT)) {
            if (unitConfig.get(TEXTURE_COMPONENT).has(SKIN_PATH) && unitConfig.get(TEXTURE_COMPONENT).has(SKIN_NAME)) {
                AssetManager.getInstance().addSkin(unitConfig.get(TEXTURE_COMPONENT).getString(SKIN_NAME), unitConfig.get(TEXTURE_COMPONENT).getString(SKIN_PATH));
                config.setSkin(AssetManager.getInstance().skins.get(unitConfig.get(TEXTURE_COMPONENT).getString(SKIN_NAME)));
            }
        }
        if (unitConfig.has(MOVEMENT_COMPONENT)) {
            config.setMovementComponent(true);
            if (unitConfig.get(MOVEMENT_COMPONENT).has(SPEED)) {
                config.setSpeed(unitConfig.get(MOVEMENT_COMPONENT).getFloat(SPEED));
            }
        }
        if (unitConfig.has(COMBAT_COMPONENT)) {
            if (unitConfig.get(COMBAT_COMPONENT).has(ATTACK_RADIUS)) {
                config.setAttackRadius(unitConfig.get(COMBAT_COMPONENT).getFloat(ATTACK_RADIUS));
            }
            if (unitConfig.get(COMBAT_COMPONENT).has(ATTACK_SPEED)) {
                config.setAttackSpeed(unitConfig.get(COMBAT_COMPONENT).getFloat(ATTACK_SPEED));
            }
            if (unitConfig.get(COMBAT_COMPONENT).has(ATTACK_DAMAGE)) {
                config.setAttackDamage(unitConfig.get(COMBAT_COMPONENT).getFloat(ATTACK_DAMAGE));
            }
            if (unitConfig.get(COMBAT_COMPONENT).has(RANGED)) {
                config.setRanged(unitConfig.get(COMBAT_COMPONENT).getBoolean(RANGED));
            }
            if (unitConfig.get(COMBAT_COMPONENT).has(RANGED_ATTACK_TYPE)) {
                config.setRangedAttackType(unitConfig.get(COMBAT_COMPONENT).getInt(RANGED_ATTACK_TYPE));
            }
        }
        if (unitConfig.has(TEAM_GAME_OBJECT_COMPONENT)) {
            if (unitConfig.get(TEAM_GAME_OBJECT_COMPONENT).has(HIT_POINTS)) {
                config.setHitPoints(unitConfig.get(TEAM_GAME_OBJECT_COMPONENT).getFloat(HIT_POINTS));
            }
        }
        if (unitConfig.has(BOUNDARY_COMPONENT)) {
            config.setBoundaryComponent(true);
        }
        if (unitConfig.has(EXPLOSION_COMPONENT)) {
            config.setExplosionComponent(true);
        }
        if (unitConfig.has(UPGRADE_COMPONENT)) {
            config.setUpgradeComponent(true);
        }
        unitConfigHashMap.put(unitConfig.getInt(ID), config);
    }
}

