//this is the drawing class


import javax.swing.*;
import java.awt.*;
import java.util.List;
//for the arrayList
import java.util.ArrayList;

public class raceTrack extends JPanel {

    private boolean raceOver = false;
    private String winnerName="";

    List<Horses> horses = new ArrayList<>(); //initialize the list

    //constructor for raceTrack
    public raceTrack(Horse h1,Horse h2, Horse h3, int raceLength) {

        horses.add(new Horses(h1,90,Color.PINK,raceLength));
        horses.add(new Horses(h2,190,Color.BLUE,raceLength));
        horses.add(new Horses(h3,290,Color.ORANGE,raceLength));

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

            //sync position
            h.updatePosition();

            //GUI horse
            g2d.setColor(h.color);
            g2d.setFont(new Font("Serif", Font.BOLD, 40));
            g2d.drawString(String.valueOf(h.getSymbol()), h.x, h.y);


            //GUI horse name
            g2d.setFont(new Font("SansSerif", Font.PLAIN, 12));
            g2d.setColor(Color.BLACK);
            g2d.drawString(h.getName(), h.x, h.y - 30);

            //to draw horses name above it
            g2d.drawString(h.getName(), h.x, h.y - 30);;



            //drawing finish line on track
            int finishX=750; //750 = 50 (start) + 700 (track length)
            g2d.setColor(Color.BLACK);
            g2d.fillRect(finishX,90 - 20, 5, 260); // vertical finish line post)


            //drawing flag icon
            g2d.setFont(new Font("Serif",Font.BOLD,24));
            g2d.drawString("\uD83C\uDFC1",finishX - 5, 80); //draw flag at the top

            if(raceOver && h.getName().equalsIgnoreCase(winnerName)){
                g2d.setColor(Color.MAGENTA);
                g2d.setFont(new Font("Serif",Font.BOLD,20));
                g2d.drawString("\uD83C\uDF89 WINNER \uD83C\uDF89",h.x,h.y-50);
            }//END if
        }//END for
    }//END paintComponent


    public void setWinner(String name){
        if(name == null || name.isEmpty()){
            raceOver = false;
            winnerName = "";
        }//END if
        else{
            raceOver = true;
            winnerName = name;
        }//END else
    }//END setWinner


}//END raceTrack