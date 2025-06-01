package com.LAMIEGames.TMIS.view;

import static com.LAMIEGames.TMIS.ecs.ECSEngine.player;

import com.LAMIEGames.TMIS.Main;
import com.LAMIEGames.TMIS.PreferenceManager;
import com.LAMIEGames.TMIS.audio.AudioManager;
import com.LAMIEGames.TMIS.audio.AudioType;
import com.LAMIEGames.TMIS.ecs.ECSEngine;
import com.LAMIEGames.TMIS.input.GameKeys;
import com.LAMIEGames.TMIS.screen.ScreenType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.reflect.ReflectionException;

public class MenuUI extends Table {
    private final TextButton playNewButton;
    private final TextButton playSavedButton;
    private final TextButton musicButton;
    private final TextButton exitButton;
    private CheckBox musicCheckbox;

    private final TextButton title;
    private final PreferenceManager preferenceManager;
    private final AudioManager audioManager;
    private Table mainTable;
    private GameUI gameUI;

    private Main context;

    public MenuUI(final Main context) {
        super(context.getSkin());
        setFillParent(true);
        this.context = context;
        preferenceManager = new PreferenceManager();
        audioManager = new AudioManager(context);

        this.setBackground(new NinePatchDrawable(new NinePatch(new Texture
            ("ui/menu_sprite.png"), 1, 1, 1, 1)));
        title = new TextButton("This Morning in Samsara", context.getSkin(), "huge");

        mainTable = new Table();
        add(mainTable).top().left().expand().padTop(200).padLeft(100);

        title.getLabel().setWrap(true);
        mainTable.add(title).width(500).left().row();

        Gdx.input.setInputProcessor(new InputMultiplexer(context.getInputManager(), context.getStage()));

        playNewButton = new TextButton("New game", context.getSkin(), "big");
        playNewButton.getLabel().setWrap(true);

        playSavedButton = new TextButton("Load game", context.getSkin(), "big");
        playNewButton.getLabel().setWrap(true);

        musicButton = new TextButton("Music", context.getSkin(), "big");
        musicButton.getLabel().setWrap(true);

        exitButton = new TextButton("Exit", context.getSkin(), "big");
        exitButton.getLabel().setWrap(true);

        playNewButton.addListener(new ChangeListener() {
             @Override
             public void changed(ChangeEvent event, Actor actor) {
                 try {
                    context.setScreen(ScreenType.LOADING);
                 } catch (ReflectionException e) {
                    throw new RuntimeException("Failed to load LOADING screen", e);
                 }
             }
        });

        playSavedButton.addListener(new ChangeListener() {
             @Override
             public void changed(ChangeEvent event, Actor actor) {
                 try {
                    context.getInputManager().notifyKeyDown(GameKeys.LOAD);
                    context.setScreen(ScreenType.GAME);
                 } catch (ReflectionException e) {
                    throw new RuntimeException("Failed to load GAME screen", e);
                 }
             }
        });

        musicCheckbox = new CheckBox(null, context.getSkin());
        musicCheckbox.setChecked( context.getPreferences().isMusicEnabled() );
        musicCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicCheckbox.isChecked();
                context.getPreferences().setMusicEnabled( enabled );
                if (enabled) {
                    audioManager.playAudio(AudioType.MENUMUSIC);
                } else {
                    audioManager.stopCurrentMusic();
                }
                return false;
            }
        });

        exitButton.addListener(new ChangeListener() {
                                   @Override
                                   public void changed(ChangeEvent event, Actor actor) {
                                       Gdx.app.exit();
                                   }
                               }
        );

        mainTable.add(playNewButton).width(400).height(50).pad(10).padTop(50).center().row();
        mainTable.add(playSavedButton).width(200).height(50).pad(10).center().row();
        mainTable.add(musicButton).width(200).height(50).pad(10).center();
        mainTable.add(musicCheckbox).width(50).height(50).pad(10).row();
        mainTable.add(exitButton).width(200).height(50).pad(10).center().row();
    }
}
