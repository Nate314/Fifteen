package com.nathangawith.umkc;

import java.util.ArrayList;

public class GameHistory {

    ArrayList<GameState> states;

    public GameHistory(GameState initialGameState) {
        this.states = new ArrayList<GameState>();
        this.states.add(initialGameState);
    }
}
