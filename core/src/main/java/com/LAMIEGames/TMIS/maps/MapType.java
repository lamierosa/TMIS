package com.LAMIEGames.TMIS.maps;

public enum MapType {
    ROOM("map/map.png", "Room_sprite_without_sun", 0),
    HALLWAY("map/map.png", "Hallway_sprite", 1),
    GATES("map/map.png", "Gates_sprite", 2),
    PARK("map/map.png", "Park_sprite", 3),
    MARKET("map/map.png", "Market_sprite", 4),
    PLAYGROUND("map/map.png", "Playground_sprite", 5),
    WASTELAND("map/map.png", "Wasteland_sprite", 6),
    FOREST("map/map.png", "Forest_sprite", 7);

    private final String atlasPath;
    private final String atlasRegion;
    private final int mapIndex;


    MapType(String atlasPath, String atlasRegion, int mapIndex) {
        this.atlasPath = atlasPath;
        this.atlasRegion = atlasRegion;
        this.mapIndex = mapIndex;
    }

    public String getAtlasPath() {
        return atlasPath;
    }

    public String getAtlasRegion() {
        return atlasRegion;
    }

    public int getMapIndex() {
        return mapIndex;
    }
}
