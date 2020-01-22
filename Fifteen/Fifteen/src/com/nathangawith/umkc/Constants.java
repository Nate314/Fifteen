package com.nathangawith.umkc;

import java.awt.Color;

public final class Constants {
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
    public static Color COLOR_TEXT = MD_WHITE;
    // board sizing integers
    public static final int BOARD_SIZE = 3;
    public static final int TILE_WIDTH = 120;
    public static final int GUTTER_SIZE = 8;
    public static final int WINDOW_X = 0; // 1920;
    public static final int WINDOW_Y = 0; // -500;
    // other constants
    public static final int MIXING_FREQUENCY = 1000 / 60; // mix at 60fps
    public static final int MIXING_NUMBER = 60 * 3;       // for three seconds
    // AI constants
    public static final String FILE_NAME = "program_1_data.txt";
}
