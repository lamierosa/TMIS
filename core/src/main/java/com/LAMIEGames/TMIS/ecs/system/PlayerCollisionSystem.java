package com.LAMIEGames.TMIS.ecs.system;

import com.LAMIEGames.TMIS.Main;
import com.LAMIEGames.TMIS.WorldContactListener;
import com.LAMIEGames.TMIS.ecs.components.RemoveComponent;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class PlayerCollisionSystem extends IteratingSystem implements WorldContactListener.PlayerCollisionListener {
//    public static boolean teleport;

    public PlayerCollisionSystem(final Main context) {
        super(Family.all(RemoveComponent.class).get());

        context.getWorldContactListener().addPlayerCollisionListener(this);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        getEngine().removeEntity(entity);
    }

//    @Override
//    public void playerCollision(Entity player, final Entity gameObject) {
//        final GameObjectComponent gameObjComp = ECSEngine.gameObjCmpMapper.get(gameObject);
//        switch (gameObjComp.type) {
//            case ITEM :
//                //remove crystal and increase player item num
//                gameObject.add(((ECSEngine) getEngine()).createComponent(RemoveComponent.class));
//                ECSEngine.playerCmpMapper.get(player).itemCount++;
//                break;
//            case FIRE :
//                break;
//            case INFECTIOUS :
//                ECSEngine.playerCmpMapper.get(player).health--;
//                break;
//            case PORTAL1 :
//                if (ECSEngine.playerCmpMapper.get(player).itemCount >= 10) {
//                    teleport = true;
//                    ECSEngine.playerCmpMapper.get(player).xp += 5;
//                }
//                break;
//            case PORTAL2 :
//                teleport = true;
//                break;
//        }
//    }

    @Override
    public void playerCollision(javax.swing.text.html.parser.Entity player, javax.swing.text.html.parser.Entity gameObject) {

    }
}
