package com.LAMIEGames.TMIS.screen;

import static com.LAMIEGames.TMIS.Main.TAG;

import com.LAMIEGames.TMIS.Main;
import com.LAMIEGames.TMIS.audio.AudioType;
import com.LAMIEGames.TMIS.input.GameKeys;
import com.LAMIEGames.TMIS.input.InputManager;
import com.LAMIEGames.TMIS.view.LoadingUI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.reflect.ReflectionException;

public class LoadingScreen extends AbstractScreen<LoadingUI> {
    private final AssetManager manager;
    private boolean isMusicLoaded;
    private SpriteBatch batch;

    public LoadingScreen(final Main context) {
        super(context);

        this.batch = context.getSpriteBatch();
        this.manager = context.getAssetManager();

        // Загрузка аудио
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
    protected LoadingUI getScreenUI(final Main context) {
        return new LoadingUI(context);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        manager.update();

        if (!isMusicLoaded && manager.isLoaded(AudioType.MENUMUSIC.getFilePath())) {
            isMusicLoaded = true;
            audioManager.playAudio(AudioType.MENUMUSIC);
        }

        screenUI.setProgress(manager.getProgress());

//        // Проверяем, завершена ли загрузка ресурсов
//        if (assMan.manager.update()) {
//            // Если загрузка завершена, инициализируем скин
//            assMan.initializeSkin();
//            assMan.loadImages();
//            // Переход на экран меню после инициализации скина
//            try {
//                context.setScreen(ScreenType.MENU);
//            } catch (ReflectionException e) {
//                throw new GdxRuntimeException("Failed to go on MENU screen", e);
//            }
//        } else {
//            float progress = assMan.manager.getProgress(); // Получаем прогресс загрузки
//            // Выводим прогресс в консоль
//            Gdx.app.debug(TAG, "Progress: " + (progress * 100) + "%");
//        }

//        // Обработка ввода для перехода на экран меню (если нужно)
//        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
//            try {
//                context.setScreen(ScreenType.MENU);
//            } catch (ReflectionException e) {
//                throw new GdxRuntimeException("Failed to go on MENU screen", e);
//            }
//        }
        }

        @Override
        public void show () {
            super.show();

        }

        @Override
        public void hide () {
            super.hide();
            audioManager.stopCurrentMusic();
        }

        @Override
        public void resize ( int width, int height){

        }

        @Override
        public void pause () {

        }

        @Override
        public void resume () {

        }

        @Override
        public void dispose () {

        }

        @Override
        public void keyPressed (InputManager inputManager, GameKeys gameKeys){
            if (manager.getProgress() >= 1) {
                try {
                    context.setScreen(ScreenType.GAME);
                } catch (ReflectionException e) {
                    throw new GdxRuntimeException("Failed to go on GAME screen", e);
                }
            }
        }

        @Override
        public void keyUp (InputManager inputManager, GameKeys gameKeys){

        }
    }

