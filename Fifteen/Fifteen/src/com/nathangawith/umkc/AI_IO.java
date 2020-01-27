package com.nathangawith.umkc;
//#region imports
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
//#endregion
public class AI_IO {
    //#region fields
    private GameBoard[] gameBoards;
    public GameBoard[] getGameBoards() { return this.gameBoards; }
    //#endregion
    //#region constructor
    /**
     * parses txt file into an array of GameBoard objects
     */
    public AI_IO() {
        // read in games and parse as ArrayList<ArrayList<Integer>>
        ArrayList<ArrayList<Integer>> boards = new ArrayList<ArrayList<Integer>>();
        boards.add(new ArrayList<Integer>());
        try {
            // for each line in the file
            for (String line : this.readFileLines(Constants.FILE_NAME)) {
                // if the correct number of numbers are on the current line
                //   add the numbers to the current ArrayList
                List<String> numbers = Arrays.asList(line.split(" "));
                if (numbers.size() == Constants.BOARD_SIZE) {
                    boards.get(boards.size() - 1).addAll(
                        numbers.parallelStream().map(x ->
                            Integer.parseInt(x)
                        ).collect(Collectors.toList()));
                // otherwise, add a new empty ArrayList
                } else {
                    boards.add(new ArrayList<Integer>());
                }
            }
            // create GameBoard objects
            this.gameBoards = new GameBoard[boards.size()];
            for (int i = 0; i < boards.size(); i++) {
                int[] arr = this.toArray(boards.get(i));
                this.gameBoards[i] = new GameBoard(arr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //#endregion
    //#region java io
    private String getParityFileName(int counter) {
        String label = String.format("_%dx%d_", Constants.BOARD_SIZE, Constants.BOARD_SIZE);
        return Constants.OUTPUT_PARITY_FILE_NAME.split(".txt")[0] + label + counter + ".txt";
    }

    /**
     * retrieves written parities from file
     */
    public HashSet<String> getParities() {
        HashSet<String> result = new HashSet<String>();
        System.out.println();
        // read all files in the form
        int fileCounter = 1;
        while (true) {
            String filename = this.getParityFileName(fileCounter);
            System.out.print("\rReading . . . " + filename);
            try {
                // add each line from files to result
                for (String line : this.readFileLines(filename)) result.add(line);
            } catch (Exception ex) { break; }
            fileCounter++;
        }
        System.out.println();
        return result;
    }

    /**
     * writes passed string to a file
     */
    public void outputParities(String output) {
        String out = "";
        String[] lines = output.split("\n");
        for (int i = 1; i < lines.length; i++) {
            out += lines[i] + "\n";
            // output to disk every 10000 lines
            if (i % 10000 == 0) {
                String filename = this.getParityFileName(i / 10000);
                System.out.print("\rWriting . . . " + filename);
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
    //#endregion
    //#region file io
    /**
     * reads file
     * code from https://www.geeksforgeeks.org/different-ways-reading-text-file-java/
     * @param filename file to read
     * @return array of lines from the file
     */
    private String[] readFileLines(String filename) throws Exception {
        String result = "";
        File file = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(file)); 
        String st;
        while ((st = br.readLine()) != null) result += st + '\n';
        br.close();
        return result.split("\n");
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
    //#endregion
}
