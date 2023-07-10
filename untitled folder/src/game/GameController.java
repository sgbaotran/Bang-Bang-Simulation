package game;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import components.Board;
import java.io.*;
import java.util.ArrayList;

/**
 * Act likes the brain of the game that it will manipulate(delete/update) JFRAME depending on the logic.
 *  @author Gia Bao Tran - Kiet Tran
 */
public class GameController extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
	private Color[] colorSet2 = { new Color(38,38,38), new Color(27,57,64), new Color(51,51,51) };
    private Color[] colorSet1 = { new Color(38,38,38), new Color(69,30,62), new Color(51,51,51) };

    private Timer timer = new Timer(1000, new ActionListener() {
        int elapsedTime = 0;
        int hours = 0;
        int minutes = 0;
        int seconds = 0;

        public void actionPerformed(ActionEvent e) {
            hours = (int) elapsedTime / 3600000;
            minutes = (int) (elapsedTime % 3600000) / 60000;
            seconds = (int) ((elapsedTime % 3600000) % 60000) / 1000;
            timerBox.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
            timerBox.setForeground(Color.WHITE);
            elapsedTime += 1000;
        }
    });

    private String[] language = { "English", "French", "Português" };
    private String[] dimensions = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };

    private ImageIcon img = new ImageIcon("src/resource/logo.png");
    private JLabel logoLabel = new JLabel(img, JLabel.CENTER);
    private JPanel logoPanel = new JPanel();

    

    private JPanel languagePanel = new JPanel();
    private JLabel languageLabel = new JLabel();
	private JComboBox<?> languageBox = new JComboBox(language);

    private JPanel designPanel = new JPanel();
    private JButton designButton = new JButton();
    private JButton randButton = new JButton();

    private JPanel dimensionPanel = new JPanel();
    private JLabel dimensionLabel = new JLabel();
	private JComboBox<?> dimensionBox = new JComboBox(dimensions);

    private JTextArea historyBox = new JTextArea("");

    private JPanel timerPanel = new JPanel();
    private JLabel timerLabel = new JLabel();
    private JLabel timerBox = new JLabel("00:00:00");

    private JPanel resetPanel = new JPanel();
    private JButton resetButton = new JButton();

    private JPanel playPanel = new JPanel();
    private JButton playButton = new JButton();

    private int dimension;
    private JFrame masterGame;
    private Board boardWest;
    private Board boardEast;
    

    /**
     * Constructs a new Controller panel.
     * 
     * @param masterGame The JFrame representing the main game window.
     */
    public GameController(JFrame masterGame) {
        logoLabel.setIcon(img);
        logoLabel.setHorizontalAlignment(JLabel.CENTER);
        dimension = Integer.parseInt((String) dimensionBox.getSelectedItem());

        this.masterGame = masterGame;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(39,70,45));
        setOpaque(true);

        configurePanels();
        addComponentsToPanel();
        setLanguage((String) languageBox.getSelectedItem());
        setVisible(true);
        createGame(dimension);
    }

    /**
     * Configures the panels and their components.
     */
    public void configurePanels() {
    	configurePanel(logoPanel);
        configurePanel(languagePanel);
        configurePanel(designPanel);
        configurePanel(dimensionPanel);
        configurePanel(timerPanel);
        configurePanel(resetPanel);
        configurePanel(playPanel);
        
        languageBox.addActionListener(this);
        designButton.addActionListener(this);
        randButton.addActionListener(this);
        dimensionBox.addActionListener(this);
        resetButton.addActionListener(this);
        playButton.addActionListener(this);
    }

    /**
     * Configures the specified panel.
     * 
     * @param panel The panel to configure.
     */
    public void configurePanel(JPanel panel) {
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 600));
    }

    /**
     * Adds components to the respective panels.
     */
    public void addComponentsToPanel() {
        // logoLabel
        logoPanel.add(logoLabel);

        languagePanel.add(languageLabel);
        languagePanel.add(languageBox);
        languageLabel.setForeground(Color.WHITE);


        designPanel.add(designButton);
        designPanel.add(randButton);

        dimensionPanel.add(dimensionLabel);
        dimensionPanel.add(dimensionBox);
        dimensionLabel.setForeground(Color.WHITE);

        timerPanel.add(timerLabel);
        timerPanel.add(timerBox);
        timerLabel.setForeground(Color.WHITE);

        resetPanel.add(resetButton);

        playPanel.add(playButton);

        add(languagePanel);
        add(logoLabel);
        add(designPanel);
        add(dimensionPanel);
        add(historyBox);
        add(timerPanel);
        add(resetPanel);
        add(playPanel);
    }

    /**
     * Handles action events from buttons.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) {
            timer.start();
        } else if (e.getSource() == designButton) {

        } else if (e.getSource() == randButton) {

            createGame(dimension);
            boardWest.randomizeShip();
            boardEast.randomizeShip();
        } else if (e.getSource() == dimensionBox || e.getSource() == resetButton) {
            JComboBox<?> comboBox = (JComboBox<?>) dimensionBox;
            dimension = Integer.parseInt((String) comboBox.getSelectedItem());
            createGame(dimension);

        } else if (e.getSource() == languageBox) {
            setLanguage((String) languageBox.getSelectedItem());
        }
    }

    /**
     * Removes the existing Board component from the main game window.
     */
    public void removeExistingBoard() {
        for (Component component : masterGame.getContentPane().getComponents()) {
            if (component instanceof Board) {
                masterGame.remove(component);
            }
        }
        masterGame.revalidate();
        masterGame.repaint();
    }

    /**
     * Creates a new game with the specified dimension.
     * 
     * @param dimension The dimension of the game.
     */
    public void createGame(int dimension) {
        removeExistingBoard();
        boardWest = new Board(dimension, colorSet1);
        boardEast = new Board(dimension, colorSet2);
        masterGame.add(boardWest, BorderLayout.WEST);
        masterGame.add(boardEast, BorderLayout.EAST);
        masterGame.revalidate();
        masterGame.repaint();
    }

    /**
     * Sets the language for the game.
     * 
     * @param language The selected language.
     */
    public void setLanguage(String language) {
        String filePath = String.format("src/resource/txt_%s.txt", language);

        ArrayList<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            languageLabel.setText(lines.get(0));
            designButton.setText(lines.get(1));
            randButton.setText(lines.get(2));
            dimensionLabel.setText(lines.get(3));
            timerLabel.setText(lines.get(4));
            resetButton.setText(lines.get(5));
            playButton.setText(lines.get(6));
            masterGame.validate();
            masterGame.repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
