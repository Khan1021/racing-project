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
    public raceTrack(List<Horse> logicHorses,List<Color> colours,int raceLength) {
        horses = new ArrayList<>();
        for(int i=0;i < logicHorses.size();i++){
            int y = 90 + i * 100;
            horses.add(new Horses(logicHorses.get(i),y,colours.get(i),raceLength));
        }//END for


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
        for(Horses h : horses) {
            g2d.fillRect(50,h.y+20,700,5);
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
            int startY = horses.get(0).y-20;    //dynamic top,based on horse's first lane
            //dynamic height:finish line will stretch based on the number of lanes
            int totalHeight = horses.size() * 100;      //assuming each lane takes up 100px
            g2d.fillRect(finishX,horses.get(0).y-20,5,totalHeight);


            //drawing flag icon
            g2d.setFont(new Font("Serif",Font.BOLD,24));
            g2d.drawString("\uD83C\uDFC1",finishX - 15, startY-10); //draw flag at the top

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