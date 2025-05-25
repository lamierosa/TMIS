package com.LAMIEGames.TMIS.screen;

import com.LAMIEGames.TMIS.Main;
import com.LAMIEGames.TMIS.audio.AudioManager;
import com.LAMIEGames.TMIS.input.GameKeyInputListener;
import com.LAMIEGames.TMIS.input.InputManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

public abstract class AbstractScreen<T extends Table> implements Screen, GameKeyInputListener {
    protected final Main context;
    protected final FitViewport viewport;
    protected final Stage stage;
    protected final T screenUI;
    protected final InputManager inputManager;
    protected final AudioManager audioManager;

    public AbstractScreen(Main context) {
        this.context = context;
        viewport = context.getScreenViewport();
        inputManager = context.getInputManager();

        screenUI = getScreenUI(context);
        stage = context.getStage();
        Gdx.input.setInputProcessor(new InputMultiplexer(inputManager, stage));
        audioManager = context.getAudioManager();
    }

    protected abstract T getScreenUI(final Main context);

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        inputManager.addInputListener(this);
        stage.addActor(screenUI);
    }

    @Override
    public void hide() {
        inputManager.removeInputListener(this);
        stage.getRoot().removeActor(screenUI);
    }
}
