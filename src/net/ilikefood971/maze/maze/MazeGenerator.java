package net.ilikefood971.maze.maze;


import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MazeGenerator {
    public static final Random RANDOM = new Random();
    private final Maze maze;
    
    public MazeGenerator(Maze maze) {
        this.maze = maze;
    }
    
    public void generate() {
        int xLength = maze.xLength;
        int yLength = maze.yLength;
        
        for (int x = 0; x < xLength; x++) {
            for (int y = 0; y < yLength; y++) {
                MazeLocation location = maze.getMazeLocations()[x][y];
                
                boolean outerEdge =
                        (x == 0 || x == xLength - 1) || (y == 0 || y == yLength - 1); // Check to see if it is the outer edge
                boolean evenWall =
                        x % 2 == 0 || y % 2 == 0;
                boolean startOrEnd =
                        location == maze.start || location == maze.end;
                if ((outerEdge || evenWall) && !startOrEnd) {
                    location.markWall();
                }
            }
        }
        
        
        Stack<MazeLocation> stack = new Stack<>();
        stack.push(maze.getMazeLocations()[maze.start.x][maze.start.y + 1]);
        
        while (!stack.isEmpty()) {
            MazeLocation current = stack.pop();
            current.markVisited();
            
            MazeLocation next = findRandomValid(current);
            if (next != null) {
                MazeLocation wall = maze.getMazeLocations()[(current.x + next.x) / 2][(current.y + next.y) / 2];
                wall.removeWall();
                
                stack.push(current); // Push current location back onto the stack
                stack.push(next);    // Push the next location onto the stack
            }
        }
        
    }
    public MazeLocation findRandomValid(MazeLocation location) {
        ArrayList<MazeLocation> surrounding = getSurrounding(location);
        
        ArrayList<MazeLocation> valid = new ArrayList<>();
        // For every location around this location
        for (MazeLocation check : surrounding) {
            // Check the surrounding one to see if it has any empty next to it
            if (check.isVisited()) {
                continue;
            }
            valid.add(check);
        }
        if (valid.isEmpty()) return null;
        return valid.get(MazeGenerator.RANDOM.nextInt(valid.size()));
    }
    
    private ArrayList<MazeLocation> getSurrounding(MazeLocation location) {
        ArrayList<MazeLocation> list = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            try {
                switch (Directions.getByInt(i)) {
                    case UP:
                        list.add(maze.getMazeLocations()[location.x][location.y - 2]);
                        break;
                    case DOWN:
                        list.add(maze.getMazeLocations()[location.x][location.y + 2]);
                        break;
                    case LEFT:
                        list.add(maze.getMazeLocations()[location.x - 2][location.y]);
                        break;
                    case RIGHT:
                        list.add(maze.getMazeLocations()[location.x + 2][location.y]);
                        break;
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
        }
        return list;
    }
    
    private enum Directions {
        UP,
        DOWN,
        LEFT,
        RIGHT;
        
        public static Directions getByInt(int i) {
            return switch (i) {
                case 0 -> UP;
                case 1 -> DOWN;
                case 2 -> LEFT;
                case 3 -> RIGHT;
                default -> null;
            };
        }
    }
}
