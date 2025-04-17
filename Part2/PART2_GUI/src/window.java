import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;


public class window {
    public static void main(String[] args){
        //creating main window

        final Horse[] pinky = new Horse[1];
        final Horse[] william = new Horse[1];
        final Horse[] pipi = new Horse[1];
        final Race[] race = new Race[1];
        final raceTrack[] trackPanel = new raceTrack[1];







        //creating JFrame called frame
        JFrame frame = new JFrame("Horse Race Sim");

        //setting the frame size
        frame.setSize(800,600);

        //when x is pressed, frame will close
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //using borderLayout to divide the window into the different areas
        frame.setLayout(new BorderLayout());

        //able to see the frame
        frame.setVisible(true);








        //create a start button
        JButton startButton = new JButton("Start");

        //restart button
        JButton restartButton = new JButton("Restart");


        //creating a button JPanel with flow layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(startButton);
        buttonPanel.add(restartButton);


        //adding the buttonPanel to the south of the frame
        frame.add(buttonPanel, BorderLayout.SOUTH);









        //timer setup
        Timer timer = new Timer(100, e -> {
            race[0].updateRaceState();
            trackPanel[0].repaint();

            if (race[0].isRaceFinished()) {
                ((Timer) e.getSource()).stop();
                String winner = race[0].getWinnerNames();
                String nameOnly = winner.split(" ")[0];
                trackPanel[0].setWinner(nameOnly);
                JOptionPane.showMessageDialog(frame, winner);
            }
        });


        //confidence sliders
        JSlider pinkySlider = new JSlider(0,100,80);    //0.8 default
        JSlider williamSlider = new JSlider(0,100,60);  //0.6 default
        JSlider pipiSlider = new JSlider(0,100,50);     //0.5 default

        pinkySlider.setMajorTickSpacing(20);
        williamSlider.setMajorTickSpacing(20);
        pipiSlider.setMajorTickSpacing(20);

        pinkySlider.setPaintTicks(true);
        williamSlider.setPaintTicks(true);
        pipiSlider.setPaintTicks(true);

        pinkySlider.setPaintLabels(true);
        williamSlider.setPaintLabels(true);
        pipiSlider.setPaintLabels(true);



        //creating panel to hold sliders
        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new GridLayout(3,2));     //label and a slider for each horse

        sliderPanel.add(new JLabel("PINKY Confidence"));
        sliderPanel.add(pinkySlider);

        sliderPanel.add(new JLabel("WILLIAM Confidence"));
        sliderPanel.add(williamSlider);

        sliderPanel.add(new JLabel("PIPI Confidence"));
        sliderPanel.add(pipiSlider);

        //END confidence sliders



        //Button action
        startButton.addActionListener(e -> {
            pinky[0] = new Horse('◇', "PINKY", pinkySlider.getValue() / 100.0);
            william[0] = new Horse('◆', "WILLIAM", williamSlider.getValue() / 100.0);
            pipi[0] = new Horse('♞', "PIPI", pipiSlider.getValue() / 100.0);

            race[0] = new Race(30);
            race[0].addHorse(pinky[0], 1);
            race[0].addHorse(william[0], 2);
            race[0].addHorse(pipi[0], 3);
            race[0].startRace();

            trackPanel[0] = new raceTrack(pinky[0], william[0], pipi[0], 30);
            frame.add(trackPanel[0], BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();

            timer.start();
        });


        restartButton.addActionListener(e -> {
            pinky[0].goBackToStart();
            william[0].goBackToStart();
            pipi[0].goBackToStart();

            race[0].startRace();
            trackPanel[0].setWinner("");
            trackPanel[0].repaint();
        });


        //END button action



    }//END main





}//END class window
