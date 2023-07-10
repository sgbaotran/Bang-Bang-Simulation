package components;
import javax.swing.*;

/**
 * Coordinate class representing a JButton with row and column properties.
 * @author Gia Bao Tran - Kiet Tran
 */
public class Coordinate extends JButton {

    private static final long serialVersionUID = 1L;
	private int column;
    private int row;

    /**
     * Constructs a Coordinate object with the specified row and column.
     *
     * @param row    the row value of the coordinate
     * @param column the column value of the coordinate
     */
    public Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Retrieves the row value of the coordinate.
     *
     * @return the row value
     */
    public int getRow() {
        return row;
    }

    /**
     * Retrieves the column value of the coordinate.
     *
     * @return the column value
     */
    public int getColumn() {
        return column;
    }
}
