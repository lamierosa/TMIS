package com.LAMIEGames.TMIS.maps;

import com.LAMIEGames.TMIS.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class MapRenderer implements Disposable {
    private final SpriteBatch batch;
    private TextureRegion[][] mapTiles; // Массив текстурных регионов для карты
    private int mapWidth;
    private int mapHeight;
    private float unitScale;

    public MapRenderer(MapType[] mapTypes, float unitScale, SpriteBatch batch) {
        this.batch = batch;
        this.unitScale = Main.UNIT_SCALE;
        loadMapTiles(mapTypes);
    }

    private void loadMapTiles(MapType[] mapTypes) {
        // Создаем текстурный атлас для загрузки текстур
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("map/map.atlas")); // Укажите правильный путь к атласу
        mapWidth = mapTypes.length; // Ширина карты в тайлах
        mapHeight = 1; // В данном случае мы предполагаем, что у нас одна строка тайлов

        // Инициализируем массив текстурных регионов
        mapTiles = new TextureRegion[mapWidth][mapHeight];

        for (int i = 0; i < mapTypes.length; i++) {
            // Получаем текстурный регион из атласа по имени региона
            mapTiles[i][0] = atlas.findRegion(mapTypes[i].getAtlasRegion());
        }
    }

    public void render() {
        if (mapTiles == null || mapTiles.length == 0) return;

        batch.begin();

        for (int x = 0; x < mapWidth; x++) {
            TextureRegion tile = mapTiles[x][0]; // Отрисовываем только первую строку
            if (tile != null) {
                batch.draw(tile, x * unitScale, 0, unitScale, unitScale); // Отрисовываем тайлы с учетом масштаба
            }
        }

        batch.end();
    }

    @Override
    public void dispose() {

    }
}



