package net.ilikefood971.maze;

import java.util.ArrayList;
import java.util.List;

import static net.ilikefood971.maze.Main.getByCoords;

public class MazeLocation {
    public final int y;
    public final int x;
    private boolean visited = false;
    private final boolean wall;
    private boolean wallExists = true;
    
    public MazeLocation(MazeRow row, int x, boolean wall) {
        this.y = Main.maze.indexOf(row);
        this.x = x;
        this.wall = wall;
    }
    
    public void run() throws InterruptedException {
        while (true) {
            this.markVisited();
            
            MazeLocation next = this.findRandomValid();
            if (next == null) {
                break;
            }
            MazeLocation wall = getByCoords(((this.x - next.x) / -2) + this.x, ((this.y - next.y) / -2) + this.y);
            wall.removeWall();
            
            next.run();
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
