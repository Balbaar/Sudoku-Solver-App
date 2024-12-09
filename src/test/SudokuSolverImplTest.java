package test;

import main.SudokuSolverImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SudokuSolverImplTest {
    private SudokuSolverImpl solver;

    @BeforeEach
    public void setUp() {
        solver = new SudokuSolverImpl();
    }

    @Test
    public void testSetAndGet() {
        solver.set(0, 0, 5);
        assertEquals(5, solver.get(0, 0));
    }

    @Test
    public void testClear() {
        solver.set(0, 0, 5);
        solver.clear(0, 0);
        assertEquals(0, solver.get(0, 0));
    }

    @Test
    public void testClearAll() {
        solver.set(0, 0, 5);
        solver.set(1, 1, 3);
        solver.clearAll();
        assertEquals(0, solver.get(0, 0));
        assertEquals(0, solver.get(1, 1));
    }

    @Test
    public void testIsValid() {
        solver.set(0, 0, 5);
        assertTrue(solver.isValid(0, 0));
        solver.set(0, 1, 5);
        assertFalse(solver.isValid(0, 1));
    }

    @Test
    public void testIsAllValid() {
        int[][] validGrid = {
                {5, 3, 0, 0, 7, 0, 0, 0, 0},
                {6, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };
        solver.setGrid(validGrid);
        assertTrue(solver.isAllValid());
    }

    @Test
    public void testSolve() {
        int[][] unsolvedGrid = {
                {5, 3, 0, 0, 7, 0, 0, 0, 0},
                {6, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };
        solver.setGrid(unsolvedGrid);
        assertTrue(solver.solve());
        assertTrue(solver.isAllValid());
    }

    @Test
    public void testSetGrid() {
        int[][] grid = {
                {5, 3, 0, 0, 7, 0, 0, 0, 0},
                {6, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };
        solver.setGrid(grid);
        assertArrayEquals(grid, solver.getGrid());
    }
}