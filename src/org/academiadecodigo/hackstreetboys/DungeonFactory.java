package org.academiadecodigo.hackstreetboys;

public class DungeonFactory {

    public static Dungeon dungeonFactory(int y, int x) {
        return new Dungeon(new Position(y, x));
    }
}
