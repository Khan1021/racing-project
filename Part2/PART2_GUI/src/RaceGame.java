import javax.swing.*;
import java.awt.*;
import javax.swing.Timer;
import java.util.*;
import java.util.List;




public class RaceGame {

    private JFrame frame;
    private raceTrack trackPanel;
    private Race race;

    private JComboBox<Integer> laneSelector;


    private List<Horse> horses = new ArrayList<>();
    private List<JSlider> sliders = new ArrayList<>();
    private List<JComboBox<String>> colourBoxes = new ArrayList<>();
    private List<JComboBox<String>> symbolBoxes = new ArrayList<>();
    private List<JSlider> confidenceSliders = new ArrayList<>();




    //race timer
    private Timer timer;



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


    //END JComboBox

    public RaceGame() {
        setupFrame();
        setupButtons();
        regenerateHorseSettings();
        frame.setVisible(true);
    }//END constructoe RaceGame





    private void setupFrame() {

        frame = new JFrame("Horse Race Sim");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());


        //setting up lane selector
        laneSelector = new JComboBox<>(new Integer[]{1,2,3,4,5,6});
        laneSelector.setSelectedIndex(2);   //default to 3 lanes

        //when user changes lane count, regenerate settings
        laneSelector.addActionListener(e -> regenerateHorseSettings());

        //putting this laneSelector on the left side of the window
        frame.add(laneSelector,BorderLayout.WEST);
    }//END setupFrame



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




    private JSlider createSlider(int initial) {
        JSlider slider = new JSlider(0, 100, initial);
        slider.setMajorTickSpacing(20);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        return slider;
    }//END createSlider





    private List<Color> getSelectedColors(){
        List<Color> colours = new ArrayList<>();

        for (JComboBox<String> box : colourBoxes){
            colours.add(colorMap.get(box.getSelectedItem()));
        }//END for each
        return colours;
    }//END getSelectedColours





    private void setupButtons() {
        JButton startButton = new JButton("Start");
        JButton restartButton = new JButton("Restart");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(restartButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        startButton.addActionListener(e -> startRace());
        restartButton.addActionListener(e -> restartRace());
    }//END setupButtons

    private void startRace() {

        //remove sliders before I add track
        if(sliderPanel != null){
            frame.remove(sliderPanel);
        }//END if


        //stopping old timer if it's running
        if(timer != null && timer.isRunning()) {
            timer.stop();
        }//END if


        horses.clear();

        for(int i=0;i<sliders.size();i++){
            char symbol = ((String) symbolBoxes.get(i).getSelectedItem()).charAt(0);
            String name = "HORSE "+ (i+1);
            double confidence = sliders.get(i).getValue()/100;

            Horse h = new Horse(symbol,name,confidence);
            horses.add(h);

        }//END for

        List<Horses> guiHorses = new ArrayList<>();
        for(int i=0;i<horses.size();i++){
            int y= 90 + i* 100;
            Color color = colorMap.get(colourBoxes.get(i).getSelectedItem());
            guiHorses.add(new Horses(horses.get(i), y, color, 30));
        }//END for

        // Remove the racetrack
        if (trackPanel != null) {
            frame.remove(trackPanel);
        }//END if


        //get selected coat colour
        List<Color> colours = getSelectedColors();

        //creating race and track
        int raceLength = 30;
        race = new Race(horses, raceLength);
        trackPanel = new raceTrack(guiHorses);
        frame.add(trackPanel,BorderLayout.CENTER);
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

        if (timer != null && timer.isRunning()) {
            timer.stop();
        }


        // Remove the racetrack
        if (trackPanel != null) {
            frame.remove(trackPanel);
            trackPanel=null;
        }//END if

        regenerateHorseSettings();

        frame.revalidate(); //  re-layout the frame
        frame.repaint();    //  force redraw

    }//END restartRace





    //when user selects a number of lanes, it will recreate the horse configuration panels
    private void regenerateHorseSettings() {
        if (sliderPanel != null) {
            frame.remove(sliderPanel);
        }

        sliderPanel = new JPanel(new GridLayout(0, 1)); // dynamic rows
        sliders.clear();
        colourBoxes.clear();
        symbolBoxes.clear();

        int count = (int) laneSelector.getSelectedItem();

        for (int i = 0; i < count; i++) {
            JSlider slider = createSlider(50);
            JComboBox<String> colourBox = new JComboBox<>(colourNames);
            JComboBox<String> symbolBox = new JComboBox<>(horseSymbols);

            sliders.add(slider);
            colourBoxes.add(colourBox);
            symbolBoxes.add(symbolBox);

            String name = "HORSE " + (i + 1);
            sliderPanel.add(createHorseSettingsPanel(name, slider, colourBox, symbolBox));
        }

        frame.add(sliderPanel, BorderLayout.NORTH);
        frame.revalidate();
        frame.repaint();
    }//END generateHorseSettings

}//END class RaceGame