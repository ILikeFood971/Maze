package net.ilikefood971.maze;

import java.util.ArrayList;

public class Wall {
    private float x;
    private float y;
    private boolean exists = true;
    private static final ArrayList<ArrayList<Wall>> locations = new ArrayList<>(); // x, y
    
    public Wall(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public void remove() { this.exists = false; }
    public boolean getExists() { return this.exists; }
    
    public static void genWalls(int width, int height) {
        for (float i = -0.5f; i < width; i++) { // x
            ArrayList<Wall> walls = new ArrayList<>(height);
            locations.add(walls);
            for (float j = -0.5f; j < height; j++) { // y
                walls.add(new Wall(i, j));
            }
        }
    }
    
    public static Wall getByCoords(float x, float y) {
        int x1 = (int) (x < 0 ? Math.ceil(x) : Math.floor(x));
        int y1 = (int) (y < 0 ? Math.ceil(y) : Math.floor(y));
        return locations.get(x1).get(y1);
    }
}
