package com.nathangawith.umkc;

import java.awt.Dimension;
import java.util.Arrays;
import java.util.function.Function;

public class GameBoard {

    private GameTile[][] tiles;
    private int size;

    /**
     * constructor if only the size of the board is known
     * @param size width of the board in tiles
     */
    public GameBoard(int size) {
        int sizeSquared = size * size;
        Function<Integer, Integer> func = (c) -> c < sizeSquared - 1 ? c + 1 : null;
        this.constructor(size, func);
    }

    /**
     * constructor if the size of the board and all of the labels are known
     * @param size width of the board in tiles
     * @param tileLabels array of Integers representing each tile on the board
     */
    public GameBoard(int size, Integer[] tileLabels) {
        Function<Integer, Integer> func = (c) -> tileLabels[c] != null ? tileLabels[c] : null;
        this.constructor(size, func);
    }

    /**
     * generic constructor used by both of the above constructors
     * @param size tile width of the board
     * @param func function used to get the ith tile
     */
    private void constructor(int size, Function<Integer, Integer> func) {
        this.size = size;
        int counter = 0;
        tiles = new GameTile[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Integer num = func.apply(counter);
                tiles[i][j] = new GameTile(num);
                counter++;
            }
        }
    }

    /**
     * searches all tiles until the blank tile is found
     * @return the position of the blank tile
     */
    public Dimension getBlankTilePosition() {
        int blankRow = -1, blankCol = -1;
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (this.getTile(i, j).getLabel() == null) {
                    blankRow = i;
                    blankCol = j;
                    break;
                }
            }
            if (blankRow != -1 || blankCol != -1) break;
        }
        return new Dimension(blankRow, blankCol);
    }

    /**
     * returns the tile for the specified row and column
     * @param row row to retrieve the tile from
     * @param col column to retrieve the tile from
     * @return the tile at the specified row and column
     */
    public GameTile getTile(int i, int j) {
        if (i < this.tiles.length) {
            if (j < this.tiles[i].length) {
                return this.tiles[i][j];
            }
        }
        String exceptionMessage = "The index you passed in (%d, %d) was out of bounds! (Size is %d)";
        throw new IndexOutOfBoundsException(String.format(exceptionMessage, i, j, this.tiles.length));
    }

    /**
     * swaps two tiles on the board
     * @param row1 row of the first tile to swap
     * @param col1 column of the first tile to swap
     * @param row2 row of the second tile to swap
     * @param col2 column of the second tile to swap
     */
    public void swapTiles(int row1, int col1, int row2, int col2) {
        GameTile temp = this.tiles[row1][col1];
        this.tiles[row1][col1] = this.tiles[row2][col2];
        this.tiles[row2][col2] = temp;
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
