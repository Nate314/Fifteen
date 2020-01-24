# Fifteen
UMKC CS461 Project1
# 
To run this project, execute run.bat

# Assignment Description
An 8-puzzle is a 3x3 grid of tiles, numbered 1-8, with the last square in the grid being empty.  A tile can be slid into the blank spot, thus changing the configuration of the puzzle.  For example,

```
2  4  7
1  3   
5  6  8
```
Can become any of these:
```
2  4  7        2  4           2  4   7
1     3        1  3  7        1  3   8
5  6  8        5  6  8        5  6    
```
The goal is to arrange the tiles in this order:
```
1  2  3
4  5  6
7  8   
```
Given an arbitrary arrangement of tiles, can this goal state be reached? Not necessarily; half of all permutations are such that reaching the goal state is impossible.  (All states have either odd or even parity; no state with odd parity is reachable from any state with even parity, and vice-versa. The proof relies on a bit of number theory, not relevant here, and not essential for this program.)  Your task is to write a program that determines if the goal state can be reached, and if so, the series of moves (path) needed to reach it. 

Your input is a text file with a series of puzzles. Each is a 3x3 grid of integers, with 0 indicating the blank square.  For each, output is either a statement that no path exists, or a listing of the tiles (by number) that must be moved, in order, to reach a solution.

*Determining a path exists*: You will be searching through possible puzzle states, tracking already-visited states so you don't return to a state you've already examined. This will eventually search all states reachable from your starting state. If none of them is the goal--it's not reachable! (This isn't as awful as it sounds; from any state, there are only about 180,000 reachable states, and moving from one state to another is a fast operation.  For larger problems--a 15-puzzle or 24-puzzle--you'd definitely want a more efficient determination.) Of course, if you want to read up on parity and check that directly, that's OK too.

*Finding the path*: Use the A* algorithm. You will need a data structure that stores puzzle states you have already checked (so you don't visit them again), and a 'pending' queue which can keep unvisited states ordered by estimated distance from the goal state. Use the Manhattan (city-block) distance from the goal as your heuristic. That is, for each tile, how far away is this tile from its goal position?

*Submission*: Submit your source code (C, C++, C#, Java, Python) and text file showing solutions. Your program will be tested against another input file with the same format.
