package com.LAMIEGames.TMIS.ecs.system;

import com.LAMIEGames.TMIS.Main;
import com.LAMIEGames.TMIS.ecs.ECSEngine;
import com.LAMIEGames.TMIS.ecs.components.B2DComponent;
import com.LAMIEGames.TMIS.ecs.components.PlayerComponent;
import com.LAMIEGames.TMIS.input.GameKeyInputListener;
import com.LAMIEGames.TMIS.input.GameKeys;
import com.LAMIEGames.TMIS.input.InputManager;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class PlayerMovementSystem extends IteratingSystem implements GameKeyInputListener {
    private boolean directionChange;
    private int xFactor;
    private int yFactor;

    public PlayerMovementSystem(final Main context) {
        super(Family.all(PlayerComponent.class, B2DComponent.class).get());
        context.getInputManager().addInputListener(this);
        directionChange = false;
        xFactor = yFactor = 0;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
//        if(directionChange) {
            final PlayerComponent playerComponent = ECSEngine.playerCmpMapper.get(entity);
            final B2DComponent b2DComponent = ECSEngine.box2dCmpMapper.get(entity);

            directionChange = false;
            b2DComponent.body.applyLinearImpulse(
                (xFactor * playerComponent.speed.x - b2DComponent.body.getLinearVelocity().x) * b2DComponent.body.getMass(),
                (yFactor * playerComponent.speed.y - b2DComponent.body.getLinearVelocity().y) * b2DComponent.body.getMass(),
                b2DComponent.body.getWorldCenter().x, b2DComponent.body.getWorldCenter().y, true
            );
//        }
    }

    @Override
    public void keyPressed(InputManager inputManager, GameKeys gameKeys) {
        switch (gameKeys) {
            case LEFT:
                directionChange = true;
                xFactor = 1;
                break;
            case RIGHT:
                directionChange = true;
                xFactor = -1;
                break;
            case DOWN:
                directionChange = true;
                yFactor = -1;
                break;
            case UP:
                directionChange = true;
                yFactor = 1;
                break;
            default:
                //nothing to do
                return;
        }

    }

    @Override
    public void keyUp(InputManager inputManager, GameKeys gameKeys) {
        switch (gameKeys) {
            case LEFT:
                directionChange = true;
                xFactor = inputManager.isKeyPressed(GameKeys.RIGHT) ? -1 : 0;
                break;
            case RIGHT:
                directionChange = true;
                xFactor = inputManager.isKeyPressed(GameKeys.LEFT) ? 1 : 0;
                break;
            case DOWN:
                directionChange = true;
                yFactor = inputManager.isKeyPressed(GameKeys.UP) ? 1 : 0;
                break;
            case UP:
                directionChange = true;
                yFactor = inputManager.isKeyPressed(GameKeys.DOWN) ? -1 : 0;
                break;
            default:
                //nothing to do
                break;
        }

    }
}
