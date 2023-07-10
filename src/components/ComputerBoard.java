package components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;

import javax.swing.BorderFactory;

public class ComputerBoard extends Board implements ActionListener {

    private PlayerBoard playerBoard;

    public ComputerBoard(int dimension, Color[] colors, String name) {
        super(dimension, colors, name);
    }

    /**
     * Event Handler that happens when a button is clicked
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        boolean hit;
        Coordinate button = (Coordinate) e.getSource();
        String buttonName = ALPHABET[button.getColumn()] + "" + (button.getRow());
        System.out.println("Player shoots: " + buttonName);

        for (Ship ship : ships) {
            int index = Arrays.binarySearch(ship.getCoordinates(), buttonName);
            hit = (index >= 0);
            if (hit) {
                paintRed(button);
                shootAtPlayer();
                return;
            }
        }

        paintBlue(button);
        shootAtPlayer();
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
        button.addActionListener(this);
        innerBoard.add(button);
    }

    public void shootAtPlayer() {
        Random rand = new Random();
        int randRow = 1 + rand.nextInt(2 * dimension);
        int randCol = 1 + rand.nextInt(2 * dimension);
        playerBoard.computerPlay(randRow, randCol);
    }

    public void setPlayerBoard(PlayerBoard board) {
        this.playerBoard = board;
    }
}
