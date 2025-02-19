package com.nathangawith.umkc;
//#region imports
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import javax.swing.JPanel;
//#endregion
@SuppressWarnings("serial")
public class MyPanel extends JPanel implements MyEventInterface {
    //#region fields
    private final GameBoard gameBoard;
    private Color textColor = Constants.MD_WHITE;
    //#endregion
    //#region constructor
    /**
     * Panel used to paint current state of the game
     * @param gameBoard GameBoard object
     */
    public MyPanel(GameBoard gameBoard) {
        super();
        this.gameBoard = gameBoard;
        this.gameBoard.setMyEventListener(this);
    }
    //#endregion
    //#region painting methods
    /**
     * This is where the drawing happens
     * @param g Graphics object used to draw
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.textColor = this.gameBoard.getIsMixing() ? Constants.COLOR_MIXING
            : (this.gameBoard.isFinished() ? Constants.COLOR_ALL_CORRECT : Constants.MD_WHITE);
        this.drawBackground(g);
        for (int row = 0; row < Constants.BOARD_SIZE; row++) {
            for (int col = 0; col < Constants.BOARD_SIZE; col++) {
                String text = this.gameBoard.getTile(row, col).getLabel();
                if (text != null) this.drawSquare(g, row, col, text);
            }
        }
    }

    /**
     * draw background
     * @param g Graphics object used to draw
     */
    private void drawBackground(Graphics g) {
        g.setColor(Constants.COLOR_BACKGROUND);
        g.fillRect(0, 0, Constants.PX_BOARD_SIZE, Constants.PX_BOARD_SIZE);
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
        g.setColor(this.textColor);
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
    //#endregion
    //#region other methods
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
     * when the game state changes,
     *   calculate the new text color and repaint the panel
     */
    @Override
    public void handle() {
        this.repaint();
    }
    //#endregion
}
