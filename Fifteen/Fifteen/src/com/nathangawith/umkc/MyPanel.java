package com.nathangawith.umkc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MyPanel extends JPanel {

    final GameState game;
    final int boardSize;
    final int tilesWide;
    final int tileWidth;
    final int gutter;

    /**
     * Panel used to paint current state of the game
     * @param game GameState object
     */
    public MyPanel(GameState game) {
        super();
        this.game = game;
        this.boardSize = game.getBoardSize();
        this.tilesWide = game.getTilesWide();
        this.tileWidth = this.boardSize / this.tilesWide;
        this.gutter = 10;
    }

    /**
     * Calculates position of tile[i][j]
     * @param i ith tile to paint
     * @param j jth tile to paint
     * @return returns x and y position of the specified tile
     */
    public Dimension getPosition(int i, int j) {
        int x = (i * this.tileWidth) + this.gutter;
        int y = (j * this.tileWidth) + this.gutter;
        return new Dimension(x, y);
    }

    /**
     * This is where the drawing happens
     */
    @Override
    public void paint(Graphics g) {
        g.setColor(new Color(255, 0, 0));
        g.fillRect(0, 0, boardSize, boardSize);
        g.setColor(new Color(0, 255, 0));
        for (int i = 0; i < this.tilesWide; i++) {
            for (int j = 0; j < this.tilesWide; j++) {
                Dimension pos = this.getPosition(i, j);
                int x = (int) pos.getWidth(),
                    y = (int) pos.getHeight(),
                    w = this.tileWidth - (2 * this.gutter),
                    h = this.tileWidth - (2 * this.gutter);
                g.fillRect(x, y, w, h);
            }
        }
    }
}
