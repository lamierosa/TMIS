package com.LAMIEGames.TMIS.view;

import com.LAMIEGames.TMIS.Main;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class MenuUI extends Table {
    public MenuUI(final Skin skin) {
        super(skin);
        setFillParent(true);
        this.setBackground(new NinePatchDrawable(new NinePatch(new Texture
            ("ui/menu_sprite.png"), 1, 1, 1, 1)));
    }
}
