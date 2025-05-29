package com.LAMIEGames.TMIS.ecs.system;

import static com.LAMIEGames.TMIS.maps.GameObjectType.PAPER;

import com.LAMIEGames.TMIS.Main;
import com.LAMIEGames.TMIS.WorldContactListener;
import com.LAMIEGames.TMIS.ecs.ECSEngine;
import com.LAMIEGames.TMIS.ecs.components.GameObjectComponent;
import com.LAMIEGames.TMIS.ecs.components.RemoveComponent;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class PlayerCollisionSystem extends IteratingSystem implements WorldContactListener.PlayerCollisionListener {
    public static boolean teleport;

    public PlayerCollisionSystem(final Main context) {
        super(Family.all(RemoveComponent.class).get());

        context.getWorldContactListener().addPlayerCollisionListener(this);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        getEngine().removeEntity(entity);
    }


    @Override
    public void playerCollision(final Entity player, final Entity gameObject) {
        final GameObjectComponent gameObjComp = ECSEngine.gameObjCmpMapper.get(gameObject);
        switch (gameObjComp.type) {
            case PAPER :
                //remove paper and increase player item num
                gameObject.add(((ECSEngine) getEngine()).createComponent(RemoveComponent.class));
                ECSEngine.playerCmpMapper.get(player).paperCount++;
                break;
//            case DOOR :
//                teleport = true;
//                break;
//            case GRAVE :
//                //todo: add this later
//                break;
        }
    }
}
