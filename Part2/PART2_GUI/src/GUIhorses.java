/*

DESCRIPTION OF CLASS:
GUI Horse class
this is the frontend horse

This handles:
    - display
    - colours
    - pixel position on screen


@author ZAYNAB A KHAN
@version (17/04)
 */


import javax.swing.ImageIcon;

public class GUIhorses {

    //position of horse on screen
    int x;
    int y;


    //logic horse reference (stores name, confidence, etc.)
    Horse logicHorse;


    int raceLength;
    int trackWidth;


    //constructor to set up a horse and race length
    public GUIhorses(Horse logicHorse, int y, int raceLength, int trackWidth) {
        this.logicHorse = logicHorse;
        this.x = 50;
        this.y = y;

        this.raceLength = raceLength;
        this.trackWidth = trackWidth;
    }//END Horses constructor


    //method to keep horse's visual position in sync with its logic distance
    public void updatePosition(){

        int trackStartX = 50;


        //scale position based on how far horse has travelled vs race length
        //ensures, distance travelled == raceLength
        double progress =  (double)logicHorse.getDistanceTravelled()/raceLength;

        this.x = trackStartX +(int)(progress * trackWidth);
    }//END updatePosition


    //helper method for GUI to get name of horse
    public String getName(){
        return logicHorse.getName();
    }//END getName


    public ImageIcon getHorseImage(){
        return logicHorse.getCurrentImage();
    }//END getHorseImage


}//END Horses
