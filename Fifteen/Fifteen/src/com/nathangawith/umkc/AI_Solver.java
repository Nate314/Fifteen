package com.nathangawith.umkc;
//#region imports
import java.util.ArrayList;
import java.util.HashMap;
//#endregion
public class AI_Solver {
    //#region fields
    public final GameHistory history;
    private ArrayList<String> visited = new ArrayList<String>();
    private HashMap<Integer, ArrayList<GameBoard>> options = new HashMap<Integer, ArrayList<GameBoard>>();
    private Boolean solvable;
    public boolean isSolvable() {
        return this.solvable == (Boolean) true ? true
            : this.solvable == (Boolean) false || this.solvable == (Boolean) null ? false : false;
    }
    //#endregion
    //#region constructor
    /**
     * generates solution as a list of key presses
     * @param gameBoard GameBoard to solve
     */
    public AI_Solver(GameBoard gameBoard) {
        this.solvable = false;
        gameBoard.history = new GameHistory(gameBoard.stringify());
        this.addOptions(gameBoard);
        // if gameBoard.isFinished() returns true or null, the game is finished
        while (this.solvable == (Boolean) false) {
            this.visited.add(gameBoard.stringify());
            gameBoard = this.nextStep(gameBoard);
            if (gameBoard == null) {
                System.out.println("ERROR!");
                break;
            }
            this.solvable = gameBoard.isFinished();
        }
        this.history = gameBoard.history;
    }
    //#endregion
    //#region private methods
    /**
     * @param GameBoard current GameBoard
     * @return the next GameBoard state
     */
    private GameBoard nextStep(GameBoard gameBoard) {
        int min = Integer.MAX_VALUE;
        for (int val : this.options.keySet())
            if (val < min) min = val;
        if (this.options.get(min).size() != 0) {
            GameBoard bestGameBoard = this.options.get(min).remove(0);
            this.addOptions(bestGameBoard);
            return bestGameBoard;
        } else {
            this.options.remove(min);
            return this.nextStep(gameBoard);
        }
    }
    /**
     * adds 0-4 child states to the options HashMap depending on if they have been visited
     * @param gameBoard current GameBoard
     */
    private void addOptions(GameBoard gameBoard) {
        Constants.KEYS.forEach(key -> {
            GameBoard temp = new GameBoard(gameBoard);
            temp.key(key);
            String stringifiedTempBoard = temp.stringify();
            if (!stringifiedTempBoard.equals(gameBoard.stringify()) && !this.visited.contains(stringifiedTempBoard)) {
                temp.history = new GameHistory(gameBoard.history);
                temp.history.add(temp.stringify(), key, temp.distanceToFinish());
                int dist = temp.distanceToFinish();
                if (!this.options.keySet().contains(dist)) {
                    this.options.put(dist, new ArrayList<GameBoard>());
                }
                this.options.get(dist).add(temp);
            }
        });
    }
    //#endregion
}
