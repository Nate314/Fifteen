package com.nathangawith.umkc;

public class GameTile {

    private Integer label;
    public String getLabel() {
        if (this.label != null) return this.label + "";
        else return null;
    }

    /**
     * GameTile constructor
     * @param label label to save for this tile
     */
    public GameTile(Integer label) {
        this.label = label;
    }
}
