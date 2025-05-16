package com.LAMIEGames.TMIS.view;

import static com.LAMIEGames.TMIS.Main.UNIT_SCALE;

import com.LAMIEGames.TMIS.Main;
import com.LAMIEGames.TMIS.ecs.ECSEngine;
import com.LAMIEGames.TMIS.ecs.components.AnimationComponent;
import com.LAMIEGames.TMIS.ecs.components.B2DComponent;
import com.LAMIEGames.TMIS.maps.MapRenderer;
import com.LAMIEGames.TMIS.maps.MapType;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.EnumMap;

public class GameRenderer implements Disposable {
    public static final String TAG = GameRenderer.class.getSimpleName();

    private final OrthographicCamera gameCamera;
    private final FitViewport screenViewport;
    private final SpriteBatch batch;
    private final AssetManager assetManager;
    private final EnumMap<AnimationType, Animation<Sprite>> animationCache;

    private final ImmutableArray<Entity> animateEntities;
    private final MapRenderer mapRenderer;

    private final Box2DDebugRenderer box2DDebugRenderer;
    private final World world;

    public GameRenderer(final Main context) {
        assetManager = context.getAssetManager();
        screenViewport = context.getScreenViewport();
        batch = context.getSpriteBatch();
        gameCamera = context.getGameCamera();

        MapType[] mapTypes = MapType.values(); // Получаем все типы карт из перечисления
        mapRenderer = new MapRenderer(mapTypes, UNIT_SCALE, batch); // Масштаб 1.0f для простоты
//        context.getMapManager().addMapListener(this);

        animationCache = new EnumMap<AnimationType, Animation<Sprite>>(AnimationType.class);

        animateEntities = context.getEcsEngine().getEntitiesFor(Family.all(
            AnimationComponent.class, B2DComponent.class).get());

        //возможно добавить профайлер
        box2DDebugRenderer = new Box2DDebugRenderer();
        world = context.getWorld();
    }

    public void render(final float alpha) {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//        screenViewport.apply(false);
        if (mapRenderer != null) {
            mapRenderer.render();
        }

        for (final Entity entity: animateEntities) {
            renderEntity(entity, alpha);
        }

//        box2DDebugRenderer.render(world, gameCamera.combined);
    }

    private void renderEntity(final Entity entity, final float alpha) {
        final B2DComponent b2DComponent = ECSEngine.box2dCmpMapper.get(entity);
        final AnimationComponent animationComponent = ECSEngine.animationCmpMapper.get(entity);

        if (animationComponent.animationType != null) {
            final Animation<Sprite> animation = getAnimation(animationComponent.animationType);
            final Sprite frame = animation.getKeyFrame(animationComponent.animationTime);
            b2DComponent.renderPosition.lerp(b2DComponent.body.getPosition(), alpha);
            frame.setBounds(b2DComponent.renderPosition.x - animationComponent.width * 0.5f,
            b2DComponent.renderPosition.y - b2DComponent.height * 0.5f, animationComponent.width, animationComponent.height);
            frame.draw(batch);
        }
    }

    private Animation<Sprite> getAnimation(AnimationType animationType) {
        Animation<Sprite> animation = animationCache.get(animationType);
        if (animation == null) {
            //create animation
            Gdx.app.debug(TAG,"Creating a new animation" + animationType);
            TextureAtlas.AtlasRegion atlasRegion = assetManager.get(animationType.getAtlasPath(),
                TextureAtlas.class).findRegion(animationType.getAtlasKey());
            final TextureRegion[][] textureRegions = atlasRegion.split(32,32);
            animation = new Animation<Sprite>(animationType.getFrameTime(), getKeyFrames
                (textureRegions[animationType.getSpriteIndex()]), Animation.PlayMode.LOOP);
            animationCache.put(animationType, animation);
        }
        return animation;
    }

    private Array<? extends Sprite> getKeyFrames(final TextureRegion[] textureRegion) {
        final Array<Sprite> keyFrames = new Array<Sprite>();
        for(final TextureRegion region : textureRegion){
            final Sprite sprite = new Sprite(region);
            sprite.setOriginCenter();
            keyFrames.add(sprite);
        }

        return keyFrames;
    }

    @Override
    public void dispose() {
        if (box2DDebugRenderer != null) {
            box2DDebugRenderer.dispose();
        }
        if (mapRenderer != null) {
            mapRenderer.dispose();
        }

    }

//    public void mapChange() {
//        mapRenderer.setMap(MapType.mapType);
//
//        if (dummySprite == null) {
//            dummySprite = assetManager.get("data/player/xana/player_xana.atlas",
//                TextureAtlas.class).createSprite("Xana");
//            dummySprite.setOriginCenter();
//        }
//    }
}
