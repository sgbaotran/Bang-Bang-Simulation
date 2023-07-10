package game;
import java.awt.*;
import javax.swing.JFrame;

import components.Board;

/**
 * Represents the main game window for the Bang Bang Simulation.
 *  @author Gia Bao Tran - Kiet Tran
 */
public class Game extends JFrame {
    private static final long serialVersionUID = 1L;
	Board player_1;
    Board player_2;

    /**
     * Constructs a new Game instance.
     */
    public Game() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Bang Bang Simulation");        
        getContentPane().setBackground(new Color(26,20,35));
        setLocationRelativeTo(null);
        setSize(1300, 725);
        setVisible(true);
        setResizable(false);
        setLayout(new BorderLayout());
        add(new StartMenu(this), BorderLayout.CENTER);
        setVisible(true);
    }
}
