package com.LAMIEGames.TMIS.screen;

import com.LAMIEGames.TMIS.Main;
import com.LAMIEGames.TMIS.input.GameKeys;
import com.LAMIEGames.TMIS.input.InputManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.reflect.ReflectionException;

public class MenuScreen extends AbstractScreen {

    public MenuScreen(final Main context) {
        super(context);
    }

    @Override
    protected Table getScreenUI(Main context) {
        return null;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
