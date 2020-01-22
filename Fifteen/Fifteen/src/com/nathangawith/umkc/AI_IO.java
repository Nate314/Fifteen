package com.nathangawith.umkc;

import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AI_IO {

    private GameState[] games;
    public GameState[] getGames() {
        return this.games;
    }

    public AI_IO() {
        // read in games and parse as ArrayList<ArrayList<Integer>>
        ArrayList<ArrayList<Integer>> boards = new ArrayList<ArrayList<Integer>>();
        boards.add(new ArrayList<Integer>());
        for (String line : this.readFile().split("\n")) {
            List<String> numbers = Arrays.asList(line.split(" "));
            if (numbers.size() == Constants.BOARD_SIZE) {
                boards.get(boards.size() - 1).addAll(
                    numbers.parallelStream().map(x -> {
                        int num = Integer.parseInt(x);
                        return num == 0 ? null : num;
                    }).collect(Collectors.toList()));
            } else {
                boards.add(new ArrayList<Integer>());
            }
        }
        // populate games
        int i = 0;
        games = new GameState[boards.size()];
        for (ArrayList<Integer> numbers : boards) {
            Integer[] arr = this.toArray(numbers);
            games[i++] = new GameState(Constants.BOARD_SIZE, arr, false);
        }
    }

    /**
     * converts ArrayList<Integer> to Integer[]
     * @param arrayList ArrayList to convert
     * @return Integer[]
     */
    private Integer[] toArray(ArrayList<Integer> arrayList) {
        Integer[] array = new Integer[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++)
            array[i] = arrayList.get(i);
        return array;
    }

    /**
     * reads file
     * code from https://www.geeksforgeeks.org/different-ways-reading-text-file-java/
     * @return file as string
     */
    private String readFile() {
        String result = "";
        try {
            File file = new File(Constants.FILE_NAME);
            BufferedReader br = new BufferedReader(new FileReader(file)); 
            String st; 
            while ((st = br.readLine()) != null) result += st + '\n';
            br.close();
        } catch(Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }
}
