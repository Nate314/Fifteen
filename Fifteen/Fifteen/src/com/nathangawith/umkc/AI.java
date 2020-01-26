package com.nathangawith.umkc;

import java.util.ArrayList;

public class AI {

    public AI() {
        AI_IO ai_io = new AI_IO();
        AI_Parity parityChecker = new AI_Parity(ai_io);
        String out = parityChecker.getOutput();
        if (out != null) {
            ai_io.outputParities(out);
        }
        for (GameState game : ai_io.getGames()) {
            String key = game.getGameBoard().stringify();
            boolean solvable = parityChecker.isOfSovableParity(key);
            System.out.println("--------------------------------");
            System.out.println(String.format("%s is %ssolvable", key, solvable ? "" : "un"));
            if (solvable) {
                long start = System.currentTimeMillis();
                ArrayList<MyKey> moves = new AI_Solver(new GameState(game)).solution;
                long end = System.currentTimeMillis();
                System.out.println("Solving took: " + (end - start) + "ms");
                moves.forEach(move -> System.out.print(move + ", "));
                System.out.println("Total of " + moves.size() + " moves");
                MyFrame frame = new MyFrame(game, false);
                try {
                    Thread.sleep(1000);
                    for (MyKey move : moves) {
                        game.key(move);
                        Thread.sleep((long) (5000 / moves.size()));
                    }
                    Thread.sleep(1000);
                } catch (Exception e) {}
                frame.dispose();
            }
        }
    }
}
