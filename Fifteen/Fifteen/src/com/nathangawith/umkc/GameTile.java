package com.nathangawith.umkc;

public class GameTile {

    private int value;
    public String getLabel() { return this.value == 0 ? null : this.value + ""; }
    public int getValue() { return this.value != 0 ? this.value
        : Constants.BOARD_SIZE * Constants.BOARD_SIZE; }

    /**
     * GameTile constructor
     * @param label label to save for this tile
     */
    public GameTile(int value) {
        this.value = value;
    }
}
