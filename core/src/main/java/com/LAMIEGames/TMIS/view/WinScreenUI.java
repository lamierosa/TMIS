package com.LAMIEGames.TMIS.view;

import com.LAMIEGames.TMIS.Main;
import com.LAMIEGames.TMIS.screen.ScreenType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.reflect.ReflectionException;

public class WinScreenUI extends Table {
    public WinScreenUI(final Main context) {
        super(context.getSkin());
        this.setFillParent(true);

//        this.setDebug(true);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //add game over title Lable
        this.add(new TextButton("You Win!", context.getSkin(), "hugeWhite").pad(20));
        this.row();

        //add game over message Label
        this.add(new TextButton("You have returned into your life!", context.getSkin(), "bigWhite").pad(10));
        this.row();

        TextButton playAgainButton = new TextButton("Play Again", context.getSkin(), "hugeWhite");
        playAgainButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    context.setScreen(ScreenType.MENU);
                } catch (ReflectionException e) {
                    throw new RuntimeException("Failed to go on MENU screen", e);
                }
            }
        });
        this.add(playAgainButton).pad(10);
        this.row();

    }
}
