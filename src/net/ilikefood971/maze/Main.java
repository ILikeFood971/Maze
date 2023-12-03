package net.ilikefood971.maze;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static final int ROWS = 50;
    public static int COLUMNS = 50;
    public static ArrayList<MazeRow> maze = new ArrayList<>();
    public static MazeLocation start;
    public static final Random RANDOM = new Random();
    public static final int START_X = 1;
    public static int END_X;
    
    public static void main(String[] args) throws InterruptedException {
        COLUMNS -= 2;
        END_X = COLUMNS;
        
        for (int i = 0; i < ROWS - 2; i++) {
            MazeRow mazeRow = new MazeRow(COLUMNS, i % 2 != 0);
            maze.add(mazeRow);
            mazeRow.init();
        }
        
        start = getByCoords(0, 0);
        
        start.run();
        
        printMaze();
        
        Scanner scanner = new Scanner(System.in);
        while (!scanner.nextLine().equalsIgnoreCase("quit")) {
            System.out.println("\n\n----------------------------------------------------------------\n\n");
            start.run();
            printMaze();
        }
    }
    
    public static MazeLocation getByCoords(int x, int y) {
        if (x < 0 || x >= maze.get(0).getLocations().size() || y < 0 || y >= maze.size()) return null;
        return maze.get(y).get(x);
    }
    
    public static void printMaze() {
        boolean extraEndColumn = COLUMNS % 2 != 0;
        
        MazeRow firstRow = new MazeRow(COLUMNS, true);
        maze.add(0, firstRow);
        firstRow.init();
        firstRow.get(START_X - 1).removeWall();
        
        if (ROWS % 2 != 0) {
            MazeRow lastRow = new MazeRow(COLUMNS, true);
            maze.add(lastRow);
            lastRow.init();
            lastRow.get(extraEndColumn ? END_X - 1 : END_X - 2).removeWall();
        } else getByCoords(END_X - 2, ROWS - 2).removeWall();
        
        System.out.println();
        for (MazeRow row : maze) {
            System.out.print("⬛");
            for (MazeLocation location : row.getLocations()) {
                if (!location.isWall() || !location.exists() /*&& (!location.isVisited() || location.isWall())*/) {
                    System.out.print("⬜"); // Cell
                } else {
                    System.out.print("⬛"); // Wall
                }
            }
            //noinspection ConstantValue
            if (extraEndColumn) {
                System.out.println("⬛"); // Move to the next row
            } else System.out.println();
        }
    }
    
    
}
