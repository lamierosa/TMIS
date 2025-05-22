package com.LAMIEGames.TMIS.view;

public enum AnimationType {
    PLAYER_DOWN("data/player/xana/player_xana.atlas", "player_xana_front", 0.05f, 0),
    PLAYER_UP("data/player/xana/player_xana.atlas", "player_xana_back", 0.05f, 0),
    PLAYER_LEFT("data/player/xana/player_xana.atlas", "player_xana_left", 0.05f, 0),
    PLAYER_RIGHT("data/player/xana/player_xana.atlas", "player_xana_right", 0.05f, 0);

    private final String atlasPath;
    private final String atlasKey;
    private final float frameTime;
    private final int spriteIndex;

    AnimationType(String atlasPath, String atlasKey, float frameTime, int spriteIndex) {
        this.atlasPath = atlasPath;
        this.atlasKey = atlasKey;
        this.frameTime = frameTime;
        this.spriteIndex = spriteIndex;
    }

    public String getAtlasPath() {
        return atlasPath;
    }

    public String getAtlasKey() {
        return atlasKey;
    }

    public float getFrameTime() {
        return frameTime;
    }

    public int getSpriteIndex() {
        return spriteIndex;
    }
}
