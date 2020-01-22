package com.nathangawith.umkc;

public class AI {

    GameState[] games;

    public AI() {
        AI_IO io = new AI_IO();
        this.games = io.getGames();
        // for (GameState game : games) {
        //     new MyFrame(game);
        // }
    }
}
