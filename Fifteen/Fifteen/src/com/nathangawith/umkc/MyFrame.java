package com.nathangawith.umkc;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MyFrame extends JFrame {
    /**
     * Window that the game runs inside of
     * @param game GameState object
     */
    public MyFrame(GameState game) {
        this(game, true);
    }

    /**
     * Window that the game runs inside of
     * @param game GameState object
     * @param enableKeyListener if false, user input will not work
     */
    public MyFrame(GameState game, boolean enableKeyListener) {
        JPanel panel = new MyPanel(game);
        int boardSize = Constants.BOARD_SIZE * Constants.TILE_WIDTH;
        this.setVisible(true);
        this.setSize(boardSize + 5, boardSize + 30);
        this.setResizable(false);
        this.setTitle("Fifteen");
        this.add(panel);
        if (enableKeyListener) this.addKeyListener(new MyKeyListener((key) -> game.key(key)));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocation(Constants.WINDOW_X, Constants.WINDOW_Y);
    }
}
