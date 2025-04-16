import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;


public class window {
    public static void main(String[] args){
        //creating main window

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


        //logic setup
        Horse pinky = new Horse('◇', "PINKY", 0.8);
        Horse william = new Horse('◆', "WILLIAM", 0.6);
        Horse pipi = new Horse('♞', "PIPI", 0.5);

        Race race = new Race(30);
        race.addHorse(pinky, 1);
        race.addHorse(william, 2);
        race.addHorse(pipi, 3);
        race.startRace();



        //creating instance of raceTrack panel
        raceTrack trackPanel = new raceTrack(pinky,william,pipi);
        frame.add(trackPanel, BorderLayout.CENTER);

        //create a start button
        JButton startButton = new JButton("Start");
        frame.add(startButton, BorderLayout.SOUTH);


        //timer setup
        Timer timer = new Timer(100,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                race.updateRaceState();
                trackPanel.repaint();

                if(race.isRaceFinished()){
                    ((Timer) e.getSource()).stop();

                    JOptionPane.showMessageDialog(frame, race.getWinnerNames());
                }//END if
            }//END actionPerformed
        });


        //Button action
        startButton.addActionListener(e -> {
            race.startRace();
            timer.start();
        });
    }//END main


}
