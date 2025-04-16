
/**
 * Write a description of class Horse here.
 *
 * @author (Zaynab Khan 230325337)
 * @version (a version number or a date)
 */
public class Horse
{
    //Fields of class Horse
    //a single unicode character that represents the horse
    private char horseSymbol;

    //String of horse name
    private String horseName;

    /*a double variable, represents the horses confidence
    between 0 and 1*/
    private double horseConfidence;

    //distance travelled by horse, stored as whole number
    private int distanceTravelled;

    //boolean flag to indicate whether the horse has fallen
    private boolean hasFallen;





    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence)
    {
        this.horseSymbol = horseSymbol;
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
        hasFallen = true;
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

    public char getSymbol()
    {
        return horseSymbol;
    }//END getSymbol

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

    public void setSymbol(char newSymbol)
    {
        horseSymbol =newSymbol;
    }//END setSymbol

}//END Horse class

