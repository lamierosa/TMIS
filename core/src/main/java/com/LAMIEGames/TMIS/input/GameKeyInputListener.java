package com.LAMIEGames.TMIS.input;

public interface GameKeyInputListener {
    void keyPressed(final InputManager inputManager, final GameKeys gameKeys);

    void keyUp(final InputManager inputManager, final GameKeys gameKeys);
}
