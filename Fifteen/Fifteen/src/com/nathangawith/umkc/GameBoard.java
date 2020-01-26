package com.nathangawith.umkc;
//#region imports
import java.util.List;
import java.util.Random;
import java.util.function.Function;
//#endregion
public class GameBoard {
    //#region private fields
    private boolean isMixing = false;
    private void fireGameUpdateEvent() { if (this.gameUpdateInterface != null) { this.gameUpdateInterface.handle(); } }
    private GameTile[][] tiles;
    private int size;
    private int blankTileRow;
    private int blankTileCol;
    //#endregion
    //#region public fields
    public boolean getIsMixing() { return this.isMixing; }
    // event listener for whoever wants to subscribe to game events
    // code from https://stackoverflow.com/questions/33948916/java-custom-event-handler-and-listeners/33950694
    private MyEventInterface gameUpdateInterface;
    public void setMyEventListener(MyEventInterface listener) { this.gameUpdateInterface = listener; }
    public int getBlankTileRow() { return this.blankTileRow; }
    public int getBlankTileCol() { return this.blankTileCol; }
    //#endregion
    //#region constructors
    /**
     * constructor if only the size of the board is known
     */
    public GameBoard() {
        this((c) -> c < Constants.SQUARE_BOARD_SIZE - 1 ? c + 1 : 0);
    }

    public GameBoard(boolean mixItUp) {
        this((c) -> c < Constants.SQUARE_BOARD_SIZE - 1 ? c + 1 : 0);
        if (mixItUp) this.mix();
    }

    /**
     * constructor if the size of the board and all of the labels are known
     * @param tileLabels array of Integers representing each tile on the board
     */
    public GameBoard(int[] tileLabels) {
        this((c) -> tileLabels[c]);
    }

    /**
     * constructor if the size of the board and all of the labels are known
     * @param tileLabels array of Integers representing each tile on the board
     */
    public GameBoard(List<Integer> tileLabels) {
        this((c) -> tileLabels.get(c));
    }

    /**
     * copy constructor
     * @param board board to copy
     */
    public GameBoard(GameBoard board) {
        this((c) -> {
            int row = c / board.size;
            int col = c % board.size;
            int num = board.getTile(row, col).getValue();
            return num == Constants.SQUARE_BOARD_SIZE ? 0 : num;
        });
    }

    /**
     * generic constructor used by both of the above constructors
     * @param func function used to get the ith tile
     */
    public GameBoard(Function<Integer, Integer> func) {
        this.size = Constants.BOARD_SIZE;
        int counter = 0;
        tiles = new GameTile[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Integer num = func.apply(counter);
                if (num == 0) {
                    this.blankTileRow = row;
                    this.blankTileCol = col;
                }
                tiles[row][col] = new GameTile(num);
                counter++;
            }
        }
    }
    //#endregion
    //#region game state modifiers
    /**
     * mixes board by randomly firing key events
     */
    private void mix() {
        Random random = new Random();
        this.fireGameUpdateEvent();
        GameBoard me = this;
        Thread mixingThread = new Thread(
            new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1500);
                        me.isMixing = true;
                        for (int i = 0; i < Constants.MIXING_NUMBER; i++) {
                            int index = random.nextInt(Constants.KEYS.size());
                            me.key(Constants.KEYS.get(index));
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
     * @return the total distance of all of the tiles to their finished state
     */
    public int distanceToFinish() {
        int result = 0;
        for (int row = 0; row < Constants.BOARD_SIZE; row++) {
            for (int col = 0; col < Constants.BOARD_SIZE; col++) {
                GameTile tile = this.getTile(row, col);
                int distance = tile.distanceToGoal(row, col);
                result += distance;
            }
        }
        return result;
    }

    /**
     * @return true if the board is in a finished state
     */
    public boolean isFinished() {
        boolean result = true;
        int lastValue = -1;
        for (int row = 0; row < this.tiles.length; row++) {
            for (int col = 0; col < this.tiles[row].length; col++) {
                int value = this.tiles[row][col].getValue();
                if (value < lastValue) result = false;
                lastValue = value;
            }
        }
        return result;
    }
    //#endregion
    //#region stringification
    /**
     * prints out console friendly game board
     */
    public void print() {
        for (int row = 0; row < Constants.BOARD_SIZE; row++) {
            for (int col = 0; col < Constants.BOARD_SIZE; col++) {
                String label = this.tiles[row][col].getLabel();
                System.out.print((label == null ? " " : label) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * @return a compresed stringy version of the board
     */
    public String stringify() {
        String result = "";
        for (int row = 0; row < Constants.BOARD_SIZE; row++) {
            for (int col = 0; col < Constants.BOARD_SIZE; col++) {
                String label = this.tiles[row][col].getLabel();
                result += (label == null ? "0" : label);
                if (row != Constants.BOARD_SIZE - 1 || col != Constants.BOARD_SIZE - 1) result += ",";
            }
        }
        return result;
    }
    //#endregion
    //#region tile retrieval and modification functions
    /**
     * returns the tile for the specified row and column
     * @param row row to retrieve the tile from
     * @param col column to retrieve the tile from
     * @return the tile at the specified row and column
     */
    public GameTile getTile(int row, int col) {
        if (row < this.tiles.length) {
            if (col < this.tiles[row].length) {
                return this.tiles[row][col];
            }
        }
        String exceptionMessage = "The index you passed in (%d, %d) was out of bounds! (Size is %d)";
        throw new IndexOutOfBoundsException(String.format(exceptionMessage, row, col, this.tiles.length));
    }

    /**
     * swaps two tiles on the board
     * @param row1 row of the first tile to swap
     * @param col1 column of the first tile to swap
     * @param row2 row of the second tile to swap
     * @param col2 column of the second tile to swap
     */
    private String swapTiles(int row1, int col1, int row2, int col2) {
        GameTile temp = this.tiles[row1][col1];
        this.tiles[row1][col1] = this.tiles[row2][col2];
        this.tiles[row2][col2] = temp;
        return this.tiles[row1][col1].getLabel();
    }

    /**
     * swaps the specified tile with the blank tile
     * @param row row of the tile to move
     * @param col column of the tile to move
     */
    public String moveTile(int row, int col) {
        String result = null;
        boolean validRow = row > -1 && row < Constants.BOARD_SIZE;
        boolean validCol = col > -1 && col < Constants.BOARD_SIZE;
        if (validRow && validCol) {
            result = this.swapTiles(this.blankTileRow, this.blankTileCol, row, col);
            this.blankTileRow = row;
            this.blankTileCol = col;
        }
        return result;
    }

    /**
     * manipulates game state based on key that was pressed
     * @param key key that was pressed
     */
    public void key(MyKey key) {
        // swap tiles for associated with the pressed key
        // System.out.print(Constants.MOVING_LABEL.get(key));
        // System.out.print(" " +
        this.moveTile(
            this.getBlankTileRow() + Constants.MOVING_ROW_DIFF.get(key),
            this.getBlankTileCol() + Constants.MOVING_COL_DIFF.get(key)
        );
        // );
        this.fireGameUpdateEvent();
    }
    //#endregion
}
