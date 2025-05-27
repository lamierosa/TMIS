package com.LAMIEGames.TMIS.view;

import static com.LAMIEGames.TMIS.Main.UNIT_SCALE;

import com.LAMIEGames.TMIS.Main;
import com.LAMIEGames.TMIS.maps.MapRenderer;
import com.LAMIEGames.TMIS.maps.MapType;
import com.LAMIEGames.TMIS.screen.ScreenType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.reflect.ReflectionException;

import java.nio.Buffer;

public class GameUI extends Table {
    private TextureAtlas atlasMap;
    private Table mainTable;
    private Table optionsTable;
    private Table controlsTable;
    private Table directionTable;
    private Table textPanel;
    private Table map;
    private Button upButton;
    private Button downButton;
    private Button rightButton;
    private Button leftButton;
    private Button toolBarButton;
    private Button backButton;
    private TextButton saveAndExitButton;

    public GameUI(final Main context) {
        super(context.getSkin());
        this.top();
        setFillParent(true);

        Gdx.input.setInputProcessor(context.getStage());

        atlasMap = new TextureAtlas(Gdx.files.internal("map/map.atlas"));

        //buttons
        upButton = new Button(context.getSkin(), "up");
        downButton = new Button(context.getSkin(), "down");
        rightButton = new Button(context.getSkin(), "right");
        leftButton = new Button(context.getSkin(), "left");
        toolBarButton = new Button(context.getSkin(), "toolBarButton");
        backButton = new Button(context.getSkin(), "backButton");

        map = new Table();

        add(map).expand().fill();
        this.row();

        mainTable = new Table();
        mainTable.setFillParent(true);

        add(mainTable);

        controlsTable = new Table();
        directionTable = new Table();

        init(context);

        mainTable.add(map).expand().center().padRight(400);
        initMap();
        mainTable.add(toolBarButton).width(100).height(100).top().right().pad(10).padTop(100);
        this.row();
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


        mainTable.add(optionsTable).expand().center().width(500).height(500);
        this.row();

        directionTable.add(upButton).width(100).height(100).pad(10).padLeft(200);
        directionTable.row(); // Переход на новую строку
        directionTable.add(leftButton).width(100).height(100).pad(10).padRight(50);
        directionTable.add(rightButton).width(100).height(100).pad(10);
        directionTable.row(); // Переход на новую строку
        directionTable.add(downButton).width(100).height(100).pad(10).padLeft(200);

        controlsTable.add(directionTable).bottom().left();
        controlsTable.padLeft(50).padBottom(50);

        mainTable.add(controlsTable).bottom().left().padRight(200);
        this.row();

        initOptionsTable(context);
    }

    private void showOptionBar(boolean show) {
        if (show) {
            makeVisibleOptions(true);
        } else {
            makeVisibleOptions(false);
        }

    }

    private void initOptionsTable (Main context) {
        saveAndExitButton = new TextButton("Save and Exit", context.getSkin(), "normalWhite");
        saveAndExitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showOptionBar(false);
                try {
                    context.setScreen(ScreenType.MENU);
                } catch (ReflectionException e) {
                    throw new RuntimeException("Failed to go on MENU", e);
                }
            }
        });

        optionsTable = new Table();
        optionsTable.setBackground(new NinePatchDrawable(new NinePatch(new Texture
            ("ui/settingsBackground.jpg"), 1, 1, 1, 1)));
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
}
