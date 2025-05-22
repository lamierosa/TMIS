package com.LAMIEGames.TMIS.view;

import com.LAMIEGames.TMIS.Main;
import com.LAMIEGames.TMIS.input.GameKeys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class LoadingUI extends Table {
    private final TextButton txtBtn;
    private final ProgressBar progressBar;
    private final TextButton pressAnyKeyBtn;

    public LoadingUI(final Main context) {
        super(context.getSkin());
        setFillParent(true);
        this.setBackground(new NinePatchDrawable(new NinePatch(new Texture
            ("ui/menu_sprite.png"), 1, 1, 1, 1)));

        progressBar = new ProgressBar(0,1,0.01f,false, getSkin());
        progressBar.setAnimateDuration(1);

        txtBtn = new TextButton("Press anywhere", getSkin(), "hugeWhite");
        txtBtn.getLabel().setWrap(true);

        pressAnyKeyBtn = new TextButton("Loading...", getSkin(), "hugeWhite");
        txtBtn.getLabel().setWrap(true);
        txtBtn.setVisible(false);
        txtBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                context.getInputManager().notifyKeyDown(GameKeys.SELECT);
                return true;
            }
        });

        add(txtBtn).expand().fill().bottom().row();
        add(pressAnyKeyBtn).expandX().fillX().bottom().row();
        add(progressBar).expandX().fillX().bottom().pad(20,25,20,25);
        bottom();
    }

    public void setProgress (final float progress){
        progressBar.setValue(progress);
        if (progress < 1 && !txtBtn.isVisible()){
            txtBtn.setVisible(true);
            txtBtn.setColor(1,1,1,0);
            txtBtn.addAction(Actions.forever(Actions.sequence
                (Actions.alpha(1,1), Actions.alpha(0,1))));
        }
    }
}
