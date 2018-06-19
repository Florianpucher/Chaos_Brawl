package com.strategy_bit.chaos_brawl.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    private static final String UNIT_ID = "unitId";
    private static final String UNIT_TYPE = "unitType";
    private static final String PREVIEW = "Preview";
    private static final String PREVIEW_IMAGE_NAME = "button_preview_name";
    private static final String PREVIEW_IMAGE_SKIN_PATH = "button_preview_skin";

    public void readFile(String file) {
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
        config.setId(unitConfig.getInt(ID));
        addName(unitConfig,config);
        addCost(unitConfig,config);
        addSound(unitConfig);
        addPreview(unitConfig,config);
        addTextureComponent(unitConfig,config);
        addMovementComponent(unitConfig,config);
        addCombatComponent(unitConfig,config);
        addTeamGameObjectComponent(unitConfig,config);
        addBoundaryComponent(unitConfig,config);
        addExplosionComponent(unitConfig,config);
        addUpgradeComponent(unitConfig,config);
        unitConfigHashMap.put(unitConfig.getInt(ID), config);
    }

    private void addPreview(JsonValue unitConfig, UnitConfig config) {
        if(unitConfig.has(PREVIEW)){
            JsonValue preview = unitConfig.get(PREVIEW);
            TextureRegion region = new TextureRegion(new Texture(Gdx.files.internal(preview.getString(PREVIEW_IMAGE_SKIN_PATH))));
            AssetManager.getInstance().skins.put(preview.getString(PREVIEW_IMAGE_NAME), region);
            config.setPreviewImage(region);
        }
    }

    private void addSound(JsonValue unitConfig) {
        if (unitConfig.has(SOUND_PATH) && unitConfig.has(SOUND_NAME)) {
            SoundManager.getInstance().addSound(unitConfig.getString(SOUND_NAME), unitConfig.getString(SOUND_PATH));
        }
    }

    private void addCost(JsonValue unitConfig, UnitConfig config) {
        if (unitConfig.has(COST)) {
            config.setCost(unitConfig.getFloat(COST));
        }
    }

    private void addName(JsonValue unitConfig, UnitConfig config) {
        if (unitConfig.has(NAME)) {
            config.setName(unitConfig.getString(NAME));
        }
    }

    private void addUpgradeComponent(JsonValue unitConfig, UnitConfig config) {
        if (unitConfig.has(UPGRADE_COMPONENT)) {
            config.setUpgradeComponent(true);
        }
    }

    private void addExplosionComponent(JsonValue unitConfig, UnitConfig config) {
        if (unitConfig.has(EXPLOSION_COMPONENT)) {
            config.setExplosionComponent(true);
        }
    }

    private void addBoundaryComponent(JsonValue unitConfig, UnitConfig config) {
        if (unitConfig.has(BOUNDARY_COMPONENT)) {
            config.setBoundaryComponent(true);
        }
    }

    private void addTextureComponent(JsonValue unitConfig, UnitConfig config) {
        if (unitConfig.has(TEXTURE_COMPONENT) && unitConfig.get(TEXTURE_COMPONENT).has(SKIN_PATH) && unitConfig.get(TEXTURE_COMPONENT).has(SKIN_NAME)) {
            AssetManager.getInstance().addSkin(unitConfig.get(TEXTURE_COMPONENT).getString(SKIN_NAME), unitConfig.get(TEXTURE_COMPONENT).getString(SKIN_PATH));
            config.setSkin(AssetManager.getInstance().skins.get(unitConfig.get(TEXTURE_COMPONENT).getString(SKIN_NAME)));
        }
    }

    private void addMovementComponent(JsonValue unitConfig, UnitConfig config) {
        if (unitConfig.has(MOVEMENT_COMPONENT)) {
            config.setMovementComponent(true);
            if (unitConfig.get(MOVEMENT_COMPONENT).has(SPEED)) {
                config.setSpeed(unitConfig.get(MOVEMENT_COMPONENT).getFloat(SPEED));
            }
        }
    }

    private void addTeamGameObjectComponent(JsonValue unitConfig, UnitConfig config) {
        if (unitConfig.has(TEAM_GAME_OBJECT_COMPONENT) && unitConfig.get(TEAM_GAME_OBJECT_COMPONENT).has(HIT_POINTS)) {
            config.setHitPoints(unitConfig.get(TEAM_GAME_OBJECT_COMPONENT).getFloat(HIT_POINTS));
            if (unitConfig.get(TEAM_GAME_OBJECT_COMPONENT).has(UNIT_TYPE)) {
                config.setUnitType(unitConfig.get(TEAM_GAME_OBJECT_COMPONENT).getInt(UNIT_TYPE));
            }
            if (unitConfig.get(TEAM_GAME_OBJECT_COMPONENT).has(UNIT_ID)) {
                config.setUnitId(unitConfig.get(TEAM_GAME_OBJECT_COMPONENT).getInt(UNIT_ID));
            }
        }
    }

    private void addCombatComponent(JsonValue unitConfig, UnitConfig config) {
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
    }
}

