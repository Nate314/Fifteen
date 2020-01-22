package com.nathangawith.umkc;

public class GameTile {

    private Integer label;
    public String getLabel() {
        if (this.label != null) return this.label + "";
        else return null;
    }

    public GameTile(Integer label) {
        this.label = label;
    }
}
