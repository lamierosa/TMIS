package com.LAMIEGames.TMIS.audio;

public enum AudioType {
    MENUMUSIC("audio/menuMusic.mp3", true, 0.3f),
    GAMEMUSIC("audio/gameMusic.mp3", true, 0.3f);

    private final String filePath;
    private final boolean isMusic;
    private final float volume;

    AudioType(String filePath, boolean isMusic, float volume) {
        this.filePath = filePath;
        this.isMusic = isMusic;
        this.volume = volume;
    }

    public String getFilePath() {
        return filePath;
    }

    public boolean isMusic() {
        return isMusic;
    }

    public float getVolume() {
        return volume;
    }
}
