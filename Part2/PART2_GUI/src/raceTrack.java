//this is the drawing class


import javax.swing.*;
import java.awt.*;
import java.util.List;
//for the arrayList
import java.util.ArrayList;

public class raceTrack extends JPanel {

    private boolean raceOver = false;
    private String winnerName="";
    private int raceLength;
    List<Horses> horses = new ArrayList<>(); //initialize the list

    //constructor for raceTrack
    public raceTrack(List<Horse> logicHorses,List<Color> colours,int raceLength, int maxTrackWidth) {
        this.raceLength = raceLength;
        horses = new ArrayList<>();
        for(int i=0;i < logicHorses.size();i++){
            int y = 90 + i * 100;
            horses.add(new Horses(logicHorses.get(i),y,colours.get(i),raceLength,maxTrackWidth));
        }//END for

        this.setPreferredSize(new Dimension(60+ maxTrackWidth+100,horses.size() * 100 + 100));

    }//END raceTrack constructor


    //we are overriding because JPanel has a default paint method built in
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        setBackground(Color.GREEN);
        if (horses.isEmpty()) return;

        // Lanes
        g2d.setColor(Color.WHITE);
        for (Horses h : horses) {
            g2d.fillRect(50, h.y + 20, h.trackWidth, 5);
        }

        // Finish Line - draw once after lanes
        Horses firstHorse = horses.get(0);
        int finishX = 50 + firstHorse.trackWidth;
        int startY = horses.get(0).y - 20;
        int totalHeight = horses.size() * 100;

        g2d.setColor(Color.BLACK);
        g2d.fillRect(finishX, startY, 5, totalHeight);

        g2d.setFont(new Font("Serif", Font.BOLD, 24));
        g2d.drawString("\uD83C\uDFC1", finishX - 15, startY - 10);

        // Draw horses
        for (Horses h : horses) {
            h.updatePosition();

            // Symbol
            g2d.setColor(h.color);
            g2d.setFont(new Font("Serif", Font.BOLD, 40));
            g2d.drawString(String.valueOf(h.getSymbol()), h.x, h.y);

            // Name
            g2d.setFont(new Font("SansSerif", Font.PLAIN, 12));
            g2d.setColor(Color.BLACK);
            g2d.drawString(h.getName(), h.x, h.y - 30);

            // Winner label
            if (raceOver && h.getName().equalsIgnoreCase(winnerName)) {
                g2d.setColor(Color.MAGENTA);
                g2d.setFont(new Font("Serif", Font.BOLD, 20));
                g2d.drawString("\uD83C\uDF89 WINNER \uD83C\uDF89", h.x, h.y - 50);
            }
        }
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

    @Override
    public Dimension getPreferredSize() {
        if (horses.isEmpty()) return new Dimension(800, 300); // default size
        int width = 50 + horses.get(0).trackWidth + 100; // enough space for race
        int height = horses.size() * 100 + 100; // height for all lanes
        return new Dimension(width, height);
    }

}//END raceTrack