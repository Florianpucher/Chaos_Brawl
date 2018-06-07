package com.strategy_bit.chaos_brawl.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.strategy_bit.chaos_brawl.config.UnitConfig;

import java.util.HashMap;
import java.util.Map;

public class UnitManager {
    public Map<Integer, UnitConfig> unitConfigHashMap;

    private final static String ID = "id";
    private final static String NAME = "name";
    private final static String COST = "cost";
    private final static String TRANSFORMCOMPONENT = "TransformComponent";
    private final static String MOVEMENTCOMPONENT = "MovementComponent";
    private final static String COMBATCOMPONENT = "CombatComponent";
    private final static String TEXTURECOMPONENT = "TextureComponent";
    private final static String TEAMGAMEOBJECTCOMPONENT = "TeamGameObjectComponent";
    private final static String BOUNDARYCOMPONENT = "BoundaryComponent";
    private final static String EXPLOSIONCOMPONENT = "ExplosionComponent";
    private final static String UPGRADECOMPONENT = "UpgradeComponent";
    private final static String POSITION = "position";
    private final static String X = "x";
    private final static String Y = "y";
    private final static String SKINNAME = "skinName";
    private final static String SKINPATH = "skinPath";
    private final static String SPEED = "speed";
    private final static String ATTACKRADIUS = "attackRadius";
    private final static String ATTACKSPEED = "attackSpeed";
    private final static String ATTACKDAMAGE = "attackDamage";
    private final static String RANGED = "ranged";
    private final static String RANGEDATTACKTYPE = "rangedAttackType";
    private final static String HITPOINTS = "hitPoints";
    private final static String TEAMID = "teamId";
    private final static String SOUNDPATH = "soundPath";
    private final static String SOUNDNAME = "soundName";

    public void readFile(String file) {
        //TODO Hellmuth reduce cognitive complexity of method
        unitConfigHashMap = new HashMap<>();
        FileHandle fileHandle = Gdx.files.internal(file);
        JsonReader jsonReader = new JsonReader();
        JsonValue jsonValue = jsonReader.parse(fileHandle);
        JsonValue.JsonIterator iterator = jsonValue.iterator();
        while (iterator.hasNext()) {
            JsonValue unitConfig = iterator.next();
            if (unitConfig.has(ID)) {
                UnitConfig config = new UnitConfig();
                if (unitConfig.has(NAME)){
                    config.setName(unitConfig.getString(NAME));
                }
                if (unitConfig.has(COST)){
                    config.setCost(unitConfig.getFloat(COST));
                }
                if (unitConfig.has(SOUNDPATH)&&unitConfig.has(SOUNDNAME)){
                    AssetManager.getInstance().addSound(unitConfig.getString(SOUNDNAME),unitConfig.getString(SOUNDPATH));
                    config.setSound(AssetManager.getInstance().sounds.get(unitConfig.getString(SOUNDNAME)));

                }
                if (unitConfig.has(TRANSFORMCOMPONENT)){
                    if (unitConfig.get(TRANSFORMCOMPONENT).has(POSITION)){
                        config.setPosition(new Vector2(unitConfig.get(TRANSFORMCOMPONENT).get(POSITION).getFloat(X),unitConfig.get(TRANSFORMCOMPONENT).get(POSITION).getFloat(Y)));
                    }else {
                        config.setPosition(new Vector2(0,0));
                    }
                }
                if (unitConfig.has(TEXTURECOMPONENT)){
                    if (unitConfig.get(TEXTURECOMPONENT).has(SKINPATH)&&unitConfig.get(TEXTURECOMPONENT).has(SKINNAME)){
                        AssetManager.getInstance().addSkin(unitConfig.get(TEXTURECOMPONENT).getString(SKINNAME),unitConfig.get(TEXTURECOMPONENT).getString(SKINPATH));
                        config.setSkin(AssetManager.getInstance().skins.get(unitConfig.get(TEXTURECOMPONENT).getString(SKINNAME)));
                    }
                }
                if (unitConfig.has(MOVEMENTCOMPONENT)){
                    config.setMovementComponent(true);
                    if (unitConfig.get(MOVEMENTCOMPONENT).has(SPEED)){
                        config.setSpeed(unitConfig.get(MOVEMENTCOMPONENT).getFloat(SPEED));
                    }
                }else {
                    config.setMovementComponent(false);
                }
                if (unitConfig.has(COMBATCOMPONENT)){
                    if (unitConfig.get(COMBATCOMPONENT).has(ATTACKRADIUS)){
                        config.setAttackRadius(unitConfig.get(COMBATCOMPONENT).getFloat(ATTACKRADIUS));
                    }
                    if (unitConfig.get(COMBATCOMPONENT).has(ATTACKSPEED)){
                        config.setAttackSpeed(unitConfig.get(COMBATCOMPONENT).getFloat(ATTACKSPEED));
                    }
                    if (unitConfig.get(COMBATCOMPONENT).has(ATTACKDAMAGE)){
                        config.setAttackDamage(unitConfig.get(COMBATCOMPONENT).getFloat(ATTACKDAMAGE));
                    }
                    if (unitConfig.get(COMBATCOMPONENT).has(RANGED)){
                        config.setRanged(unitConfig.get(COMBATCOMPONENT).getBoolean(RANGED));
                    }else {
                        config.setRanged(false);
                    }
                    if (unitConfig.get(COMBATCOMPONENT).has(RANGEDATTACKTYPE)){
                        config.setRangedAttackType(unitConfig.get(COMBATCOMPONENT).getInt(RANGEDATTACKTYPE));
                    }else {
                        config.setRangedAttackType(0);
                    }
                }
                if (unitConfig.has(TEAMGAMEOBJECTCOMPONENT)){
                    if (unitConfig.get(TEAMGAMEOBJECTCOMPONENT).has(HITPOINTS)){
                        config.setHitPoints(unitConfig.get(TEAMGAMEOBJECTCOMPONENT).getFloat(HITPOINTS));
                    }
                    if (unitConfig.get(TEAMGAMEOBJECTCOMPONENT).has(TEAMID)){
                        config.setTeamId(unitConfig.get(TEAMGAMEOBJECTCOMPONENT).getInt(TEAMID));
                    }else {
                        config.setTeamId(-1);
                    }
                }
                if (unitConfig.has(BOUNDARYCOMPONENT)){
                    config.setBoundaryComponent(true);
                }else {
                    config.setBoundaryComponent(false);
                }
                if (unitConfig.has(EXPLOSIONCOMPONENT)){
                    config.setExplosionComponent(true);
                }else {
                    config.setExplosionComponent(false);
                }
                if (unitConfig.has(UPGRADECOMPONENT)){
                    config.setUpgradeComponent(true);
                }else {
                    config.setUpgradeComponent(false);
                }
                unitConfigHashMap.put(unitConfig.getInt(ID),config);
            }
        }
    }
}
