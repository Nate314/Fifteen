package com.nathangawith.umkc;

public class Fifteen {
    /**
     * main method
     * @param args
     */
    public static void main(String[] args) {
        Fifteen.setupConstants();
        // new MyFrame(new GameBoard(true));
        long start = System.currentTimeMillis();
        new AI(false);
        long end = System.currentTimeMillis();
        System.out.println(String.format("Total running time: %dms", end - start));
    }

    /**
     * sets up constants required to run the rest of the program
     */
    private static void setupConstants() {
        long permutations = 1;
        Constants.MOVING_ROW_DIFF.put(MyKey.UP, 1);
        Constants.MOVING_ROW_DIFF.put(MyKey.DOWN, -1);
        Constants.MOVING_ROW_DIFF.put(MyKey.LEFT, 0);
        Constants.MOVING_ROW_DIFF.put(MyKey.RIGHT, 0);
        Constants.MOVING_COL_DIFF.put(MyKey.UP, 0);
        Constants.MOVING_COL_DIFF.put(MyKey.DOWN, 0);
        Constants.MOVING_COL_DIFF.put(MyKey.LEFT, 1);
        Constants.MOVING_COL_DIFF.put(MyKey.RIGHT, -1);
        Constants.MOVING_LABEL.put(MyKey.UP, " UP");
        Constants.MOVING_LABEL.put(MyKey.DOWN, " DOWN");
        Constants.MOVING_LABEL.put(MyKey.RIGHT, " RIGHT");
        Constants.MOVING_LABEL.put(MyKey.LEFT, " LEFT");
        for (int i = Constants.SQUARE_BOARD_SIZE; i > 2; i--) permutations *= i;
        Constants.PERMUTATIONS = permutations;
    }
}
