package com.LAMIEGames.TMIS;

import static com.LAMIEGames.TMIS.Main.BIT_GAME_OBJECT;
import static com.LAMIEGames.TMIS.Main.BIT_PLAYER;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

import javax.swing.text.html.parser.Entity;

public class WorldContactListener implements ContactListener {
    private final Array<PlayerCollisionListener> listeners;

    public WorldContactListener() {
        listeners = new Array<PlayerCollisionListener>();
    }

    public void addPlayerCollisionListener(final PlayerCollisionListener listener) {
        listeners.add(listener);
    }

    @Override
    public void beginContact(Contact contact) {

        final Entity player;
        final Entity gameObj;
        final Body bodyA = contact.getFixtureA().getBody();
        final Body bodyB = contact.getFixtureB().getBody();
        final int catFixA = contact.getFixtureA().getFilterData().categoryBits;
        final int catFixB = contact.getFixtureB().getFilterData().categoryBits;

        if ((int) (catFixA & BIT_PLAYER) == BIT_PLAYER) {
            player = (Entity) (bodyA.getUserData());
        }
        else if ((int) (catFixB & BIT_PLAYER) == BIT_PLAYER) {
            player = (Entity) (bodyB.getUserData());
        }
        else {
            return;
        }

        if ((int) (catFixA & BIT_GAME_OBJECT) == BIT_GAME_OBJECT) {
            gameObj = (Entity) (bodyA.getUserData());
        }
        else if ((int) (catFixB & BIT_GAME_OBJECT) == BIT_GAME_OBJECT) {
            gameObj = (Entity) (bodyB.getUserData());
        }
        else {
            return;
        }

        for (final PlayerCollisionListener listener : listeners) {
            listener.playerCollision(player, gameObj);
        }

    }

    @Override
    public void endContact(Contact contact) {
//        final Fixture fixtureA = contact.getFixtureA();
//        final Fixture fixtureB = contact.getFixtureB();
//
//        Gdx.app.debug("CONTACT", "END: " + fixtureA.getBody().
//            getUserData() + " "  + fixtureA.isSensor());
//        Gdx.app.debug("CONTACT", "END: " + fixtureB.getBody().
//            getUserData() + " "  + fixtureB.isSensor());
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        contact.setEnabled(false);

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public interface PlayerCollisionListener {
        void playerCollision(final Entity player, final Entity gameObject);
    }
}
