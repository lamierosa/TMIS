package com.LAMIEGames.TMIS.screen;

import com.LAMIEGames.TMIS.Main;
import com.LAMIEGames.TMIS.audio.AudioType;
import com.LAMIEGames.TMIS.input.GameKeys;
import com.LAMIEGames.TMIS.input.InputManager;
import com.LAMIEGames.TMIS.view.GameUI;
import com.LAMIEGames.TMIS.view.LoadingUI;
import com.LAMIEGames.TMIS.view.MenuUI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.reflect.ReflectionException;

public class MenuScreen extends AbstractScreen {
    private final AssetManager manager;
    private boolean isMusicLoaded;
    private SpriteBatch batch;

    public MenuScreen(final Main context) {
        super(context);

        this.batch = context.getSpriteBatch();
        this.manager = context.getAssetManager();

        isMusicLoaded = false;
        for (final AudioType audioType : AudioType.values()) {
            if (audioType.isMusic()) {
                manager.load(audioType.getFilePath(), Music.class);
            } else {
                manager.load(audioType.getFilePath(), Sound.class);
            }
        }
    }

    @Override
    protected MenuUI getScreenUI(final Main context) {
        return new MenuUI(context.getSkin());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        manager.update();

        if (!isMusicLoaded && manager.isLoaded(AudioType.MENUMUSIC.getFilePath())) {
            isMusicLoaded = true;
            audioManager.playAudio(AudioType.MENUMUSIC);
        }


        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            try {
                context.setScreen(ScreenType.GAME);
            } catch (ReflectionException e) {
                throw new GdxRuntimeException("Failed to go on GAME screen", e);
            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void keyPressed(InputManager inputManager, GameKeys gameKeys) {

    }

    @Override
    public void keyUp(InputManager inputManager, GameKeys gameKeys) {

    }
}
