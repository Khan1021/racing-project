import javax.swing.*;
import java.awt.*;

public class window {
    public static void main(String[] args){
        //creating main window

        //creating JFrame called frame
            JFrame frame = new JFrame("Horse Race Sim");

            //setting the frame size
            frame.setSize(800,600);

            //when x is pressed, frame will close
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //able to see the frame
            frame.setVisible(true);




        //using borderLayout to divide the window into the different areas
        frame.setLayout(new BorderLayout());

        //adding raceTrack panel to the main frame
        raceTrack track = new raceTrack();


        //creating instance of raceTrack panel
        raceTrack trackPanel = new raceTrack();

        //create a start button
        JButton startButton = new JButton("Start");





        //adding things to the frame
        frame.add(track);

            // Add it to the center of the window
            frame.add(trackPanel, BorderLayout.CENTER);
    }//END main


}
