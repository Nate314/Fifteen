package com.nathangawith.umkc;
//#region imports
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
//#endregion
public class AI {
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
            System.out.println();
            System.out.println("--------------------------------");
            // check if game is solvable or unsolvable and print
            AI_Solver solver = new AI_Solver(new GameBoard(gameBoard));
            boolean solvable = solver.isSolvable();
            System.out.println(String.format("%d) is %ssolvable", ++counter, solvable ? "" : "NOT "));
            gameBoard.print();
            if (solvable) {
                GameHistory history = solver.history;
                this.print(history);
                // display solution with GUI
                if (enableGUI) this.showSolutionWithGUI(gameBoard, history);
            }
        }
        System.out.println();
        System.out.println("--------------------------------");
    }
    //#endregion
    //#region private methods
    /**
     * prints historical info to the console
     * @param history history info about the solve
     */
    private void print(GameHistory history) {
		BiConsumer<String, List<String>> print = (label, arr) -> {
            System.out.println(label + ":");
            System.out.println(String.join(", ", arr));
            System.out.println();
        };
        if (Constants.PRINT_MOVES)
            print.accept("Moves", history.keyList.stream().map(x -> "" + x).collect(Collectors.toList()));
        if (Constants.PRINT_TILES)
            print.accept("Tiles", history.swappedTiles.stream().map(x -> "" + x).collect(Collectors.toList()));
        if (Constants.PRINT_DISTANCES)
            print.accept("Distances", history.distances.stream().map(x -> "" + x).collect(Collectors.toList()));
        System.out.println("Total of " + history.keyList.size() + " moves");
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
