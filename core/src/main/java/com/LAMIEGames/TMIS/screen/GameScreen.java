package com.LAMIEGames.TMIS.screen;

import static com.LAMIEGames.TMIS.Main.UNIT_SCALE;
import static com.LAMIEGames.TMIS.Main.alpha;

import com.LAMIEGames.TMIS.Main;
import com.LAMIEGames.TMIS.PreferenceManager;
import com.LAMIEGames.TMIS.audio.AudioType;
import com.LAMIEGames.TMIS.ecs.ECSEngine;
import com.LAMIEGames.TMIS.input.GameKeys;
import com.LAMIEGames.TMIS.input.InputManager;
import com.LAMIEGames.TMIS.maps.Map;
import com.LAMIEGames.TMIS.maps.MapListener;
import com.LAMIEGames.TMIS.maps.MapManager;
import com.LAMIEGames.TMIS.maps.MapType;
import com.LAMIEGames.TMIS.view.GameUI;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.reflect.ReflectionException;

public class GameScreen extends AbstractScreen implements MapListener {
    //todo: добавить карты на экран
    private Entity player;
    private final AssetManager manager;
    private final MapManager mapManager;
    private boolean isMusicLoaded;
    PreferenceManager preferenceManager;

    public GameScreen(Main context) {
        super(context);
        this.manager = context.getAssetManager();

        mapManager = context.getMapManager();
        mapManager.addMapListener(this);
        mapManager.setMap(MapType.MAP_DREAM);
        preferenceManager = new PreferenceManager();

        isMusicLoaded = false;
        for (final AudioType audioType : AudioType.values()) {
            if (audioType.isMusic()) {
                manager.load(audioType.getFilePath(), Music.class);
            }
        }

        player = context.getEcsEngine().createPlayer(mapManager.getCurrentMap().getPlayerStartLocation(),1f,1f);
        context.getGameCamera().position.set((mapManager.getCurrentMap().getPlayerStartLocation()), 0);
        audioManager.playAudio(AudioType.GAMEMUSIC);
    }

    public static Entity getPlayer(final Entity player) {
        return player;
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        context.getGameRenderer().render(alpha);
//
//        Texture texture = new Texture(Gdx.files.internal("map/map.png"));

        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            preferenceManager.saveGameState(player);
            System.out.println("saved");
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_RIGHT)) {
            preferenceManager.loadGameState(player);
            System.out.println("loaded");
        }
//
//        if(Gdx.input.isKeyJustPressed(Input.Keys.P)) {
//            preferenceManager.saveGameState(player);
//            System.out.println("saved");
//        }
//        else if(Gdx.input.isKeyJustPressed(Input.Keys.O)) {
//            preferenceManager.loadGameState(player);
//            System.out.println("loaded");
//        }

        if(ECSEngine.playerCmpMapper.get(player).paperCount == 3) {
            try {
                context.setScreen(ScreenType.WIN);
            } catch (ReflectionException e) {
                throw new RuntimeException("Failed to set WINw screen", e);
            }
        }
        ((GameUI) screenUI).addPaper(ECSEngine.playerCmpMapper.get(player).paperCount);

//        viewport.apply(true);

    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void hide() {
        super.hide();
        audioManager.stopCurrentMusic();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

//    @Override
    protected GameUI getScreenUI(final Main context) {
//        gameUI = new GameUI(context);
//        gameUI.setPlayer(player);
        return new GameUI(context);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void keyPressed(final InputManager inputManager,final GameKeys gameKeys) {
//        gameUI.keyPressed(inputManager, gameKeys);
    }

    @Override
    public void keyUp(final InputManager inputManager,final GameKeys gameKeys) {

    }

    @Override
    public void mapChange(Map map) {

    }
}
