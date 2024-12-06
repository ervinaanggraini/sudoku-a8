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
    JButton btnReset = new JButton("Reset");  // New button for Reset

    public SudokuMain() {
        // Set up the container and main layout
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        // Add the game board panel in the center
        cp.add(board, BorderLayout.CENTER);

        // Create a panel to hold the buttons and add it to the South
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));  // Align buttons horizontally
        buttonPanel.add(btnNewGame);
        buttonPanel.add(btnReset);  // Add the Reset button beside New Game
        cp.add(buttonPanel, BorderLayout.SOUTH);

        // Add the Hint button to the North
        cp.add(btnHint, BorderLayout.NORTH);

        // Add action listener for the "New Game" button
        btnNewGame.addActionListener(e -> board.newGame());

        // Add action listener for the "Hint" button
        btnHint.addActionListener(e -> board.giveHint());  // Call the giveHint method on the board when pressed

        // Add action listener for the "Reset" button
        btnReset.addActionListener(e -> resetGame());  // Call the resetGame method when pressed

        board.newGame();  // Start the game by calling newGame() on the board

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sudoku");
        setVisible(true);
    }

    // Method to reset only the user-entered values (editable cells)
    private void resetGame() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                Cell cell = board.getCell(row, col);
                if (cell.status == CellStatus.TO_GUESS || cell.status == CellStatus.CORRECT_GUESS || cell.status == CellStatus.WRONG_GUESS) {
                    // Clear the text in editable cells
                    cell.setText("");
                    cell.status = CellStatus.TO_GUESS;  // Reset status to 'TO_GUESS' (need to guess)
                    cell.paint();  // Repaint the cell based on the updated status
                }
            }
        }
    }

    public static void main(String[] args) {
        new SudokuMain();  // Create an instance of the SudokuMain window
    }
}
