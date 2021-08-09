import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private final int size;
    private final WeightedQuickUnionUF relations;
    private final WeightedQuickUnionUF relationsBackWash;
    private final int virtualTop;
    private final int virtualBottom;
    private int openSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("Grid size must be an positive integer");
        this.grid = new boolean[n][n];
        this.relations = new WeightedQuickUnionUF(n * n + 2);
        this.relationsBackWash = new WeightedQuickUnionUF(n * n + 1);
        this.size = n;
        this.virtualTop = n * n;
        this.virtualBottom = virtualTop + 1;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                this.grid[i][j] = false;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        this.checkConditions(row, col);
        if (!this.isOpen(row, col)) {
            this.grid[row - 1][col - 1] = true;
            this.setUnion(row, col);
            if (row == 1 || row == this.size)
                this.setVirtualSite(row, col);
            openSites++;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        this.checkConditions(row, col);
        return this.grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        this.checkConditions(row, col);
        int p = (row - 1) * this.size + (col - 1);
        return this.isOpen(row, col) && this.relationsBackWash.find(p) == this.relationsBackWash
                .find(this.virtualTop);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return this.relations.find(this.virtualTop) == this.relations.find(this.virtualBottom);
    }

    private void setVirtualSite(int row, int col) {
        if (row == 1) {
            relations.union(this.virtualTop, col - 1);
            relationsBackWash.union(this.virtualTop, col - 1);
        }
        if (row == this.size)
            relations.union(this.virtualBottom, col - 1 + this.virtualTop - this.size);
    }

    private void setUnion(int row, int col) {
        int p = (row - 1) * this.size + (col - 1);
        if (row - 1 > 0 && this.isOpen(row - 1, col)) {
            relations.union(p, p - this.size);
            relationsBackWash.union(p, p - this.size);
        }
        if (row + 1 <= this.size && this.isOpen(row + 1, col)) {
            relations.union(p, p + this.size);
            relationsBackWash.union(p, p + this.size);
        }
        if (col - 1 > 0 && this.isOpen(row, col - 1)) {
            relations.union(p, p - 1);
            relationsBackWash.union(p, p - 1);
        }
        if (col + 1 <= this.size && this.isOpen(row, col + 1)) {
            relations.union(p, p + 1);
            relationsBackWash.union(p, p + 1);
        }
    }

    private void checkConditions(int row, int col) {
        if (row < 1 || row > this.size || col < 1 || col > this.size)
            throw new IllegalArgumentException(
                    "Row and column indices must be integers between 1 and n");
    }

    // test client (optional)
    public static void main(String[] args) {
        int size = Integer.parseInt(args[0]);
        Percolation perc = new Percolation(size);
        StdOut.println(perc.isFull(1, 1));
        StdOut.println(perc.isOpen(1, 1));
        StdOut.println(perc.percolates());
        perc.open(1, 1);
        StdOut.println(perc.isFull(1, 1));
        StdOut.println(perc.isOpen(1, 1));
        StdOut.println(perc.percolates());
    }
}
