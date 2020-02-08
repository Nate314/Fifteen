package com.nathangawith.umkc;
//#region imports
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
//#endregion
public class AI {
    //#region fields
    private String output = "";
    private void appendToOutput(String str, boolean println) {
        this.output += str + (println ? "\n" : "");
        if (println) System.out.println(str);
        else System.out.print(str);
    }
    private void appendToOutput(String str) {
        this.appendToOutput(str, true);
    }
    //#endregion
    //#region constructor
    /**
     * reads file and prints solutions
     */
    public AI(boolean enableGUI) {
        // read in games and calculate invalid parity set
        AI_IO ai_io = new AI_IO();
        // for each game that is defined in the input file
        int counter = 0;
        for (GameBoard gameBoard : ai_io.getGameBoards()) {
            this.appendToOutput("--------------------------------");
            // check if game is solvable or unsolvable and print
            AI_Solver solver = new AI_Solver(new GameBoard(gameBoard));
            boolean solvable = solver.isSolvable();
            this.appendToOutput(String.format("(%d) Is %ssolvable", ++counter, solvable ? "" : "NOT "));
            this.appendToOutput(gameBoard.beautifulStringify());
            if (solvable) {
                GameHistory history = solver.history;
                this.appendToOutput(this.printableString(history));
                // display solution with GUI
                if (enableGUI) this.showSolutionWithGUI(gameBoard, history);
            }
        }
        this.appendToOutput("\n--------------------------------");
        String input_file = String.format("%s.txt", Constants.FILE_NAME).split(".txt")[0];
        ai_io.write(String.format("%s_output.txt", input_file), this.output);
    }
    //#endregion
    //#region private methods
    /**
     * prints historical info to the console
     * @param history history info about the solve
     * @return printable string representing the history
     */
    private String printableString(GameHistory history) {
        String result = "";
        BiFunction<String, List<String>, String> print = (label, arr) -> {
            String res = "";
            res += label + ":\n";
            res += String.join(", ", arr) + "\n";
            return res;
        };
        int depth = 0;
        if (Constants.PRINT_MOVES)
            result += print.apply("Moves", history.keyList.stream().map(x -> "" + x).collect(Collectors.toList()));
        if (Constants.PRINT_TILES)
            result += print.apply("Tiles to move", history.swappedTiles.stream().map(x -> "" + x).collect(Collectors.toList()));
        if (Constants.PRINT_DISTANCES) {
            result += print.apply("Distances", history.distances.stream().map(x -> "" + x).collect(Collectors.toList()));
            result += "- ";
            for (int dist : history.distances) result += String.format("%d, ", depth++);
            result += "\n";
            depth = 0;
            for (int dist : history.distances) result += String.format("%d, ", dist - depth++);
            result += "\n";
        }
        result += "\nTotal of " + history.keyList.size() + " moves";
        return result;
    }
    /**
     * Displays the solution to the user
     * @param gameBoard GameBoard to start with
     * @param history history of the solve
     */
    private void showSolutionWithGUI(GameBoard gameBoard, GameHistory history) {
        MyFrame frame = new MyFrame(gameBoard, false);
        try {
            Thread.sleep(1000);
            long pause = Math.min((long) (5000 / history.keyList.size()), 500);
            for (MyKey move : history.keyList) {
                gameBoard.key(move);
                Thread.sleep(pause);
            }
            Thread.sleep(1000);
        } catch (Exception e) {}
        frame.dispose();
    }
    //#endregion
}
