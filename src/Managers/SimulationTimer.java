package Managers;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;

/**
 * Constructor that builds the SimulationTimer, this class is Observable .
 */

@SuppressWarnings("deprecation")
public class SimulationTimer extends Observable{

	//Variables
	private boolean pause = false;
	private int delay = 1;
	private int tickRate = 1;
	private int interval = tickRate*100;  // iterate every sec.
	private int currentTick = 0;
	
	public void pause(){
		if(!pause)
			pause = true;
		else if (pause)
			pause = false;
	}
	
	//Functions
	public void setInterval(int newInterval){
		interval = newInterval;
	}

	public void activateTimer(){
		  Timer timer = new Timer();
		   
		  timer.scheduleAtFixedRate(new TimerTask() {
		          public void run() 
		          {
		        	  if(!pause)
		        	  {
		        		  Platform.runLater(
		    					  () -> 
		    					  {
					        		  //This is where the observable let's the observers know that something has changed
						        	  setChanged();
						        	  notifyObservers(); // Let the observer (hotelmanager) know that the status has changed
						              System.out.println("tick: "+currentTick);
						              currentTick++;
		    					  });
		        	  }
		          }
		      }, delay, interval);
	}
}
