package com.nathangawith.umkc;

import java.util.List;
import java.util.function.Function;

public class GameBoard {

    private GameTile[][] tiles;
    private int size;
    private int blankTileRow;
    private int blankTileCol;
    public int getBlankTileRow() { return this.blankTileRow; }
    public int getBlankTileCol() { return this.blankTileCol; }

    /**
     * constructor if only the size of the board is known
     */
    public GameBoard() {
        int square = Constants.BOARD_SIZE * Constants.BOARD_SIZE;
        Function<Integer, Integer> func = (c) -> c < square - 1 ? c + 1 : 0;
        this.constructor(func);
    }

    /**
     * constructor if the size of the board and all of the labels are known
     * @param tileLabels array of Integers representing each tile on the board
     */
    public GameBoard(int[] tileLabels) {
        Function<Integer, Integer> func = (c) -> tileLabels[c];
        this.constructor(func);
    }

    /**
     * constructor if the size of the board and all of the labels are known
     * @param tileLabels array of Integers representing each tile on the board
     */
    public GameBoard(List<Integer> tileLabels) {
        Function<Integer, Integer> func = (c) -> tileLabels.get(c);
        this.constructor(func);
    }

    /**
     * copy constructor
     * @param board board to copy
     */
    public GameBoard(GameBoard board) {
        int square = Constants.BOARD_SIZE * Constants.BOARD_SIZE;
        Function<Integer, Integer> func = (c) -> {
            int row = c / board.size;
            int col = c % board.size;
            int num = board.getTile(row, col).getValue();
            return num == square ? 0 : num;
        };
        this.constructor(func);
    }

    /**
     * generic constructor used by both of the above constructors
     * @param func function used to get the ith tile
     */
    private void constructor(Function<Integer, Integer> func) {
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

}
