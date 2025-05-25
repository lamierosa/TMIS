package com.LAMIEGames.TMIS.ecs.system;

import com.LAMIEGames.TMIS.Main;
import com.LAMIEGames.TMIS.ecs.ECSEngine;
import com.LAMIEGames.TMIS.ecs.components.B2DComponent;
import com.LAMIEGames.TMIS.ecs.components.PlayerComponent;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class PlayerCameraSystem extends IteratingSystem {
    private final OrthographicCamera gameCamera;

    public PlayerCameraSystem(final Main context) {
        super(Family.all(PlayerComponent.class, B2DComponent.class).get());
        gameCamera = context.getGameCamera();
    }

    @Override
    protected void processEntity(final Entity entity, float deltaTime) {
        gameCamera.position.set(ECSEngine.box2dCmpMapper.get(entity).renderPosition, 0);
        gameCamera.update();
    }
}
