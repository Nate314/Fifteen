package com.nathangawith.umkc;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Fifteen {
    /**
     * main method
     * @param args
     */
    public static void main(String[] args) {
        List<String> arguments = Arrays.asList(args);
        System.out.println("ARGS:");
        System.out.println(String.join(",", args));
        // Human Mode: +humanmode             // allows a human to play the game rather than the AI
        // Enable GUI: +enablegui             // enables the GUI so you can see the puzzle solve itself
        // Input File: inputfile:example.txt  // changes the input filename
        // Prnt Moves: +printmoves            // prints UP, DOWN, LEFT, RIGHT etc to show what moves the AI are making
        // Prnt Dists: +printdistances        // enables the printing of distances from the finished state for each move the AI makes
        // Prnt Tiles: -printtiles            // disables the printing of the tiles that are being swapped by the AI
        boolean enableGUI = arguments.contains("+enablegui");
        boolean humanMode = arguments.contains("+humanmode");
        Constants.PRINT_MOVES = arguments.contains("+printmoves");
        Constants.PRINT_DISTANCES = arguments.contains("+printdistances");
        Constants.PRINT_TILES = !arguments.contains("-printtiles");
        List<String> filearg = arguments.stream().filter(x -> x.startsWith("inputfile:")).collect(Collectors.toList());
        if (filearg.size() > 0) {
            String[] fileargarr = filearg.get(0).split("inputfile:");
            if (fileargarr.length > 1) Constants.FILE_NAME = fileargarr[1];
        }
        Fifteen.main(humanMode, enableGUI);
    }

    /**
     * main method
     */
    private static void main(boolean humanMode, boolean enableGUI) {
        Fifteen.setupConstants();
        if (humanMode) new MyFrame(new GameBoard(true));
        else {
            long start = System.currentTimeMillis();
            new AI(enableGUI);
            long end = System.currentTimeMillis();
            System.out.println(String.format("Total running time: %.2f seconds", (end - start) / 1000f));
        }
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
        for (int i = Constants.SQUARE_BOARD_SIZE; i > 2; i--) permutations *= i;
        Constants.PERMUTATIONS = permutations;
    }
}
