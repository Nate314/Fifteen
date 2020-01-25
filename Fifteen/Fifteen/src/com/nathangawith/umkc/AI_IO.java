package com.nathangawith.umkc;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AI_IO {

    private GameState[] games;
    public GameState[] getGames() {
        return this.games;
    }

    /**
     * parses txt file into an array of GameState objects
     */
    public AI_IO() {
        // read in games and parse as ArrayList<ArrayList<Integer>>
        ArrayList<ArrayList<Integer>> boards = new ArrayList<ArrayList<Integer>>();
        boards.add(new ArrayList<Integer>());
        try {
            for (String line : this.readFile(Constants.FILE_NAME).split("\n")) {
                List<String> numbers = Arrays.asList(line.split(" "));
                if (numbers.size() == Constants.BOARD_SIZE) {
                    boards.get(boards.size() - 1).addAll(
                        numbers.parallelStream().map(x ->
                            Integer.parseInt(x)
                        ).collect(Collectors.toList()));
                } else {
                    boards.add(new ArrayList<Integer>());
                }
            }
            // populate games
            int i = 0;
            games = new GameState[boards.size()];
            for (ArrayList<Integer> numbers : boards) {
                int[] arr = this.toArray(numbers);
                games[i++] = new GameState(arr, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * retrieves written parities from file
     */
    public HashSet<String> getParities() {
        System.out.println("Retrieving . . .");
        HashSet<String> result = new HashSet<String>();
        ArrayList<String> lines = new ArrayList<String>();
        System.out.println();
        for (int i = 1; i < 100; i++) {
            System.out.print("\rReading . . . " + i);
            try {
                String filename = Constants.OUTPUT_PARITY_FILE_NAME.split(".txt")[0] + i + ".txt";
                for (String line : this.readFile(filename).split("\n")) {
                    lines.add(line);
                }
            } catch (Exception ex) {
                break;
            }
        }
        int i = 0;
        System.out.println();
        for (String str : lines) {
            System.out.print("\rAdding . . . " + (++i));
            result.add(str);
        }
        System.out.println();
        return result;
    }

    /**
     * writes passed string to a file
     */
    public void outputParities(String output) {
        int i = 0;
        String out = "";
        for (String line : output.split("\n")) {
            out += line + "\n";
            if (++i % 10000 == 0) {
                String filename = Constants.OUTPUT_PARITY_FILE_NAME.split(".txt")[0] + (i / 10000) + ".txt";
                this.writeFile(filename, out);
                out = "";
            }
        }
    }

    /**
     * converts ArrayList<Integer> to Integer[]
     * @param arrayList ArrayList to convert
     * @return Integer[]
     */
    private int[] toArray(ArrayList<Integer> arrayList) {
        int[] array = new int[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++)
            array[i] = arrayList.get(i);
        return array;
    }

    /**
     * reads file
     * code from https://www.geeksforgeeks.org/different-ways-reading-text-file-java/
     * @param filename file to read
     * @return file as string
     */
    private String readFile(String filename) throws Exception {
        String result = "";
        File file = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(file)); 
        String st;
        while ((st = br.readLine()) != null) result += st + '\n';
        br.close();
        return result;
    }

    /**
     * writes file
     * code from https://www.w3schools.com/java/java_files_create.asp
     */
    private void writeFile(String filename, String output) {
        try {
            File myObj = new File(filename);
            myObj.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred when creating the file.");
            e.printStackTrace();
        }
        try {
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write(output);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred when writing the file contents.");
            e.printStackTrace();
        }
    }
}
