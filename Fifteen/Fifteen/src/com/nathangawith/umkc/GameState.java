package com.nathangawith.umkc;

public class GameState {

    /**
     * game constants
     */
    private final int tilesWide;
    private final int boardSize;
    public final int getTilesWide() { return tilesWide; }
    public final int getBoardSize() { return boardSize; }

    /**
     * constructor for a Fifteen Game GameState class
     * @param tilesWide number of tiles wide to make the game (e.g. Fifteen is typically 4 tiles wide)
     * @param tileSize width and height of each tile
     */
    public GameState(int tilesWide, int tileSize) {
        this.tilesWide = tilesWide;
        this.boardSize = tilesWide * tileSize;
    }

    /**
     * manipulates game state based on key that was pressed
     * @param key key that was pressed
     */
    public void key(MyKey key) {
        switch (key) {
            case UP:
                System.out.println("UP");
                break;
            case DOWN:
                System.out.println("DOWN");
                break;
            case LEFT:
                System.out.println("LEFT");
                break;
            case RIGHT:
                System.out.println("RIGHT");
                break;
            default:
                break;
        }
    }

}
