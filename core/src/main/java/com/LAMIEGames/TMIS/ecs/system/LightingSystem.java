package com.LAMIEGames.TMIS.ecs.system;

import com.LAMIEGames.TMIS.ecs.ECSEngine;
import com.LAMIEGames.TMIS.ecs.components.B2DComponent;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;

public class LightingSystem extends IteratingSystem {
    public LightingSystem() {
        super(Family.all(B2DComponent.class).get());
    }

    @Override
    protected void processEntity(final Entity entity, float deltaTime) {
        final B2DComponent b2dComponent = ECSEngine.box2dCmpMapper.get(entity);
        if(b2dComponent.light != null && b2dComponent.lightFluctuationDistance>0) {
            b2dComponent.lightFluctuationTime += (b2dComponent.lightFluctuationSpeed)*deltaTime;
            if(b2dComponent.lightFluctuationTime> MathUtils.PI2) {
                b2dComponent.lightFluctuationTime = 0;
            }
            b2dComponent.light.setDistance(b2dComponent.lightDistance+MathUtils.sin(b2dComponent.lightFluctuationTime)*b2dComponent.lightFluctuationDistance);

        }
    }
}
