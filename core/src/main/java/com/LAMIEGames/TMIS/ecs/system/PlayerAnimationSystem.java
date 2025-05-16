package com.LAMIEGames.TMIS.ecs.system;

import com.LAMIEGames.TMIS.Main;
import com.LAMIEGames.TMIS.ecs.ECSEngine;
import com.LAMIEGames.TMIS.ecs.components.AnimationComponent;
import com.LAMIEGames.TMIS.ecs.components.B2DComponent;
import com.LAMIEGames.TMIS.ecs.components.PlayerComponent;
import com.LAMIEGames.TMIS.view.AnimationType;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;

public class PlayerAnimationSystem extends IteratingSystem {

    public PlayerAnimationSystem(Main context) {
        super(Family.all(AnimationComponent.class, PlayerComponent.class, B2DComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final AnimationComponent animationComponent = ECSEngine.animationCmpMapper.get(entity);
        final B2DComponent b2DComponent = ECSEngine.box2dCmpMapper.get(entity);

        if (b2DComponent.body.getLinearVelocity().equals(Vector2.Zero)) {
            //player does not move
            animationComponent.animationTime = 0;
        } else if (b2DComponent.body.getLinearVelocity().x > 0) {
            //player moves to the right
            animationComponent.animationType = AnimationType.PLAYER_RIGHT;
        } else if (b2DComponent.body.getLinearVelocity().x < 0) {
            //player moves to the left
            animationComponent.animationType = AnimationType.PLAYER_LEFT;
        } else if (b2DComponent.body.getLinearVelocity().y > 0) {
            //player moves up
            animationComponent.animationType = AnimationType.PLAYER_UP;
        } else if (b2DComponent.body.getLinearVelocity().y < 0) {
            //player moves down
            animationComponent.animationType = AnimationType.PLAYER_DOWN;

        }
    }
}
