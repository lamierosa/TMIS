package com.LAMIEGames.TMIS.screen;

import com.LAMIEGames.TMIS.Main;
import com.LAMIEGames.TMIS.input.GameKeys;
import com.LAMIEGames.TMIS.input.InputManager;
import com.LAMIEGames.TMIS.view.GameUI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class GameScreen extends AbstractScreen<GameUI> {

    public GameScreen(final Main context) {
        super(context);
//        spawnCollisionAreas();
        //todo: сделать что-то со со стартовой локацией потому что map у меня нету
        context.getEcsEngine().createPlayer(new Vector2(0,0),0.75f,0.75f);
    }

    @Override
    protected GameUI getScreenUI(final Main context) {
        return new GameUI(context.getSkin());
    }

    @Override
    public void render(final float delta) {
        // сюда, возможно, добавить переключение с локации на локацию

        viewport.apply(true);
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
    public void keyPressed(final InputManager inputManager,final GameKeys gameKeys) {

    }

    @Override
    public void keyUp(final InputManager inputManager,final GameKeys gameKeys) {

    }
}
