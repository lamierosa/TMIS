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
    private Table optionsTable;
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

        //room
//        TextureRegionDrawable roomRegion = new TextureRegionDrawable(atlasMap.findRegion("Room_sprite_without_sun"));
//        Image room = new Image(roomRegion);
//        room.setAlign(Align.center);
//
//        //hallway
//        TextureRegionDrawable hallwayRegion = new TextureRegionDrawable(atlasMap.findRegion("Hallway_sprite"));
//        Image hallway = new Image(hallwayRegion);
//        hallway.setAlign(Align.center);
//
//        add(room).width(800).height(800).expand().fill().row();
////        room.remove();

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
        initOptionsTable(context);
        add(downButton).width(100).height(100).left().bottom().pad(100).expand();
        add(rightButton).width(100).height(100).left().bottom().pad(100).expand();
        add(leftButton).width(100).height(100).left().bottom().pad(100).expand();
        add(upButton).width(100).height(100).left().bottom().pad(100).expand();
        this.row();
        add(toolBarButton).width(100).height(100).left().top().expand();
        add(optionsTable).expand().center().width(250).height(250);
    }

    private void showOptionBar(boolean show) {
        if (show) {
            makeVisibleOptions(true);
        } else {
            makeVisibleOptions(false);
        }

    }

    private void initOptionsTable (Main context) {
        saveAndExitButton = new TextButton("Save and Exit", context.getSkin(), "normal");
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
//        optionsTable.setBackground(new NinePatchDrawable(new NinePatch(new Texture
//            ("options.jpg"), 1, 1, 1, 1)));
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
