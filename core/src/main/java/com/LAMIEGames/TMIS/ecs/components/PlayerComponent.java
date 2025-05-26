package com.LAMIEGames.TMIS.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class PlayerComponent implements Component, Pool.Poolable {
    public boolean hasPaper;
    public Vector2 speed = new Vector2();
    public int paperCount;
    public Vector2 position = new Vector2();

    @Override
    public void reset() {
        hasPaper = false;
        speed.setZero();
        position.set(0,0);
        paperCount = 0;
    }
}
