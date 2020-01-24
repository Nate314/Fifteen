package com.nathangawith.umkc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AI {

    ArrayList<String> orderedKeys = new ArrayList<String>();
    HashSet<String> checkedGameKeys = new HashSet<String>();
    HashMap<String, String[]> gameStates = new HashMap<String, String[]>();
    HashMap<String, GameBoard[]> gameBoards = new HashMap<String, GameBoard[]>();
    private boolean solutionFound = false;
    private int statesCounter = 0;

    public AI() {
        // AI_IO io = new AI_IO();
        // GameState[] games = io.getGames();
        // new MyFrame(new GameState(games[0]));
        // new MyFrame(new GameState(null, true));
        // new MyFrame(new GameState(null, false));
        this.AI_STUFF();
    }

    private void AI_STUFF() {
        long start = System.currentTimeMillis();
        AI_IO io = new AI_IO();
        GameState[] games = io.getGames();
        // for (GameState game : games) {
        //     // new MyFrame(game);
        //     game.getGameBoard().print();
        // }

        // look for solution to current game
        this.checkChildrenForSolution(games[0]);
        while (!this.solutionFound) {
            Set<String> keySet = this.gameStates.keySet().stream().collect(Collectors.toSet());
            keySet.removeAll(this.checkedGameKeys);
            for (String key : keySet) {
                this.checkedGameKeys.add(key);
                for (GameBoard board : this.gameBoards.get(key)) {
                    if (board != null && !this.solutionFound) {
                        GameState game = new GameState(board);
                        this.checkChildrenForSolution(game);
                    }
                }
            }
        }
        long end = System.currentTimeMillis();
        for (int i = 1; i < 4; i++) {
            String aGameKey = this.orderedKeys.get(this.orderedKeys.size() - i);
            System.out.println("Solution Found: " + this.solutionFound);
            System.out.println("Total Time: " + (end - start));
            System.out.println(String.format("gameKey: %s", aGameKey));
            String[] temp = this.gameStates.get(aGameKey);
            System.out.println(temp);
            for (String str : temp) {
                if (str != null) {
                    System.out.println(str);
                    for (GameBoard board : this.gameBoards.get(aGameKey)) {
                        if (board != null) {
                            System.out.println();
                            System.out.println(board.stringify());
                            board.print();
                            System.out.println();
                        }
                    }
                }
            }
        }
    }

    /**
     * checks if pressing UP, DOWN, LEFT, or RIGHT will result in a solution
     *    for the game passed
     * @param game game to check children of
     */
    private void checkChildrenForSolution(GameState game) {
        String gameKey = game.getGameBoard().stringify();
        String[] children = new String[Constants.KEYS.length];
        GameBoard[] childBoards = new GameBoard[Constants.KEYS.length];
        // loop through the different key combinations and add child states
        for (int i = 0; i < Constants.KEYS.length; i++) {
            GameState tempGame = new GameState(game);
            tempGame.key(Constants.KEYS[i]);
            GameBoard tempBoard = tempGame.getGameBoard();
            boolean isFinished = tempBoard.isFinished();
            if (isFinished) {
                System.out.println(isFinished);
                tempBoard.print();
            }
            this.solutionFound = this.solutionFound || isFinished;
            String stringifiedBoard = tempBoard.stringify();
            children[i] = stringifiedBoard;
            childBoards[i] = tempBoard;
        }
        System.out.println(String.format("States: %d", this.statesCounter));
        // save key, children, and boards
        if (!this.gameStates.keySet().contains(gameKey)) {
            this.statesCounter++;
            this.orderedKeys.add(gameKey);
            this.gameStates.put(gameKey, children);
            this.gameBoards.put(gameKey, childBoards);
        }
    }
}
