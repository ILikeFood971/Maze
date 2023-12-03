package net.ilikefood971.maze;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Main extends JFrame {
    public static final int ROWS = 1000;
    public static int COLUMNS = 1000;
    public static ArrayList<MazeRow> maze;
    public static MazeLocation start;
    public static final Random RANDOM = new Random();
    public static final int START_X = 1;
    public static int END_X;
    public static final int CELL_SIZE = 1;
    
    
    public static void main(String[] args) throws InterruptedException {
        COLUMNS -= 2;
        END_X = COLUMNS;
        
        buildMaze();
        
        new Main().printMaze();
    }
    public static void buildMaze() throws InterruptedException {
        maze = new ArrayList<>();
        
        for (int i = 0; i < ROWS - 2; i++) {
            MazeRow mazeRow = new MazeRow(COLUMNS, i % 2 != 0);
            maze.add(mazeRow);
            mazeRow.init();
        }
        
        start = getByCoords(START_X - 1, 0);
        start.run();
        
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
    }
    
    public static MazeLocation getByCoords(int x, int y) {
        if (x < 0 || x >= maze.get(0).getLocations().size() || y < 0 || y >= maze.size()) return null;
        return maze.get(y).get(x);
    }
    
    public void printMaze() {
        
        setTitle("Maze Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        MazePanel mazePanel = new MazePanel();
        setLayout(new BorderLayout());
        add(mazePanel, BorderLayout.CENTER);
        
        JButton button = new JButton("Generate!");
        button.addActionListener(e -> {
            try {
                buildMaze();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            mazePanel.repaint();
        });
        add(button, BorderLayout.SOUTH);
        
        
        mazePanel.repaint();
        
        getContentPane().setPreferredSize(new Dimension((CELL_SIZE + 1) * COLUMNS, (CELL_SIZE) * ROWS));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private class MazePanel extends JPanel {
        @SuppressWarnings("ConstantValue")
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            
            
            
            
            
            // Drawing logic
            for (int y = 0; y < maze.size(); y++) {
                g.fillRect(0, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                ArrayList<MazeLocation> locations = maze.get(y).getLocations();
                for (int x = 0; x < locations.size(); x++) {
                    MazeLocation mazeLocation = locations.get(x);
                    if (!mazeLocation.isWall() || !mazeLocation.exists()) {
                    } else {
                        g.fillRect(x * CELL_SIZE + CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    }
                }
            }
        }
    }
    
}
