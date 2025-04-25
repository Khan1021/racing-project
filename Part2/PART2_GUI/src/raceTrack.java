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
    List<GUIhorses> horses = new ArrayList<>(); //initialize the list

    private String weatherType;

    private ImageIcon horseImage;

    //constructor for raceTrack
    public raceTrack(List<Horse> logicHorses,int raceLength, int maxTrackWidth,String weatherType ) {
        this.raceLength = raceLength;
        horses = new ArrayList<>();
        this.weatherType = weatherType;
        for (int i = 0; i < logicHorses.size(); i++) {
            int y = 90 + i * 100;
            horses.add(new GUIhorses(logicHorses.get(i), y, raceLength, maxTrackWidth));
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
        for (GUIhorses h : horses) {
            g2d.fillRect(50, h.y + 20, h.trackWidth, 5);
        }

        // Finish Line - draw once after lanes
        GUIhorses firstHorse = horses.get(0);
        int finishX = 50 + firstHorse.trackWidth;
        int startY = horses.get(0).y - 20;
        int totalHeight = horses.size() * 100;

        g2d.setColor(Color.BLACK);
        g2d.fillRect(finishX, startY, 5, totalHeight);

        g2d.setFont(new Font("Serif", Font.BOLD, 24));
        g2d.drawString("\uD83C\uDFC1", finishX - 15, startY - 10);

        //weather label
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("SansSerif",Font.BOLD,20));
        g2d.drawString("Weather: "+ weatherType,50,40);


        //weather background
        switch(weatherType.toLowerCase()){
            case "muddy": setBackground(new Color(139,69,19)); break;   //changing background colour to brown
            case "dry": setBackground(new Color(222,184,135));  break;
            case "icy": setBackground(Color.CYAN); break;      //blue
            default:    setBackground(Color.GREEN); break;  //normal grass
        }//END switch

        // Draw horses
        for (GUIhorses h : horses) {
            h.updatePosition();

           //horse image
            Image img = h.getHorseImage().getImage();
            g2d.drawImage(img, h.x, h.y - 40, 60, 60, null);

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