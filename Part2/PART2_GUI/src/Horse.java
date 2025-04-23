
/**
 * Write a description of class Horse here.
 * this is my backend of the race horse:
 *  - handles horse logic
 *  - attributees like name, confidence and fall state
 *  - movement
 *  - does everything BUT draw the visuals
 *
 * @author (Zaynab Khan 230325337)
 * @version (21/04)
 */


import javax.swing.ImageIcon;

public class Horse
{
    //Fields of class Horse
    //a  character that represents the horse
    private ImageIcon horseImage;
    private ImageIcon fallenImage;

    //String of horse name
    private String horseName;

    /*a double variable, represents the horses confidence
    between 0 and 1*/
    private double horseConfidence;

    //distance travelled by horse, stored as whole number
    private int distanceTravelled;

    //boolean flag to indicate whether the horse has fallen
    private boolean hasFallen;


    //default speed of horses
    private int baseSpeed = 1;




    //statistics tracking fields
    private long startTime;
    private long endTime;
    private int fallCount;



    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public Horse(ImageIcon horseImage,ImageIcon fallenImage, String horseName, double horseConfidence)
    {
        this.horseImage = horseImage;
        this.fallenImage = fallenImage;
        this.horseName = horseName;
        this.horseConfidence = horseConfidence;
        this.hasFallen =false;
        this.distanceTravelled = 0;
    }//END constructor Horse





    //Other methods of class Horse

    /*
    marking horse as fallen
        means setting the hasFallen flag == true

        purpose is to update the state of the horse to reflect
        it has fallen
        */
    public void fall()
    {
        this.hasFallen = true;
        this.fallCount++;
    }//END fall

    public double getConfidence()
    {
        return horseConfidence;
    }//END getConfidence

    public int getDistanceTravelled()
    {
        return distanceTravelled;
    }//END getDistanceTravelled

    public String getName()
    {
        return horseName;
    }//END getName



    public void goBackToStart()
    {
        this.distanceTravelled=0;
        this.hasFallen=false;
    }//END goBackToStart

    public boolean hasFallen()
    {
        return hasFallen;
    }//END hasFallen

    public void moveForward()
    {
        distanceTravelled++;
    }//END moveForward

    public void setConfidence(double newConfidence)
    {

            if (newConfidence < 0 || newConfidence > 1) {
                System.out.println("Confidence must be between 0 and 1.");
                return;
            }
            horseConfidence = newConfidence;


    }//END setConfidence


    //Speed getters and setters
    public int getBaseSpeed(){
        return baseSpeed;
    }//END getBaseSpeed

    public void setBaseSpeed(int speed){
        this.baseSpeed = speed;
    }//END setBaseSpeed



    //symbol getters and setters

    //method for horse falling logic, when horse falls then symbol goes to 'x'
    public ImageIcon getHorseImage(){
        if(hasFallen){
            return horseImage;
        }
        else{
            return horseImage;
        }
    }//END getDisplaySymbol

    public ImageIcon getCurrentImage(){
        return hasFallen ? fallenImage : horseImage;
    }//END getCurrentImage




    //statistics methods
    //setter methods
    public void setStartTime(long time){
        this.startTime = time;
    }//END setStartTime

    public void setEndTime(long time){
        this.endTime = time;
    }//END setEndTime

    //getter methods
    public long getStartTime(){
        return startTime;
    }//END getStartTime

    public long getEndTime(){
        return endTime;
    }//END getEndTime

    public int getFallCount(){
        return fallCount;
    }//END getFallCount


}//END Horse class

