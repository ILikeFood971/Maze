package net.ilikefood971.maze.maze;

public class MazeLocation {
    public final int x;
    public final int y;
    private boolean visited = false;
    private boolean wall = false;
    private boolean isSolution = false;
    
    public MazeLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void markVisited() {
        this.visited = true;
    }
    public void removeWall() {
        if (this.wall) {
            this.wall = false;
        }
    }
    public void markWall() {
        this.wall = true;
    }
    
    public boolean isWall() {
        return wall;
    }
    
    public boolean isVisited() {
        return visited;
    }
    
    public boolean isSolution() {
        return isSolution;
    }
    
    public void setSolution(boolean solution) {
        isSolution = solution;
    }
}
