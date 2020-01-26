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

    /**
     * @param row row to calculate the distance from
     * @param col column to calculate the distance from
     * @return the city block distance from the passed position to the goal position
     */
    public int distanceToGoal(int row, int col) {
        int goalRow = (this.getValue() - 1) / Constants.BOARD_SIZE;
        int goalCol = (this.getValue() - 1) % Constants.BOARD_SIZE;
        int horizontalDistance = Math.abs(row - goalRow);
        int verticalDistance = Math.abs(col - goalCol);
        return (horizontalDistance + verticalDistance);
    }
}
