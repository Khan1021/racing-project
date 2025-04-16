public class Main {
    public static void main(String[] args) {


    Horse pinky = new Horse('◇',"pinky",1.0);
    Horse william = new Horse('◆',"william",0.3);
    Horse fluffy = new Horse('♞',"fluffy",0.1);

        Race newRace = new Race(5);

        //horses being made
        newRace.addHorse(pinky,1);
        newRace.addHorse(william,2);
        newRace.addHorse(fluffy,3);

        newRace.startRace();
    }//END main
}
