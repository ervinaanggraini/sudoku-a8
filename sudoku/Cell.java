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

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * The Cell class models the cells of the Sudoku puzzle, by customizing (subclass)
 * the javax.swing.JTextField to include row/column, puzzle number and status.
 */
public class Cell extends JTextField {
    private static final long serialVersionUID = 1L;  // to prevent serial warning

    // Define named constants for JTextField's colors and fonts
    public static final Color BG_GIVEN = new Color(0, 0, 90); // RGB
    public static final Color FG_GIVEN = new Color(216, 216, 216);
    public static final Color FG_NOT_GIVEN = Color.white;
    public static final Color BG_TO_GUESS  = new Color(45, 50, 180); 
    public static final Color BG_CORRECT_GUESS = new Color(0, 216, 0);
    public static final Color BG_WRONG_GUESS   = new Color(216, 0, 0);
    public static final Font FONT_NUMBERS = new Font("OCR A Extended", Font.PLAIN, 28);
    public static final Color BG_HINT = new Color(0, 255, 0);  // Warna hijau terang untuk hint


    // Define properties (package-visible)
    /** The row and column number [0-8] of this cell */
    int row, col;
    /** The puzzle number [1-9] for this cell */
    int number;
    /** The status of this cell defined in enum CellStatus */
    CellStatus status;
    boolean wasCorrect = false;

    /** Constructor */
    public Cell(int row, int col) {
        super();   // JTextField
        this.row = row;
        this.col = col;
        // Inherited from JTextField: Beautify all the cells once for all
        super.setHorizontalAlignment(JTextField.CENTER);
        super.setFont(FONT_NUMBERS);

        // Add KeyListener to handle input without needing ENTER key
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                
                // Process input only if it's a digit between 1-9
                if (Character.isDigit(keyChar) && keyChar >= '1' && keyChar <= '9') {
                    setText(String.valueOf(keyChar));  // Set the number typed in the cell
                    e.consume();  // Prevent JTextField from adding the character again
                    checkAnswer();  // Directly check if the input is correct or not
                } else {
                    // Ignore invalid input or non-numeric keys
                    e.consume();  // Prevent the event from propagating
                }
            }
        });
    }

    /** Reset this cell for a new game, given the puzzle number and isGiven */
    public void newGame(int number, boolean isGiven) {
        this.number = number;
        status = isGiven ? CellStatus.GIVEN : CellStatus.TO_GUESS;
        wasCorrect = false;
        paint();    // paint itself
    }

    /** This Cell (JTextField) paints itself based on its status */
    public void paint() {
        if (status == CellStatus.GIVEN) {
            super.setText(number + "");
            super.setEditable(false);
            super.setBackground(BG_GIVEN);
            super.setForeground(FG_GIVEN);
        } else if (status == CellStatus.TO_GUESS) {
            super.setText("");
            super.setEditable(true);
            super.setBackground(BG_TO_GUESS);
            super.setForeground(FG_NOT_GIVEN);
        } else if (status == CellStatus.CORRECT_GUESS) {
            super.setBackground(BG_CORRECT_GUESS);
        } else if (status == CellStatus.WRONG_GUESS) {
            super.setBackground(BG_WRONG_GUESS);
        } else if (status == CellStatus.HINT) {  // Tambahkan pengecekan status HINT
            super.setBackground(BG_HINT);  // Gunakan warna hijau untuk hint
        }
    }

    /** Check if the user's input is correct or not */
    public void checkAnswer() {
        if (Integer.parseInt(getText()) == number) {
            SoundEffect.playSound("true.wav");
            if (!wasCorrect) {
                wasCorrect = true;  // Mark as correct
                ((SudokuMain) SwingUtilities.getWindowAncestor(this)).updateScore(10);  // Add score for correct answer
            }
            status = CellStatus.CORRECT_GUESS;  // Correct guess
        } else {
            SoundEffect.playSound("wrong.wav");
            if (wasCorrect) {
                wasCorrect = false;  // Mark as incorrect if previously correct
                ((SudokuMain) SwingUtilities.getWindowAncestor(this)).updateScore(-10);  // Subtract score for incorrect answer
            }
            status = CellStatus.WRONG_GUESS;    // Incorrect guess
        }
        paint();  // Repaint the cell based on the updated status
    }

    public void clearConflictHighlight() {
        // Restore the original background if there is no conflict
        if (status == CellStatus.GIVEN) {
            setBackground(BG_GIVEN);
        } else if (status == CellStatus.TO_GUESS) {
            setBackground(BG_TO_GUESS);
        }
        // The text color should also be updated when conflict is cleared
        if (status == CellStatus.GIVEN) {
            setForeground(FG_GIVEN);
        } else if (status == CellStatus.TO_GUESS) {
            setForeground(FG_NOT_GIVEN);
        }
    }
}

