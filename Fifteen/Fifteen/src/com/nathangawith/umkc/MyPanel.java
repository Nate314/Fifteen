package com.nathangawith.umkc;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MyPanel extends JPanel {

    final GameState game;
    final int boardSize;
    final int tilesWide;
    final int tileWidth;

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
    }

    /**
     * Calculates position of tile[i][j]
     * @param i ith tile to paint
     * @param j jth tile to paint
     * @return returns x and y position of the specified tile
     */
    public Dimension getPosition(int i, int j) {
        int x = (i * this.tileWidth) + Constants.GUTTER_SIZE;
        int y = (j * this.tileWidth) + Constants.GUTTER_SIZE;
        return new Dimension(x, y);
    }

    /**
     * This is where the drawing happens
     * @param g Graphics object used to draw
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.drawBackground(g);
        for (int i = 0; i < this.tilesWide; i++) {
            for (int j = 0; j < this.tilesWide; j++) {
                String text = this.game.getTextForTile(i, j);
                if (text != null) this.drawSquare(g, i, j, text);
            }
        }
    }

    /**
     * draw background
     * @param g Graphics object used to draw
     */
    private void drawBackground(Graphics g) {
        g.setColor(Constants.BACKGROUND_COLOR);
        g.fillRect(0, 0, boardSize, boardSize);
    }

    /**
     * draws one square on the board
     * @param g Graphics object used to draw
     * @param i row number to draw
     * @param j column number to draw
     * @param text text to draw on the square
     */
    private void drawSquare(Graphics g, int i, int j, String text) {
        // draw square
        g.setColor(Constants.FOREGROUND_COLOR);
        Dimension pos = this.getPosition(i, j);
        int x = (int) pos.getWidth(),
            y = (int) pos.getHeight(),
            w = this.tileWidth - (2 * Constants.GUTTER_SIZE),
            h = this.tileWidth - (2 * Constants.GUTTER_SIZE);
        g.fillRect(x, y, w, h);
        // draw text
        g.setColor(Constants.TEXT_COLOR);
        Font font = new Font("Consolas", Font.BOLD, (int) (this.tileWidth * 0.55));
        this.drawCenteredText(g, font, text, new Rectangle(x, y, w, h));
    }

    /**
     * draws text centered in the boundingRectangle
     * Code retrieved from http://www.java2s.com/Tutorials/Java/Graphics_How_to/Text/Center_a_string_in_a_rectangle.htm
     * @param g Graphics object used to draw
     * @param font font used to draw text with
     * @param text text to dwar
     * @param boundingRectangle rectangle that defines the boundaries for the text
     */
    private void drawCenteredText(Graphics g, Font font, String text, Rectangle boundingRectangle) {
        int rectangleX = (int) boundingRectangle.getX(),
            rectangleY = (int) boundingRectangle.getY(),
            rectangleWidth = (int) boundingRectangle.getWidth(),
            rectangleHeight = (int) boundingRectangle.getHeight();
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        g2d.drawString(text,
            ((rectangleWidth - fm.stringWidth(text)) / 2) + rectangleX,
            ((rectangleHeight - fm.getHeight()) / 2) + fm.getAscent() + rectangleY);
        g2d.dispose();
    }
}
