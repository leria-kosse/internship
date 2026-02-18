package com.gamewerks.bgm;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
import com.gamewerks.bgm.engine.Well;

public class WellTests {
    private static boolean x = true;
    private static boolean o = false;

    @Test
    public void emptyWellTest() {
        boolean[][] grid = {
          {o, o, o, o},
          {o, x, x, o},
          {o, x, x, o},
          {o, o, o, o}
        };
        Well well = new Well(grid);
        assertFalse(well.isCompletedRow(0));
        assertFalse(well.isCompletedRow(1));
        assertFalse(well.isCompletedRow(2));
        assertFalse(well.isCompletedRow(3));
    }
}
