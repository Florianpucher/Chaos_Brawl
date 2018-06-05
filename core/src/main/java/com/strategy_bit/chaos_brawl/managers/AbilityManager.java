package com.strategy_bit.chaos_brawl.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.strategy_bit.chaos_brawl.config.AbilityConfig;

import java.util.HashMap;
import java.util.Map;

public class AbilityManager {
    public Map<Integer, AbilityConfig> abilityConfigHashMap;
    private static final String ID="id";
    private static final String NAME="name";
    private static final String COST="cost";
    private static final String TEXTURE_COMPONENT ="TextureComponent";
    private static final String SKIN_PATH ="skinPath";
    private static final String SKIN_NAME ="skinName";

    public void readFile(String file) {
        abilityConfigHashMap = new HashMap<>();
        FileHandle fileHandle = Gdx.files.internal(file);
        JsonReader jsonReader = new JsonReader();
        JsonValue jsonValue = jsonReader.parse(fileHandle);
        JsonValue.JsonIterator iterator = jsonValue.iterator();
        while (iterator.hasNext()) {
            JsonValue abilityConfig = iterator.next();
            if (abilityConfig.has(ID)) {
                AbilityConfig config = new AbilityConfig();
                if (abilityConfig.has(NAME)){
                    config.setName(abilityConfig.getString(NAME));
                }
                if (abilityConfig.has(COST)){
                    config.setCost(abilityConfig.getFloat(COST));
                }
                if (abilityConfig.has(TEXTURE_COMPONENT)){
                    if (abilityConfig.get(TEXTURE_COMPONENT).has(SKIN_PATH)&&abilityConfig.get(TEXTURE_COMPONENT).has(SKIN_NAME)){
                        AssetManager.getInstance().addSkin(abilityConfig.get(TEXTURE_COMPONENT).getString(SKIN_NAME),abilityConfig.get(TEXTURE_COMPONENT).getString(SKIN_PATH));
                        config.setSkin(AssetManager.getInstance().skins.get(abilityConfig.get(TEXTURE_COMPONENT).getString(SKIN_NAME)));
                    }
                }
                abilityConfigHashMap.put(abilityConfig.getInt(ID),config);
            }
        }
    }
}
