package com.LAMIEGames.TMIS;

import com.LAMIEGames.TMIS.ecs.ECSEngine;
import com.LAMIEGames.TMIS.ecs.components.B2DComponent;
import com.LAMIEGames.TMIS.ecs.components.PlayerComponent;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class PreferenceManager implements Json.Serializable{

    private final Preferences preferences;
    private final Json json;
    private final JsonReader jsonReader;
    private int paperCollected;

    private final Vector2 playerPos;

    public PreferenceManager() {
        preferences = Gdx.app.getPreferences("LAMIEGames_Preferences");
        json = new Json();
        jsonReader = new JsonReader();
        playerPos = new Vector2();
    }

    public boolean containsKey(final String key) {
        return (preferences.contains(key));
    }

    public void setFloatValue(final String key, final float value) {
        preferences.putFloat(key, value);
        preferences.flush();
    }

    public float getFloatValue(final String key) {
        return preferences.getFloat(key, 0.0f);
    }

    public void saveGameState(final Entity player) {

        playerPos.set(ECSEngine.box2dCmpMapper.get(player).body.getPosition());
        paperCollected = ECSEngine.playerCmpMapper.get(player).paperCount;

        preferences.putString("GAME_STATE", new Json().toJson(this));

        preferences.flush();
    }

    public void loadGameState(final Entity player) {
        final JsonValue savedJsonStr = jsonReader.parse(preferences.getString("GAME_STATE"));

        final B2DComponent b2DComponent = ECSEngine.box2dCmpMapper.get(player);
        b2DComponent.body.setTransform(savedJsonStr.getFloat("PLAYER_X",
            0f),savedJsonStr.getFloat("PLAYER_Y", 0f),b2DComponent.body.getAngle());
        final PlayerComponent playerComponent = ECSEngine.playerCmpMapper.get(player);
        playerComponent.paperCount = savedJsonStr.getInt("PAPER_COLLECTED", 0);

    }

    public void deleteGameState(final Entity player) {
        preferences.remove("GAME_STATE");
        preferences.flush();
    }

    @Override
    public void write(Json json) {
        json.writeValue("PLAYER_X", playerPos.x);
        json.writeValue("PLAYER_Y", playerPos.y);
        json.writeValue("PAPER_COLLECTED", paperCollected);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {

    }
}
