package net.ilikefood971.maze.maze;

import java.util.*;
import java.util.function.Predicate;

public class Solver {
    private final Maze maze;
    public Solver(Maze maze) {
        this.maze = maze;
    }
    
    public void solve(MazeLocation start) {
        Stack<MazeLocation> stack = new Stack<>();
        Set<MazeLocation> visited = new HashSet<>();
        stack.push(start);
        Predicate<MazeLocation> checkValid = location -> !location.isSolution() && !location.isWall() && !visited.contains(location);
        while (!stack.isEmpty()) {
            MazeLocation current = stack.pop();
            visited.add(current);
            
            
            if (current.equals(maze.end)) {
                current.setSolution(true);
                return;
            }
            ArrayList<MazeLocation> surrounding = getSurrounding(current);
            
            
            List<MazeLocation> valid = surrounding.stream().filter(checkValid).toList();
            
            if (!valid.isEmpty()) {
                current.setSolution(true);
                MazeLocation next = valid.get(0);
                stack.push(current);
                stack.push(next);
            } else current.setSolution(false);
        }
    }
    
    public ArrayList<MazeLocation> getSurrounding(MazeLocation location) {
        ArrayList<MazeLocation> list = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            try {
                switch (i) {
                    case 0: // UP
                        list.add(maze.getMazeLocations()[location.x][location.y - 1]);
                        break;
                    case 1: // DOWN
                        list.add(maze.getMazeLocations()[location.x][location.y + 1]);
                        break;
                    case 2: // LEFT
                        list.add(maze.getMazeLocations()[location.x - 1][location.y]);
                        break;
                    case 3: // RIGHT
                        list.add(maze.getMazeLocations()[location.x + 1][location.y]);
                        break;
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
        }
        return list;
    }
}
