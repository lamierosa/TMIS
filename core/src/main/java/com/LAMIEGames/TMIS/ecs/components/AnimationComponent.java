package com.LAMIEGames.TMIS.ecs.components;

import com.LAMIEGames.TMIS.view.AnimationType;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class AnimationComponent implements Component, Pool.Poolable {
    public AnimationType animationType;
    public float animationTime;
    public float width;
    public float height;

    @Override
    public void reset() {
        animationTime = 0;
        animationType = null;
        width = height = 0;
    }
}
