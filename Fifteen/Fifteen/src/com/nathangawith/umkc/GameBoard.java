package com.nathangawith.umkc;

import java.awt.Dimension;
import java.util.Arrays;

public class GameBoard {

    private GameTile[][] tiles;
    private int size;

    public GameBoard(int size) {
        this.size = size;
        int counter = 1;
        int sizeSquared = size * size;
        tiles = new GameTile[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Integer num = counter < sizeSquared ? counter : null;
                tiles[i][j] = new GameTile(num);
                counter++;
            }
        }
    }

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
        }
        return new Dimension(blankRow, blankCol);
    }

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
