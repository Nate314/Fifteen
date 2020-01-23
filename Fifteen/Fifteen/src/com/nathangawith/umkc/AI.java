package com.nathangawith.umkc;

public class AI {

    public AI() {
        new MyFrame(new GameState(null, true));
        // new MyFrame(new GameState(null, false));
        AI_IO io = new AI_IO();
        GameState[] games = io.getGames();
        for (GameState game : games) {
            // new MyFrame(game);
            game.getGameBoard().print();
        }
        this.ai(games[0]);
    }

    private void ai(GameState game) {

    }
}
