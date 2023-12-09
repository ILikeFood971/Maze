package net.ilikefood971.maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static net.ilikefood971.maze.Main.getByCoords;

public class MazeLocation {
    public final int y;
    public final int x;
    private boolean visited = false;
    private final boolean wall;
    private boolean wallExists = true;
    private boolean isSolution = false;
    
    public MazeLocation(MazeRow row, int x, boolean wall) {
        this.y = Main.maze.indexOf(row);
        this.x = x;
        this.wall = wall;
    }
    
    public void run() {
        Stack<MazeLocation> stack = new Stack<>();
        stack.push(this);
        
        while (!stack.isEmpty()) {
            MazeLocation current = stack.pop();
            current.markVisited();
            
            MazeLocation next = current.findRandomValid();
            if (next != null) {
                MazeLocation wall = getByCoords(((current.x - next.x) / -2) + current.x, ((current.y - next.y) / -2) + current.y);
                wall.removeWall();
                
                stack.push(current); // Push current location back onto the stack
                stack.push(next);    // Push the next location onto the stack
            }
        }
    }
    
    public void markVisited() {
        this.visited = true;
    }
    
    private static <T> void addIfNotNull(T item, List<T> list) {
        if (item != null) list.add(item);
    }
    
    public boolean isVisited() {
        return visited;
    }
    public boolean isWall() {
        return wall;
    }
    public void removeWall() {
        if (!this.isWall()) return;
        wallExists = false;
    }
    public boolean exists() {
        return wallExists;
    }
    
    public boolean isSolution() {
        return isSolution;
    }
    
    public MazeLocation findRandomValid() {
        if (this.isWall()) return null;
        ArrayList<MazeLocation> surrounding = getSurrounding();
        ArrayList<MazeLocation> valid = new ArrayList<>();
        // For every location around this location
        for (MazeLocation location : surrounding) {
            // Check the surrounding one to see if it has any empty next to it
            if (location.isVisited()) {
                continue;
            }
            valid.add(location);
        }
        if (valid.isEmpty()) return null;
        return valid.get(Main.RANDOM.nextInt(valid.size()));
    }
    
    private ArrayList<MazeLocation> getSurrounding() {
        if (this.isWall()) {
            return null;
        }
        ArrayList<MazeLocation> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    addIfNotNull(getByCoords(this.x, this.y - 2), list); // Above
                    break;
                case 1:
                    addIfNotNull(getByCoords(this.x, this.y + 2), list); // Below
                    break;
                case 2:
                    addIfNotNull(getByCoords(this.x + 2, this.y), list); // Right
                    break;
                case 3:
                    addIfNotNull(getByCoords(this.x - 2, this.y), list); // Left
                    break;
            }
        }
        return list;
    }
}
