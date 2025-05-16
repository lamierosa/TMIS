package com.LAMIEGames.TMIS.input;

import com.badlogic.gdx.Input;

public enum GameKeys {
    UP (Input.Keys.W, Input.Keys.UP),
    DOWN (Input.Keys.S, Input.Keys.DOWN),
    LEFT (Input.Keys.D, Input.Keys.LEFT),
    RIGHT (Input.Keys.A, Input.Keys.RIGHT),
    SELECT (Input.Keys.ENTER, Input.Keys.F),
    BACK (Input.Keys.ESCAPE),
    TOOL (Input.Keys.SPACE);

    final int[] keyCode;

    GameKeys(final int... keyCode) {
        this.keyCode = keyCode;
    }

    public int[] getKeyCode() {
        return keyCode;
    }
}
