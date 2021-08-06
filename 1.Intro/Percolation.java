import edu.princeton.cs.algs4.StdRandom;

public class Percolation {
    private int[][] grid;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        this.grid = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                this.grid[i][j] = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!this.isOpen(row, col))
            this.grid[row - 1][col - 1] = 1;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return this.grid[row - 1][col - 1] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row == 1)
            return true;
        if (!this.isOpen(row, col))
            return false;
        if (this.isOpen(row - 1, col))
            return this.isFull(row - 1, col);
        else if (this.isOpen(row, (col % 5) + 1) && (col % 5) + 1 != col)
            return this.isFull(row, (col % 5) + 1);
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int n = this.grid.length;
        int openSites = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (this.grid[i][j] == 1)
                    openSites++;
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        int n = this.grid.length;
        for (int i = 0; i < n; i++)
            if (this.isFull(n, i))
                return true;
        return false;
    }

    public void printTable() {
        int n = this.grid.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                System.out.print(this.grid[i][j] + " ");
            System.out.println();
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        int size = 5;
        Percolation perc = new Percolation(size);
        int n = 0;
        while (!perc.percolates()) {
            perc.open(StdRandom.uniform(size) + 1, StdRandom.uniform(size) + 1);
            n++;
        }
        perc.printTable();
    }
}
