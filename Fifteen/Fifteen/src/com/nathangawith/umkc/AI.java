package com.nathangawith.umkc;

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
        }
    }

}
