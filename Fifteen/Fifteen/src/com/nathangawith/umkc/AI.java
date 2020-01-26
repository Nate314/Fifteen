package com.nathangawith.umkc;
//#region imports
import java.util.ArrayList;
//#endregion
public class AI {
    //#region constructor
    /**
     * reads file and prints solutions
     */
    public AI(boolean enableGUI) {
        // read in games and calculate invalid parity set
        AI_IO ai_io = new AI_IO();
        AI_Parity parityChecker = new AI_Parity(ai_io);
        // for each game that is defined in the input file
        for (GameBoard gameBoard : ai_io.getGameBoards()) {
            // check if game is solvable or unsolvable and print
            String gameKey = gameBoard.stringify();
            boolean solvable = parityChecker.isOfSovableParity(gameKey);
            System.out.println();
            System.out.println("--------------------------------");
            System.out.println(String.format("%s is %ssolvable", gameKey, solvable ? "" : "un"));
            if (solvable) {
                // if the game is solvable generate solution
                AI_Solver solver = new AI_Solver(new GameBoard(gameBoard));
                ArrayList<MyKey> moves = solver.solution;
                moves.forEach(move -> System.out.print(move + ", "));
                System.out.println("Total of " + moves.size() + " moves");
                // display solution with GUI
                if (enableGUI) {
                    MyFrame frame = new MyFrame(gameBoard, false);
                    try {
                        Thread.sleep(1000);
                        for (MyKey move : moves) {
                            gameBoard.key(move);
                            Thread.sleep((long) (5000 / moves.size()));
                        }
                        Thread.sleep(1000);
                    } catch (Exception e) {}
                    frame.dispose();
                }
            }
        }
    }
    //#endregion
}
