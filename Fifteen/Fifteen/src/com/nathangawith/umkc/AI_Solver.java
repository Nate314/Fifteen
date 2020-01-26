package com.nathangawith.umkc;
//#region imports
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
//#endregion
public class AI_Solver {
    //#region fields
    public ArrayList<MyKey> solution;
    //#endregion
    //#region constructor
    /**
     * generates solution as a list of key presses
     * @param gameBoard GameBoard to solve
     * @return an ArrayList of MyKey that designates the order of tile swaps to solve the game
     */
    public AI_Solver(GameBoard gameBoard) {
        ArrayList<String> gameStates = new ArrayList<String>();
        ArrayList<MyKey> moves = new ArrayList<MyKey>();
        System.out.println(gameBoard.distanceToFinish());
        while (!gameBoard.isFinished()) {
            HashMap<MyKey, Integer> options = new HashMap<MyKey, Integer>();
            Constants.KEYS.parallelStream().forEach(key -> {
                GameBoard tempGame = new GameBoard(gameBoard);
                tempGame.key(key);
                options.put(key, tempGame.distanceToFinish());
            });
            MyKey minKey = this.getMinKey(options, gameBoard, gameStates);
            gameStates.add(gameBoard.stringify());
            moves.add(minKey);
            gameBoard.key(minKey);
        }
        this.solution = moves;
    }
    //#endregion
    //#region private methods
    /**
     * @param gameBoard game to check when picking a random key
     * @return a random MyKey that will change the GameState passed
     */
    private MyKey pickRandomKey(GameBoard gameBoard) {
        MyKey randomKey = null;
        String stringifiedBoard = gameBoard.stringify();
        String nextBoard = stringifiedBoard;
        while (nextBoard.equals(stringifiedBoard)) {
            randomKey = Constants.KEYS.get(new Random().nextInt(Constants.KEYS.size()));
            GameBoard tempGame = new GameBoard(gameBoard);
            tempGame.key(randomKey);
            nextBoard = tempGame.stringify();
        }
        return randomKey;
    }

    /**
     * returns the key that will minimize the score and not result in a previous
     * gameboard that has already been seen. If all options result in a previous
     * gameboard, then a random key is returned.
     * @param options a map between keys and the scores of the resulting GameBoards
     * @param gameBoard current GameBoard
     * @param gameStates previous stringified boards
     * @return the key that will minimize the score
     */
    private MyKey getMinKey(HashMap<MyKey, Integer> options, GameBoard gameBoard, ArrayList<String> gameStates) {
        // if there are options available
        if (options.keySet().size() > 0) {
            int min = Integer.MAX_VALUE;
            MyKey minKey = null;
            // find key press associated with the minimum score
            for (MyKey key : options.keySet()) {
                int val = options.get(key);
                if (val < min) {
                    min = val;
                    minKey = key;
                }
            }
            if (minKey != null) {
                GameBoard temp = new GameBoard(gameBoard);
                temp.key(minKey);
                // if the state has not been visited
                if (!gameStates.contains(temp.stringify())) {
                    return minKey;
                // otherwise recursively call without the calculated min key
                } else {
                    options.remove(minKey);
                    return this.getMinKey(options, gameBoard, gameStates);
                }
            }
        }
        // if the minimum key leads to a state that
        // has already been visited then pick a random key
        return this.pickRandomKey(gameBoard);
    }
    //#endregion
}
