package com.LAMIEGames.TMIS.ecs;

import static com.LAMIEGames.TMIS.Main.BIT_GAME_OBJECT;
import static com.LAMIEGames.TMIS.Main.BIT_GROUND;
import static com.LAMIEGames.TMIS.Main.BIT_PLAYER;
import static com.LAMIEGames.TMIS.Main.BODY_DEF;
import static com.LAMIEGames.TMIS.Main.FIXTURE_DEF;
import static com.LAMIEGames.TMIS.Main.UNIT_SCALE;

import com.LAMIEGames.TMIS.Main;
import com.LAMIEGames.TMIS.ecs.components.AnimationComponent;
import com.LAMIEGames.TMIS.ecs.components.B2DComponent;
import com.LAMIEGames.TMIS.ecs.components.GameObjectComponent;
import com.LAMIEGames.TMIS.ecs.components.PlayerComponent;
import com.LAMIEGames.TMIS.ecs.system.AnimationSystem;
import com.LAMIEGames.TMIS.ecs.system.PlayerAnimationSystem;
import com.LAMIEGames.TMIS.ecs.system.PlayerCameraSystem;
import com.LAMIEGames.TMIS.ecs.system.PlayerCollisionSystem;
import com.LAMIEGames.TMIS.ecs.system.PlayerMovementSystem;
import com.LAMIEGames.TMIS.maps.GameObjects;
import com.LAMIEGames.TMIS.view.AnimationType;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class ECSEngine extends PooledEngine {
    public static final ComponentMapper<PlayerComponent> playerCmpMapper =
        ComponentMapper.getFor(PlayerComponent.class);
    public static final ComponentMapper<B2DComponent> box2dCmpMapper =
        ComponentMapper.getFor(B2DComponent.class);
    public static final ComponentMapper<AnimationComponent> animationCmpMapper =
        ComponentMapper.getFor(AnimationComponent.class);
    public static final ComponentMapper<GameObjectComponent> gameObjCmpMapper =
        ComponentMapper.getFor(GameObjectComponent.class);


    private final BodyDef bodyDef;
    private final FixtureDef fixtureDef;
    private final Vector2 localPosition;
    private final Vector2 posBeforeRotation;
    private final Vector2 posAfterRotation;

    private final World world;
    public static Entity player;

    public ECSEngine(Main context) {
        super();

        world = context.getWorld();
        bodyDef = context.BODY_DEF;
        fixtureDef = context.FIXTURE_DEF;

        localPosition = new Vector2();
        posBeforeRotation = new Vector2();
        posAfterRotation = new Vector2();

        this.addSystem(new PlayerMovementSystem(context));
        this.addSystem(new PlayerCameraSystem(context));
        this.addSystem(new AnimationSystem(context));
        this.addSystem(new PlayerAnimationSystem(context));
        this.addSystem(new PlayerCollisionSystem(context));
    }

    public Entity createPlayer(Vector2 playerSpawnLocation, final float width, final float height) {
        player = this.createEntity();

        //player component
        final PlayerComponent playerComponent = this.createComponent(PlayerComponent.class);
        playerComponent.speed.set(3,3);
        player.add(playerComponent);

        //box2d component
        Main.resetBodiesAndFixtureDefinition();

        final B2DComponent b2DComponent = this.createComponent(B2DComponent.class);
        bodyDef.position.set(playerSpawnLocation.x, playerSpawnLocation.y + (height * 0.5f));
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2DComponent.body = world.createBody(bodyDef);
        b2DComponent.body.setUserData(player);
        b2DComponent.width = width;
        b2DComponent.height = height;
        b2DComponent.renderPosition.set(b2DComponent.body.getPosition());

        fixtureDef.filter.categoryBits = BIT_PLAYER;
        fixtureDef.filter.maskBits = BIT_GROUND| BIT_GAME_OBJECT;
        final PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(0.5f, 0.5f);
        fixtureDef.shape = polygonShape;
        b2DComponent.body.createFixture(fixtureDef);
        polygonShape.dispose();
        player.add(b2DComponent);

        //animation component
        final AnimationComponent animationComponent = this.createComponent(AnimationComponent.class);
        animationComponent.animationType = AnimationType.PLAYER_RIGHT;
        animationComponent.width = 32 * UNIT_SCALE * 0.75f;
        animationComponent.height = 32 * UNIT_SCALE * 0.75f;
        player.add(animationComponent);

        this.addEntity(player);
        return player;
    }

    public void createGameObject(final GameObjects gameObject) {
        final Entity gameObjEntity = this.createEntity();

        final AnimationComponent animationComponent = this.createComponent(AnimationComponent.class);
        animationComponent.animationType = null;
        animationComponent.width = gameObject.getWidth();
        animationComponent.height = gameObject.getHeight();
        gameObjEntity.add(animationComponent);

        Main.resetBodiesAndFixtureDefinition();
        final float halfW = gameObject.getWidth() * 0.5f;
        final float halfH = gameObject.getHeight() * 0.5f;
        final B2DComponent b2DComponent = this.createComponent(B2DComponent.class);
        BODY_DEF.type = BodyDef.BodyType.StaticBody;
        BODY_DEF.position.set(gameObject.getPosition().x + halfW, gameObject.getPosition().y + halfH);
        b2DComponent.body = world.createBody(BODY_DEF);
        b2DComponent.body.setUserData(gameObjEntity);
        b2DComponent.width = gameObject.getWidth();
        b2DComponent.height = gameObject.getHeight();

        localPosition.set(-halfW, -halfH);
        posBeforeRotation.set(b2DComponent.body.getWorldPoint(localPosition));
        b2DComponent.body.setTransform(b2DComponent.body.getPosition(), 0f);
        posAfterRotation.set(b2DComponent.body.getWorldPoint(localPosition));

        b2DComponent.body.setTransform(b2DComponent.body.getPosition().
            add(posBeforeRotation).sub(posAfterRotation), 0f);
        b2DComponent.renderPosition.set(b2DComponent.body.getPosition().x -
            animationComponent.width * 0.5f, b2DComponent.body.getPosition().y - b2DComponent.height * 0.5f);

        FIXTURE_DEF.filter.categoryBits = BIT_GAME_OBJECT;
        FIXTURE_DEF.filter.maskBits = BIT_PLAYER;
        final PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(halfW, halfH);
        FIXTURE_DEF.shape = pShape;
        if(gameObject.getType().name().equals("DOOR") || gameObject.getType().name().equals("PAPER")
            || gameObject.getType().name().equals("GRAVE")) {
            FIXTURE_DEF.isSensor = true;
        }
        b2DComponent.body.createFixture(FIXTURE_DEF);
        pShape.dispose();
        gameObjEntity.add(b2DComponent);

        final GameObjectComponent gameObjectComponent = this.createComponent(GameObjectComponent.class);
        gameObjectComponent.animationIndex = gameObject.getAnimationIndex();
        gameObjectComponent.type = gameObject.getType();
        gameObjEntity.add(gameObjectComponent);

        this.addEntity(gameObjEntity);
    }

    //getter of player
    public Entity getPlayer() {
        return player;
    }
}
