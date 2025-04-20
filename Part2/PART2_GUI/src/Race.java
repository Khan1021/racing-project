import java.util.concurrent.TimeUnit;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
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

    private List<Horse> horses;


    private WeatherCondition condition;

    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     *
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(List<Horse> horses,int distance)
    {
        // initialise  variables
        this.horses = horses;
        this.raceLength = distance;
    }//END Race constructor

    /**
     * Adds a horse to the race in a given lane
     *
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */


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
        for(Horse h: horses){
            h.goBackToStart();
        }//END for
    }//END startRace


    //Will use a java swing timer to call on this once every 100ms
    public void updateRaceState() {
       for(Horse h : horses){
           moveHorse(h);
       }//END for


        //debugging
        for(Horse h: horses){
            System.out.println(h.getName()+" distance "+h.getDistanceTravelled());
        }//END for
    }//END updateRaceState



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
            double adjustedConfidence = theHorse.getConfidence() +  condition.confidenceModifier;
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










    //helper methods for the GUI
    public boolean isRaceFinished(){
        for(Horse h: horses){
            if(raceWonBy(h)){return true;}
        }//END for

        return allFallen();
    }//END isRaceOve r

    public String getWinnerNames(){
        List<Horse> winners = new ArrayList<>();

        for(Horse h:horses){
            if(raceWonBy(h)){
                winners.add(h);
            }//END if
        }//END for



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
        for(Horse h: horses){
            if(!h.hasFallen()) {
                return false;
            }//END if
        }//END for
        return true;
    }//END allFallen


    //method to get raceLength
    public int getRaceLength(){
        return raceLength;
    }//END getRaceLength
}//END Race class



