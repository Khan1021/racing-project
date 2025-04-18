import javax.swing.*;
import java.awt.*;
import javax.swing.Timer;
import java.util.*;




public class RaceGame {

    private JFrame frame;
    private raceTrack trackPanel;
    private Race race;
    private Horse pinky, william, pipi;


    //race timer
    private Timer timer;


    //the slider for the horse confidence
    private JSlider pinkySlider, williamSlider, pipiSlider;

    private JPanel sliderPanel; //store reference to panel so we can remove later
    //END slider

    //defining an array of unicode character for horses
    private final String[] horseSymbols = {"♞", "♘", "★", "◆", "◉", "✿"};


    //add JComboBoxes for horse colour and horseSymbol
    private final String[] colourNames = {"Pink","Blue","Orange","Red","Cyan"};
    private final Map<String, Color> colorMap = Map.of(
            "Pink", Color.PINK,
            "Blue", Color.BLUE,
            "Orange", Color.ORANGE,
            "Cyan", Color.CYAN,
            "Red", Color.RED,
            "Black", Color.BLACK,
            "Gray", Color.GRAY
    );

    private JComboBox<String> pinkyColourBox;
    private JComboBox<String> williamColourBox;
    private JComboBox<String> pipiColourBox;

    private JComboBox<String> pinkySymbolBox;
    private JComboBox<String> williamSymbolBox;
    private JComboBox<String> pipiSymbolBox;

    //END JComboBox

    public RaceGame() {
        setupFrame();
        setupSliders();
        setupButtons();
        frame.setVisible(true);
    }

    private void setupFrame() {
        frame = new JFrame("Horse Race Sim");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

    }

    //helper method
    private JPanel createHorseSettingsPanel(String name, JSlider slider, JComboBox<String> colorBox, JComboBox<String> symbolBox){
        JPanel panel = new JPanel(new GridLayout(3,2));    //3 settings per horse
        panel.setBorder(BorderFactory.createTitledBorder(name + " Settings"));     //title border


        //row 1: confidence slider
        panel.add(new JLabel(name + " Confidence"));
        panel.add(slider);

        //row 2: coat colour selector
        panel.add(new JLabel(name + " Coat colour"));
        panel.add(colorBox);


        //row 3: Symbol selector
        panel.add(new JLabel(name + " Symbol"));
        panel.add(symbolBox);

        return panel;
    }//END createHorseSettingsPanel


    private void setupSliders() {
        sliderPanel = new JPanel(new GridLayout(3,1));

        //for each horse
        // Create PINKY settings panel and add to sliderPanel
        pinkySlider = createSlider(80);
        pinkyColourBox = new JComboBox<>(colourNames);
        pinkySymbolBox = new JComboBox<>(horseSymbols);
        sliderPanel.add(createHorseSettingsPanel("PINKY", pinkySlider, pinkyColourBox, pinkySymbolBox));

        // Create WILLIAM settings panel and add to sliderPanel
        williamSlider = createSlider(60);
        williamColourBox = new JComboBox<>(colourNames);
        williamSymbolBox = new JComboBox<>(horseSymbols);
        sliderPanel.add(createHorseSettingsPanel("WILLIAM", williamSlider, williamColourBox, williamSymbolBox));

        // Create PIPI settings panel and add to sliderPanel
        pipiSlider = createSlider(50);
        pipiColourBox = new JComboBox<>(colourNames);
        pipiSymbolBox = new JComboBox<>(horseSymbols);
        sliderPanel.add(createHorseSettingsPanel("PIPI", pipiSlider, pipiColourBox, pipiSymbolBox));


        frame.add(sliderPanel, BorderLayout.NORTH);
    }

    private JSlider createSlider(int initial) {
        JSlider slider = new JSlider(0, 100, initial);
        slider.setMajorTickSpacing(20);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        return slider;
    }

    private void setupButtons() {
        JButton startButton = new JButton("Start");
        JButton restartButton = new JButton("Restart");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(restartButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        startButton.addActionListener(e -> startRace());
        restartButton.addActionListener(e -> restartRace());
    }

    private void startRace() {

        //remove sliders before I add track
        if(sliderPanel != null){
            frame.remove(sliderPanel);
        }//END if


        //stopping old timer if it's running
        if(timer != null && timer.isRunning()) {
            timer.stop();
        }//END if


        //fetch symbols from JComboBoxes
        char pinkyChar = ((String) pinkySymbolBox.getSelectedItem()).charAt(0);
        char williamChar = ((String) williamSymbolBox.getSelectedItem()).charAt(0);
        char pipiChar = ((String) pipiSymbolBox.getSelectedItem()).charAt(0);




        // create horses with slider values
        pinky = new Horse(pinkyChar, "PINKY", pinkySlider.getValue() / 100.0);
        william = new Horse(williamChar, "WILLIAM", williamSlider.getValue() / 100.0);
        pipi = new Horse(pipiChar, "PIPI", pipiSlider.getValue() / 100.0);


        Color pinkyColour = colorMap.get((String) pinkyColourBox.getSelectedItem());
        Color williamColour = colorMap.get((String) williamColourBox.getSelectedItem());
        Color pipiColour = colorMap.get((String) pipiColourBox.getSelectedItem());


        // Remove the racetrack
        if (trackPanel != null) {
            frame.remove(trackPanel);
        }


        // create race and add horses
        race = new Race(30);
        race.addHorse(pinky, 1);
        race.addHorse(william, 2);
        race.addHorse(pipi, 3);
        race.startRace();

        // create track panel
        trackPanel = new raceTrack(pinky, william, pipi, race.getRaceLength(),pinkyColour,williamColour,pipiColour);

        frame.add(trackPanel, BorderLayout.CENTER);


        //refresh layout
        frame.revalidate();
        frame.repaint();

        // setup timer
        timer = new Timer(100, evt -> {
            race.updateRaceState();
            trackPanel.repaint();

            if (race.isRaceFinished()) {
                timer.stop();
                String winner = race.getWinnerNames();
                String nameOnly = winner.split(" ")[0];
                trackPanel.setWinner(nameOnly);

                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(frame, winner);
                });
            }//END if
        });

        timer.start();
    }//END startRace

    private void restartRace() {

        // Remove the racetrack
        if (trackPanel != null) {
            frame.remove(trackPanel);
        }//END if



        // Add the sliders back
        if(sliderPanel !=null) {
            frame.add(sliderPanel, BorderLayout.NORTH);
        }//END if

        //resetting logic + visuals
        if (race != null && pinky != null && william != null && pipi != null) {
            pinky.goBackToStart();
            william.goBackToStart();
            pipi.goBackToStart();

            race.startRace();
            trackPanel.setWinner("");
            trackPanel.repaint();
        }//END if


        //resetting confidence values to default
        pinkySlider.setValue(80);
        williamSlider.setValue(60);
        pipiSlider.setValue(50);


        frame.revalidate(); //  re-layout the frame
        frame.repaint();    //  force redraw

    }//END restartRace
}//END class RaceGame