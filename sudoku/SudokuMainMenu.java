package sudoku;

import java.awt.*;
import javax.swing.*;

public class SudokuMainMenu extends JFrame {
    private static final long serialVersionUID = 1L;

    public SudokuMainMenu() {
        // Set up the main menu layout
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        JLabel title = new JLabel("Sudoku Game", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        cp.add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        JButton btnStartGame = new JButton("Start Game");
        JButton btnInstructions = new JButton("Instructions");
        JButton btnExit = new JButton("Exit");

        buttonPanel.add(btnStartGame);
        buttonPanel.add(btnInstructions);
        buttonPanel.add(btnExit);
        cp.add(buttonPanel, BorderLayout.CENTER);

        // Action listeners
        btnStartGame.addActionListener(e -> {
            new SudokuMain(); // Open the game window
            dispose(); // Close the menu window
        });

        btnInstructions.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Welcome to Sudoku!\n\n"
                        + "Instructions:\n"
                        + "1. Fill in the grid so that every row, column, and 3x3 sub-grid\n"
                        + "   contains the numbers 1-9.\n"
                        + "2. Use the hint button if you get stuck.\n"
                        + "3. Select your difficulty level before starting a new game.\n\n"
                        + "Good luck!",
                "Instructions", JOptionPane.INFORMATION_MESSAGE));

        btnExit.addActionListener(e -> System.exit(0));

        // Frame settings
        setTitle("Sudoku Menu");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    public static void main(String[] args) {
        new SudokuMainMenu(); // Launch the menu screen
    }
}
