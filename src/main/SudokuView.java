package main;

import javax.swing.*;
import java.awt.*;

public class SudokuView extends JFrame {
    private final JTextField[][] cells = new JTextField[9][9];
    private final SudokuSolver solver = new SudokuSolverImpl();

    public SudokuView() {
        setTitle("Sudoku Solver");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 500));
        setResizable(false);
        initializeGrid();
        initializeControls();
        pack();
        setVisible(true);
    }

    private void initializeGrid() {
        JPanel mainGridPanel = new JPanel(new GridLayout(3, 3));
        mainGridPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Padding around the main grid

        for (int subGridRow = 0; subGridRow < 3; subGridRow++) {
            for (int subGridCol = 0; subGridCol < 3; subGridCol++) {
                JPanel subPanel = new JPanel(new GridLayout(3, 3));
                subPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Dark border for each sub-grid
                for (int cellRow = 0; cellRow < 3; cellRow++) {
                    for (int cellCol = 0; cellCol < 3; cellCol++) {
                        int row = subGridRow * 3 + cellRow; // Map to the main grid row
                        int col = subGridCol * 3 + cellCol; // Map to the main grid col
                        JTextField cell = new JTextField();
                        cell.setHorizontalAlignment(JTextField.CENTER);
                        cells[row][col] = cell;
                        subPanel.add(cell);
                    }
                }
                mainGridPanel.add(subPanel);
            }
        }
        add(mainGridPanel, BorderLayout.CENTER);
    }

    private void initializeControls() {
        JPanel controlPanel = new JPanel();
        JButton solveButton = new JButton("Solve");
        JButton clearButton = new JButton("Clear");

        solveButton.addActionListener(e -> solveSudoku());
        clearButton.addActionListener(e -> clearGrid());

        controlPanel.add(solveButton);
        controlPanel.add(clearButton);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private void solveSudoku() {
        try {
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    String text = cells[row][col].getText().trim();
                    solver.set(row, col, text.isEmpty() ? 0 : Integer.parseInt(text));
                }
            }
            if (solver.solve()) {
                int[][] solvedGrid = solver.getGrid();
                for (int row = 0; row < 9; row++)
                    for (int col = 0; col < 9; col++)
                        cells[row][col].setText(String.valueOf(solvedGrid[row][col]));
            } else {
                JOptionPane.showMessageDialog(this, "No solution found!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
        }
    }

    private void clearGrid() {
        solver.clearAll();
        for (JTextField[] row : cells)
            for (JTextField cell : row)
                cell.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SudokuView::new);
    }
}
