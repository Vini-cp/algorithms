import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Solver {
    private MinPQ<SearchNode> pqueue;
    private Queue<Board> solution;
    private SearchNode initial;
    private boolean isSolvable;
    private int moves;

    private static class SearchNode implements Comparable<SearchNode> {
        private final Board current;
        private final SearchNode previous;
        private final int priority;

        SearchNode(Board board, SearchNode previous, int moves) {
            this.current = board;
            this.previous = previous;
            this.priority = moves + current.manhattan();
        }

        public int compareTo(SearchNode that) {
            return Integer.compare(this.priority, that.priority);
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException("The board should not be null");

        if (initial.isSolvable())
            solveBoard(initial);
        else
            isSolvable = false;
    }

    private void solveBoard(Board first) {
        isSolvable = true;
        initial = new SearchNode(first, null, 0);
        SearchNode neighbors;
        moves++;

        pqueue = new MinPQ<SearchNode>();
        solution = new Queue<Board>();

        pqueue.insert(initial);
        solution.enqueue(first);

        for (Board board : initial.current.neighbors()) {
            if (initial.previous == null || !board.equals(initial.previous.current)) {
                neighbors = new SearchNode(board, initial, moves);
                pqueue.insert(neighbors);
            }
        }

        for (Iterator<SearchNode> it = pqueue.iterator(); it.hasNext(); ) {
            SearchNode node = it.next();
            StdOut.println(node.priority);
            StdOut.println(node.current.toString());
        }
    }

    // is the initial board solvable?)
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable)
            return -1;
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable)
            return null;
        return solution;
    }

    public static void main(String[] args) {
        int[][] tiles = {
                { 8, 1, 3 },
                { 4, 0, 2 },
                { 7, 6, 5 }
        };

        Board b = new Board(tiles);
        Solver slv = new Solver(b);
    }
    // // test client
    // public static void main(String[] args) {
    //     // create initial board from file
    //     In in = new In(args[0]);
    //     int n = in.readInt();
    //     int[][] tiles = new int[n][n];
    //     for (int i = 0; i < n; i++)
    //         for (int j = 0; j < n; j++)
    //             tiles[i][j] = in.readInt();
    //     Board initial = new Board(tiles);
    //
    //     // solve the puzzle
    //     Solver solver = new Solver(initial);
    //
    //     // print solution to standard output
    //     if (!solver.isSolvable())
    //         StdOut.println("No solution possible");
    //     else {
    //         StdOut.println("Minimum number of moves = " + solver.moves());
    //         for (Board board : solver.solution())
    //             StdOut.println(board);
    //     }
    // }
}
