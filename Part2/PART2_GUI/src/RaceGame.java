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


    private JScrollPane scrollPane;

    private List<Horse> horses = new ArrayList<>();
    private List<JSlider> sliders = new ArrayList<>();
    private List<JComboBox<String>> colourBoxes = new ArrayList<>();
    private List<JComboBox<String>> symbolBoxes = new ArrayList<>();
    private List<JSlider> confidenceSliders = new ArrayList<>();

    //creating list to store
    private List<JSlider> speedSliders = new ArrayList<>();

    //class variable for customisable track lane length
    private JTextField trackLengthField;


    //weather dropdown box
    private JComboBox<String> weatherSelector;
    private WeatherCondition currentWeather;


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


        //weather dropdown box
        String[] weatherOptions = {"Normal","muddy","dry","icy"};
        weatherSelector = new JComboBox<>(weatherOptions);
        weatherSelector.setSelectedItem("Normal");
        frame.add(weatherSelector,BorderLayout.NORTH);

        //setting up lane selector
        laneSelector = new JComboBox<>(new Integer[]{1,2,3,4,5,6});
        laneSelector.setSelectedIndex(2);   //default to 3 lanes

        //when user changes lane count, regenerate settings
        laneSelector.addActionListener(e -> regenerateHorseSettings());

        //putting this laneSelector on the left side of the window
        frame.add(laneSelector,BorderLayout.WEST);
    }//END setupFrame



    //helper method
    private JPanel createHorseSettingsPanel(String name, JSlider confidenceSlider, JComboBox<String> colorBox, JComboBox<String> symbolBox,JSlider speedSlider){
        JPanel panel = new JPanel(new GridLayout(4,2,5,5));    //4 rows,2 columns,5 horizontal gap and 5 vertical gap


        panel.setBorder(BorderFactory.createTitledBorder(name + " Settings"));     //title border


        JPanel paddedPanel = new JPanel(new BorderLayout());
        paddedPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        paddedPanel.add(panel,BorderLayout.CENTER);

        //row 1: confidence slider
        panel.add(new JLabel(name + " Confidence"));
        panel.add(confidenceSlider);

        //row 2: coat colour selector
        panel.add(new JLabel(name + " Coat colour"));
        panel.add(colorBox);


        //row 3: Symbol selector
        panel.add(new JLabel(name + " Symbol"));
        panel.add(symbolBox);


        //row 4: speed selector
        panel.add(new JLabel(name+ "speed"));
        panel.add(speedSlider);

        return paddedPanel;
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
        // Remove sliders before we add the track
        if (sliderPanel != null) {
            frame.remove(sliderPanel);
        }

        // Stop any previous race timer
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }

        horses.clear();

        //get the selected weather
        String selectedWeather = (String) weatherSelector.getSelectedItem();
        currentWeather = WeatherCondition.getWeatherCondition(selectedWeather);


        // Create Horse logic objects
        for (int i = 0; i < sliders.size(); i++) {
            char symbol = ((String) symbolBoxes.get(i).getSelectedItem()).charAt(0);
            String name = "HORSE " + (i + 1);
            double confidence = sliders.get(i).getValue() / 100.0;

            Horse h = new Horse(symbol, name, confidence);
            h.setBaseSpeed(speedSliders.get(i).getValue());

            horses.add(h);
        }

        // Get track length from user input safely
        int raceLength;
        try {
            raceLength = Integer.parseInt(trackLengthField.getText());
            if (raceLength <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid track length. Using default of 30.");
            raceLength = 30;
        }

        // Make the track width scale based on the race length
        int baseWidth = 700;
        int trackWidth = Math.max(300,(int)((raceLength/30.0)*baseWidth));

        // Create race and GUI

        //weather
        WeatherCondition selectedCondition = WeatherCondition.getWeatherCondition("muddy");
        race = new Race(horses, raceLength,selectedCondition);


        List<Color> colours = getSelectedColors();
        trackPanel = new raceTrack(horses, colours, raceLength, trackWidth);

        // Remove old scroll pane if it exists
        if (scrollPane != null) {
            frame.remove(scrollPane);
        }

    // Wrap track panel in a scroll pane
        scrollPane = new JScrollPane(trackPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        frame.add(scrollPane, BorderLayout.CENTER);



        // Refresh UI
        trackPanel.revalidate();
        scrollPane.revalidate();
        frame.revalidate();
        frame.repaint();


        // Set up the race timer
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
            }
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
        if(scrollPane != null){
            frame.remove(scrollPane);
        }//END if


        sliderPanel = new JPanel();
        sliderPanel.setLayout(new BoxLayout(sliderPanel,BoxLayout.Y_AXIS));
        sliderPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        sliders.clear();
        colourBoxes.clear();
        symbolBoxes.clear();
        speedSliders.clear();


        int count = (int) laneSelector.getSelectedItem();


        //making customisable track length
        JPanel trackLengthPanel = new JPanel(new FlowLayout());
        trackLengthPanel.add(new JLabel("Track Length:"));

        trackLengthField = new JTextField("30",5);
        trackLengthPanel.add(trackLengthField);

        //adding this to the top of the sliderPanel
        sliderPanel.add(trackLengthPanel);

        //END customisable track length

        for (int i = 0; i < count; i++) {
            String name = "Horse " + (i + 1);

            //JSliders and dropdowns
            JSlider confidenceSlider = createSlider(50);
            JSlider speedSlider = new JSlider(1,10,5);

            speedSlider.setMajorTickSpacing(1);
            speedSlider.setPaintTicks(true);
            speedSlider.setPaintLabels(true);


            JComboBox<String> colourBox = new JComboBox<>(colourNames);
            JComboBox<String> symbolBox = new JComboBox<>(horseSymbols);

            sliders.add(confidenceSlider);
            colourBoxes.add(colourBox);
            symbolBoxes.add(symbolBox);
            speedSliders.add(speedSlider);

            //add full horse settings panel here
            sliderPanel.add(createHorseSettingsPanel(name,confidenceSlider,colourBox,symbolBox,speedSlider));
        }//regenerateHorseSettings

        //wrapping the panel in a scroll pane and add it
        scrollPane = new JScrollPane(sliderPanel);
        scrollPane.setPreferredSize(new Dimension(800,300));
        frame.add(scrollPane,BorderLayout.CENTER);


        resizeFrameForLanes();
        frame.revalidate();
        frame.repaint();
    }//END generateHorseSettings


    //helper method for trackLengthField
    private int getTrackLengthFromField() {
        try{
            return Integer.parseInt(trackLengthField.getText());

        }//END try
        catch(NumberFormatException e){
            return 30;  //default trackLength
        }//END catch
    }//END getTrackLengthFromField

    //grows the frame
    private void resizeFrameForLanes(){
        int numLanes = (int) laneSelector.getSelectedItem();
        int height = 200 + numLanes * 130;
        frame.setSize(800,height);
    }//END resizeFrameForLanes




}//END class RaceGame