package net.ilikefood971.maze;

import net.ilikefood971.maze.UI.MazeUI;
import net.ilikefood971.maze.maze.Maze;


import javax.swing.*;

public class Main extends JFrame {
    public static void main(String[] args) {
        Maze maze = new Maze(15, 15);
        new MazeUI(maze);
    }
}