package com.nathangawith.umkc;
//#region imports
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
//#endregion
public class Fifteen {
    //#region fields
    private static boolean humanMode;
    private static boolean enableGUI;
    //#endregion
    //#region main
    /**
     * main method
     * @param args
     */
    public static void main(String[] args) {
        // setup initial values from command line and constants
        Fifteen.setupArgumentConstants(Arrays.asList(args));
        Fifteen.setupConstants();
        // run application
        if (Fifteen.humanMode) new MyFrame(new GameBoard(true));
        else {
            long start = System.currentTimeMillis();
            new AI(Fifteen.enableGUI);
            long end = System.currentTimeMillis();
            System.out.println(String.format("Total running time: %.2f seconds", (end - start) / 1000f));
        }
    }
    //#endregion
    //#region setup
    private static void setupArgumentConstants(List<String> arguments) {
        // Human Mode: +humanmode             // allows a human to play the game rather than the AI
        // Enable GUI: +enablegui             // enables the GUI so you can see the puzzle solve itself
        // Input File: inputfile:example.txt  // changes the input filename
        // Board Size: boardsize:3            // changes the horizontal and vertical size of the board (by default, the boardsize is 3)
        // Prnt Moves: +printmoves            // prints UP, DOWN, LEFT, RIGHT etc to show what moves the AI are making
        // Prnt Dists: +printdistances        // enables the printing of distances from the finished state for each move the AI makes
        // Prnt Tiles: -printtiles            // disables the printing of the tiles that are being swapped by the AI
        Fifteen.humanMode = arguments.contains("+humanmode");
        Fifteen.enableGUI = arguments.contains("+enablegui");
        Constants.PRINT_MOVES = arguments.contains("+printmoves");
        Constants.PRINT_DISTANCES = arguments.contains("+printdistances");
        Constants.PRINT_TILES = !arguments.contains("-printtiles");
        String file_name = Fifteen.parseArgument(arguments, "inputfile:");
        Constants.FILE_NAME = file_name == null ? Constants.FILE_NAME : file_name;
        String board_size_string = Fifteen.parseArgument(arguments, "boardsize:");
        if (board_size_string != null) {
            try {
                int BOARD_SIZE = Integer.parseInt(board_size_string);
                Constants.BOARD_SIZE = BOARD_SIZE;
                Constants.SQUARE_BOARD_SIZE = BOARD_SIZE * BOARD_SIZE;
            } catch (Exception e) {
                String error = String.format("ERR: Could not parse the 'boardsize:%x' as an integer", board_size_string);
                System.out.println(error);
            }
        }
    }

    /**
     * @param arguments command line arguments
     * @param parameterName parameter name of the value to retrieve
     * @return String of value attached to the given parameterName
     */
    private static String parseArgument(List<String> arguments, String parameterName) {
        List<String> filearg = arguments.stream().filter(x -> x.startsWith(parameterName)).collect(Collectors.toList());
        if (filearg.size() > 0) {
            String[] fileargarr = filearg.get(0).split(parameterName);
            if (fileargarr.length > 1) return fileargarr[1];
        }
        return null;
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
        Constants.IMPOSSIBLE_GAME_ARRAY = new int[Constants.SQUARE_BOARD_SIZE];
        Constants.SOLVED_GAME_ARRAY = new int[Constants.SQUARE_BOARD_SIZE];
        for (int i = 0; i < Constants.SQUARE_BOARD_SIZE; i++) {
            Constants.IMPOSSIBLE_GAME_ARRAY[i] = i + 1;
            Constants.SOLVED_GAME_ARRAY[i] = i + 1;
        }
        Constants.SOLVED_GAME_ARRAY[Constants.SQUARE_BOARD_SIZE - 1] = 0;
        Constants.IMPOSSIBLE_GAME_ARRAY[Constants.SQUARE_BOARD_SIZE - 1] = 0;
        Constants.IMPOSSIBLE_GAME_ARRAY[Constants.SQUARE_BOARD_SIZE - 2] = Constants.SQUARE_BOARD_SIZE - 2;
        Constants.IMPOSSIBLE_GAME_ARRAY[Constants.SQUARE_BOARD_SIZE - 3] = Constants.SQUARE_BOARD_SIZE - 1;
        Constants.STRINGIFIED_SOLVED_GAME = "";
        Constants.STRINGIFIED_IMPOSSIBLE_GAME = "";
        for (int i = 0; i < Constants.SQUARE_BOARD_SIZE; i++) {
            Constants.STRINGIFIED_SOLVED_GAME += Constants.SOLVED_GAME_ARRAY[i] + ",";
            Constants.STRINGIFIED_IMPOSSIBLE_GAME += Constants.IMPOSSIBLE_GAME_ARRAY[i] + ",";
        }
        Constants.STRINGIFIED_SOLVED_GAME = Constants.STRINGIFIED_SOLVED_GAME.substring(0, Constants.STRINGIFIED_SOLVED_GAME.length() - 1);
        Constants.STRINGIFIED_IMPOSSIBLE_GAME = Constants.STRINGIFIED_IMPOSSIBLE_GAME.substring(0, Constants.STRINGIFIED_IMPOSSIBLE_GAME.length() - 1);
    }
    //#endregion
}
