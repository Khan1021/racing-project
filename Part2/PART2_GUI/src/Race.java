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


    private Weather currentCondition;

    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     *
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(List<Horse> horses,int distance,Weather condition)
    {
        // initialise  variables
        this.horses = horses;
        this.raceLength = distance;
        this.currentCondition = condition;
    }//END Race constructor

    /*
     * Adds a horse to the race in a given lane
     *
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */


    /*
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the
     * race is finished
     */
    public void startRace()
    {
        long now = System.currentTimeMillis();
        for(Horse h: horses){
            h.setStartTime(now);
        }//END for

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

                //record endTime if horse has just finished
                if(h.getDistanceTravelled() >= raceLength && h.getEndTime() ==0){
                    h.setEndTime(System.currentTimeMillis());
                }//END if
       }//END for

        //debugging
        for(Horse h: horses){
            System.out.println(h.getName()+" distance "+h.getDistanceTravelled());
        }//END for
    }//END updateRaceState



    /*
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     *
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse) {
        if (!theHorse.hasFallen()) {
            double adjustedConfidence = theHorse.getConfidence();
            if (currentCondition != null) {
                adjustedConfidence += currentCondition.getConfidenceModifier();
                adjustedConfidence = Math.max(0.0, Math.min(1.0, adjustedConfidence));
            }//END if

            double speedModifier = currentCondition != null ? currentCondition.getSpeedModifier() : 1.0;
            double moveChance = adjustedConfidence * speedModifier;

            // Speed logic: move as many steps as baseSpeed
            if (Math.random() < moveChance) {
                for (int i = 0; i < theHorse.getBaseSpeed(); i++) {
                    theHorse.moveForward();
                }//END for
            }//END if

            // Fall logic
            double fallModifier = currentCondition != null ? currentCondition.getFallRiskModifier() : 1.0;
            double fallChance = 0.1 * adjustedConfidence * adjustedConfidence * fallModifier;

            if (Math.random() < fallChance) {
                theHorse.fall();
            }//END if
        }//END if
    }//END moveHorse


    /*
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
        }//END if
        else
        {
            return false;
        }//END else
    }//END raceWonBy



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



    public String getPerformanceMetrics(){
        StringBuilder report = new StringBuilder("\uD83C\uDFC1 Race performance metrics:\n");


        for(Horse h: horses){
            report.append(h.getName()).append("\n");

            //time taken to complete race in seconds
            long start = h.getStartTime();
            long end = h.getEndTime();
            if(end >0 && start>0){
                long durationMillis = end - start;
                double durationSeconds = durationMillis / 1000.0;

                double averageSpeed = h.getDistanceTravelled() / durationSeconds;

                report.append("Time: ").append(String.format("%.2f", durationSeconds)).append(" sec\n");
                report.append("Avg Speed: ").append(String.format("%.2f",averageSpeed)).append(" tiles/second\n");
            }//END if
            else{
                report.append("Time: N/A (Did not finish)\n");
                report.append("Average Speed: N/A\n");
            }//END else


            //fall count
            report.append(" Falls: ").append(h.getFallCount()).append("\n\n");
        }//END for


        return report.toString();
    }//END getPerformanceMetrics
}//END Race class



