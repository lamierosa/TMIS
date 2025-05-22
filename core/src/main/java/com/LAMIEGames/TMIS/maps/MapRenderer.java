package com.LAMIEGames.TMIS.maps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.Map;

public class MapRenderer implements Disposable {
    private final SpriteBatch batch;
    private final TextureAtlas atlas; // Атлас текстур
    private final Map<String, TextureRegion> mapCache; // Кэш для текстур карт
    private TextureRegion currentMapTile; // Текущий текстурный регион для карты
    private float unitScale;

    public MapRenderer(float unitScale, SpriteBatch batch) {
        this.batch = batch;
        this.unitScale = unitScale; // Используем переданный unitScale
        this.atlas = new TextureAtlas(Gdx.files.internal("map/map.atlas")); // Загружаем атлас один раз
        this.mapCache = new HashMap<>(); // Инициализируем кэш
    }

    public void setMap(MapType mapType) {
        String regionName = mapType.getAtlasRegion(); // Получаем имя региона
        currentMapTile = mapCache.computeIfAbsent(regionName, key -> atlas.findRegion(key)); // Загружаем из кэша или атласа
    }

    public void render() {
        if (currentMapTile == null) return; // Проверяем, загружена ли карта

        batch.begin();
        batch.draw(currentMapTile, 0, 0, unitScale, unitScale); // Отрисовываем тайл с учетом масштаба
        batch.end();
    }

    @Override
    public void dispose() {
        atlas.dispose(); // Освобождаем ресурсы атласа
        batch.dispose(); // Освобождаем ресурсы спрайт-батча
    }
}



