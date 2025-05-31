package com.LAMIEGames.TMIS.view;

import com.LAMIEGames.TMIS.Main;
import com.LAMIEGames.TMIS.PreferenceManager;
import com.LAMIEGames.TMIS.audio.AudioManager;
import com.LAMIEGames.TMIS.audio.AudioType;
import com.LAMIEGames.TMIS.ecs.ECSEngine;
import com.LAMIEGames.TMIS.input.GameKeys;
import com.LAMIEGames.TMIS.input.InputManager;
import com.LAMIEGames.TMIS.screen.GameScreen;
import com.LAMIEGames.TMIS.screen.ScreenType;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.reflect.ReflectionException;

public class GameUI extends Table {
    private Table toolBarTable;
    private TextureAtlas atlasMap;
    private Table mainTable;
    private Table optionsTable;
    private Table controlsTable;
    private Table directionTable;
    private Table textPanelTable;
    private Button upButton;
    private Button downButton;
    private Button rightButton;
    private Button leftButton;
    private Button toolBarButton;
    private Button backButton;
    private TextButton musicButton;
    private TextButton resumeButton;
    private TextButton saveAndExitButton;
    private CheckBox musicCheckbox;
    public int paperCount;
    private Label paperCountLabel;
    private ECSEngine ecsEngine;
    private Entity player;
    private Button textPanel;

    private final PreferenceManager preferenceManager;
    private final AudioManager audioManager;

    public GameUI(final Main context) {
        super(context.getSkin());
        this.top();
        setFillParent(true);

        Gdx.input.setInputProcessor(context.getStage());

        atlasMap = new TextureAtlas(Gdx.files.internal("map/map.atlas"));
        preferenceManager = new PreferenceManager();
        audioManager = new AudioManager(context);
        ecsEngine = context.getEcsEngine();
        player = setPlayer(player);

        //buttons
        musicButton = new TextButton("Music", context.getSkin(), "normalWhite");
        upButton = new Button(context.getSkin(), "up");
        downButton = new Button(context.getSkin(), "down");
        rightButton = new Button(context.getSkin(), "right");
        leftButton = new Button(context.getSkin(), "left");
        toolBarButton = new Button(context.getSkin(), "toolBarButton");
        backButton = new Button(context.getSkin(), "backButton");
        paperCountLabel = new Label("Paper count: " + paperCount, context.getSkin());
        textPanel = new Button(context.getSkin(), "textPanelButton");

        textPanelTable = new Table();

        mainTable = new Table();
        mainTable.setFillParent(true);

        controlsTable = new Table();
        directionTable = new Table();
        toolBarTable = new Table();

        add(paperCountLabel).left().top().expand().pad(10).padLeft(50).padTop(30);
        add(toolBarTable).top().right().pad(10).padTop(30);
        initOptionsTable(context);
        toolBarTable.add(toolBarButton).top().width(100).height(100).expand().padRight(50);
        this.row();

        init(context);
    }


    public void init(final Main context) {
        toolBarButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // if the options button is clicked, show the options table
                showOptionBar(true);
            }
        });

        directionTable.add(upButton).width(100).height(100).pad(10).padLeft(200);
        directionTable.row(); // Переход на новую строку
        directionTable.add(leftButton).width(100).height(100).pad(10).padRight(50);
        directionTable.add(rightButton).width(100).height(100).pad(10);
        directionTable.row(); // Переход на новую строку
        directionTable.add(downButton).width(100).height(100).pad(10).padLeft(200);

        controlsTable.add(directionTable).bottom().left();

        controlsTable.getColor().a = 0.7f;
        add(controlsTable).bottom().left().padBottom(30);
        add(textPanelTable).right().expandX().padTop(200).padRight(60);
        textPanel.getColor().a = 0.7f;
        textPanelTable.add(textPanel).bottom().right().expand().width(1200).height(350).padBottom(40);
        this.row();

        initControls(context);
    }

    private void initControls(final Main context) {
        upButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                context.getInputManager().notifyKeyDown(GameKeys.UP);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                context.getInputManager().notifyKeyUp(GameKeys.UP);
            }
        });

        downButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                context.getInputManager().notifyKeyDown(GameKeys.DOWN);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                context.getInputManager().notifyKeyUp(GameKeys.DOWN);
            }
        });

        rightButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                context.getInputManager().notifyKeyDown(GameKeys.LEFT);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                context.getInputManager().notifyKeyUp(GameKeys.LEFT);
            }
        });

        leftButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                context.getInputManager().notifyKeyDown(GameKeys.RIGHT);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                context.getInputManager().notifyKeyUp(GameKeys.RIGHT);
            }
        });
    }


    private void showOptionBar(boolean show) {
        if (show) {
            makeVisibleOptions(true);
        } else {
            makeVisibleOptions(false);
        }

    }

    private void initOptionsTable(final Main context) {
        saveAndExitButton = new TextButton("Save and Exit", context.getSkin(), "normalWhite");
        saveAndExitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showOptionBar(false);
                try {
                    preferenceManager.saveGameState(player);
                    context.setScreen(ScreenType.MENU);
                } catch (ReflectionException e) {
                    throw new RuntimeException("Failed to go on MENU", e);
                }
            }
        });

        resumeButton = new TextButton("Resume", context.getSkin(), "normalWhite");
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showOptionBar(false);
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
                    audioManager.playAudio(AudioType.GAMEMUSIC);
                } else {
                    audioManager.stopCurrentMusic();
                }
                return false;
            }
        });

        optionsTable = new Table();
        toolBarTable.add(optionsTable).expand().width(400).height(400);
        optionsTable.setBackground(new NinePatchDrawable(new NinePatch(new Texture
            ("ui/settingsBackground.jpg"), 1, 1, 1, 1)));

        optionsTable.add(resumeButton).width(200).height(50).pad(10).row();
        optionsTable.add(saveAndExitButton).width(200).height(50).pad(10).row();
        optionsTable.add(musicButton).width(200).height(50).pad(10).center();
        optionsTable.add(musicCheckbox).width(50).height(50).pad(10).row();
        makeVisibleOptions(false);
    }

    private void makeVisibleOptions(boolean show) {
        if (show) {
            optionsTable.setVisible(true);
            toolBarButton.setVisible(false);
        } else {
            optionsTable.setVisible(false);
            toolBarButton.setVisible(true);
        }
    }

    public Entity setPlayer(Entity player) {
        this.player = GameScreen.getPlayer(player);
        return player;
    }

    public void keyPressed(InputManager inputManager, GameKeys gameKeys) {

    }

    public void addPaper(int newItemCount) {
        paperCount = newItemCount;
        paperCountLabel.setText("Paper Count: " + paperCount + "/3");
    }

    public void resetPaper(int paperCount) {
        if(paperCount != 0) {
            paperCount = 0;
        }
    }
}
