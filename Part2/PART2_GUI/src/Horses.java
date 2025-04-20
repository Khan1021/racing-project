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


import javax.swing.*;
import java.awt.*;

public class Horses {

    //position of horse on screen
    int x;
    int y;

    //colour of horse
    Color color;

    //logic horse reference (stores name, confidence, etc.)
    Horse logicHorse;


    int raceLength;
    int trackWidth;

    //constructor to set up a horse and race length
    public Horses(Horse logicHorse,int y, Color color, int raceLength,int trackWidth) {
        this.logicHorse = logicHorse;
        this.x = 50;
        this.y = y;
        this.color = color;
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

    //if horse falls show 'x' icon
    public char getSymbol(){
        return logicHorse.getDisplaySymbol();
    }//END getSymbol


}//END Horses
