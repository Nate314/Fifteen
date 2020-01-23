package com.nathangawith.umkc;

import java.awt.Dimension;
import java.util.Random;
import java.lang.Thread;

public class GameState {

    private final int tilesWide;
    private GameBoard board;
    private boolean isMixing = false;
    public boolean getIsMixing() {
        return this.isMixing;
    }
    public GameBoard getGameBoard() {
        return new GameBoard(this.board);
    }

    // event listener for whoever wants to subscribe to game events
    // code from https://stackoverflow.com/questions/33948916/java-custom-event-handler-and-listeners/33950694
    private MyEventInterface gameUpdateInterface;
    public void setMyEventListener(MyEventInterface listener) {
        this.gameUpdateInterface = listener;
    }

    /**
     * constructor for a Fifteen Game GameState class
     * @param tileLabels if an array is passed in here, those labels will be used
     *                      for the initial game state. If null is passed, the inital
     *                      game state will be the correct finished game state.
     * @param mixItUp if true, then the board will be mixed prior to gameplay
     */
    public GameState(Integer[] tileLabels, boolean mixItUp) {
        this.tilesWide = Constants.BOARD_SIZE;
        this.board = tileLabels != null
            ? new GameBoard(tileLabels) : new GameBoard();
        if (mixItUp) this.mix();
    }

    /**
     * emits game update event to listener
     */
    private void fireGameUpdateEvent() {
        if (this.gameUpdateInterface != null) {
            this.gameUpdateInterface.handle();
        }
    }

    /**
     * mixes board by randomly firing key events
     */
    private void mix() {
        MyKey[] keys = new MyKey[] {MyKey.UP, MyKey.DOWN, MyKey.LEFT, MyKey.RIGHT};
        Random random = new Random();
        this.fireGameUpdateEvent();
        GameState me = this;
        Thread mixingThread = new Thread(
            new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1500);
                        me.isMixing = true;
                        for (int i = 0; i < Constants.MIXING_NUMBER; i++) {
                            int index = random.nextInt(4);
                            me.key(keys[index]);
                            Thread.sleep(Constants.MIXING_FREQUENCY);
                        }
                    } catch (Exception ex) { }
                    me.isMixing = false;
                    me.fireGameUpdateEvent();
                }
            }
        );
        mixingThread.start();
    }

    /**
     * @param row row
     * @param col column
     * @return the text for the specified tile
     */
    public String getTextForTile(int row, int col) {
        return this.board.getTile(row, col).getLabel();
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
        // switch over key pressed and swap tiles
        switch (key) {
            case UP:
                if (blankRow < this.tilesWide - 1) {
                    System.out.print(" UP");
                    this.board.moveTile(blankRow + 1, blankCol);
                }
                break;
            case DOWN:
                if (blankRow > 0) {
                    System.out.print(" DOWN");
                    this.board.moveTile(blankRow - 1, blankCol);
                }
                break;
            case LEFT:
                if (blankCol < this.tilesWide - 1) {
                    System.out.print(" LEFT");
                    this.board.moveTile(blankRow, blankCol + 1);
                }
                break;
            case RIGHT:
                if (blankCol > 0) {
                    System.out.print(" RIGHT");
                    this.board.moveTile(blankRow, blankCol - 1);
                }
                break;
            default:
                break;
        }
        this.fireGameUpdateEvent();
    }

}
