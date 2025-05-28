package com.LAMIEGames.TMIS.screen;

import com.LAMIEGames.TMIS.Main;
import com.LAMIEGames.TMIS.audio.AudioManager;
import com.LAMIEGames.TMIS.input.GameKeyInputListener;
import com.LAMIEGames.TMIS.input.InputManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

public abstract class AbstractScreen<T extends Table> implements Screen, GameKeyInputListener {
    protected final Main context;
    protected final FitViewport viewport;
    protected final Stage stage;
    protected final T screenUI;
    protected final World world;
    protected final InputManager inputManager;
    protected final AudioManager audioManager;
    protected final Box2DDebugRenderer box2DDebugRenderer;


    public AbstractScreen(final Main context) {
        this.context = context;
        viewport = context.getScreenViewport();
        this.world = context.getWorld();
        this.box2DDebugRenderer = context.getBox2DDebugRenderer();
        inputManager = context.getInputManager();
        stage = context.getStage();
        screenUI = getScreenUI(context);

        Gdx.input.setInputProcessor(new InputMultiplexer(inputManager, stage));
        audioManager = context.getAudioManager();
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(final int width, final int height) {
        viewport.update(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        stage.getRoot().removeActor(screenUI);
        inputManager.removeInputListener(this);
    }

    @Override
    public void show() {
        inputManager.addInputListener(this);
        stage.addActor(screenUI);
    }

    protected abstract T getScreenUI(final Main context);

    @Override
    public void dispose() {

    }
}
