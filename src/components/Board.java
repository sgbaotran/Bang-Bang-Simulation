package components;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;

public abstract class Board extends JPanel {
	protected static final long serialVersionUID = 1L;
	protected static final Color COORDINATE_COLOR = new Color(37, 38, 67);
	protected static final int WIDTH = 500;
	protected static final int HEIGHT = 500;
	public static final char[] ALPHABET = { '|', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
			'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	protected JPanel innerBoard;
	protected Coordinate[][] coordinates;
	protected Map<String, Coordinate> coordinatesMap;
	protected int dimension;
	protected Ship[] ships;
	protected int hoverRow;
	protected int hoverCol;
	protected boolean hoverActive;
	protected Color[][] originalColors; // Store the original button colors
	protected JProgressBar progressBar;
	protected JLabel progressLabel;
	protected Coordinate button;
	protected String position;

	/**
	 * Constructs a new Board instance.
	 * 
	 * @param dimension the dimension of the board
	 * @param colors    an array of colors for the board
	 */
	public Board(int dimension, Color[] colors, String name) {
		this.coordinatesMap = new HashMap<>();
		this.ships = new Ship[(dimension + 1) * dimension / 2];
		this.dimension = dimension;
		int numCell = (dimension * 2) + 1;
		this.coordinates = new Coordinate[numCell][numCell];
		this.originalColors = new Color[numCell][numCell]; // Initialize originalColors array
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(colors[2]);

		this.innerBoard = new JPanel(new GridLayout(numCell + 1, numCell + 1));
		this.innerBoard.setBackground(colors[2]);

		int buttonSize = WIDTH / numCell;

		for (int row = 0; row < numCell; row++) {
			for (int col = 0; col < numCell; col++) {
				if (row == 0) {
					addAlphabetCoordinateLabel(col, buttonSize, colors[0]);
				} else if (col == 0) {
					addNumericCoordinateLabel(row, buttonSize, colors[1]);
				} else {
					addCoordinateButton(row, col, buttonSize);
				}
			}
		}

		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

		progressBar = new JProgressBar(0, 100);
		progressBar.setStringPainted(true); // Enable percentage text
		progressBar.setValue(0); // Set initial value
		progressBar.setPreferredSize(new Dimension(WIDTH, 20));

		progressLabel = new JLabel("0%"); // Initial label text
		progressLabel.setForeground(Color.white);
		progressLabel.setPreferredSize(new Dimension(WIDTH, 20));
		progressLabel.setHorizontalAlignment(SwingConstants.CENTER);

		// Add the progress bar and label to the board panel
		JLabel playerLabel = new JLabel(name, JLabel.CENTER);
		playerLabel.setVerticalAlignment(JLabel.BOTTOM);
		playerLabel.setFont(new Font("Verdana", Font.PLAIN, 30));
		playerLabel.setForeground(Color.white);
		add(innerBoard);
		add(playerLabel);
		add(progressBar);

	}

	/**
	 * Adds an alphabet coordinate label to the inner board panel.
	 *
	 * @param col        the column index
	 * @param buttonSize the size of the button
	 * @param color      the color of the label
	 */
	public void addAlphabetCoordinateLabel(int col, int buttonSize, Color color) {
		JLabel label = new JLabel(Character.toString(ALPHABET[col]), SwingConstants.CENTER);
		label.setBackground(color);
		label.setForeground(Color.WHITE);
		label.setOpaque(true);
		label.setPreferredSize(new Dimension(buttonSize, buttonSize));
		innerBoard.add(label);
	}

	/**
	 * Adds a numeric coordinate label to the inner board panel.
	 *
	 * @param row        the row index
	 * @param buttonSize the size of the button
	 * @param color      the color of the label
	 */
	public void addNumericCoordinateLabel(int row, int buttonSize, Color color) {
		JLabel label = new JLabel(Integer.toString(row), SwingConstants.CENTER);
		label.setBackground(color);
		label.setForeground(Color.WHITE);
		label.setOpaque(true);
		label.setPreferredSize(new Dimension(buttonSize, buttonSize));
		innerBoard.add(label);
	}

	/**
	 * Adds a coordinate button to the inner board panel.
	 *
	 * @param row        the row index
	 * @param col        the column index
	 * @param buttonSize the size of the button
	 */
	public abstract void addCoordinateButton(int row, int col, int buttonSize);

	/**
	 * Updates the button colors based on the hover state.
	 */
	public void updateButtonColors() {
		for (int i = 1; i < coordinates.length; i++) {
			for (int j = 1; j < coordinates[i].length; j++) {
				Coordinate button = coordinates[i][j];
				if (hoverActive && i == hoverRow && j >= hoverCol && j < hoverCol + 5) {
					if (j + 4 >= coordinates[i].length || !isSuitableForShip(i, j, 5)) {
						button.setBackground(Color.RED); // Turn button red if hover range is not suitable for a ship of
															// length 5
					} else {
						button.setBackground(Color.GRAY); // Change color if within the hover range and suitable for a
															// ship of length 5
					}
				} else {
					button.setBackground(originalColors[i][j]); // Revert to original color
				}
			}
		}
	}

	/**
	 * Checks if the specified range of buttons is suitable for a ship of the given
	 * length.
	 *
	 * @param row    the row index of the starting button
	 * @param col    the column index of the starting button
	 * @param length the length of the ship
	 * @return true if the range is suitable for a ship of the given length, false
	 *         otherwise
	 */
	public boolean isSuitableForShip(int row, int col, int length) {
		if (col + length > coordinates[row].length) {
			return false; // Range exceeds the board dimensions
		}

		for (int i = col; i < col + length; i++) {
			if (coordinates[row][i].getBackground() != COORDINATE_COLOR
					|| !originalColors[row][i].equals(COORDINATE_COLOR)) {
				return false; // Range contains a button that is already occupied
			}
		}

		return true; // Range is suitable for a ship of the given length
	}

	/**
	 * Paints the button permanently when clicked and updates the adjacent buttons'
	 * colors accordingly.
	 *
	 * @param button     the button to be painted permanently
	 * @param placeShips true if placing ships, false if removing ships
	 */
	public void paintPermanent(Coordinate button, boolean placeShips) {
		int row = button.getRow();
		int col = button.getColumn();
		int length = 5; // Length of the ship to be placed or removed

		// Determine the start and end indices for updating colors
		int startCol = Math.max(col - 1, 1);
		int endCol = Math.min(col + length, 2 * dimension);

		// Update colors for the current row
		for (int j = startCol; j <= endCol; j++) {
			Coordinate currentButton = coordinates[row][j];
			if (placeShips) {
				currentButton.setBackground(Color.BLUE);
			} else {
				currentButton.setBackground(originalColors[row][j]);
			}
		}

		// Update colors for the adjacent rows
		for (int i = row - 1; i <= row + 1; i++) {
			if (i < 1 || i >= coordinates.length) {
				continue; // Skip invalid rows
			}
			for (int j = startCol; j <= endCol; j++) {
				Coordinate currentButton = coordinates[i][j];
				if (placeShips) {
					currentButton.setBackground(Color.BLUE);
				} else {
					currentButton.setBackground(originalColors[i][j]);
				}
			}
		}
	}

	/**
	 * Places a ship at the specified row and column on the board.
	 *
	 * @param row the row index
	 * @param col the column index
	 */
	public void placeShip(int row, int col) {
		Coordinate button = coordinates[row][col];
		if (button.getBackground() == Color.GRAY) {
			paintPermanent(button, true);
		}
	}

	/**
	 * Removes a ship at the specified row and column on the board.
	 *
	 * @param row the row index
	 * @param col the column index
	 */
	public void removeShip(int row, int col) {
		Coordinate button = coordinates[row][col];
		if (button.getBackground() == Color.BLUE) {
			paintPermanent(button, false);
		}
	}

	/**
	 * Generates ships for the board based on the dimension.
	 */
	public void generateShip() {
		int index = 0;
		for (int i = dimension; i >= 1; i--) {
			for (int j = 0; j < dimension - i + 1; j++) {
				ships[index] = new Ship(i);
				index++;
			}
		}
	}

	/**
	 * Randomly places ships on the board without collisions.
	 */
	public void randomizeShip() {
		generateShip();
		Random rand = new Random();
		for (Ship ship : ships) {
			boolean shipPlaced = false;

			while (!shipPlaced) { // Limit the number of attempts to avoid an infinite loop
				int randRow = 1 + rand.nextInt(2 * dimension);
				int randCol = 1 + rand.nextInt(2 * dimension);

				if (isSuitableForShip(randRow, randCol, ship.getLength())) {
					for (int i = randCol; i < ship.getLength() + randCol; i++) {
						coordinates[randRow][i].setBackground(Color.GRAY);
						coordinates[randRow][i].setText(Integer.toString(ship.getLength()));
					}

					ship.coordinateSetter(randRow, randCol);
					shipPlaced = true;
				}
			}
		}
	}

	/**
	 * Paint red, indicating a hit.
	 */
	public void paintRed(Coordinate button) {
		button.setBackground(Color.RED);
		button.setForeground(Color.white);
		button.setOpaque(true);
	}

	/**
	 * Paint blue, indicating a miss.
	 */
	public void paintBlue(Coordinate button) {
		button.setBackground(Color.blue);
		button.setForeground(Color.white);
		button.setOpaque(true);
	}

}
