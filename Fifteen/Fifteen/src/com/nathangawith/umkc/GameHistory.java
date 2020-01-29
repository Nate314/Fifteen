package com.nathangawith.umkc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameHistory {
    //#region fields
    public ArrayList<MyKey> keyList;
    public ArrayList<Integer> distances;
    public ArrayList<String> swappedTiles;
    public ArrayList<String> stringifiedBoards;
    //#endregion
    //#region constructors
    public GameHistory(String initialStringifiedBoard) {
        this.keyList = new ArrayList<MyKey>();
        this.distances = new ArrayList<Integer>();
        this.swappedTiles = new ArrayList<String>();
        this.stringifiedBoards = new ArrayList<String>();
        if (initialStringifiedBoard != null) this.stringifiedBoards.add(initialStringifiedBoard);
    }
    public GameHistory(GameHistory history) {
        this((String) null);
        history.keyList.forEach(x -> this.keyList.add(x));
        history.distances.forEach(x -> this.distances.add(x));
        history.swappedTiles.forEach(x -> this.swappedTiles.add(x));
        history.stringifiedBoards.forEach(x -> this.stringifiedBoards.add(x));
    }
    //#endregion
    //#region public methods
    /**
     * adds state to history
     * @param stringifiedBoard state of board after key press
     * @param key key being pressed
     */
    public void add(String stringifiedBoard, MyKey key, int distance) {
        List<String> lastBoard = Arrays.asList(this.stringifiedBoards.get(this.stringifiedBoards.size() - 1).split(","));
        String swappedTile = Arrays.asList(stringifiedBoard.split(",")).get(lastBoard.indexOf("0"));
        this.keyList.add(key);
        this.distances.add(distance);
        this.swappedTiles.add(swappedTile);
        this.stringifiedBoards.add(stringifiedBoard);
    }

    /**
     * removes the last gameBoard from the history
     * @return the last stringified GameBoard that was removed
     */
    public String pop() {
        String result = this.stringifiedBoards.get(this.stringifiedBoards.size() - 1);
        this.keyList.remove(this.keyList.size() - 1);
        this.distances.remove(this.distances.size() - 1);
        this.swappedTiles.remove(this.swappedTiles.size() - 1);
        this.stringifiedBoards.remove(this.stringifiedBoards.size() - 1);
        return result;
    }

    /**
     * @return the number of items added to this history object
     */
    public int size() {
        return this.keyList.size();
    }
    //#endregion
}
