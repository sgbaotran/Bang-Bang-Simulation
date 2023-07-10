package components;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.Random;

import javax.swing.BorderFactory;

public class PlayerBoard extends Board {

    private ComputerBoard computerBoard;

    public PlayerBoard(int dimension, Color[] colors, String name) {
        super(dimension, colors, name);
    }

    /**
     * Adds a coordinate button to the inner board panel.
     *
     * @param row        the row index
     * @param col        the column index
     * @param buttonSize the size of the button
     */
    public void addCoordinateButton(int row, int col, int buttonSize) {
        button = new Coordinate(row, col);
        String buttonName = ALPHABET[col] + Integer.toString(row);
        coordinatesMap.put(buttonName, button);
        coordinates[row][col] = button;
        originalColors[row][col] = COORDINATE_COLOR; // Store the original color

        // button.addMouseListener(new MouseAdapter() {
        // @Override
        // public void mouseEntered(MouseEvent e) {
        // hoverActive = true;
        // hoverRow = row;
        // hoverCol = col;
        // updateButtonColors();
        // }
        //
        // @Override
        // public void mouseExited(MouseEvent e) {
        // hoverActive = false;
        // updateButtonColors();
        // }
        //
        // @Override
        // public void mouseClicked(MouseEvent e) {
        // if (button.getBackground() == Color.GREEN) {
        // paintPermanent(button, true);
        // }
        // }
        // });
        button.setBackground(originalColors[row][col]); // Use the original color
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(buttonSize, buttonSize));
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        innerBoard.add(button);
    }

    /**
     * Player that random shoots
     */
    public void computerPlay(int randRow, int randCol) {
        String buttonName = ALPHABET[randCol] + Integer.toString(randRow);
        Coordinate button = coordinatesMap.get(buttonName);
        System.out.println("Computer shoots: " + buttonName);

        for (Ship ship : ships) {
            int index = Arrays.binarySearch(ship.getCoordinates(), buttonName);
            boolean found = (index >= 0);
            if (found) {
                paintRed(button);
                return;
            }
        }

        paintBlue(button);
    }

    public void setComputerBoard(ComputerBoard board) {
        this.computerBoard = board;
    }

}
