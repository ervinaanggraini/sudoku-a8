/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #1
 * 1 - 5026231078 - Hilman Mumtaz Sya`bani
 * 2 - 5026231101 - Muhammad Akmal Rafiansyah
 * 3 - 5026231042 - Ervina Anggraini
 */

package sudoku;

import java.util.Random;

/**
 * The Sudoku number puzzle to be solved
 */
public class Puzzle {
    int[][] numbers = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    boolean[][] isGiven = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];

    public Puzzle() {
        super();
    }

    /**
     * Generate a new random puzzle with the specified number of cells to guess.
     * @param cellsToGuess the number of clues to leave (cells with known values)
     */
    public void newPuzzle(int cellsToGuess) {
        // Step 1: Generate a valid completed Sudoku grid
        generateValidSudoku();

        // Step 2: Randomly remove cells to create the puzzle
        Random random = new Random();

        // Total cells in the grid
        int totalCells = SudokuConstants.GRID_SIZE * SudokuConstants.GRID_SIZE;

        // Initialize all cells as 'given' (clue cells)
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                isGiven[row][col] = true; // Mark all cells as given (clue cells)
            }
        }

        // Step 3: Calculate how many cells need to be removed
        int cellsToRemove = totalCells - cellsToGuess;

        // Array of indices to store the grid cells
        int[] indices = new int[totalCells];
        for (int i = 0; i < totalCells; i++) {
            indices[i] = i;
        }

        // Shuffle the indices to randomly select which cells to remove
        shuffleArray(indices, random);

        // Step 4: Remove cells to meet the desired number of clues
        for (int i = 0; i < cellsToRemove; i++) {
            int index = indices[i];
            int row = index / SudokuConstants.GRID_SIZE;
            int col = index % SudokuConstants.GRID_SIZE;
            isGiven[row][col] = false; // Mark the cell as empty (remove the clue)
        }
    }

    /**
     * Generates a valid completed Sudoku grid using a backtracking algorithm.
     */
    private void generateValidSudoku() {
        // Reset the numbers grid to be empty
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                numbers[row][col] = 0;
            }
        }

        // Fill the Sudoku grid using backtracking
        if (!solveSudoku(0, 0)) {
            System.out.println("Failed to generate Sudoku puzzle.");
        }
    }

    /**
     * Solves the Sudoku puzzle using a backtracking algorithm.
     * This function fills in the grid with a valid Sudoku solution.
     * @param row the current row to fill
     * @param col the current column to fill
     * @return true if the puzzle is solved, false otherwise
     */
    private boolean solveSudoku(int row, int col) {
        if (row == SudokuConstants.GRID_SIZE) {
            return true; // Puzzle solved
        }

        if (col == SudokuConstants.GRID_SIZE) {
            return solveSudoku(row + 1, 0); // Move to the next row
        }

        // Try all numbers from 1 to 9
        for (int num = 1; num <= SudokuConstants.GRID_SIZE; num++) {
            if (isSafe(row, col, num)) {
                numbers[row][col] = num; // Try placing num
                if (solveSudoku(row, col + 1)) {
                    return true; // If the rest of the puzzle is solved
                }
                numbers[row][col] = 0; // Backtrack if not solved
            }
        }

        return false; // No solution found
    }

    /**
     * Checks if it's safe to place a number in a given row and column.
     * @param row the row index
     * @param col the column index
     * @param num the number to check
     * @return true if the number can be placed in the cell, false otherwise
     */
    private boolean isSafe(int row, int col, int num) {
        // Check the row
        for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
            if (numbers[row][i] == num) {
                return false;
            }
        }

        // Check the column
        for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
            if (numbers[i][col] == num) {
                return false;
            }
        }

        // Check the 3x3 subgrid
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (numbers[i + startRow][j + startCol] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Shuffle the array of indices randomly
     * @param array the array to shuffle
     * @param random the Random object to use for shuffling
     */
    private void shuffleArray(int[] array, Random random) {
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            // Swap array[i] with the element at random index
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
}
