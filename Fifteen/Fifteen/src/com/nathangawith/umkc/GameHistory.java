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
    //#region constructor
    public GameHistory(String initialStringifiedBoard) {
        this.keyList = new ArrayList<MyKey>();
        this.distances = new ArrayList<Integer>();
        this.swappedTiles = new ArrayList<String>();
        this.stringifiedBoards = new ArrayList<String>();
        this.stringifiedBoards.add(initialStringifiedBoard);
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

    // /**
    //  * simplifies history
    //  * console.log(``.split('|').map((v, i) => `(${i}, ${v})`).join(', '));
    //  */
    // public void simplify() {
    //     boolean finished = true;
    //     int firstIndex, lastIndex;
    //     for (String stringifiedBoard : stringifiedBoards) {
    //         firstIndex = stringifiedBoards.indexOf(stringifiedBoard);
    //         lastIndex = stringifiedBoards.lastIndexOf(stringifiedBoard);
    //         if (firstIndex != lastIndex) {
    //             finished = false;
    //             System.out.println(String.format("%d-%d", firstIndex, lastIndex));
    //             List<MyKey> newKeyList = this.keyList.subList(0, firstIndex);
    //             List<Integer> newDistances = this.distances.subList(0, firstIndex);
    //             List<String> newSwappedTiles = this.swappedTiles.subList(0, firstIndex);
    //             List<String> newStringifiedBoards = this.stringifiedBoards.subList(0, firstIndex);
    //             newKeyList.addAll(this.keyList.subList(lastIndex, this.keyList.size()));
    //             newDistances.addAll(this.distances.subList(lastIndex, this.distances.size()));
    //             newSwappedTiles.addAll(this.swappedTiles.subList(lastIndex, this.swappedTiles.size()));
    //             newStringifiedBoards.addAll(this.stringifiedBoards.subList(lastIndex, this.stringifiedBoards.size()));
    //             this.keyList = (ArrayList<MyKey>) newKeyList.stream().collect(Collectors.toList());
    //             this.distances = (ArrayList<Integer>) newDistances.stream().collect(Collectors.toList());
    //             this.swappedTiles = (ArrayList<String>) newSwappedTiles.stream().collect(Collectors.toList());
    //             this.stringifiedBoards = (ArrayList<String>) newStringifiedBoards.stream().collect(Collectors.toList());
    //             break;
    //         }
    //     }
    //     if (!finished) this.simplify();
    // }
    //#endregion
}
