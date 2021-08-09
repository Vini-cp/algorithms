import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double[] stats;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (!(n >= 1 && trials >= 1)) {
            throw new IllegalArgumentException("Size n or number of trials not positive.");
        }
        this.stats = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                perc.open(StdRandom.uniform(n) + 1, StdRandom.uniform(n) + 1);
            }
            this.stats[i] = (double) perc.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.stats);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.stats);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double mean = this.mean();
        double stddev = this.stddev();

        return mean - (CONFIDENCE_95 * stddev) / Math.sqrt(this.stats.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mean = this.mean();
        double stddev = this.stddev();

        return mean + (CONFIDENCE_95 * stddev) / Math.sqrt(this.stats.length);
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats simulation = new PercolationStats(Integer.parseInt(args[0]),
                                                           Integer.parseInt(args[1]));
        StdOut.println("mean                    = " + simulation.mean());
        StdOut.println("stddev                  = " + simulation.stddev());
        StdOut.println("95% confidence interval = [" + simulation.confidenceLo() + ", " + simulation
                .confidenceHi() + "]");
    }
}
