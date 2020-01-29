package com.nathangawith.umkc;
//#region imports
import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
//#endregion
/**
 * Constants used throughout the application can be modified here.
 *   Everything here is final except for the COLOR_TEXT object
 */
public final class Constants {
    //#region colors
    // colors from https://material.io/resources/color/#!/?view.left=0&view.right=0&primary.color=1565C0
    public static final Color MD_PRIMARY_BLUE = new Color(21, 101, 192);
    public static final Color MD_LIGHT_BLUE = new Color(94, 146, 243);
    public static final Color MD_DARK_BLUE = new Color(0, 60, 143);
    public static final Color MD_WHITE = new Color(255, 255, 255);
    public static final Color MD_RED = new Color(255, 0, 0);
    public static final Color MD_GREEN = new Color(0, 255, 0);
    // application colors
    public static final Color COLOR_BACKGROUND = MD_PRIMARY_BLUE;
    public static final Color COLOR_FOREGROUND = MD_LIGHT_BLUE;
    public static final Color COLOR_ALL_CORRECT = MD_GREEN;
    public static final Color COLOR_MIXING = MD_RED;
    //#endregion
    //#region board sizing integers
    public static int BOARD_SIZE = 3;
    public static final int TILE_WIDTH = 120;
    public static final int GUTTER_SIZE = 6;
    public static final int WINDOW_X = 0; // 1920;
    public static final int WINDOW_Y = 0; // -500;
    public static int SQUARE_BOARD_SIZE = Constants.BOARD_SIZE * Constants.BOARD_SIZE;
    public static final int PX_BOARD_SIZE = Constants.BOARD_SIZE * Constants.TILE_WIDTH;
    //#endregion
    //#region other constants
    public static final int MIXING_FREQUENCY = 1000 / 60; // mix at 60fps
    public static final int MIXING_NUMBER = 60 * 3;       // for three seconds
    public static final HashMap<MyKey, Integer> MOVING_ROW_DIFF = new HashMap<MyKey, Integer>();
    public static final HashMap<MyKey, Integer> MOVING_COL_DIFF = new HashMap<MyKey, Integer>();
    public static final List<MyKey> KEYS = Arrays.asList(new MyKey[] { MyKey.UP, MyKey.DOWN, MyKey.LEFT, MyKey.RIGHT });
    public static long PERMUTATIONS;
    public static int[] SOLVED_GAME_ARRAY;
    public static int[] IMPOSSIBLE_GAME_ARRAY;
    public static String STRINGIFIED_SOLVED_GAME;
    public static String STRINGIFIED_IMPOSSIBLE_GAME;
    //#endregion
    //#region files
    public static String FILE_NAME = "program_1_data.txt";
    //#endregion
    //#region printing booleans for AI output
    public static boolean PRINT_MOVES = false;
    public static boolean PRINT_DISTANCES = false;
    public static boolean PRINT_TILES = true;
    //#endregion
}
