package com.LAMIEGames.TMIS.screen;

import com.badlogic.gdx.Screen;

public enum ScreenType {
    GAME(GameScreen.class),
    MENU(MenuScreen.class),
    LOADING(LoadingScreen.class);
//    GAMEOVER(GameOver.class),
//    WIN(WinScreen.class);

    private final Class<? extends Screen> screenClass;

    ScreenType(final Class<? extends Screen> screenClass) {
        this.screenClass = screenClass;
    }

    public Class<? extends Screen> getScreenClass() {
        return screenClass;
    }
}
