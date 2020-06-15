package org.academiadecodigo.hackstreetboys;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

import java.util.LinkedList;
import java.util.List;

public class Labyrinth {

    private Dungeon[][] dungeons;
    private Rectangle rectangle;
    private static final int PADDING = 10;
    private static final int CELL_SIZE = 10;
    private int col;
    private int row;
    private int height;
    private int width;
    private List<Dungeon> exploredPath;
    private int currentX;
    private int currentY;
    private int possibleExit = 4;

    public Labyrinth (int col, int row) {
        this.col = col;
        this.row = row;
        rectangle = new Rectangle(PADDING, PADDING, col * CELL_SIZE, row * CELL_SIZE);
    }

    public void drawLabyrinth() {
        rectangle.setColor(Color.BLACK);
        rectangle.draw();
        rectangle.fill();
        drawDungeons();
        setUp();

        explorationMovement();

        findLastPossibleExit();

        System.out.println("This will be the destination of the maze!!!!");
        System.out.println("Current Y: " + currentY + " Current X: " + currentX);
        dungeons[currentY][currentX].destination();


        exploringWithMap();
    }

    public void drawDungeons() {
        dungeons = new Dungeon[col][row];

        for(int y = 0; y < col; y++) {
            for(int x = 0; x < row; x++) {
                dungeons[y][x] = DungeonFactory.dungeonFactory(y, x);
                dungeons[y][x].drawRooms();
            }
        }
    }

    public void setUp() {
        exploredPath = new LinkedList<>();

        int firstPathX = (int) Math.ceil(Math.random() * col - 1);
        int firstPathY = (int) Math.ceil(Math.random() * row - 1);
        currentX = firstPathX;
        currentY = firstPathY;
        System.out.println("Y: " + firstPathY + " / X: " + firstPathX);
        exploredPath.add(dungeons[firstPathY][firstPathX]);
        dungeons[firstPathY][firstPathX].setExplored(true);
        System.out.println("dungeon Y: " + dungeons[firstPathY][firstPathX].getPos().getY() + " dungeon X:" + dungeons[firstPathY][firstPathX].getPos().getX());
    }


    public void explorationMovement() {
        while (possibleExit > 0) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int randomDirection = (int) Math.ceil(Math.random() * Direction.values().length);

            switch (randomDirection) {
                case 1:
                    if (currentX > 0) {
                        if (!dungeons[currentY][currentX - 1].isExplored()) {
                            currentX -= 1;
                            dungeons[currentY][currentX].setExplored(true);
                            exploredPath.add(dungeons[currentY][currentX]);
                            possibleExit++;
                            break;
                        }
                    }
                    possibleExit --;
                    randomDirection = 2;
                case 2:
                    if (currentX < 49) {
                        if (!dungeons[currentY][currentX + 1].isExplored()) {
                            currentX += 1;
                            dungeons[currentY][currentX].setExplored(true);
                            exploredPath.add(dungeons[currentY][currentX]);
                            possibleExit++;
                            break;
                        }
                    }
                    possibleExit --;
                    randomDirection = 3;
                case 3:
                    if (currentY < 49) {
                        if (!dungeons[currentY + 1][currentX].isExplored()) {
                            currentY += 1;
                            dungeons[currentY][currentX].setExplored(true);
                            exploredPath.add(dungeons[currentY][currentX]);
                            possibleExit++;
                            break;
                        }
                    }
                    possibleExit --;
                    randomDirection = 4;
                case 4:
                    if (currentY > 0) {
                        if (!dungeons[currentY - 1][currentX].isExplored()) {
                            currentY -= 1;
                            dungeons[currentY][currentX].setExplored(true);
                            exploredPath.add(dungeons[currentY][currentX]);
                            possibleExit++;
                            break;
                        }
                    }
                    possibleExit --;
                    randomDirection = 1;
                default:
                    if (currentY > 0) {
                        if (!dungeons[currentY - 1][currentX].isExplored()) {
                            currentY -= 1;
                            dungeons[currentY][currentX].setExplored(true);
                            exploredPath.add(dungeons[currentY][currentX]);
                        }
                    }
                    break;
            }
        }

    }

    public void exploringWithMap () {
        int index = 0;
        Dungeon currentDungeon = exploredPath.get(index);
        currentY = currentDungeon.getPos().getY();
        currentX = currentDungeon.getPos().getX();
        dungeons[currentY][currentX].setExploredByMap(true);
        while (!dungeons[currentY][currentX].isDestination()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            index++;
            currentDungeon = exploredPath.get(index);
            currentY = currentDungeon.getPos().getY();
            currentX = currentDungeon.getPos().getX();
            dungeons[currentY][currentX].setExploredByMap(true);
        }
    }

    //TODO: this method needs to be very well thought also regarding its positioning
    public void findLastPossibleExit() {
        boolean exit = false;
        Dungeon lastDungeon;
        while (!exit) {
            lastDungeon = exploredPath.get(exploredPath.size() - 1);
            System.out.println(exploredPath.size());
            if(!dungeons[lastDungeon.getPos().getY() - 1][lastDungeon.getPos().getX()].isExplored()) {
                explorationMovement();
                possibleExit = 4;
                exit = true;
            } else if (!dungeons[lastDungeon.getPos().getY() + 1][lastDungeon.getPos().getX()].isExplored()) {
                explorationMovement();
                possibleExit = 4;
                exit = true;
            } else if (!dungeons[lastDungeon.getPos().getY()][lastDungeon.getPos().getX() - 1].isExplored()) {
                explorationMovement();
                possibleExit = 4;
                exit = true;
            } else if (!dungeons[lastDungeon.getPos().getY()][lastDungeon.getPos().getX() + 1].isExplored()) {
                explorationMovement();
                possibleExit = 4;
                exit = true;
            }
            exploredPath.remove(exploredPath.size() - 1);
        }
        //System.out.println("List size " + exploredPath.size() + " Last Index: " + exploredPath.indexOf(exploredPath.get(exploredPath.size() - 1)));

    }

    public int getHeight() {
        return this.rectangle.getHeight();
    }

    public int getWidth() {
        return this.rectangle.getWidth();
    }
}
