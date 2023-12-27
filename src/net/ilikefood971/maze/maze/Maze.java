package net.ilikefood971.maze.maze;

public class Maze {
    public final int xLength;
    public final int yLength;
    public final MazeLocation start;
    public final MazeLocation end;
    private final MazeLocation[][] mazeLocations;
    
    public Maze(int xLength, int yLength) {
        if (xLength % 2 == 0) xLength++;
        if (yLength % 2 == 0) yLength++;
        
        this.xLength = xLength;
        this.yLength = yLength;
        int startX = 1;
        int endX = xLength - 2;
        
        mazeLocations = new MazeLocation[xLength][yLength];
        for (int x = 0; x < this.xLength; x++) {
            for (int y = 0; y < this.yLength; y++) {
                this.mazeLocations[x][y] = new MazeLocation(x, y);
            }
        }
        
        this.start = mazeLocations[startX][0];
        this.end = mazeLocations[endX][yLength - 1];
        MazeGenerator generator = new MazeGenerator(this);
        Solver solver = new Solver(this);
        
        generator.generate();
        solver.solve(this.start);
    }
    
    public MazeLocation[][] getMazeLocations() {
        return mazeLocations;
    }
}
