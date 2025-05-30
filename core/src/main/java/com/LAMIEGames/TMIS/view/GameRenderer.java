package com.LAMIEGames.TMIS.view;

import static com.LAMIEGames.TMIS.Main.UNIT_SCALE;
import static com.LAMIEGames.TMIS.Main.alpha;

import com.LAMIEGames.TMIS.Main;
import com.LAMIEGames.TMIS.ecs.ECSEngine;
import com.LAMIEGames.TMIS.ecs.components.AnimationComponent;
import com.LAMIEGames.TMIS.ecs.components.B2DComponent;
import com.LAMIEGames.TMIS.ecs.components.GameObjectComponent;
import com.LAMIEGames.TMIS.maps.Map;
import com.LAMIEGames.TMIS.maps.MapListener;
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
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.EnumMap;

import box2dLight.RayHandler;

public class GameRenderer implements Disposable, MapListener {
    public static final String TAG = GameRenderer.class.getSimpleName();

    private final OrthographicCamera gameCamera;
    private final FitViewport screenViewport;
    private final SpriteBatch batch;
    private final AssetManager assetManager;
    private final EnumMap<AnimationType, Animation<Sprite>> animationCache;
    private final GLProfiler profiler;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final Array<TiledMapTileLayer> tiledMapLayers;

    private ObjectMap<String, TextureRegion[][]> regionCache;
    private final ImmutableArray<Entity> animateEntities;
    private final ImmutableArray<Entity> gameObjectEntities;

    private final Box2DDebugRenderer box2DDebugRenderer;
    private final World world;
    private IntMap<Animation<Sprite>> mapAnimations;
    private final RayHandler rayHandler;

    public GameRenderer(final Main context) {

        screenViewport = context.getScreenViewport();
        screenViewport.setWorldHeight(9);
        screenViewport.setWorldWidth(16);

        assetManager = context.getAssetManager();
        batch = context.getSpriteBatch();
        gameCamera = context.getGameCamera();
        regionCache = new ObjectMap<String, TextureRegion[][]>();
        animationCache = new EnumMap<AnimationType, Animation<Sprite>>(AnimationType.class);

        gameObjectEntities = context.getEcsEngine().getEntitiesFor(Family.all(GameObjectComponent.class, B2DComponent.class, AnimationComponent.class).get());
        animateEntities = context.getEcsEngine().getEntitiesFor(Family.all(
            AnimationComponent.class, B2DComponent.class).exclude(GameObjectComponent.class).get());

        mapRenderer = new OrthogonalTiledMapRenderer(null, UNIT_SCALE, batch);
        context.getMapManager().addMapListener((MapListener) this);
        //mapRenderer.setMap(mainGame.getMapManager().getTiledMap());
        tiledMapLayers = new Array<TiledMapTileLayer>();
//        context.getMapManager().addMapListener(this);

        //profiler
        profiler = new GLProfiler(Gdx.graphics);
        profiler.enable();

        if (profiler.isEnabled()) {
            box2DDebugRenderer = new Box2DDebugRenderer();
            world = context.getWorld();
        } else {
            box2DDebugRenderer = null;
            world = null;
        }

        rayHandler = context.getRayHandler();

    }

    public void render(final float delta) {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//        batch.setProjectionMatrix(gameCamera.combined);

        screenViewport.apply(false);
        mapRenderer.setView(gameCamera);
        batch.begin();

        if (mapRenderer.getMap() != null) {
            AnimatedTiledMapTile.updateAnimationBaseTime();
            for (final TiledMapTileLayer layer : tiledMapLayers) {
                mapRenderer.renderTileLayer(layer);
            }
        }

        for (final Entity entity :gameObjectEntities) {
            renderGameObject(entity, alpha);
        }
        for (final Entity entity: animateEntities) {
            renderEntity(entity, alpha);
            final B2DComponent b2DComponent = ECSEngine.box2dCmpMapper.get(entity);
        }
        batch.end();
        rayHandler.setCombinedMatrix(gameCamera);
        rayHandler.updateAndRender();
        profiler.disable();

//        if (profiler.isEnabled()) {
////            Gdx.app.debug("RenderInfo", "Bindings:" + profiler.getTextureBindings());
////            Gdx.app.debug("RenderInfo", "Drawcalls:" + profiler.getDrawCalls());
//            profiler.reset();
//
//            if (box2DDebugRenderer != null && world != null) {
//                box2DDebugRenderer.render(world, gameCamera.combined); // Отрисовка отладочной информации Box2D
//            }
//        }

        if (profiler.isEnabled()) {
            profiler.reset();
            box2DDebugRenderer.render(world, screenViewport.getCamera().combined);
        }
    }

    private void renderGameObject(final Entity entity, final float alpha) {
        final B2DComponent b2DComponent = ECSEngine.box2dCmpMapper.get(entity);
        final AnimationComponent animationComponent = ECSEngine.animationCmpMapper.get(entity);
        final GameObjectComponent gameObjectComponent = ECSEngine.gameObjCmpMapper.get(entity);

        if (gameObjectComponent.animationIndex != -1) {
            final Animation<Sprite> animation = mapAnimations.get(gameObjectComponent.animationIndex);
            final Sprite frame = animation.getKeyFrame(animationComponent.animationTime);

            frame.setBounds(b2DComponent.renderPosition.x, b2DComponent.renderPosition.y, animationComponent.width, animationComponent.height);
            frame.setOriginCenter();
            frame.setRotation(b2DComponent.body.getAngle() * MathUtils.radDeg);
            frame.draw(batch);
        }
    }


        private void renderEntity(final Entity entity, final float alpha) {
        final B2DComponent b2DComponent = ECSEngine.box2dCmpMapper.get(entity);
        final AnimationComponent animationComponent = ECSEngine.animationCmpMapper.get(entity);

        if (animationComponent.animationType != null) {
            final Animation<Sprite> animation = getAnimation(animationComponent.animationType);
            final Sprite frame = animation.getKeyFrame(animationComponent.animationTime);
            b2DComponent.renderPosition.lerp(b2DComponent.body.getPosition(), alpha);
            frame.setBounds(b2DComponent.renderPosition.x - animationComponent.width * 0.75f,
            b2DComponent.renderPosition.y - b2DComponent.height * 0.75f, animationComponent.width, animationComponent.height);
            frame.draw(batch);
        }
    }

    private Animation<Sprite> getAnimation(AnimationType animationType) {
        Animation<Sprite> animation = animationCache.get(animationType);
        if (animation == null) {
            //create animation
            Gdx.app.debug(TAG, "Creating a new animation " + animationType);
            TextureRegion[][] textureRegions = regionCache.get(animationType.getAtlasKey());
            if(textureRegions == null) {
                Gdx.app.debug(TAG, "Creating texture regions for " + animationType.getAtlasKey());
                final TextureAtlas.AtlasRegion atlasRegion = assetManager.get(animationType.getAtlasPath(),
                TextureAtlas.class).findRegion(animationType.getAtlasKey());
            textureRegions = atlasRegion.split(32, 32);
            regionCache.put(animationType.getAtlasKey(), textureRegions);
            }
        animation = new Animation<Sprite>(animationType.getFrameTime(), getKeyFrames
            (textureRegions[animationType.getSpriteIndex()]));
        animation.setPlayMode(Animation.PlayMode.LOOP);
        animationCache.put(animationType, animation);

        }
        return animation;
    }

    private Sprite[] getKeyFrames(final TextureRegion[] textureRegion) {
        final Sprite [] keyFrames = new Sprite[textureRegion.length];

        int i = 0;
        for(final TextureRegion region : textureRegion){
            final Sprite sprite = new Sprite(region);
            sprite.setOriginCenter();
            keyFrames[i++] = sprite;
        }
        return keyFrames;
    }

    @Override
    public void dispose() {
        if (box2DDebugRenderer != null) {
            box2DDebugRenderer.dispose();
        }
        mapRenderer.dispose();
    }

//    // Метод для получения текстуры из кэша
//    public TextureRegion[][] getTextureFromCache(String regionName) {
//        return regionCache.get(regionName);
//    }
//
//    // Метод для добавления текстуры в кэш
//    public void addTextureToCache(String regionName, TextureRegion[][] textureRegions) {
//        regionCache.put(regionName, textureRegions);
//    }

    @Override
    public void mapChange(Map map) {
        mapRenderer.setMap(map.getTiledMap());
        map.getTiledMap().getLayers().getByType(TiledMapTileLayer.class, tiledMapLayers);
        mapAnimations = map.getMapAnimations();
    }
}
