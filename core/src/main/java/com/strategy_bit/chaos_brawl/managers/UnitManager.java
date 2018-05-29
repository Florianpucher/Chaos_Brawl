package com.strategy_bit.chaos_brawl.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.strategy_bit.chaos_brawl.config.UnitConfig;

import java.util.HashMap;

public class UnitManager {
    public HashMap<Integer, UnitConfig> unitConfigHashMap;

    public void readFile(String file) {
        unitConfigHashMap = new HashMap<>();
        FileHandle fileHandle = Gdx.files.internal(file);
        JsonReader jsonReader = new JsonReader();
        JsonValue jsonValue = jsonReader.parse(fileHandle);
        JsonValue.JsonIterator iterator = jsonValue.iterator();
        while (iterator.hasNext()) {
            JsonValue unitConfig = iterator.next();
            if (unitConfig.has("id")) {
                UnitConfig config = new UnitConfig();
                if (unitConfig.has("name")){
                    config.setName(unitConfig.getString("name"));
                }
                if (unitConfig.has("cost")){
                    config.setCost(unitConfig.getFloat("cost"));
                }
                if (unitConfig.has("TransformComponent")){
                    if (unitConfig.get("TransformComponent").has("position")){
                        config.setPosition(new Vector2(unitConfig.get("TransformComponent").get("position").getFloat("x"),unitConfig.get("TransformComponent").get("position").getFloat("y")));
                    }else {
                        config.setPosition(new Vector2(0,0));
                    }
                }
                if (unitConfig.has("TextureComponent")){
                    if (unitConfig.get("TextureComponent").has("skinPath")&&unitConfig.get("TextureComponent").has("skinName")){
                        AssetManager.getInstance().addSkin(unitConfig.get("TextureComponent").getString("skinName"),unitConfig.get("TextureComponent").getString("skinPath"));
                        config.setSkin(AssetManager.getInstance().skins.get(unitConfig.get("TextureComponent").getString("skinName")));
                    }
                }
                if (unitConfig.has("MovementComponent")){
                    config.setMovementComponent(true);
                    if (unitConfig.get("MovementComponent").has("speed")){
                        config.setSpeed(unitConfig.get("MovementComponent").getFloat("speed"));
                    }
                }else {
                    config.setMovementComponent(false);
                }
                if (unitConfig.has("CombatComponent")){
                    if (unitConfig.get("CombatComponent").has("attackRadius")){
                        config.setAttackRadius(unitConfig.get("CombatComponent").getFloat("attackRadius"));
                    }
                    if (unitConfig.get("CombatComponent").has("attackSpeed")){
                        config.setAttackSpeed(unitConfig.get("CombatComponent").getFloat("attackSpeed"));
                    }
                    if (unitConfig.get("CombatComponent").has("attackDamage")){
                        config.setAttackDamage(unitConfig.get("CombatComponent").getFloat("attackDamage"));
                    }
                    if (unitConfig.get("CombatComponent").has("ranged")){
                        config.setRanged(unitConfig.get("CombatComponent").getBoolean("ranged"));
                    }else {
                        config.setRanged(false);
                    }
                    if (unitConfig.get("CombatComponent").has("mage")){
                        config.setMage(unitConfig.get("CombatComponent").getBoolean("mage"));
                    }else {
                        config.setMage(false);
                    }
                }
                if (unitConfig.has("TeamGameObjectComponent")){
                    if (unitConfig.get("TeamGameObjectComponent").has("hitPoints")){
                        config.setHitPoints(unitConfig.get("TeamGameObjectComponent").getFloat("hitPoints"));
                    }
                    if (unitConfig.get("TeamGameObjectComponent").has("teamId")){
                        config.setTeamId(unitConfig.get("TeamGameObjectComponent").getInt("teamId"));
                    }else {
                        config.setTeamId(-1);
                    }
                }
                if (unitConfig.has("BoundaryComponent")){
                    config.setBoundaryComponent(true);
                }else {
                    config.setBoundaryComponent(false);
                }
                if (unitConfig.has("ExplosionComponent")){
                    config.setExplosionComponent(true);
                }else {
                    config.setExplosionComponent(false);
                }
                if (unitConfig.has("UpgradeComponent")){
                    config.setUpgradeComponent(true);
                }else {
                    config.setUpgradeComponent(false);
                }
                unitConfigHashMap.put(unitConfig.getInt("id"),config);
            }
        }
    }
}
