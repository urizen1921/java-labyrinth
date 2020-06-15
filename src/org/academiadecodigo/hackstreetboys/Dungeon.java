package org.academiadecodigo.hackstreetboys;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

public class Dungeon {

    private Rectangle rectangle;
    private static final int CELL_SIZE = 10;
    private static final int PADDING = 10;
    private Position pos;
    private int x;
    private int y;
    private boolean explored = false;
    private boolean exploredByMap = true;
    private boolean destination = false;

    public Dungeon(Position pos) {
        this.pos = pos;
        this.rectangle = new Rectangle(CELL_SIZE * pos.getY() + PADDING, CELL_SIZE * pos.getX() + PADDING, CELL_SIZE, CELL_SIZE);
    }

    public void drawRooms () {
        rectangle.setColor(Color.WHITE);
        rectangle.draw();
    }

    public boolean isExplored() {
        return explored;
    }

    public void setExplored(boolean explored) {
        rectangle.setColor(Color.YELLOW);
        rectangle.draw();
        rectangle.fill();
        this.explored = explored;
    }

    public void setExploredByMap(boolean explored) {
        rectangle.setColor(Color.BLUE);
        rectangle.draw();
        rectangle.fill();
        this.exploredByMap = exploredByMap;
    }

    public void destination() {
        rectangle.setColor(Color.RED);
        rectangle.draw();
        rectangle.fill();
        destination = true;
    }

    public boolean isDestination() {
        return destination;
    }

    public Position getPos() {
        return pos;
    }
}
