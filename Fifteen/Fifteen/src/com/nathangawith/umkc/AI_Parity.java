package com.nathangawith.umkc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AI_Parity {

    private ArrayList<String> orderedKeys = new ArrayList<String>();
    private HashSet<String> checkedGameKeys = new HashSet<String>();
    private HashMap<String, String[]> gameStates = new HashMap<String, String[]>();
    private HashMap<String, GameBoard[]> gameBoards = new HashMap<String, GameBoard[]>();
    private boolean solutionFound = false;
    private int statesCounter = 0;
    private final int halfNineFactorial = 9*8*7*6*5*4*3;
    private String output = null;
    public String getOutput() { return this.output; }
    private int keyCount = Constants.KEYS.size();

    /** */
    public AI_Parity(AI_IO ai_io) {
        HashSet<String> p = ai_io.getParities();
        System.out.println("~~~~~~~~~~~~~~~~");
        if (p != null && p.size() > 5) {
            this.checkedGameKeys = p;
        } else {
            this.calculateInvalidStates();
            this.output = String.join("\n", this.checkedGameKeys.stream().collect(Collectors.toList()));
        }
    }

    /**
     * builds HashSets in memory that will
     *    contain all unsolvable game states
     */
    private void calculateInvalidStates() {
        long start = System.currentTimeMillis();
        GameState impossibleGame = new GameState(new int[] {1, 2, 3, 4, 5, 6, 8, 7, 0}, false);

        // look for solution to current game
        this.checkChildrenForSolution(impossibleGame);
        while (!this.solutionFound && this.statesCounter < this.halfNineFactorial) {
            Set<String> keySet = this.gameStates.keySet().stream().collect(Collectors.toSet());
            keySet.removeAll(this.checkedGameKeys);
            if (keySet != null && keySet.size() != 0) {
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
        }
        long end = System.currentTimeMillis();
        System.out.println();
        System.out.println("Solution Found: " + this.solutionFound);
        System.out.println("Total Time: " + (end - start));
        if (this.solutionFound) {
            for (int i = 1; i < 4; i++) {
                String aGameKey = this.orderedKeys.get(this.orderedKeys.size() - i);
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
        } else {
            System.out.println("No Solution");
        }
    }

    /**
     * if the game key is found in the checkedGameKeys, it is not solvable
     * @param key puzzle key
     * @return true if the puzzle key is solvable
     */
    public boolean isOfSovableParity(String key) {
        return !this.checkedGameKeys.contains(key);
    }

    /**
     * checks if pressing UP, DOWN, LEFT, or RIGHT will result in a solution
     *    for the game passed
     * @param game game to check children of
     */
    private void checkChildrenForSolution(GameState game) {
        String gameKey = game.getGameBoard().stringify();
        String[] children = new String[this.keyCount];
        GameBoard[] childBoards = new GameBoard[this.keyCount];
        // loop through the different key combinations and add child states
        Constants.KEYS.parallelStream().forEach(key -> {
            int i = key.ordinal();
            GameState tempGame = new GameState(game);
            tempGame.key(key);
            GameBoard tempBoard = tempGame.getGameBoard();
            boolean isFinished = tempBoard.isFinished();
            if (isFinished) {
                System.out.println();
                System.out.println(isFinished);
                tempBoard.print();
            }
            this.solutionFound = this.solutionFound || isFinished;
            String stringifiedBoard = tempBoard.stringify();
            children[i] = stringifiedBoard;
            childBoards[i] = tempBoard;
        });
        System.out.print(String.format("\rStates: %d / %d (%f%%)",
            this.statesCounter, this.halfNineFactorial,
            100 * ((float) this.statesCounter) / ((float) this.halfNineFactorial)));
        // save key, children, and boards
        if (!this.gameStates.keySet().contains(gameKey)) {
            this.statesCounter++;
            this.orderedKeys.add(gameKey);
            this.gameStates.put(gameKey, children);
            this.gameBoards.put(gameKey, childBoards);
        }
    }
}
