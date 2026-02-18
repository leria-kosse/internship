package com.gamewerks.bgm.util;

import java.util.ArrayList;
import java.util.List;

/**
 * A position records a location in the well along with a "gravity delta" that
 * is a partial row reduction due to gravity calculations.
 * 
 * @param row the row
 * @param col the column
 * @param gravityDelta a partial row reduction due to gravity expressed in units of
 *                     <code>1 / Constants.GRAVITY_UNIT</code>.
 */
public record Position(int row, int col, int gravityDelta) {
    /**
     * Constructs a new position from the given arguments with no gravity delta.
     * @param row the row
     * @param col the column
     */
    public Position(int row, int col) {
        this(row, col, 0);
    }
   
    /**
     * @param row the row amount to add
     * @param col the col amount to add
     * @return a new position that is the result of adding <code>(row, col)</code> to
     *         this position.
     */
    public Position add(int row, int col) {
        return new Position(this.row + row, this.col + col, gravityDelta);
    }

    /**
     * @param gravity the amount of gravity delta to add to this position.
     * @return A list of positions obtained by moving this position down (i.e., subtracting)
     *         rows by the given gravity amount. Every whole number position encountered on
     *         this path is included in the list, in order. The final position of the list is
     *         the final whole number position of the path but including the left-over gravity
     *         delta from the translation.
     */
    public List<Position> getPathFromGravity(int gravity) {
        List<Position> ret = new ArrayList<>();
        int sum = this.gravityDelta + gravity;
        int whole = sum / Constants.GRAVITY_UNIT;
        int rem = sum % Constants.GRAVITY_UNIT;
        if (whole == 0) {
            ret.add(new Position(this.row, this.col, rem));
        } else {
            for (int i = 1; i <= whole; i++) {
                ret.add(new Position(this.row - i, this.col, 0));
            }
            if (rem > 0) {
                ret.add(new Position(this.row - whole, this.col, rem));
            }
        }
        return ret;
    }
}
