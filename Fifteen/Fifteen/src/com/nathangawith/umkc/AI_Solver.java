package com.nathangawith.umkc;
//#region imports
import java.util.ArrayList;
import java.util.HashMap;
//#endregion
public class AI_Solver {
    //#region fields
    public final GameHistory history;
    public ArrayList<String> badStates = new ArrayList<String>();
    //#endregion
    //#region constructor
    /**
     * generates solution as a list of key presses
     * @param gameBoard GameBoard to solve
     */
    public AI_Solver(GameBoard gameBoard) {
        this.history = new GameHistory(gameBoard.stringify());
        while (!gameBoard.isFinished()) gameBoard = this.nextStep(gameBoard);
    }
    //#endregion
    //#region private methods
    /**
     * @param GameBoard current GameBoard
     * @return the next GameBoard state
     */
    private GameBoard nextStep(GameBoard gameBoard) {
        MyKey[] minKeys = this.orderedKeys(gameBoard);
        for (MyKey minKey : minKeys) {
            GameBoard temp = new GameBoard(gameBoard);
            temp.key(minKey);
            String stringifiedBoard = temp.stringify();
            if (this.history.stringifiedBoards.contains(stringifiedBoard)
                || this.badStates.contains(stringifiedBoard)) {
                    continue;
            } else {
                this.history.add(temp.stringify(), minKey, temp.distanceToFinish());
                return temp;
            }
        }
        this.badStates.add(this.history.pop());
        return this.nextStep(new GameBoard(this.history.stringifiedBoards.get(this.history.stringifiedBoards.size() - 1).split(",")));
    }

    /**
     * wrapper function for
     * <code>MyKey[] orderedKeys(HashMap<MyKey, Integer> options, MyKey[] result, int index)</code>
     * @param gameBoard the current gameBoard
     * @return an ordered list of keys based on score
     */
    private MyKey[] orderedKeys(GameBoard gameBoard) {
        return orderedKeys(new HashMap<MyKey, Integer>() {
            private static final long serialVersionUID = 1L;
            public HashMap<MyKey, Integer> init() {
                Constants.KEYS.stream().forEach(key -> {
                    GameBoard tempGame = new GameBoard(gameBoard);
                    tempGame.key(key);
                    this.put(key, tempGame.distanceToFinish());
                });
                return this;
            }
        }.init(), new MyKey[Constants.KEYS.size()], 0);
    }

    /**
     * @param options a map between keys and the scores of the resulting GameBoards
     * @param result result that is built recursively
     * @param index index used to index result recursively
     * @return an ordered list of keys based on score
     */
    private MyKey[] orderedKeys(HashMap<MyKey, Integer> options, MyKey[] result, int index) {
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
        result[index++] = minKey;
        options.remove(minKey);
        if (index == Constants.KEYS.size()) return result;
        else return this.orderedKeys(options, result, index);
    }
    //#endregion
}
