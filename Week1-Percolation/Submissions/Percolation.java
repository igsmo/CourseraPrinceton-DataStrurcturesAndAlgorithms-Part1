/* *****************************************************************************
 *  Name:              Igor Smorag
 *  Last modified:     28/11/2022
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Class containing all the functions necessary to implement a percolation algorithm.
 * Percolation means the event in which the top and the bottom rows are connected in a 2D grid.
 */
public class Percolation {
    /**
     * 2D grid containing information if a cell is open or closed.
     */
    private int[][] grid;
    /**
     * Count of open sites in the grid.
     */
    private int openSites;
    /**
     * Edge size of the grid. It defines both number of rows and columns.
     */
    private int n;
    /**
     * Union keeping track of all connected points.
     */
    private WeightedQuickUnionUF unionUF;

    /**
     * Constructor for Percolation class.
     * Initializes all the variables and closes all the cells in the grid.'
     * Opens top and bottom rows.
     *
     * @param n Edge size of a square grid.
     */
    public Percolation(int n) {
        this.n = n;

        if (0 >= this.n)
            throw new IllegalArgumentException();

        this.grid = new int[this.n][this.n];
        this.openSites = 0;
        this.unionUF = new WeightedQuickUnionUF(this.n * this.n);

        blockAllSites();

        openBottomAndTopRow();
    }

    /**
     * Opens all cells in the top and bottom row.
     * It is done to detect percolation from ANY cell in the top or bottom row.
     * Why it needs to be done?
     * The implementation of function percolates() checks if the top-left
     * corner is connected to the bottom-right corner in a grid.
     * Now, the cells on the top and the bottom are connected, so
     * any of the cells in those two rows has the same connections.
     */
    private void openBottomAndTopRow() {
        // Iterate through the number of cells in a row
        for (int i = 0; i <= this.n; i++) {
            // Connect element 0 (top-left) to i-th element (so the top row)
            this.unionUF.union(0, i);
            // Connect element N*N-1 (last element, so bottom-left)
            // to N*N-1-i element (so the bottom row)
            this.unionUF.union(this.n * this.n - 1, this.n * this.n - 1 - i);
        }
    }

    /**
     * Initializes all the sites to be blocked.
     */
    private void blockAllSites() {
        // Iterate through every row
        for (int i = 0; i < this.n; i++) {
            // Iterate through every column
            for (int j = 0; j < this.n; j++) {
                // Set grid value to 1 (blocked)
                this.grid[i][j] = 1;
            }
        }
    }

    /**
     * Calculates index of element in union from row and column.
     *
     * @param row 1-indexed row number.
     * @param col 1-indexed column number.
     * @return Index of element in union.
     */
    private int getIdFromCoords(int row, int col) {

        return (row - 1) * this.n + (col - 1);
    }

    /**
     * Opens the site at coordinates row,col if it is not already open and
     * connects all the open, surrounding cells.
     *
     * @param row 1-indexed row number.
     * @param col 1-indexed column number.
     */
    public void open(int row, int col) {
        if (row - 1 >= this.n || col - 1 >= this.n)
            throw new IllegalArgumentException();

        // Don't do anything if already open.
        if (this.grid[row - 1][col - 1] == 0)
            return;

        // Open the cell
        this.grid[row - 1][col - 1] = 0;

        int id = getIdFromCoords(row, col);

        // If one of the surrounding points is open, connects it.
        if (isOpen(row - 1, col))
            this.unionUF.union(getIdFromCoords(row - 1, col), id);
        if (isOpen(row + 1, col))
            this.unionUF.union(getIdFromCoords(row + 1, col), id);
        if (isOpen(row, col - 1))
            this.unionUF.union(getIdFromCoords(row, col - 1), id);
        if (isOpen(row, col + 1))
            this.unionUF.union(getIdFromCoords(row, col + 1), id);

        // Increment open site count
        this.openSites++;
    }

    /**
     * Checks if a cell in the grid is open.
     *
     * @param row 1-indexed row number.
     * @param col 1-indexed column number.
     * @return true if cell is open, false if closed OR outside the grid bounds.
     */
    public boolean isOpen(int row, int col) {
        // Check if outside bounds and return false if so
        if (row - 1 >= this.n || col - 1 >= this.n || row - 1 < 0 || col - 1 < 0)
            return false;

        return this.grid[row - 1][col - 1] == 0;
    }

    /**
     * Checks if a cell in the grid is closed.
     *
     * @param row 1-indexed row number.
     * @param col 1-indexed column number.
     * @return true if cell is closed, false if open OR outside the grid bounds.
     */
    public boolean isFull(int row, int col) {
        // Check if outside bounds and return false if so
        if (row - 1 >= this.n || col - 1 >= this.n || row - 1 < 0 || col - 1 < 0)
            return false;

        return this.grid[row - 1][col - 1] == 1;
    }

    /**
     * Getter for open sites count with a needed function name.
     *
     * @return Count of open sites in a grid.
     */
    public int numberOfOpenSites() {
        return openSites;
    }

    /**
     * Checks if the system percolates. It means, it check if the top and
     * bottom rows are connected.
     *
     * @return true if percolates, false otherwise.
     */
    public boolean percolates() {
        return unionUF.connected(0, this.n * this.n - 1);
    }

}
