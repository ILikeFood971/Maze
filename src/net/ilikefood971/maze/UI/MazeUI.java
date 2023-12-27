package net.ilikefood971.maze.UI;

import net.ilikefood971.maze.maze.Maze;
import net.ilikefood971.maze.maze.MazeLocation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MazeUI extends JFrame {
    MazePanel mazePanel;
    SettingsPanel settingsPanel;
    private Maze maze;
    private Maze nextMaze;
    
    public MazeUI(Maze startingMaze) {
        this.maze = startingMaze;
        this.nextMaze = new Maze(maze.xLength, maze.yLength);
        setupApp();
    }
    
    public void newMaze(Maze maze) {
        this.maze = maze;
        this.nextMaze = new Maze(maze.xLength, maze.yLength);
        
        mazePanel.resetPlayer();
        repaint();
    }
    
    public void setupApp() {
        
        setTitle("Maze Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        mazePanel = new MazePanel();
        settingsPanel = new SettingsPanel();
        
        setLayout(new BorderLayout(10, 10));
        
        add(mazePanel, BorderLayout.CENTER);
        add(settingsPanel, BorderLayout.EAST);

        mazePanel.addKeyListener(mazePanel);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        getContentPane().setPreferredSize(new Dimension(screenSize.width / 3, screenSize.width / 3));
        pack();
        setVisible(true);
        mazePanel.requestFocusInWindow();
    }
    
    private class MazePanel extends JPanel implements KeyListener {
        private final Set<Integer> keysPressed;
        private int playerX;
        private int playerY;
        private boolean showSolution;
        
        public MazePanel() {
            this.playerX = maze.start.x;
            this.playerY = maze.start.y;
            this.keysPressed = new HashSet<>(4);
            showSolution = false;
        }
        
        public void resetPlayer() {
            this.playerX = maze.start.x;
            this.playerY = maze.start.y;
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            // Drawing logic
            int cellSize = Math.min(getWidth() / maze.xLength, getHeight() / maze.yLength);
            for (int x = 0; x < maze.xLength; x++) {
                for (int y = 0; y < maze.yLength; y++) {
                    MazeLocation mazeLocation = maze.getMazeLocations()[x][y];
                    if (mazeLocation.isWall()) {
                        g.setColor(Color.BLACK);
                        g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                    } else if (showSolution && mazeLocation.isSolution()) {
                        g.setColor(Color.GREEN);
                        g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                    }
                }
            }
            g.setColor(Color.RED);
            g.fillRect(playerX * cellSize, playerY * cellSize, cellSize, cellSize);
        }
        
        @Override
        public void keyTyped(KeyEvent e) {
        
        }
        
        @Override
        public void keyPressed(KeyEvent e) {
            keysPressed.add(e.getKeyCode());
            for (int next : keysPressed) {
                try {
                    switch (next) {
                        case KeyEvent.VK_UP: {
                            if (!Objects.requireNonNull(maze.getMazeLocations()[playerX][playerY - 1]).isWall()) {
                                playerY--;
                            }
                            break;
                        }
                        case KeyEvent.VK_DOWN: {
                            if (!Objects.requireNonNull(maze.getMazeLocations()[playerX][playerY + 1]).isWall()) {
                                playerY++;
                            }
                            break;
                        }
                        case KeyEvent.VK_LEFT: {
                            if (!Objects.requireNonNull(maze.getMazeLocations()[playerX - 1][playerY]).isWall()) {
                                playerX--;
                            }
                            break;
                        }
                        case KeyEvent.VK_RIGHT: {
                            if (!Objects.requireNonNull(maze.getMazeLocations()[playerX + 1][playerY]).isWall()) {
                                playerX++;
                            }
                            break;
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                } finally {
                    if (playerX == maze.end.x && playerY == maze.end.y) {
                        newMaze(nextMaze);
                    }
                    repaint();
                }
            }
        }
        
        @Override
        public void keyReleased(KeyEvent e) {
            keysPressed.remove(e.getKeyCode());
        }
    }
    
    private class SettingsPanel extends JPanel {
        JButton generateButton;
        JButton solveButton;
        public SettingsPanel() {
            setLayout(new GridLayout(2, 2));
            
            generateButton = new JButton("Generate!");
            generateButton.addActionListener(e -> {
                newMaze(nextMaze);
                mazePanel.requestFocusInWindow();
            });
            
            solveButton = new JButton("Solve");
            solveButton.addActionListener(e -> {
                mazePanel.showSolution = !mazePanel.showSolution;
                mazePanel.repaint();
                mazePanel.requestFocusInWindow();
            });
            
            JSlider widthSlider = new JSlider(5, 1000);
            JSlider heightSlider = new JSlider(5, 1000);
            
            widthSlider.setBorder(BorderFactory.createTitledBorder("Maze Width"));
            widthSlider.setMajorTickSpacing(285);
            widthSlider.setPaintLabels(true);
            widthSlider.setPaintTicks(true);
            widthSlider.setFocusable(false);
            
            heightSlider.setBorder(BorderFactory.createTitledBorder("Maze Height"));
            heightSlider.setMajorTickSpacing(285);
            heightSlider.setPaintLabels(true);
            heightSlider.setPaintTicks(true);
            heightSlider.setFocusable(false);
            
            widthSlider.setValue(maze.xLength);
            heightSlider.setValue(maze.yLength);
            
            add(generateButton);
            add(solveButton);
            add(widthSlider);
            add(heightSlider);
            
            widthSlider.addChangeListener(e -> newMaze(new Maze(widthSlider.getModel().getValue(), maze.yLength)));
            heightSlider.addChangeListener(e -> newMaze(new Maze(maze.xLength, heightSlider.getModel().getValue())));
        }
    }
}
