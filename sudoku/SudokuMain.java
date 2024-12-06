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
    JButton btnHint = new JButton("Hint");  // New button for Hint

    public SudokuMain() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        cp.add(board, BorderLayout.CENTER);
        cp.add(btnNewGame, BorderLayout.SOUTH);
        cp.add(btnHint, BorderLayout.NORTH);  // Add the Hint button to the NORTH section

        // [TODO 1] Add action listener for the "New Game" button
        btnNewGame.addActionListener(e -> board.newGame());

        // [TODO 6] Add action listener for the "Hint" button
        btnHint.addActionListener(e -> board.giveHint());  // Call the giveHint method on the board when pressed

        board.newGame();  // Start the game by calling newGame() on the board

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sudoku");
        setVisible(true);
    }

    public static void main(String[] args) {
        new SudokuMain();  // Create an instance of the SudokuMain window
    }
}
