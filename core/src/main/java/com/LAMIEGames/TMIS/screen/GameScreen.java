package com.LAMIEGames.TMIS.screen;

import static com.LAMIEGames.TMIS.Main.UNIT_SCALE;
import static com.LAMIEGames.TMIS.Main.alpha;

import com.LAMIEGames.TMIS.Main;
import com.LAMIEGames.TMIS.PreferenceManager;
import com.LAMIEGames.TMIS.audio.AudioType;
import com.LAMIEGames.TMIS.input.GameKeys;
import com.LAMIEGames.TMIS.input.InputManager;
import com.LAMIEGames.TMIS.maps.MapRenderer;
import com.LAMIEGames.TMIS.maps.MapType;
import com.LAMIEGames.TMIS.view.GameUI;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class GameScreen extends AbstractScreen<GameUI> {
    private final MapRenderer mapRenderer;
    //todo: добавить карты на экран
    private final PreferenceManager preferenceManager;
    private final Entity player;

    public GameScreen(final Main context) {
        super(context);
//        //map renderer доработать
        mapRenderer = new MapRenderer(UNIT_SCALE, context.getSpriteBatch());


        preferenceManager = new PreferenceManager();

        //todo: сделать что-то со со стартовой локацией потому что map у меня нету

        player = context.getEcsEngine().createPlayer(new Vector2(0,0),0.75f,0.75f);
//        context.getGameCamera().position.set();
        audioManager.playAudio(AudioType.GAMEMUSIC);
    }



    @Override
    public void render(float delta) {
        // сюда, возможно, добавить переключение с локации на локацию
        mapRenderer.setMap(MapType.ROOM);
        context.getGameRenderer().render(alpha);

        preferenceManager.saveGameState(player);

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

//    @Override
    protected GameUI getScreenUI(final Main context) {
        return new GameUI(context);
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
