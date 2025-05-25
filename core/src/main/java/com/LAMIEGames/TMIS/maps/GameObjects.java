package com.LAMIEGames.TMIS.maps;

import com.badlogic.gdx.math.Vector2;

public class GameObjects {
    private final GameObjectType type;
    private final Vector2 position;
    private final float width;
    private final float height;
    private final int animationIndex;

    public GameObjects(GameObjectType type, Vector2 position,
                       float width, float height, int animationIndex) {
        this.type = type;
        this.position = position;
        this.width = width;
        this.height = height;
        this.animationIndex = animationIndex;
    }

    public GameObjectType getType() {
        return type;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public int getAnimationIndex() {
        return animationIndex;
    }
}
