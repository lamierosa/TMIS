package com.LAMIEGames.TMIS.view;

import com.LAMIEGames.TMIS.Main;
import com.LAMIEGames.TMIS.PreferenceManager;
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
import com.badlogic.gdx.scenes.scene2d.ui.Button;
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
    private Table map;
    private Button upButton;
    private Button downButton;
    private Button rightButton;
    private Button leftButton;
    private Button toolBarButton;
    private Button backButton;
    private TextButton resumeButton;
    private TextButton saveAndExitButton;
    public int paperCount;
    private Label paperCountLabel;
    private ECSEngine ecsEngine;
    private Entity player;
    private Button textPanel;

    private final PreferenceManager preferenceManager;


    public GameUI(final Main context) {
        super(context.getSkin());
        this.top();
        setFillParent(true);

        Gdx.input.setInputProcessor(context.getStage());

        atlasMap = new TextureAtlas(Gdx.files.internal("map/map.atlas"));
        preferenceManager = new PreferenceManager();
        ecsEngine = context.getEcsEngine();
        player = setPlayer(player);

        //buttons
        upButton = new Button(context.getSkin(), "up");
        downButton = new Button(context.getSkin(), "down");
        rightButton = new Button(context.getSkin(), "right");
        leftButton = new Button(context.getSkin(), "left");
        toolBarButton = new Button(context.getSkin(), "toolBarButton");
        backButton = new Button(context.getSkin(), "backButton");
        paperCountLabel = new Label("Paper count: " + paperCount, context.getSkin());
        textPanel = new Button(context.getSkin(), "textPanelButton");

        map = new Table();
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


//        mainTable.add(map).expand().center().padRight(400);
//        initMap();


    }

    private void initMap() {
        TextureRegionDrawable roomRegion = new TextureRegionDrawable(atlasMap.findRegion("Room_sprite_without_sun"));
        Image room = new Image(roomRegion);

        TextureRegionDrawable hallwayRegion = new TextureRegionDrawable(atlasMap.findRegion("Hallway_sprite"));
        Image hallway = new Image(hallwayRegion);

        map.add(room).width(800).height(800).expand().fill().center().pad(10);
        map.row(); // Переход на новую строку
//        map.add(hallway).expand().center().pad(10);
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
        textPanelTable.add(textPanel).bottom().right().expand().width(1200).height(400).padBottom(40);
        this.row();

//        initControls();
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
//                    preferenceManager.saveGameState(player);
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

        optionsTable = new Table();
        toolBarTable.add(optionsTable).expand().width(400).height(400);
        optionsTable.setBackground(new NinePatchDrawable(new NinePatch(new Texture
            ("ui/settingsBackground.jpg"), 1, 1, 1, 1)));

        optionsTable.add(resumeButton).width(200).height(50).pad(10).row();
        optionsTable.add(saveAndExitButton).width(200).height(50).pad(10).row();
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
}
