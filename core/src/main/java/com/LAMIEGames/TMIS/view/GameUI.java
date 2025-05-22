package com.LAMIEGames.TMIS.view;

import com.LAMIEGames.TMIS.Main;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class GameUI extends Table {

    public GameUI(final Main context) {
        super(context.getSkin());
        setFillParent(true);
    }
}
