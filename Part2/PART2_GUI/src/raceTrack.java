//this is the drawing class


import javax.swing.*;
import java.awt.*;
import java.util.List;
//for the arrayList
import java.util.ArrayList;

public class raceTrack extends JPanel {

    List<Horses> horses = new ArrayList<>(); //initialize the list

    //constructor for raceTrack
    public raceTrack(Horse h1,Horse h2, Horse h3) {

        horses.add(new Horses(h1,90,Color.PINK));
        horses.add(new Horses(h2,190,Color.BLUE));
        horses.add(new Horses(h3,290,Color.ORANGE));

    }//END raceTrack constructor


    //we are overriding because JPanel has a default paint method built in
    @Override
    //this method is automatically called when repaint() is used
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        //setting background colour of panel
        setBackground(Color.GREEN);


        //draw 3 horizontal lanes
        g2d.setColor(Color.WHITE);
        //for loop for filled white rectangles (meant to be lanes)
        for(int i=1;i<=3;i++){
            // startX, startY, width, height
            g2d.fillRect(50,i*100,700,5);
        }//END for


        // Draw horses
        for (Horses h : horses) {
            h.updatePosition();
            g2d.setColor(h.color);
            g2d.fillRect(h.x, h.y - 20, 40, 20);

            //to draw horses name above it
            g2d.setColor(Color.BLACK);
            g2d.drawString(h.getName(), h.x, h.y - 25);;
        }
    }//END paintComponent


}//END raceTrack