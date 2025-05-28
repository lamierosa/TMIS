package com.LAMIEGames.TMIS;

import com.LAMIEGames.TMIS.audio.AudioManager;
import com.LAMIEGames.TMIS.audio.AudioType;
import com.LAMIEGames.TMIS.ecs.ECSEngine;
import com.LAMIEGames.TMIS.input.InputManager;
import com.LAMIEGames.TMIS.screen.AbstractScreen;
import com.LAMIEGames.TMIS.screen.MenuScreen;
import com.LAMIEGames.TMIS.screen.ScreenType;
import com.LAMIEGames.TMIS.view.GameRenderer;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Colors;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.EnumMap;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    public static final String TAG = (Main.class).getSimpleName();
    private EnumMap<ScreenType, AbstractScreen> screenCache;
    private FitViewport screenViewport;
    private SpriteBatch batch;
    private OrthographicCamera gameCamera;
    private GLProfiler profiler;

    public static final float UNIT_SCALE = 1/32f;
    public static final short BIT_PLAYER = 1<<0;
    public static final short BIT_GROUND = 1<<1;
    public static final short BIT_GAME_OBJECT = 1 << 2;

    private World world;
    private WorldContactListener worldContactListener;
    private  Box2DDebugRenderer box2DDebugRenderer;

    public static float alpha;
    public static final BodyDef BODY_DEF = new BodyDef();
    public static final FixtureDef FIXTURE_DEF = new FixtureDef();
    //что то связанное с дельта тайм
    private static final float FIXED_TIME_STEP = 1/60f;
    private float accumulator;

    private Stage stage;
    private Skin skin;

    public AssetManager assetManager;
    private InputManager inputManager;
    private AudioManager audioManager;
    private AppPreferences preferences;
    private PreferenceManager preferenceManager;

    private ECSEngine ecsEngine;

    private GameRenderer gameRenderer;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        batch = new SpriteBatch();

        //box2d stuff
        accumulator = 0;
        Box2D.init();
        world = new World(Vector2.Zero, true);
        worldContactListener = new WorldContactListener();
        world.setContactListener(worldContactListener);

        box2DDebugRenderer = new Box2DDebugRenderer();

        assetManager = new AssetManager();
        assetManager.load("data/player/xana/player_xana.atlas", TextureAtlas.class);

//        manager.setLoader(TiledMap.class, new TmxMapLoader(manager.getFileHandleResolver()));

        initializeSkin();

        stage = new Stage(new FitViewport(1920,1080), batch);

        //audio
        audioManager = new AudioManager(this);

        //input
        inputManager = new InputManager();
        Gdx.input.setInputProcessor(new InputMultiplexer(inputManager, stage));

        //set first screen
        gameCamera = new OrthographicCamera();
        screenViewport = new FitViewport(9, 16, gameCamera);

        //ecs engine
        ecsEngine = new ECSEngine(this);

        preferenceManager = new PreferenceManager();
        preferences = new AppPreferences();

        screenCache = new EnumMap<ScreenType, AbstractScreen>(ScreenType.class);
        try {
            setScreen(ScreenType.MENU);
        } catch (ReflectionException e) {
            throw new GdxRuntimeException("Failed to create first screen", e);
        }

        //game renderer
        gameRenderer = new GameRenderer(this);

//        texture = new Texture(Gdx.files.internal("data/map/map.png"));
    }

    public static void resetBodiesAndFixtureDefinition() {
        BODY_DEF.position.set(0,0);
        BODY_DEF.gravityScale = 1;
        BODY_DEF.type = BodyDef.BodyType.StaticBody;
        BODY_DEF.fixedRotation = false;

        FIXTURE_DEF.density = 0;
        FIXTURE_DEF.isSensor = false;
        FIXTURE_DEF.restitution = 0;
        FIXTURE_DEF.friction = 0.2f;
        FIXTURE_DEF.filter.categoryBits = 0x0001;
        FIXTURE_DEF.filter.maskBits = -1;
        FIXTURE_DEF.shape = null;
    }

    public void initializeSkin() {
        //setup markup colors
        Colors.put("Red", Color.RED);
        Colors.put("Blue", Color.BLUE);


        // Инициализация генератора шрифтов
        final FreeTypeFontGenerator fontGenerator;
        final FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;

        final ObjectMap<String, Object> resources = new ObjectMap<String, Object>();
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("ui/fontTMIS.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.minFilter = Texture.TextureFilter.Linear;
        fontParameter.magFilter = Texture.TextureFilter.Linear;

        // Создание шрифтов разных размеров
        final int[] sizesToCreate = {16, 20, 26, 32};

        for (int size: sizesToCreate) {
            fontParameter.size = size;
            final BitmapFont bitmapFont = fontGenerator.generateFont(fontParameter);
            bitmapFont.getData().markupEnabled = true;
            resources.put("font_" + size, bitmapFont);
        }

        fontGenerator.dispose();

        // load skin
        final SkinLoader.SkinParameter skinParameter = new SkinLoader.
            SkinParameter("ui/hud.atlas", resources);
        assetManager.load("ui/hud.json", Skin.class, skinParameter);
        assetManager.finishLoading();
        skin = assetManager.get("ui/hud.json", Skin.class);
    }

    public OrthographicCamera getGameCamera() {
        return this.gameCamera;
    }

    public ECSEngine getEcsEngine() {
        return ecsEngine;
    }

    public World getWorld() {
        return world;
    }

    public AudioManager getAudioManager() {
        return audioManager;
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public Stage getStage() {
        return stage;
    }

    public Skin getSkin() {
        return skin;
    }

    public AppPreferences getPreferences() {
        return this.preferences;
    }
    public SpriteBatch getSpriteBatch() {
        return batch;
    }

    public FitViewport getScreenViewport() {
        return screenViewport;
    }

    public WorldContactListener getWorldContactListener() {
        return this.worldContactListener;
    }

    public GameRenderer getGameRenderer() {
        return this.gameRenderer;
    }

    public Box2DDebugRenderer getBox2DDebugRenderer() {
        return box2DDebugRenderer;
    }

    public void setScreen(final ScreenType screenType) throws ReflectionException {
        final Screen screen = screenCache.get(screenType);
        if (screen == null) {
            try {
                Gdx.app.debug(TAG, "Created " + screenType + " screen");
                final AbstractScreen newScreen = (AbstractScreen) ClassReflection.getConstructor
                    (screenType.getScreenClass(), Main.class).newInstance(this);
                screenCache.put(screenType, newScreen);
                super.setScreen(newScreen);
            } catch (ReflectionException e) {
                throw new GdxRuntimeException("Failed to create " + screenType + " screen", e);
            }
        } else {
            Gdx.app.debug(TAG, "Went to " + screenType + " screen");
            super.setScreen(screen);
        }
    }

    @Override
    public void render() {
        super.render();
        final float deltaTime = Math.min(0.25f, Gdx.graphics.getDeltaTime());
        ecsEngine.update(deltaTime);

        accumulator += deltaTime;
        while (accumulator >= FIXED_TIME_STEP) {
            world.step(FIXED_TIME_STEP, 6, 2);
            accumulator -= FIXED_TIME_STEP;
        }

        alpha = accumulator / FIXED_TIME_STEP;
//        gameRenderer.render(alpha);
        stage.getViewport().apply();
        stage.act(deltaTime);
        stage.draw();

    }

    @Override
    public void dispose() {
        super.dispose();
        box2DDebugRenderer.dispose();
        world.dispose();
        assetManager.dispose();
        batch.dispose();
        stage.dispose();
    }

}
