package com.nathangawith.umkc;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        int sizeSquared = Constants.BOARD_SIZE * Constants.BOARD_SIZE;
        Function<Integer, Integer> func = (c) -> c < sizeSquared - 1 ? c + 1 : null;
        this.constructor(func);
    }

    /**
     * constructor if the size of the board and all of the labels are known
     * @param tileLabels array of Integers representing each tile on the board
     */
    public GameBoard(Integer[] tileLabels) {
        Function<Integer, Integer> func = (c) -> tileLabels[c] != null ? tileLabels[c] : null;
        this.constructor(func);
    }

    /**
     * copy constructor
     * @param board board to copy
     */
    public GameBoard(GameBoard board) {
        Function<Integer, Integer> func = (c) -> {
            int row = c / board.size;
            int col = c % board.size;
            String str = board.getTile(row, col).getLabel();
            return str == null ? null : Integer.parseInt(board.getTile(row, col).getLabel());
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
                if (num == null) {
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
        System.out.println(
            String.join("\n",
                Arrays.asList(this.tiles).stream().map(
                    row -> String.join(" ",
                        Arrays.asList(row).stream().map(
                            tile -> tile.getLabel() == null ? " " : tile.getLabel()
                        ).collect(Collectors.toList())
                    )
                ).collect(Collectors.toList())
            ) + "\n"
        );
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
    private void swapTiles(int row1, int col1, int row2, int col2) {
        GameTile temp = this.tiles[row1][col1];
        this.tiles[row1][col1] = this.tiles[row2][col2];
        this.tiles[row2][col2] = temp;
    }

    /**
     * swaps the specified tile with the blank tile
     * @param row row of the tile to move
     * @param col column of the tile to move
     */
    public void moveTile(int row, int col) {
        boolean validRow = row > -1 && row < Constants.BOARD_SIZE;
        boolean validCol = col > -1 && col < Constants.BOARD_SIZE;
        if (validRow && validCol) {
            this.swapTiles(this.blankTileRow, this.blankTileCol, row, col);
            this.blankTileRow = row;
            this.blankTileCol = col;
        }
    }

    /**
     * returns -1, 0, or 1 based on how int1 and int2 compare
     * @param int1 left integer to compare
     * @param int2 right integer to compare
     * @return -1 if left < right, 1 if left > right, 0 if left == right
     */
    private int compareInteger(int int1, int int2) {
        return int1 < int2 ? -1 : (int1 > int2 ? 1 : 0);
    }

    /**
     * returns -1, 0, or 1 based on how str1 and str2 compare
     * @param str1 left string to compare
     * @param str2 right string to compare
     * @return -1 if left < right, 1 if left > right, 0 if left == right
     */
    private int compareString(String str1, String str2) {
        if (Arrays.asList(Arrays.asList(str1.split("")).stream().map((character) -> {
            return Arrays.asList("-0123456789".split("")).contains(character);
        }).toArray()).contains(false)
        || Arrays.asList(Arrays.asList(str1.split("")).stream().map((character) -> {
            return Arrays.asList("-0123456789".split("")).contains(character);
        }).toArray()).contains(false)) {
            // TODO compare string function
            return 0;
        } else {
            return this.compareInteger(Integer.parseInt(str1), Integer.parseInt(str2));
        }
    }

    /**
     * @return true if the board is in a finished state
     */
    public boolean isFinished() {
        boolean result = true;
        String lastLabel = "-1";
        for (int row = 0; row < this.tiles.length; row++) {
            for (int col = 0; col < this.tiles[row].length; col++) {
                String label = this.tiles[row][col].getLabel();
                label = label == null ? "" + this.size * this.size : label;
                if (this.compareString(lastLabel, label) != -1) {
                    result = false;
                }
                lastLabel = label;
            }
        }
        return result;
    }

}
