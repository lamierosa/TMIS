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
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class GameScreen extends AbstractScreen<GameUI> {
    private final MapRenderer mapRenderer;
    //todo: добавить карты на экран
    private final PreferenceManager preferenceManager;
    private final Entity player;
    private final AssetManager manager;
    private boolean isMusicLoaded;

    public GameScreen(final Main context) {
        super(context);
        this.manager = context.getAssetManager();
//        //map renderer доработать
        mapRenderer = new MapRenderer(UNIT_SCALE, context.getSpriteBatch());
        mapRenderer.setMap(MapType.ROOM);
        preferenceManager = new PreferenceManager();

        isMusicLoaded = false;
        for (final AudioType audioType : AudioType.values()) {
            if (audioType.isMusic()) {
                manager.load(audioType.getFilePath(), Music.class);
            }
        }
        //todo: сделать что-то со со стартовой локацией потому что map у меня нету

        player = context.getEcsEngine().createPlayer(new Vector2(0,0),1f,1f);
        context.getGameCamera().position.set((new Vector2(0,0)), 0);

    }



    @Override
    public void render(float delta) {
        super.render(delta);
        manager.update();

        if (!isMusicLoaded && manager.isLoaded(AudioType.GAMEMUSIC.getFilePath())) {
            isMusicLoaded = true;
            audioManager.playAudio(AudioType.GAMEMUSIC);
        }
//        Texture texture = new Texture(Gdx.files.internal("map/map.png"));

        preferenceManager.saveGameState(player);

        viewport.apply(true);
        context.getGameRenderer().render(alpha);
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
