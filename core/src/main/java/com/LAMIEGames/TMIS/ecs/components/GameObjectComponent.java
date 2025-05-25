package com.LAMIEGames.TMIS.ecs.components;

import com.LAMIEGames.TMIS.maps.GameObjectType;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class GameObjectComponent implements Component, Pool.Poolable {
    public GameObjectType type;
    public int animationIndex;

    @Override
    public void reset() {
        type = null;
        animationIndex = -1;
    }
}
