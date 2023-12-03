package net.ilikefood971.maze;

import java.util.ArrayList;

public class MazeRow {
    
    private final ArrayList<MazeLocation> locations = new ArrayList<>();
    private final int length;
    private final boolean wallRow;
    
    public MazeRow(int length, boolean wallRow) {
        this.length = length;
        this.wallRow = wallRow;
    }
    
    public void init() {
        for (int i = 0; i < this.length; i++) {
            locations.add(new MazeLocation(this, i, this.wallRow));
            if (i == this.length - 1) continue;
            locations.add(new MazeLocation(this, ++i, true));
        }
    }
    
    public MazeLocation get(int i) {
        return locations.get(i);
    }
    
    public ArrayList<MazeLocation> getLocations() {
        return locations;
    }
}
