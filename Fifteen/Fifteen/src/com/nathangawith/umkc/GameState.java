package com.nathangawith.umkc;

import java.awt.Dimension;
import java.util.Random;
import java.lang.Thread;

public class GameState {

    // private fields
    private final int tilesWide;
    private final int boardSize;
    private GameBoard board;
    // public fields
    public final int getTilesWide() { return tilesWide; }
    public final int getBoardSize() { return boardSize; }
    public boolean inSession = false;

    /**
     * constructor for a Fifteen Game GameState class
     * @param tilesWide number of tiles wide to make the game (e.g. Fifteen is typically 4 tiles wide)
     * @param tileSize width and height of each tile
     */
    public GameState(int tilesWide, int tileSize) {
        this.tilesWide = tilesWide;
        this.boardSize = tilesWide * tileSize;
        this.board = new GameBoard(tilesWide);
        MyKey[] keys = new MyKey[] {MyKey.UP, MyKey.DOWN, MyKey.LEFT, MyKey.RIGHT};
        Random random = new Random();
        this.inSession = false;
        this.calculateColors();
        GameState me = this;
        Thread mixingThread = new Thread(
            new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(100);
                        for (int i = 0; i < Constants.MIXING_NUMBER; i++) {
                            int index = random.nextInt(4);
                            me.key(keys[index]);
                            Thread.sleep(Constants.MIXING_FREQUENCY);
                        }
                    } catch (Exception ex) { }
                    me.inSession = true;
                    me.calculateColors();
                }
            }
        );
        mixingThread.start();
    }

    /**
     * @param i row
     * @param j column
     * @return the text for the specified tile
     */
    public String getTextForTile(int i, int j) {
        return this.board.getTile(i, j).getLabel();
    }

    public void calculateColors() {
        if (this.inSession) {
            if (this.board.isFinished()) {
                Constants.COLOR_TEXT = Constants.COLOR_ALL_CORRECT;
            } else {
                Constants.COLOR_TEXT = Constants.MD_WHITE;
            }
        } else {
            Constants.COLOR_TEXT = Constants.COLOR_MIXING;
        }
    }

    /**
     * manipulates game state based on key that was pressed
     * @param key key that was pressed
     */
    public void key(MyKey key) {
        // find blank tile
        Dimension blankTilePosition = this.board.getBlankTilePosition();
        int blankRow = (int) blankTilePosition.getWidth(), blankCol = (int) blankTilePosition.getHeight();
        if (blankRow == -1 || blankCol == -1) return;
        switch (key) {
            case UP:
                if (blankRow < this.tilesWide - 1) {
                    System.out.print(" UP");
                    this.board.swapTiles(blankRow, blankCol, blankRow + 1, blankCol);
                }
                break;
            case DOWN:
                if (blankRow > 0) {
                    System.out.print(" DOWN");
                    this.board.swapTiles(blankRow, blankCol, blankRow - 1, blankCol);
                }
                break;
            case LEFT:
                if (blankCol < this.tilesWide - 1) {
                    System.out.print(" LEFT");
                    this.board.swapTiles(blankRow, blankCol, blankRow, blankCol + 1);
                }
                break;
            case RIGHT:
                if (blankCol > 0) {
                    System.out.print(" RIGHT");
                    this.board.swapTiles(blankRow, blankCol, blankRow, blankCol - 1);
                }
                break;
            default:
                break;
        }
        this.calculateColors();
    }

}
