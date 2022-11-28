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
    private boolean[] grid;
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
     * Initializes all the variables and closes all the cells in the grid.
     *
     * @param n Edge size of a square grid.
     */
    public Percolation(int n) {
        this.n = n;

        if (0 >= this.n)
            throw new IllegalArgumentException();

        /**
         * Initialization of grid and unionUF size is set to n^2 + 2.
         * It is because, we define an imaginary part of the grid, which is
         * on the top and the bottom. It will help to perform the
         * perlocates() function quicker, than if we connected the whole
         * top and bottom rows and checked for union with each of the cells.
         *
         * The concept of the imaginary top and bottom is that we simply move
         * the whole grid by one place to the right (we move the 1D array to index + 1).
         * It makes our case even easier, as the grid provided by the test
         * files is 1-indexed.
         */
        this.grid = new boolean[this.n * this.n + 2];
        this.unionUF = new WeightedQuickUnionUF(this.n * this.n + 2);

        this.openSites = 0;
    }

    /**
     * Calculates index of element in union from row and column.
     *
     * @param row 1-indexed row number.
     * @param col 1-indexed column number.
     * @return Index of element in union.
     */
    private int getIdFromCoords(int row, int col) {
        checkOutOfBounds(row, col);

        return (row - 1) * this.n + (col - 1);
    }

    /**
     * Checks if the coordinates are within bounds of the grid.
     */
    private void checkOutOfBounds(int row, int col) {
        if (row > this.n || row < 1 || col > this.n || col < 1)
            throw new IllegalArgumentException();
    }

    /**
     * Opens the site at coordinates row,col if it is not already open and
     * connects all the open, surrounding cells.
     *
     * @param row 1-indexed row number.
     * @param col 1-indexed column number.
     */
    public void open(int row, int col) {
        checkOutOfBounds(row, col);

        int id = getIdFromCoords(row, col);

        // Don't do anything if already open.
        if (this.grid[id])
            return;

        /**
         * This if statement connects the grid to the imaginary rows.
         */
        if (row == 1) {
            unionUF.union(0, id);
        }
        else if (row == this.n) {
            unionUF.union(this.n * this.n + 1, id);
        }

        // Open the cell
        this.grid[id] = true;

        // If one of the surrounding points is open, connects it.
        if (row != 1 && isOpen(row - 1, col))
            this.unionUF.union(getIdFromCoords(row - 1, col), id);
        if (row != this.n && isOpen(row + 1, col))
            this.unionUF.union(getIdFromCoords(row + 1, col), id);
        if (col != 1 && isOpen(row, col - 1))
            this.unionUF.union(getIdFromCoords(row, col - 1), id);
        if (col != this.n && isOpen(row, col + 1))
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
        checkOutOfBounds(row, col);

        int id = getIdFromCoords(row, col);

        return this.grid[id];
    }

    /**
     * Checks if a cell in the grid is closed.
     *
     * @param row 1-indexed row number.
     * @param col 1-indexed column number.
     * @return true if cell is closed, false if open OR outside the grid bounds.
     */
    public boolean isFull(int row, int col) {
        // No need to check for checkOutOfBounds, as isOpen is doing it.
        if (!isOpen(row, col))
            return false;

        // Returns true if it is connected to virtual top or bottom.
        int id = getIdFromCoords(row, col);

        return (unionUF.find(id) == unionUF.find(0));
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
        return unionUF.find(0) == unionUF.find(this.n * this.n + 1);
    }

}
