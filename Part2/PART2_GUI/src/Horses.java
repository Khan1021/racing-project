/*

DESCRIPTION OF CLASS:
GUI Horse class
this is the frontend horse

This handles:
    - display
    - colours
    - pixel position on screen


@author ZAYNAB A KHAN
@version (16/04)
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


    //constructor to set up a horse
    public Horses(Horse logicHorse,int y, Color color){
        this.logicHorse = logicHorse;
        this.x = 50;
        this.y = y;
        this.color = color;
    }//END Horses constructor


    //method to keep horse's visual position in sync with its logic distance
    public void updatePosition(){

        //scale distance to pixels
        this.x = 50 + logicHorse.getDistanceTravelled() * 15;

    }//END updatePosition


    //helper method for GUI to get name of horse
    public String getName(){
        return logicHorse.getName();
    }//END getName


    public char getSymbol(){
        return logicHorse.getSymbol();
    }//END getSymbol


}//END Horses
