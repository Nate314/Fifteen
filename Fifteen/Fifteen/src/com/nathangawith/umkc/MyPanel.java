package com.nathangawith.umkc;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import javax.swing.JPanel;
import java.lang.Thread;

@SuppressWarnings("serial")
public class MyPanel extends JPanel implements MyEventInterface {

    final GameState game;
    final int boardSize;

    // private int mixingCounter = 0;
    // private final int mixMax = 3; // mix for 10 seconds at 60 fps

    /**
     * Panel used to paint current state of the game
     * @param game GameState object
     */
    public MyPanel(GameState game) {
        super();
        this.game = game;
        this.game.setMyEventListener(this);
        this.boardSize = Constants.BOARD_SIZE * Constants.TILE_WIDTH;
        JPanel me = this;
        Thread uiReDrawThread = new Thread(
            new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep((int) 1000 / 60); // 60 fps
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        me.repaint();
                    }
                }
            }
        );
        uiReDrawThread.start();
    }

    /**
     * sets the text color to the appropriate game state color
     */
    public void calculateColors() {
        Constants.COLOR_TEXT = this.game.getIsMixing() ? Constants.COLOR_MIXING
            : this.game.getGameBoard().isFinished() ? Constants.COLOR_ALL_CORRECT : Constants.MD_WHITE;
    }

    /**
     * Calculates position of tile[i][j]
     * @param row rowof the tile to paint
     * @param col column of the tile to paint
     * @return returns x and y position of the specified tile
     */
    public Dimension getPosition(int row, int col) {
        int x = (col * Constants.TILE_WIDTH) + Constants.GUTTER_SIZE;
        int y = (row * Constants.TILE_WIDTH) + Constants.GUTTER_SIZE;
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
        for (int row = 0; row < Constants.BOARD_SIZE; row++) {
            for (int col = 0; col < Constants.BOARD_SIZE; col++) {
                String text = this.game.getTextForTile(row, col);
                if (text != null) this.drawSquare(g, row, col, text);
            }
        }
        // this.repaint();
    }

    /**
     * draw background
     * @param g Graphics object used to draw
     */
    private void drawBackground(Graphics g) {
        g.setColor(Constants.COLOR_BACKGROUND);
        g.fillRect(0, 0, boardSize, boardSize);
    }

    /**
     * draws one square on the board
     * @param g Graphics object used to draw
     * @param row row number to draw
     * @param col column number to draw
     * @param text text to draw on the square
     */
    private void drawSquare(Graphics g, int row, int col, String text) {
        // draw square
        g.setColor(Constants.COLOR_FOREGROUND);
        Dimension pos = this.getPosition(row, col);
        int x = (int) pos.getWidth(),
            y = (int) pos.getHeight(),
            w = Constants.TILE_WIDTH - (2 * Constants.GUTTER_SIZE),
            h = Constants.TILE_WIDTH - (2 * Constants.GUTTER_SIZE);
        g.fillRect(x, y, w, h);
        // draw text
        g.setColor(Constants.COLOR_TEXT);
        Font font = new Font("Consolas", Font.BOLD, (int) (Constants.TILE_WIDTH * 0.55));
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

    /**
     * when the game state changes
     */
    @Override
    public void handle() {
        this.calculateColors();
    }
}
