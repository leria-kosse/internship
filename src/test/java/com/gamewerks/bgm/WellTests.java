package com.gamewerks.bgm;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import com.gamewerks.bgm.engine.Well;

public class WellTests {
    private static boolean x = true;
    private static boolean o = false;

    @Test
    public void emptyWellTest() {
        boolean[][] grid = {
                { o, o, o, o },
                { o, x, x, o },
                { o, x, x, o },
                { o, o, o, o }
        };
        Well well = new Well(grid);
        assertFalse(well.isCompletedRow(0));
        assertFalse(well.isCompletedRow(1));
        assertFalse(well.isCompletedRow(2));
        assertFalse(well.isCompletedRow(3));
    }

    @Test
    public void oneCompletedRowTest() {
        boolean[][] grid = {
                { x, x, x, x },
                { o, o, o, o }
        };
        Well well = new Well(grid);
        assertTrue(well.isCompletedRow(0));
        assertFalse(well.isCompletedRow(1));
    }

     @Test
    public void getCompletedRowsTest() {
        boolean[][] grid = {
                { o, o, o, o },
                { x, x, x, x },
                { o, o, x, o },
                { x, x, x, x }
        };
        Well well = new Well(grid);
        
        List<Integer> comp = well.getCompletedRows();
        assertTrue(comp.size() == 2);
        assertFalse(well.isCompletedRow(0));
        assertFalse(well.isCompletedRow(2));
    }

    @Test
    public void deleteRowTest() {
        boolean[][] grid = {
                { x, x, x, x },
                { o, x, o, o }
        };
        Well well = new Well(grid);

        well.deleteRow(0);

        assertFalse(well.isCompletedRow(0));
        assertFalse(well.isCompletedRow(1));
    }

    @Test
    public void deleteMultipleRowsTest() {
        boolean[][] grid = {
                { o, o, o, o },
                { o, x, o, o },
                { x, x, o, x },
                { x, x, x, x },
                { x, x, x, x }
        };
        Well well = new Well(grid);
        // before deletion: rows 3, 4 are complete, rows 0, 1, and 2 are not
        assertFalse(well.isCompletedRow(0));
        assertFalse(well.isCompletedRow(1));
        assertFalse(well.isCompletedRow(2));
        assertTrue(well.isCompletedRow(3));
        assertTrue(well.isCompletedRow(4));

        well.deleteRows(well.getCompletedRows());

        // after deletion: no more completed rows, bcs we deleted all the complete rows
        assertFalse(well.isCompletedRow(0));
        assertFalse(well.isCompletedRow(1));
        assertFalse(well.isCompletedRow(2));
        assertFalse(well.isCompletedRow(3));
        assertFalse(well.isCompletedRow(4));
    }
}
