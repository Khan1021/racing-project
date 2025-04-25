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

    private List<JComboBox<ImageIcon>> imageSelectors = new ArrayList<>();

    private ImageIcon horseImage;


    private List<JSlider> confidenceSliders = new ArrayList<>();

    //creating list to store
    private List<JSlider> speedSliders = new ArrayList<>();

    //class variable for customisable track lane length
    private JTextField trackLengthField;


    //weather dropdown box
    private JComboBox<String> weatherSelector;
    private Weather currentWeather;


    //race timer
    private Timer timer;



    private JPanel sliderPanel; //store reference to panel so we can remove later
    //END slider


    //horse images
    private final String[] horseImageNames={
            "Morgan.png",
            "americanPaint.png",
            "arabian.png",
            "unicorn.png"
    };



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
    private JPanel createHorseSettingsPanel(String name, JSlider confidenceSlider, JComboBox<ImageIcon> imageBox, JSlider speedSlider){
        JPanel panel = new JPanel(new GridLayout(3,2,5,5)); // now only 3 rows
        panel.setBorder(BorderFactory.createTitledBorder(name + " Settings"));

        JPanel paddedPanel = new JPanel(new BorderLayout());
        paddedPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        paddedPanel.add(panel, BorderLayout.CENTER);

        panel.add(new JLabel(name + " Confidence"));
        panel.add(confidenceSlider);

        panel.add(new JLabel(name + " Image"));
        panel.add(imageBox);

        panel.add(new JLabel(name + " Speed"));
        panel.add(speedSlider);

        return paddedPanel;
    }





    private JSlider createSlider(int initial) {
        JSlider slider = new JSlider(0, 100, initial);
        slider.setMajorTickSpacing(20);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        return slider;
    }//END createSlider



    private void setupButtons() {
        JButton startButton = new JButton("Start");
        JButton restartButton = new JButton("Restart");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(restartButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        startButton.addActionListener(e -> startRaceGUI());
        restartButton.addActionListener(e -> restartRace());
    }//END setupButtons

    private void startRaceGUI() {
        // Remove sliders before we add the track
        if (sliderPanel != null) {
            frame.remove(sliderPanel);
        }

        // Stop any previous race timer
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }

        //horses.clear();

        // Get selected weather
        String selectedWeather = (String) weatherSelector.getSelectedItem();
        currentWeather = Weather.getWeatherCondition(selectedWeather);

        List<ImageIcon> selectedImages = new ArrayList<>();
        for (JComboBox<ImageIcon> box : imageSelectors) {
            selectedImages.add((ImageIcon) box.getSelectedItem());
        }

        for (int i = 0; i < horses.size(); i++) {
            Horse h = horses.get(i);
            h.setConfidence(sliders.get(i).getValue() / 100.0);
            h.setBaseSpeed(speedSliders.get(i).getValue());
            h.goBackToStart();  //resets distance, fall state, and timers
        }//END for





        // Get track length
        int raceLength;
        try {
            raceLength = Integer.parseInt(trackLengthField.getText());
            if (raceLength <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid track length. Using default of 30.");
            raceLength = 30;
        }

        int baseWidth = 700;
        int trackWidth = Math.max(300, (int) ((raceLength / 30.0) * baseWidth));

        Weather selectedCondition = Weather.getWeatherCondition("muddy");
        race = new Race(horses, raceLength, selectedCondition);
        race.startRace();

        trackPanel = new raceTrack(horses, raceLength, trackWidth, currentWeather.getType());

        if (scrollPane != null) {
            frame.remove(scrollPane);
        }

        scrollPane = new JScrollPane(trackPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        frame.add(scrollPane, BorderLayout.CENTER);

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

                    // Show performance metrics
                    String metrics = race.getPerformanceMetrics();
                    JTextArea metricsArea = new JTextArea(metrics);
                    metricsArea.setEditable(false);
                    metricsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
                    JScrollPane metricsScroll = new JScrollPane(metricsArea);
                    metricsScroll.setPreferredSize(new Dimension(400, 200));

                    JOptionPane.showMessageDialog(
                            frame,
                            metricsScroll,
                            "Race Summary",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                });
            }
        });

        timer.start(); //Start the race after setup
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


        imageSelectors.clear();


        for (int i = 0; i < count; i++) {
            String name = "Horse " + (i + 1);

            JSlider confidenceSlider = createSlider(50);
            JSlider speedSlider = new JSlider(1, 10, 5);
            speedSlider.setMajorTickSpacing(1);
            speedSlider.setPaintTicks(true);
            speedSlider.setPaintLabels(true);

            //Make a  combo box for each horse
            JComboBox<ImageIcon> imageBox = new JComboBox<>();

            ImageIcon morgan = loadHorseImage("Morgan.png");
            if(morgan != null){
                morgan.setDescription("Morgan.png");
                imageBox.addItem(morgan);
            }//END if

            ImageIcon paint = loadHorseImage("americanPaint.png");
            if(paint != null){
                paint.setDescription("americanPaint.png");
            }//END if

            ImageIcon arabian = loadHorseImage("arabian.png");
            if(arabian != null){
                arabian.setDescription("arabian.png");
            }//END if


            ImageIcon unicorn = loadHorseImage("unicorn.png");
            if(unicorn != null){
                unicorn.setDescription("unicorn.png");
            }//END if


            for(String imageName: horseImageNames){
                ImageIcon icon = loadHorseImage(imageName);
                if (icon != null) {
                    icon.setDescription(imageName); // <-- you missed this before
                    imageBox.addItem(icon);
                }
            }//END for

            imageSelectors.add(imageBox);




            sliders.add(confidenceSlider);
            speedSliders.add(speedSlider);

            sliderPanel.add(createHorseSettingsPanel(name, confidenceSlider, imageBox, speedSlider));
        }//END for




        //wrapping the panel in a scroll pane and add it
        scrollPane = new JScrollPane(sliderPanel);
        scrollPane.setPreferredSize(new Dimension(800,300));
        frame.add(scrollPane,BorderLayout.CENTER);


        resizeFrameForLanes();

        horses.clear();  // Reset the horse list

        for (int i = 0; i < sliders.size(); i++) {
            String name = "HORSE " + (i + 1);
            double confidence = sliders.get(i).getValue() / 100.0;

            ImageIcon upright = (ImageIcon) imageSelectors.get(i).getSelectedItem();
            String fileName = upright.getDescription();
            String baseName = fileName.substring(0, fileName.lastIndexOf("."));
            ImageIcon fallen = loadHorseImage(baseName + "Fallen.png");

            if (fallen == null) {
                throw new RuntimeException("Missing fallen image for: " + baseName + "Fallen.png");
            }

            Horse h = new Horse(upright, fallen, name, confidence);
            h.setBaseSpeed(speedSliders.get(i).getValue());

            horses.add(h); // the horse exists BEFORE startRaceGUI() uses it
        }


        frame.revalidate();
        frame.repaint();
    }//END generateHorseSettings


    //helper method for horse images
    private ImageIcon loadHorseImage(String fileName) {
        try {
            java.net.URL imageURL = getClass().getClassLoader().getResource("horsesGUI/" + fileName);
            if (imageURL == null) throw new RuntimeException("Image not found");

            ImageIcon icon = new ImageIcon(imageURL);
            icon.setDescription(fileName); // 💥 THIS is how we tag it for later
            return icon;
        } catch (Exception e) {
            System.out.println("Could not load image: " + fileName);
            return null;
        }
    }//END loadHorseImage

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



    public ImageIcon getHorseImage(){
        return horseImage;
    }//END getHorseImage


}//END class RaceGame