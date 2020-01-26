package com.nathangawith.umkc;
//#region imports
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
//#endregion
public class AI_Parity {
    //#region fields
    private ArrayList<String> orderedKeys = new ArrayList<String>();
    private HashSet<String> checkedGameKeys = new HashSet<String>();
    private HashMap<String, String[]> gameStates = new HashMap<String, String[]>();
    private HashMap<String, GameBoard[]> gameBoards = new HashMap<String, GameBoard[]>();
    private int statesCounter = 0;
    private int keyCount = Constants.KEYS.size();
    //#endregion
    //#region constructor
    /**
     * loads invalid parites
     * @param ai_io io object for reading and writing files
     */
    public AI_Parity(AI_IO ai_io) {
        HashSet<String> p = ai_io.getParities();
        System.out.println("~~~~~~~~~~~~~~~~");
        // if the unsolvable parities have been written to the disk
        if (p != null && p.size() > 5) {
            this.checkedGameKeys = p;
        // otherwise, calculate invalid states and save to disk
        } else {
            this.calculateInvalidStates();
            String out = String.join("\n", this.checkedGameKeys.stream().collect(Collectors.toList()));
            ai_io.outputParities(out);
        }
    }
    //#endregion
    //#region public methods
    /**
     * if the game key is found in the checkedGameKeys, it is not solvable
     * @param key puzzle key
     * @return true if the puzzle key is solvable
     */
    public boolean isOfSovableParity(String key) {
        return !this.checkedGameKeys.contains(key);
    }
    //#endregion
    //#region computation
    /**
     * builds HashSets in memory that will
     * contain all unsolvable game states
     */
    private void calculateInvalidStates() {
        // for 3x3,  {1, 2, 3, 4, 5, 6, 8, 7, 0} is an invalid board
        int[] impossibleGameArray = new int[Constants.SQUARE_BOARD_SIZE];
        for (int i = 0; i < Constants.SQUARE_BOARD_SIZE; i++) impossibleGameArray[i] = i + 1;
        impossibleGameArray[Constants.SQUARE_BOARD_SIZE - 1] = 0;
        impossibleGameArray[Constants.SQUARE_BOARD_SIZE - 2] = Constants.SQUARE_BOARD_SIZE - 2;
        impossibleGameArray[Constants.SQUARE_BOARD_SIZE - 3] = Constants.SQUARE_BOARD_SIZE - 1;
        GameBoard impossibleGame = new GameBoard(impossibleGameArray);

        // look for solution to current game.
        // Since the game is impossible, all impossible states will be found.
        this.checkChildrenForSolution(impossibleGame);
        while (this.statesCounter < Constants.PERMUTATIONS) {
            Set<String> keySet = this.gameStates.keySet().stream().collect(Collectors.toSet());
            keySet.removeAll(this.checkedGameKeys);
            if (keySet != null && keySet.size() != 0) {
                for (String key : keySet) {
                    this.checkedGameKeys.add(key);
                    for (GameBoard board : this.gameBoards.get(key))
                        if (board != null)
                            this.checkChildrenForSolution(new GameBoard(board));
                }
            }
        }
    }

    /**
     * checks if pressing UP, DOWN, LEFT, or RIGHT will result in a solution
     *    for the game passed
     * @param game game to check children of
     */
    private void checkChildrenForSolution(GameBoard game) {
        String gameKey = game.stringify();
        String[] children = new String[this.keyCount];
        GameBoard[] childBoards = new GameBoard[this.keyCount];
        // loop through the different key combinations and add child states
        Constants.KEYS.parallelStream().forEach(key -> {
            int i = key.ordinal();
            GameBoard tempGame = new GameBoard(game);
            tempGame.key(key);
            GameBoard tempBoard = tempGame;
            String stringifiedBoard = tempBoard.stringify();
            children[i] = stringifiedBoard;
            childBoards[i] = tempBoard;
        });
        // save key, children, and boards
        if (!this.gameStates.keySet().contains(gameKey)) {
            this.statesCounter++;
            this.orderedKeys.add(gameKey);
            this.gameStates.put(gameKey, children);
            this.gameBoards.put(gameKey, childBoards);
        }
        // print status
        System.out.print(String.format("\rStates: %d / %d (%f%%)",
            this.statesCounter, Constants.PERMUTATIONS,
            100 * ((float) this.statesCounter) / ((float) Constants.PERMUTATIONS)));
    }
    //#endregion
}
