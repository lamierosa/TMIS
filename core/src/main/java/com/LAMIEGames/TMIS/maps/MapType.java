package com.LAMIEGames.TMIS.maps;

public enum MapType {
    MAP_DREAM("map/mapTile/map_dream.tmx");
    private final String filePath;

    MapType( final String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }


//    ROOM("map/map.png", "Room_sprite_without_sun", 0),
//    HALLWAY("map/map.png", "Hallway_sprite", 1),
//    GATES("map/map.png", "Gates_sprite", 2),
//    PARK("map/map.png", "Park_sprite", 3),
//    MARKET("map/map.png", "Market_sprite", 4),
//    PLAYGROUND("map/map.png", "Playground_sprite", 5),
//    WASTELAND("map/map.png", "Wasteland_sprite", 6),
//    FOREST("map/map.png", "Forest_sprite", 7);
}
