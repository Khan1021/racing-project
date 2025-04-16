import javax.swing.*;
import java.awt.*;

public class Horses {

    //posistion of horse on screen
    int x,y;

    //colour of horse
    Color color;

    //name of horse
    String name;


    //constructor to set up a horse
    public Horses(String name,int x,int y, Color color){
        this.name = name;
        this.x = x;
        this.y = y;
        this.color = color;
    }//END Horses constructor
}//END Horses
