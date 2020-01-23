package com.nathangawith.umkc;

import java.util.ArrayList;

public class GameHistory {

    private ArrayList<GameState> states;

    /**
     * GameHistory constructor
     * @param initialGameState initial game state to store in states
     */
    public GameHistory(GameState initialGameState) {
        this.states = new ArrayList<GameState>();
        this.states.add(initialGameState);
    }
}
