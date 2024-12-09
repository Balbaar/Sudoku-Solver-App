package main;

import java.util.Arrays;

public class SudokuSolverImpl implements SudokuSolver {
    private int[][] grid = new int[9][9];

    @Override
    public boolean solve() {
        return solve(0, 0);
    }

    private boolean solve(int row, int col) {
        if (row == 9) return true; // Reached end of grid
        if (col == 9) return solve(row + 1, 0); // Move to next row
        if (grid[row][col] != 0) return solve(row, col + 1); // Skip filled cells

        for (int num = 1; num <= 9; num++) {
            grid[row][col] = num;
            if (isValid(row, col) && solve(row, col + 1)) return true;
            grid[row][col] = 0; // Backtrack
        }
        return false;
    }

    @Override
    public void set(int row, int col, int digit) {
        if (row < 0 || row >= 9 || col < 0 || col >= 9)
            throw new IndexOutOfBoundsException("Invalid row or column index");
        if (digit < 0 || digit > 9)
            throw new IllegalArgumentException("Digit must be between 0 and 9");
        grid[row][col] = digit;
    }

    @Override
    public int get(int row, int col) {
        if (row < 0 || row >= 9 || col < 0 || col >= 9)
            throw new IndexOutOfBoundsException("Invalid row or column index");
        return grid[row][col];
    }

    @Override
    public void clear(int row, int col) {
        set(row, col, 0);
    }

    @Override
    public void clearAll() {
        for (int[] row : grid) Arrays.fill(row, 0);
    }

    @Override
    public boolean isValid(int row, int col) {
        int num = grid[row][col];
        if (num == 0) return true; // Empty cell
        grid[row][col] = 0; // Temporarily clear for validation
        boolean isValid = checkRow(row, num) && checkCol(col, num) && checkBox(row, col, num);
        grid[row][col] = num; // Restore value
        return isValid;
    }

    private boolean checkRow(int row, int num) {
        for (int col = 0; col < 9; col++) if (grid[row][col] == num) return false;
        return true;
    }

    private boolean checkCol(int col, int num) {
        for (int row = 0; row < 9; row++) if (grid[row][col] == num) return false;
        return true;
    }

    private boolean checkBox(int row, int col, int num) {
        int boxRow = (row / 3) * 3, boxCol = (col / 3) * 3;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (grid[boxRow + i][boxCol + j] == num) return false;
        return true;
    }

    @Override
    public boolean isAllValid() {
        for (int row = 0; row < 9; row++)
            for (int col = 0; col < 9; col++)
                if (!isValid(row, col)) return false;
        return true;
    }

    @Override
    public void setGrid(int[][] m) {
        if (m.length != 9 || Arrays.stream(m).anyMatch(row -> row.length != 9))
            throw new IllegalArgumentException("Grid must be 9x9");
        for (int[] row : m)
            for (int cell : row)
                if (cell < 0 || cell > 9)
                    throw new IllegalArgumentException("Grid values must be between 0 and 9");
        this.grid = Arrays.stream(m).map(int[]::clone).toArray(int[][]::new);
    }

    @Override
    public int[][] getGrid() {
        return Arrays.stream(grid).map(int[]::clone).toArray(int[][]::new);
    }
}
