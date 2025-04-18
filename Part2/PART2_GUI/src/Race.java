import java.util.concurrent.TimeUnit;
import java.lang.Math;
import java.util.ArrayList;

/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 *
 * @author McRaceface
 * @version 1.0
 */
public class Race
{
    private int raceLength;
    private Horse lane1Horse;
    private Horse lane2Horse;
    private Horse lane3Horse;

    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     *
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int distance)
    {
        // initialise instance variables
        raceLength = distance;
        lane1Horse = null;
        lane2Horse = null;
        lane3Horse = null;
    }//END Race constructor

    /**
     * Adds a horse to the race in a given lane
     *
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse, int laneNumber)
    {
        if (laneNumber == 1)
        {
            lane1Horse = theHorse;
        }
        else if (laneNumber == 2)
        {
            lane2Horse = theHorse;
        }
        else if (laneNumber == 3)
        {
            lane3Horse = theHorse;
        }
        else
        {
            System.out.println("Cannot add horse to lane " + laneNumber + " because there is no such lane");
        }
    }//END addHorse

    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the
     * race is finished
     */
    public void startRace()
    {
        //declare a local variable to tell us when the race is finished
        boolean finished = false;

        //reset all the lanes (all horses not fallen and back to 0).
        lane1Horse.goBackToStart();
        lane2Horse.goBackToStart();
        lane3Horse.goBackToStart();
    }//END startRace


    //Will use a java swing timer to call on this once every 100ms
    public void updateRaceState() {
        moveHorse(lane1Horse);
        moveHorse(lane2Horse);
        moveHorse(lane3Horse);
    }



    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     *
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse)
    {
        //if the horse has fallen it cannot move,
        //so only run if it has not fallen
        if  (!theHorse.hasFallen())
        {
            //the probability that the horse will move forward depends on the confidence;
            if (Math.random() < theHorse.getConfidence())
            {
                theHorse.moveForward();
            }//END if

            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence
            //so if you double the confidence, the probability that it will fall is *2
            if (Math.random() < (0.1*theHorse.getConfidence()*theHorse.getConfidence()))
            {
                theHorse.fall();
            }//END if
        }//END if

    }//END moveHorse

    /**
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(Horse theHorse)
    {
        if (theHorse.getDistanceTravelled() == raceLength)
        {
            return true;
        }
        else
        {
            return false;
        }
    }




    /*
    this method announces the winner and accounts for the situation where
    there is a draw
     */
    private void announceResult(){
        //create a list to hold the winning horses
        ArrayList<Horse> winners = new ArrayList<>();

        if(raceWonBy(lane1Horse)){
            winners.add(lane1Horse);
        }//END if
        if(raceWonBy(lane2Horse)){
            winners.add(lane2Horse);
        }//END if
        if(raceWonBy(lane3Horse)){
            winners.add(lane3Horse);
        }//END if

        //announcing result based on the number of winners
        if(winners.size() >1){
            System.out.print("It is a draw between: ");
            for(Horse i:winners){
                System.out.print(i.getName() + " ");
            }//END for
            System.out.println();

        }//END if
        else if (winners.size() ==1){
            System.out.println(winners.get(0).getName() + " has won!");
        }//END else if
        else{
            //this is the case where there are no winners, maybe they all fell
            System.out.println("There is no winner!");
        }//END else
    }//END announceResult

    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     *
     * @param aChar the character to Print
     */
    private void multiplePrint(char aChar, int times)
    {
        int i = 0;
        while (i < times)
        {
            System.out.print(aChar);
            i = i + 1;
        }
    }


    //helper methods for the GUI
    public boolean isRaceFinished(){
        return (raceWonBy(lane1Horse) || raceWonBy(lane2Horse)|| raceWonBy(lane3Horse)||allFallen());
    }//END isRaceOve r

    public String getWinnerNames(){
        ArrayList<Horse> winners = new ArrayList<>();

        if(raceWonBy(lane1Horse)){
            winners.add(lane1Horse);
        }//END if

        if(raceWonBy(lane2Horse)){
            winners.add(lane2Horse);
        }//END if

        if(raceWonBy(lane3Horse)){
            winners.add(lane3Horse);
        }//END if

        if(winners.size()==0){
            return ("There is no winner!");
        }//END if

        if(winners.size() ==1){
            return (winners.get(0).getName() + " has won the race!!");
        }//END if


        StringBuilder draw = new StringBuilder("It is a draw between: ");
        for(Horse h: winners) draw.append(h.getName()).append(" ");
        return draw.toString();
    }//END getWinnerNames


    //method to return boolean value true or false if all horses have fallen
    private boolean allFallen(){
        return(lane1Horse.hasFallen() && lane2Horse.hasFallen() && lane3Horse.hasFallen());
    }//END allFallen


    //method to get raceLength
    public int getRaceLength(){
        return raceLength;
    }//END getRaceLength
}//END Race class



