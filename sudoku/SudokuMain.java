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

import java.awt.*;
import javax.swing.*;

/**
 * The main Sudoku program
 */
public class SudokuMain extends JFrame {
    private static final long serialVersionUID = 1L;

    GameBoardPanel board = new GameBoardPanel();
    JButton btnNewGame = new JButton("New Game");
    JButton btnHint = new JButton("Hint");
    JButton btnReset = new JButton("Reset");
    JComboBox<String> difficultyComboBox = new JComboBox<>(new String[]{"Easy", "Medium", "Hard"});
    
    // Adding a label to display the score
    JLabel lblScore = new JLabel("Score: 0");
    int score = 0;  // To track the player's score

    public SudokuMain() {
        // Set up the container and main layout
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        // Add the game board panel in the center
        cp.add(board, BorderLayout.CENTER);

        // Create a panel to hold the buttons and add it to the South
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnNewGame);
        buttonPanel.add(btnReset);
        buttonPanel.add(difficultyComboBox);
        cp.add(buttonPanel, BorderLayout.SOUTH);

        // Add the Hint button and score label to the North
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        northPanel.add(btnHint);
        northPanel.add(lblScore);  // Display score on the UI
        cp.add(northPanel, BorderLayout.NORTH);

        // Add action listener for the "New Game" button
        btnNewGame.addActionListener(e -> {
            String selectedDifficulty = (String) difficultyComboBox.getSelectedItem();
            int numClues = getClueCountForDifficulty(selectedDifficulty);
            score = 0;  // Reset score when a new game starts
            lblScore.setText("Score: " + score);  // Update score display
            board.newGame(numClues);  // Start a new game with the selected difficulty
        });

        // Add action listener for the "Hint" button
        btnHint.addActionListener(e -> board.giveHint());

        // Add action listener for the "Reset" button
        btnReset.addActionListener(e -> resetGame());

        // Set default difficulty and start the game
        difficultyComboBox.setSelectedIndex(1);  // Default to "Medium"
        board.newGame(getClueCountForDifficulty("Medium"));

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sudoku");
        setVisible(true);
    }

    // Method to reset the game and clear user inputs
    private void resetGame() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                Cell cell = board.getCell(row, col);
                if (cell.status == CellStatus.TO_GUESS || cell.status == CellStatus.CORRECT_GUESS || cell.status == CellStatus.WRONG_GUESS) {
                    cell.setText("");
                    cell.status = CellStatus.TO_GUESS;
                    cell.paint();
                }
            }
        }
        score = 0;  // Reset score on reset
        lblScore.setText("Score: " + score);  // Update score display
    }

    // Function to return number of clues based on difficulty
    private int getClueCountForDifficulty(String difficulty) {
        switch (difficulty) {
            case "Easy": return 35;
            case "Medium": return 25;
            case "Hard": return 15;
            default: return 25;
        }
    }

    // Update the score based on the user's input
    public void updateScore(int points) {
        score += points;
        lblScore.setText("Score: " + score);  // Update the score label
    }

    public static void main(String[] args) {
        new SudokuMain();
    }
}

