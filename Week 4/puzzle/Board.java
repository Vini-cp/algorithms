import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class Board {
    private final int dimension;
    private int[][] board;
    private int zeroXpos;
    private int zeroYpos;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        dimension = tiles.length;
        copyBoard(tiles);
        findZero();
    }

    // string representation of this board
    public String toString() {
        String boardRepresentation;
        StringBuilder builder = new StringBuilder();
        builder.append(dimension + "\n");
        for (int[] row : board) {
            builder.append(" ");
            for (int tile : row)
                builder.append(tile + "  ");
            builder.append("\n");
        }
        boardRepresentation = builder.toString();
        return boardRepresentation;
    }

    // board dimension n
    public int dimension() {
        return dimension;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++)
                if (board[i][j] != (i * dimension + j + 1) && board[i][j] > 0)
                    hamming++;
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++)
                if (board[i][j] != (i * dimension + j + 1) && board[i][j] > 0)
                    manhattan += Math.abs((board[i][j] - 1) % dimension - j) +
                            Math.abs((board[i][j] - 1) / dimension - i);
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        // checking if the two objects
        // point to same object
        if (this == y)
            return true;

        // checking for two condition:
        // 1) object is pointing to null
        // 2) if the objects belong to
        // same class or not
        if (y == null
                || this.getClass() != y.getClass())
            return false;

        Board toCompare = (Board) y; // type casting object to the
        // intended class type

        // checking if the two
        // objects share all the same values
        return Arrays.deepEquals(this.board, toCompare.board);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> boards = new Stack<Board>();
        int i = zeroYpos;
        int j = zeroXpos;
        if (i > 0)
            boards.push(swap(i, j, i - 1, j));
        if (i < dimension - 1)
            boards.push(swap(i, j, i + 1, j));
        if (j > 0)
            boards.push(swap(i, j, i, j - 1));
        if (j < dimension - 1)
            boards.push(swap(i, j, i, j + 1));
        return boards;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int i = zeroYpos;
        int j = zeroXpos;

        if (StdRandom.uniform(2) == 0) { // Change left and right
            if (j == 0)
                return swap(i, j, i, j + 1);
            else if (j == dimension - 1)
                return swap(i, j, i, j - 1);
            else {
                if (StdRandom.uniform(2) == 0)
                    return swap(i, j, i, j + 1);
                else
                    return swap(i, j, i, j - 1);
            }
        }
        else { // Change up and down
            if (i == 0)
                return swap(i, j, i + 1, j);
            else if (i == dimension - 1)
                return swap(i, j, i - 1, j);
            else {
                if (StdRandom.uniform(2) == 0)
                    return swap(i, j, i + 1, j);
                else
                    return swap(i, j, i - 1, j);
            }
        }
    }

    public boolean isSolvable() {
        Board testSolvable;
        // comparing lines
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension - 1; j++) {
                if (board[i][j] == 0 || board[i][j + 1] == 0)
                    continue;
                testSolvable = swap(i, j, i, j + 1);
                if (testSolvable.isGoal())
                    return false;
            }
        }
        // comparing columns
        for (int i = 0; i < dimension - 1; i++) {
            for (int j = 0; j < dimension; j++) {
                if (board[i][j] == 0 || board[i + 1][j] == 0)
                    continue;
                testSolvable = swap(i, j, i + 1, j);
                if (testSolvable.isGoal())
                    return false;
            }
        }
        return true;
    }

    private void findZero() {
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++)
                if (board[i][j] == 0) {
                    zeroYpos = i;
                    zeroXpos = j;
                    break;
                }
    }

    private Board swap(int i, int j, int k, int m) {
        Board twin = new Board(board);
        int swap = twin.board[i][j];
        twin.board[i][j] = twin.board[k][m];
        twin.board[k][m] = swap;
        return twin;
    }

    private void copyBoard(int[][] tiles) {
        board = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++)
                board[i][j] = tiles[i][j];
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = {
                { 8, 1, 3 },
                { 4, 0, 2 },
                { 7, 6, 5 }
        };

        int[][] newTiles = {
                { 8, 1, 3 },
                { 4, 0, 2 },
                { 7, 5, 6 }
        };

        int[][] unsolvableBoard = {
                { 1, 2, 3 },
                { 4, 5, 6 },
                { 8, 7, 0 }
        };

        Board board = new Board(tiles);

        StdOut.println("Check board dimension: ");
        StdOut.println("Expected: 3");
        StdOut.println("Received: " + board.dimension);
        StdOut.println();

        StdOut.println("Check board: ");
        StdOut.println("Expected:\n3\n 8  1  3\n 4  0  2\n 7  6  5\n");
        StdOut.println("Received:\n" + board.toString());
        StdOut.println();

        StdOut.println("Check hamming sum: ");
        StdOut.println("Expected: 5");
        StdOut.println("Received: " + board.hamming());
        StdOut.println();

        StdOut.println("Check manhattan sum: ");
        StdOut.println("Expected: 10");
        StdOut.println("Received: " + board.manhattan());
        StdOut.println();

        StdOut.println("Check isGoal method: ");
        StdOut.println("Expected: false");
        StdOut.println("Received: " + board.isGoal());
        StdOut.println();

        StdOut.println("Check isSolvable method: ");
        StdOut.println("Expected: true");
        StdOut.println("Received: " + board.isSolvable());
        StdOut.println();

        StdOut.println("Check isSolvable method with unsolvable board: ");
        Board unsolvable = new Board(unsolvableBoard);
        StdOut.println("Expected: false");
        StdOut.println("Received: " + unsolvable.isSolvable());
        StdOut.println();

        Board copyBoard = new Board(tiles);
        StdOut.println("Check equals method with another board and the same board: ");
        StdOut.println("Board with the same tiles: ");
        StdOut.println("Expected: true");
        StdOut.println("Received: " + board.equals(copyBoard));

        Board newBoard = new Board(newTiles);
        StdOut.println("Board with the different tiles: ");
        StdOut.println("Expected: false");
        StdOut.println("Received: " + board.equals(newBoard));
        StdOut.println();

        StdOut.println("Check twin method: ");
        Board twin = board.twin();
        StdOut.println("Board:\n3\n 8  1  3\n 4  0  2\n 7  6  5\n");
        StdOut.println(twin.toString());

        StdOut.println("Check neighbors method: ");
        StdOut.println("Board:\n3\n 8  1  3\n 4  0  2\n 7  6  5\n");
        Iterable<Board> boards = board.neighbors();
        for (Board b : boards)
            StdOut.println(b.toString());
    }
}
