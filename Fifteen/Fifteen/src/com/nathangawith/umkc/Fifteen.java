package com.nathangawith.umkc;

public class Fifteen {
    /**
     * main method
     * @param args
     */
    public static void main(String[] args) {
        new MyFrame(new GameState(Constants.BOARD_SIZE, null, true));
        // new AI();
    }
}
