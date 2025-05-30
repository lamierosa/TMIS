package com.LAMIEGames.TMIS.maps;

import static com.LAMIEGames.TMIS.Main.UNIT_SCALE;

public class CollisionArea {
    private final float x;
    private final float y;
    private final float[] vertices;

    public CollisionArea (float x, float y, float[] vertices) {
        this.x = x * UNIT_SCALE;
        this.y = y * UNIT_SCALE;
        this.vertices = vertices;

        for (int i = 0; i < vertices.length; i+=2) {
            vertices[i] = vertices[i] * UNIT_SCALE;
            vertices[i+1] = vertices[i+1] * UNIT_SCALE;
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float[] getVertices() {
        return vertices;
    }
}
