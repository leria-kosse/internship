package com.gamewerks.bgm.engine;

import java.util.LinkedList;
import java.util.List;

import com.gamewerks.bgm.util.Constants;
import com.gamewerks.bgm.util.Position;

/**
 * The well is a two-dimensional grid, filled in by pieces that have been
 * locked into place. Row/col (0, 0) corresponds to the bottom-left corner
 * of the well.
 */
public class Well {
    private boolean[][] grid;

    /**
     * Constructs a new well with the given size
     * @param width the width of the well (the number of columns)
     * @param height the height of the well (tne number of rows)
     */
    public Well(int width, int height) {
        grid = new boolean[height][width];
    }

    /**
     * Constructs a new well whose size is ({@link Constants#BOARD_WIDTH},
     * {@link Constants#BOARD_HEIGHT}).
     */
    public Well() {
        grid = new boolean[Constants.BOARD_HEIGHT][Constants.BOARD_WIDTH];
    }

    /**
     * Constructs a new well from the given grid.
     * @param grid the initial state of this well
     */
    public Well(boolean[][] grid) {
        this.grid = grid;
    }

    /** @return the well's width (columns of the board) */
    public int getWidth() {
        return grid[0].length;
    }

    /** @return the well's height (rows of the board) */
    public int getHeight() {
        return grid.length;
    }
   
    /**
     * @param row the row
     * @param col the column
     * @return true iff (row, col) is a valid position in this well
     */
    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col <= grid[0].length;
    }
   
    /**
     * @param p the piece
     * @return true iff the given piece collides with the well
     */
    public boolean collides(Piece p) {
        return collides(p.getLayout(), p.getPosition());
    }
   
    /**
     * @param layout the piece layout
     * @param pos position
     * @return true iff the given piece, expressed in terms of its layout and
     *         position collides with the well
     */
    public boolean collides(boolean[][] layout, Position pos) {
        for (int row = 0; row < layout.length; row++) {
            int wellRow = pos.row() - (layout.length - 1 - row);
            for (int col = 0; col < layout[row].length; col++) {
                int wellCol = col + pos.col();
                if (layout[row][col]) {
                    if (!isValidPosition(wellRow, wellCol)) {
                        return true;
                    } else if (grid[wellRow][wellCol]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
   
    /**
     * Adds the given piece to the well.
     * @param p the piece to add
     */
    public void addToWell(Piece p) {
        boolean[][] layout = p.getLayout();
        Position pos = p.getPosition();
        for (int row = 0; row < layout.length; row++) {
            int wellRow = pos.row() - (layout.length - 1 - row);
            for (int col = 0; col < layout[row].length; col++) {
                int wellCol = pos.col() + col;
                if (isValidPosition(wellRow, wellCol) && layout[row][col]) {
                    grid[wellRow][wellCol] = true;
                }
            }
        }
    }

    /**
     * Deletes the given row from the well, shifting all rows above it
     * downwards into the newly freed space.
     * @param n the index of the row to delete
     */
    public void deleteRow(int n) {
        for (int row = n; row < getHeight() - 1; row++) {
            for (int col = 0; col < getWidth(); col++) {
                grid[row][col] = grid[row + 1][col];
            }
        }
        for (int col = 0; col < getWidth(); col++) {
            grid[getHeight() - 1][col] = false;
        }
    }
   
    /**
     * Deletes the given list of rows from the well.
     * @param rows the rows to delete
     */
    public void deleteRows(List<Integer> rows) {
        for (int i = 0; i < rows.size(); i++) {
            int row = (Integer) rows.get(i);
            deleteRow(row);
        }
    }

    /**
     * @param row the row to check
     * @return true iff the given row is complete, i.e., completely filled in
     */
    public boolean isCompletedRow(int row) {
        boolean isCompleted = true;
        for (int col = 0; col < getWidth(); col++) {
            isCompleted = isCompleted && grid[row][col];
        }
        return isCompleted;
    }
   
    /** @return all completed rows for this well as a list */
    public List<Integer> getCompletedRows() {
        List<Integer> completedRows = new LinkedList<>();
        for (int row = getHeight() - 1; row >= 0; row--) {
            if (isCompletedRow(row)) {
                completedRows.add(row);
            }
        }
        return completedRows;
    }

    /** @return the underlying grid (2d-array) of this well */
    public boolean[][] getGrid() {
        return grid;
    }
}
