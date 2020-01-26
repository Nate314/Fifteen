package com.nathangawith.umkc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class AI_Solver {

    public ArrayList<MyKey> solution;

    /**
     * @param game GameState to solve
     * @return an ArrayList of MyKey that designates the order of tile swaps to solve the game
     */
    public AI_Solver(GameState game) {
        ArrayList<String> gameStates = new ArrayList<String>();
        ArrayList<MyKey> moves = new ArrayList<MyKey>();
        System.out.println(game.distanceToFinish());
        while (true) {
            if (game.getGameBoard().isFinished()) {
                break;
            }
            HashMap<MyKey, Integer> options = new HashMap<MyKey, Integer>();
            Constants.KEYS.parallelStream().forEach(key -> {
                GameState tempGame = new GameState(game);
                tempGame.key(key);
                options.put(key, tempGame.distanceToFinish());
            });
            MyKey minKey = this.getMinKey(options, game, gameStates);
            if (minKey == null) {
                System.out.println("fail");
                break;
            } else {
                gameStates.add(game.getGameBoard().stringify());
                moves.add(minKey);
                game.key(minKey);
            }
        }
        this.solution = moves;
    }

    /**
     * @param game game to check when picking a random key
     * @return a random MyKey that will change the GameState passed
     */
    private MyKey pickRandomKey(GameState game) {
        MyKey randomKey = null;
        String stringifiedBoard = game.getGameBoard().stringify();
        String nextBoard = stringifiedBoard;
        while (nextBoard.equals(stringifiedBoard)) {
            randomKey = Constants.KEYS.get(new Random().nextInt(Constants.KEYS.size()));
            GameState tempGame = new GameState(game);
            tempGame.key(randomKey);
            nextBoard = tempGame.getGameBoard().stringify();
        }
        return randomKey;
    }

    /**
     * returns the key that will minimize the score and not result in a previous
     * gamestate that has already been seen. If all options result in a previous
     * gamestate, then a random key is returned.
     * @param options a map between keys and the scores of the resulting GameStates
     * @param game current GameState
     * @param gameStates previous stringified boards
     * @return the key that will minimize the score
     */
    private MyKey getMinKey(HashMap<MyKey, Integer> options, GameState game, ArrayList<String> gameStates) {
        if (options.keySet().size() > 0) {
            int min = Integer.MAX_VALUE;
            MyKey minKey = null;
            for (MyKey key : options.keySet()) {
                int val = options.get(key);
                if (val < min) {
                    min = val;
                    minKey = key;
                }
            }
            if (minKey != null) {
                GameState temp = new GameState(game);
                temp.key(minKey);
                if (gameStates.contains(temp.getGameBoard().stringify())) {
                    options.remove(minKey);
                    return this.getMinKey(options, game, gameStates);
                } else {
                    return minKey;
                }
            }
        }
        return this.pickRandomKey(game);
    }
}
