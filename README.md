# Horse Racing Simulator

This is a horse racing simulator. Part 1 consists of the console version where horses and the like are represented through ascii and Unicode characters.

Part2 is the GUI version. This allows for a JFrame to appear on the screen and users can interact with it through the various buttons and sliders.


## How it is made:
**Tech used:** JDK 17

I used an object oriented approach to create this horse racing simulator. This meant using encapsulation to ensure clean and readable code.


## Optimisations

I originally implemented standard encapsulation procedures such as getter and setter methods (methods which set and retrieve private class values). However I soon realised this is not as efficient as if I were to create new classes for different aspects of the simulator.

  

For example, I initially created the weather in the raceTrack.java class file as it made sense at the time as this class file is responsible for creating the game for the simulation. This includes updating the winning horse, race length, number of lanes. Initially I put all weather methods in this class however this made the class hard to read.

  

Alternatively I began making new classes, this would be the 'Weather.java' class. In the Weather class I created the relevant variables and methods, this includes horse modifierss that affected their performance according to the weather. This allowed me to create an instance of weather In the raceGame.java class and enhanced the readability of the class file.

## Lessons learned

I was exposed to Java Swing for the first time. This meant learning about the Swing components, and libraries. 

## Installation

Clone the repository locally

 - git clone [ZKhan0011/racing-project at master](https://github.com/ZKhan0011/racing-project/tree/master)
 - ensure all files are in the same folder

 

## running the program part1

 - open the command prompt on your pc
 - navigate to the directory where you cloned the repository on the CMD using 'cd *yourPath/part1/Race/src..*' 
 - to check if in right directory use the 'dir' command
	 - this lists all files in the directory
 - now 'javac *.java'
- 'java Main'
-  now you should have a textual representation of the horse simulation

## running the program part2
 - open the command prompt on your pc
 - navigate to the directory where you cloned the repository on the CMD using 'cd *yourPath1/part2/PART2_GUI/src*
 - to check if in right directory use the 'dir' command
	 - this lists all files in the directory
 - now 'javac *.java'
- 'java Main'
-  now you should have a graphical representation of the horse simulation




## Usage
For textual version (part1) once the Main.java is compiled and called, it should automatically run.

For the GUI version (part2), once Main.java is compiled and called another window should pop up with the graphical user interface.

 

 - menu consists of:
 
 - weather (at the top as a dropdown box)
 
 - lane length (directly below as integer input)
 
 - number of lanes (on the left of the window)
 
 - horse confidence (in the centre of the screen, the slider with the name 'horse confidence')
 
	 - the higher the confidence the faster the horse but more likely to fall
	 - lower the confidence the slower the horse but less likely to fall
 - horse breed 
	 - choose from a few different horse breeds by clicking on the dropdown box in the center of the screen with the horse image
	 
 - horse speed (the slider with the name 'horse speed' and select the horse speed)
