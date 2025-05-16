package com.LAMIEGames.TMIS.view;

import com.LAMIEGames.TMIS.Main;
import com.LAMIEGames.TMIS.input.GameKeys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class LoadingUI extends Table {
    private final TextButton txtBtn;
//    private final ProgressBar progressBar;
    private final TextButton pressAnyKeyBtn;

    public LoadingUI(final Main context) {
        super(context.getSkin());
        setFillParent(true);

//        progressBar = new ProgressBar(0,1,0.01f,false, getSkin());
//        progressBar.setAnimateDuration(1);

        txtBtn = new TextButton("[Red]Loading...", getSkin(), "huge");
        txtBtn.getLabel().setWrap(true);

        pressAnyKeyBtn = new TextButton("Press any key", getSkin(), "normal");
        pressAnyKeyBtn.getLabel().setWrap(true);
        pressAnyKeyBtn.setVisible(false);
        pressAnyKeyBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                context.getInputManager().notifyKeyDown(GameKeys.SELECT);
                return true;
            }
        });

        add(pressAnyKeyBtn).expand().fill().center().row();
        add(txtBtn).expandX().fillX().bottom().row();
//        add(progressBar).expandX().fillX().bottom().pad(20,25,20,25);
        bottom();
    }

    public void setProgress (final float progress){
//        progressBar.setValue(progress);
        if (progress < 1 && !pressAnyKeyBtn.isVisible()){
            pressAnyKeyBtn.setVisible(true);
            pressAnyKeyBtn.setColor(1,1,1,0);
            pressAnyKeyBtn.addAction(Actions.forever(Actions.sequence
                (Actions.alpha(1,1), Actions.alpha(0,1))));
        }
    }
}
