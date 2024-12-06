package sudoku;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * The game board consisting of 9x9 Sudoku cells.
 */
public class GameBoardPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    public static final int CELL_SIZE = 60;   // Cell width/height in pixels
    public static final int BOARD_WIDTH  = CELL_SIZE * SudokuConstants.GRID_SIZE;
    public static final int BOARD_HEIGHT = CELL_SIZE * SudokuConstants.GRID_SIZE;

    private Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    private Puzzle puzzle = new Puzzle();

    /** Constructor */
    public GameBoardPanel() {
        super.setLayout(new GridLayout(SudokuConstants.GRID_SIZE, SudokuConstants.GRID_SIZE));

        // Create the 9x9 cells and add them to the JPanel
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col] = new Cell(row, col);
                super.add(cells[row][col]);   // Add the cell to the panel
            }
        }

        // Allocate a common listener for all the editable cells
        CellInputListener listener = new CellInputListener();

        // Add the listener to all editable cells
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].isEditable()) {
                    cells[row][col].addActionListener(listener);
                }
            }
        }

        super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
    }

    /**
     * Start a new game and reset the board based on the puzzle.
     */
    public void newGame(int numClues) {
        puzzle.newPuzzle(numClues);  // Adjust puzzle generation based on selected difficulty
    
        // Initialize all cells based on the puzzle data
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
            }
        }
    }    

    /**
     * Reset the game to its initial state.
     */
    public void resetGame() {
        puzzle.newPuzzle(25);  // Re-generate the puzzle to its original state

        // Reset all cells to their initial state (given or empty)
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
            }
        }
    }

    // [TODO 2] Define the listener class for editable cells
    private class CellInputListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Cell sourceCell = (Cell) e.getSource();
            
            try {
                int numberIn = Integer.parseInt(sourceCell.getText());
                System.out.println("You entered " + numberIn);

                // Check the guessed number
                if (numberIn == sourceCell.number) {
                    sourceCell.status = CellStatus.CORRECT_GUESS;
                } else {
                    sourceCell.status = CellStatus.WRONG_GUESS;
                }
                sourceCell.paint();  // Repaint the cell based on its status

                // Check if the puzzle is solved after this move
                if (isSolved()) {
                    JOptionPane.showMessageDialog(null, "Congratulations! You solved the puzzle!");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input: not a number.");
            }
        }
    }

    /**
     * Check if the puzzle is solved.
     */
    public boolean isSolved() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Provide a hint by filling an empty editable cell with the correct value.
     */
    public void giveHint() {
        // Find an editable cell that is empty (not filled by the user yet)
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].isEditable() && cells[row][col].getText().isEmpty()) {
                    // If the cell is empty, fill it with the correct number
                    int correctValue = puzzle.numbers[row][col];
                    cells[row][col].setText(String.valueOf(correctValue));  // Set the correct number
                    cells[row][col].status = CellStatus.CORRECT_GUESS;  // Mark it as correctly guessed
                    cells[row][col].paint();  // Repaint the cell to show the change
                    return;  // Provide only one hint at a time
                }
            }
        }

        // If no hint is available, show a message
        JOptionPane.showMessageDialog(null, "No more hints available!");
    }
    public Cell getCell(int row, int col) {
        return cells[row][col];
    }    
}

