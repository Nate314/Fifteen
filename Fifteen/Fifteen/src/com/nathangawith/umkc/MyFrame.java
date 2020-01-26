package com.nathangawith.umkc;
//#region imports
import javax.swing.JFrame;
import javax.swing.JPanel;
//#endregion
@SuppressWarnings("serial")
public class MyFrame extends JFrame {
    //#region constructors
    /**
     * Window that the game runs inside of
     * @param gameBoard GameBoard object
     */
    public MyFrame(GameBoard gameBoard) {
        this(gameBoard, true);
    }

    /**
     * Window that the game runs inside of
     * @param gameBoard GameBoard object
     * @param enableKeyListener if false, user input will not work
     */
    public MyFrame(GameBoard gameBoard, boolean enableKeyListener) {
        JPanel panel = new MyPanel(gameBoard);
        int boardSize = Constants.BOARD_SIZE * Constants.TILE_WIDTH;
        this.setVisible(true);
        this.setSize(boardSize + 5, boardSize + 30);
        this.setResizable(false);
        this.setTitle("Fifteen");
        this.add(panel);
        if (enableKeyListener) this.addKeyListener(new MyKeyListener((key) -> gameBoard.key(key)));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocation(Constants.WINDOW_X, Constants.WINDOW_Y);
    }
    //#endregion
}
