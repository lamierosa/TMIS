package com.LAMIEGames.TMIS.ecs;

import static com.LAMIEGames.TMIS.Main.BIT_GROUND;
import static com.LAMIEGames.TMIS.Main.BIT_PLAYER;
import static com.LAMIEGames.TMIS.Main.UNIT_SCALE;

import com.LAMIEGames.TMIS.Main;
import com.LAMIEGames.TMIS.ecs.components.AnimationComponent;
import com.LAMIEGames.TMIS.ecs.components.B2DComponent;
import com.LAMIEGames.TMIS.ecs.components.PlayerComponent;
import com.LAMIEGames.TMIS.ecs.system.AnimationSystem;
import com.LAMIEGames.TMIS.ecs.system.PlayerAnimationSystem;
import com.LAMIEGames.TMIS.ecs.system.PlayerCameraSystem;
import com.LAMIEGames.TMIS.ecs.system.PlayerMovementSystem;
import com.LAMIEGames.TMIS.view.AnimationType;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
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

    private final BodyDef bodyDef;
    private final FixtureDef fixtureDef;

    private final World world;


    public ECSEngine(Main context) {
        super();

        world = context.getWorld();

        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();

        this.addSystem(new PlayerMovementSystem(context));
        this.addSystem(new PlayerCameraSystem(context));
        this.addSystem(new AnimationSystem(context));
        this.addSystem(new PlayerAnimationSystem(context));

    }


    public Entity createPlayer(Vector2 playerSpawnLocation, final float width, final float height) {
        final Entity player = this.createEntity();

        //player component
        PlayerComponent playerComponent = this.createComponent(PlayerComponent.class);
        playerComponent.speed.set(3,3);
        player.add(playerComponent);


        //box2d component
        Main.resetBodieAndFixtureDefinition();
        B2DComponent b2DComponent = this.createComponent(B2DComponent.class);
        bodyDef.position.set(playerSpawnLocation.x, playerSpawnLocation.y + (height * 0.5f));
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2DComponent.body = world.createBody(bodyDef);
        b2DComponent.body.setUserData("PLAYER");
        b2DComponent.width = width;
        b2DComponent.height = height;
        b2DComponent.renderPosition.set(b2DComponent.body.getPosition());

        fixtureDef.filter.categoryBits = BIT_PLAYER;
        fixtureDef.filter.maskBits = BIT_GROUND;
        final PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(width * 0.5f, height * 0.5f);
        fixtureDef.shape = polygonShape;
        b2DComponent.body.createFixture(fixtureDef);
        polygonShape.dispose();
        player.add(b2DComponent);

        //animation component
        final AnimationComponent animationComponent = this.createComponent(AnimationComponent.class);
        animationComponent.animationType = AnimationType.PLAYER_RIGHT;
        animationComponent.width = 64 * UNIT_SCALE * 0.75f;
        animationComponent.height = 64 * UNIT_SCALE * 0.75f;
        player.add(animationComponent);

        this.addEntity(player);
        return player;
    }
}
