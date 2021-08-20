import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
    private int dimension;
    private int[][] board;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        dimension = tiles.length;
        copyBoard(tiles);
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
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null || this.getClass() != y.getClass())
            return false;
        return (this == y);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> boards = new Stack<Board>();
        int[] position = findZero();
        int i = position[0];
        int j = position[1];
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
        int i = StdRandom.uniform(dimension);
        int j = StdRandom.uniform(dimension);

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

    private int[] findZero() {
        int[] position = new int[2];
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++)
                if (board[i][j] == 0) {
                    position[0] = i;
                    position[1] = j;
                    break;
                }
        return position;
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

        Board copyBoard = new Board(tiles);
        StdOut.println("Check equals method with another board and the same board: ");
        StdOut.println("Expected: false");
        StdOut.println("Received: " + board.equals(copyBoard));

        StdOut.println("Expected: true");
        StdOut.println("Received: " + board.equals(board));
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
