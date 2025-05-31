package com.LAMIEGames.TMIS.screen;

import com.LAMIEGames.TMIS.Main;
import com.LAMIEGames.TMIS.input.GameKeys;
import com.LAMIEGames.TMIS.input.InputManager;
import com.LAMIEGames.TMIS.view.WinScreenUI;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class WinScreen extends AbstractScreen{
    public WinScreen(Main context) {
        super(context);
    }

    @Override
    protected Table getScreenUI(Main context) {
        return new WinScreenUI(context);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void keyPressed(InputManager manager, GameKeys keys) {

    }

    @Override
    public void keyUp(InputManager manager, GameKeys keys) {

    }
}
