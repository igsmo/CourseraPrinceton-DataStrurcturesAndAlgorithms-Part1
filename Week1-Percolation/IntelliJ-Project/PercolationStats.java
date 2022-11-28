/* *****************************************************************************
 *  Name:              Igor Smorag
 *  Last modified:     28/11/2022
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Brute-force tool to calculate the rate at which the percolation will occur
 * in a 2D grid when choosing random cells to be closed.
 */
public class PercolationStats {

    /**
     * Stores all the results of calculated thresholds.
     * Threshold = (no. of open sites when starts to percolate) / (sites).
     * No. of sites is n^2.
     */
    private double[] thresholds;
    /**
     * Edge size of the grid. It defines both number of rows and columns.
     */
    private int n;
    /**
     * Number of trials to be performed.
     * During each trial a new Percolation is created and the experiment starts from the beginning.
     */
    private int trials;

    /**
     * Constructor for PercolationStats class.
     *
     * @param n      Edge size of a square grid.
     * @param trials Number of trials to be performed.
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();

        this.thresholds = new double[trials];
        this.n = n;
        this.trials = trials;
    }

    /**
     * Calculates the mean value of all recorded threshold values.
     *
     * @return Mean of threshold values.
     */
    public double mean() {
        return StdStats.mean(this.thresholds);
    }

    /**
     * Calculates the standard deviation value of all recorded threshold values.
     *
     * @return Standard deviation of threshold values.
     */
    public double stddev() {
        return StdStats.stddev(this.thresholds);
    }

    /**
     * Calculates the lower bound of 95% confidence of all recorded threshold values.
     * It means that there is a 95% probability of the threshold value to be higher than this return
     * value.
     *
     * @return Lower bound of 95% confidence of threshold values.
     */
    public double confidenceLo() {
        return mean() - 1.96 / Math.sqrt(this.trials);
    }

    /**
     * Calculates the upper bound of 95% confidence of all recorded threshold values.
     * It means that there is a 95% probability of the threshold value to be lower than this return
     * value.
     *
     * @return Upper bound of 95% confidence of threshold values.
     */
    public double confidenceHi() {
        return mean() + 1.96 / Math.sqrt(this.trials);
    }

    /**
     * Starter of the program.
     * Initiates
     *
     * @param args Expects two arguments: n (size of the grid edge) and trials (number of
     *             repetitions to be performed).
     */
    public static void main(String[] args) {
        // Read the passed value in args[]
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        // Initialize percolation stats
        PercolationStats percolationStats = new PercolationStats(n, trials);

        // Repeat the experiment a specified number of times (trials)
        for (int trial = 0; trial < trials; trial++) {

            // Each trial, start over by creating a new percolation
            Percolation perc = new Percolation(n);

            // Keep opening random cells in the grid until the system percolates, so
            // the top and bottom rows are connected
            while (!perc.percolates()) {
                perc.open(StdRandom.uniformInt(1, n + 1), StdRandom.uniformInt(1, n + 1));
            }

            // Set the current threshold value of the PercolationStats class.
            // It sets it to openSites/allSites
            percolationStats.thresholds[trial] =
                    (double) perc.numberOfOpenSites() / (double) (n * n);
        }

        // Print out the result
        System.out.println("mean = " + String.valueOf(percolationStats.mean()));
        System.out.println("stddev = " + String.valueOf(percolationStats.stddev()));
        System.out.println(
                "95% conf int = [" + String.valueOf(percolationStats.confidenceLo()) +
                        ", " + String.valueOf(percolationStats.confidenceHi()) + "]");
    }

}