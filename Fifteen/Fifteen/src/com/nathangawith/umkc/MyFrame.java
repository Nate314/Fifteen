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
        JPanel panel = new MyPanel(game);
        int boardSize = game.getBoardSize();
        this.setVisible(true);
        this.setSize(boardSize + 5, boardSize + 30);
        this.setResizable(false);
        this.setTitle("Fifteen");
        this.add(panel);
        this.addKeyListener(new MyKeyListener((key) -> game.key(key)));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
