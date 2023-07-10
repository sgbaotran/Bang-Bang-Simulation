package components;

/**
 * The Ship class represents a ship in a game. It stores information about the ship's length and position on the game board. And coordinates
 */
public class Ship {
    private int length;
    private int row;
    private int col;
    private int count;
    private String[] shipCoordinates;
    /**
     * Constructs a Ship object with the specified length.
     *
     * @param length the length of the ship
     */
    public Ship(int length) {
        this.length = length;
        this.shipCoordinates = new String[length];
        this.count =0 ; 
    }

    /**
     * Sets the coordinates of the ship on the game board.
     *
     * @param row the row coordinate of the ship
     * @param col the column coordinate of the ship
     */
    public void coordinateSetter(int row, int col) {
        this.row = row;
        this.col = col;
        createCoordinates(row,col);   
    }

    /**
     * Returns the length of the ship.
     *
     * @return the length of the ship
     */
    public int getLength() {
        return length;
    }

    /**
     * Returns the row coordinate of the ship.
     *
     * @return the row coordinate of the ship
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column coordinate of the ship.
     *
     * @return the column coordinate of the ship
     */
    public int getCol() {
        return col;
    }
    
    public String[] getCoordinates() {
    	return this.shipCoordinates;
    }
    
    public void createCoordinates(int row, int col) {
    	for(int i =0  ; i < length; i++) {
            shipCoordinates[i] = Board.ALPHABET[col+i] + Integer.toString(row);
    	}
    }
}
