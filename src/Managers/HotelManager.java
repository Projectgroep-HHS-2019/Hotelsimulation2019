package Managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import Areas.Area;
import Areas.Cinema;
import Areas.HotelRoom;
import Factories.PersonFactory;
import Persons.Cleaner;
import Persons.Person;
import javafx.application.Platform;
import EventLib.HotelEvent;

/**
 * The HotelManager controls the hotel based on received events.
 *
 */

@SuppressWarnings("deprecation")
public class HotelManager implements EventLib.HotelEventListener, Observer{

	//Variables
	public static int guestCounter = 0;
	private int selectedRoomId;
	
	public boolean evacuateGuestMode = false;
	public static boolean evacuateCleanerMode = false;
	public static boolean moviePlaying = false;
	public boolean reimportPeople = false;
	public static int movieTime = SettingBuilder.movieTime;
	public static int movieTimeRemaining = SettingBuilder.movieTime;
	
	public static int currentGuestAmount = 0;
	public static int currentGuestAmountInRoom = 0;
	public static int currentGuestAmountInFitness = 0;
	public static int currentGuestAmountInRestaurant = 0;
	public static int currentGuestAmountInCinema = 0;
	
	public static int currentCleanerAmount = 0;
	public static int currentCleanerAmountInCleaning = 0;
	public static int currentCleanerAmountInEmergencyCleaning = 0;

	public static SimulationTimer timer;
	public static List<Person> cleaners =  Collections.synchronizedList(new ArrayList<Person>());
	public static List<Person> guests =  Collections.synchronizedList(new ArrayList<Person>());
	ShortestPath.Dijkstra _ds = new ShortestPath.Dijkstra();
	
	/**
	 * Constructor that builds the HotelManager.
	 */
	public HotelManager()
	{
		GridBuilder gridBuilder = new GridBuilder();
		gridBuilder.buildGrid();

		addCleaners(2);

		timer = new SimulationTimer();
		timer.addObserver(this);
		timer.setInterval(SettingBuilder.tickSpeed);
		timer.activateTimer();
	}
	
	/**
     * method that counts the amount of guests / status.
     */
	private void setRealtimeStatistics()
	{
		currentGuestAmount = guests.size();
		currentGuestAmountInRoom = 0;
		currentGuestAmountInFitness = 0;
		currentGuestAmountInRestaurant = 0;
		currentGuestAmountInCinema = 0;
		
		currentCleanerAmountInEmergencyCleaning = 0;
		currentCleanerAmountInCleaning = 0;
		
		synchronized (guests) 
		{
			for(Person guest : guests) 
			{
				if(guest.getStatus().equals("GO_BACK_TO_ROOM") && guest.currentRoute.isEmpty())
				{
					currentGuestAmountInRoom++;
				}
				
				else if(guest.getStatus().equals("INSIDE_FITNESS"))
				{
					currentGuestAmountInFitness++;
				}
				
				else if(guest.getStatus().equals("NEED_FOOD") && guest.currentRoute.isEmpty())
				{
					currentGuestAmountInRestaurant++;
				}
				else if(guest.getStatus().equals("GO_TO_CINEMA") && guest.currentRoute.isEmpty())
				{
					currentGuestAmountInCinema++;
				}
			}
		}
		
		currentCleanerAmount = cleaners.size();
		
		synchronized (cleaners) 
		{
			for(Person cleaner : cleaners) 
			{
				if(cleaner.getStatus().equals("EMERGENCY"))
				{
					currentCleanerAmountInEmergencyCleaning++;
				}
				
				else if(cleaner.getStatus().equals("GOTODIRTYROOM"))
				{
					currentCleanerAmountInCleaning++;
				}
			}
		}
	}
	
	//call performAction for every object in arraylist guests
	private static void personsPerformActions()
	{
		synchronized (guests) {
			for(Person guest : guests) {
				guest.performAction();
			}
		}		
		synchronized (cleaners) {
			for(Person cleaner : cleaners) {
				cleaner.performAction();
			}
		}
	}
	
	//call moveToArea for every object in arraylist guests
	private static void moveCharacters() 
	{
		synchronized (guests) 
		{
	  		for(Person guest : guests) 
	  		{
				guest.moveToArea();
	  		}  			
		}
		synchronized (cleaners) 
		{
			for(Person cleaner : cleaners) 
			{
				cleaner.moveToArea();
			} 			
		}		
	}
	
	private void evacuatePeople(){
		evacuateGuestMode = true;
		evacuateCleanerMode = true;
		synchronized (guests) {
	  		for(Person guest : guests) {
	  			if(!guest.getAvailability()){
	  				guest.setVisible();
					guest.setStatus("GO_OUTSIDE");
					guest.clearRoute();
					guest.getRoute(Person.getLobby());
	  			}
	  		}  			
		}
		
		synchronized (cleaners) {
			for(Person cleaner : cleaners) {
				cleaner.setStatus("GO_OUTSIDE");
				cleaner.clearRoute();
				cleaner.getRoute(Person.getLobby());
			} 			
		}
	}
	
	private void checkMovie()
	{
		for (Area object: Area.getAreaList()) 
		{
			if(object instanceof Cinema) 
			{
				if(moviePlaying)
				{
					if(movieTimeRemaining > 0)
					{
						movieTimeRemaining--;
					}
				
					else
					{
						((Cinema) object).stopMovie();
					}
				}
				else if (!moviePlaying)
				{
					//
				}
			}
		}
	}
	
	//Add a cleaner
	private void addCleaners(int amount)
	{
		for(int i = 1; i <= amount; i++)
		{
			Person xx = PersonFactory.createPerson("Cleaner","INACTIVE",i,true,2,GridBuilder.getMaxY()+1);
			Cleaner c = (Cleaner) xx;
			c.setId(i);
			cleaners.add(xx);
		}
	}
	
	/**
     * method that returns an available room based on prefered stars.
     * @param prefStars the preffered amount of stars by the guest.
     * @return object.id The id of the room that was found.
     */
	private synchronized int getRoom(int prefStars)
	{
		int starAmount = prefStars;
		boolean checkForRoom = true;
		while(checkForRoom)
		{
			for (Area object: Area.getAreaList()) 
			{
				if(object instanceof HotelRoom) 
				{
					if (object.stars == starAmount && object.available == true)
					{					
						object.setAvailability(false);
						return object.id;
					}
				}
			}
			if (starAmount < 5)
			{
				starAmount += 1;
			} 
			
			else if (starAmount == 5)
			{
				checkForRoom = false;
			}
		}
		return 0;
	}
	
	//Return area of the room (input roomnumber)
	public synchronized static Area getRoomNode(int roomId) 
	{
		for (Area object: Area.getAreaList()) 
		{
			if(object instanceof HotelRoom) 
			{
				if (object.id == roomId)
				{					
					return object;
				}
			}
		}
		return null;
	}
	
	//Return area of the room (input roomnumber) and set the availability to false
	private synchronized static Area getRoomNodeAfterCheckIn(int roomId) 
	{
		for (Area object: Area.getAreaList()) 
		{
			if(object instanceof HotelRoom) 
			{
				if (object.id == roomId)
				{					
					object.setAvailability(false);
					return object;
				}
			}
		}
		return null;
	}
	
	private synchronized void sendGuestToCinema(int guestId)
	{
		String status = "GOTO_CINEMA";
		synchronized (guests) {
			for(Person guest : guests) {
				if(guest.getId() == guestId) 
				{
					guest.currentRoute.clear();
					guest.setStatus(status);
					guest.getRoute(findClosestArea("Cinema", guestId));
				}
	  		}
		}
	}
	
	private synchronized void sendGuestToFitness(int guestId, int hte) 
	{
		String status = "GOTO_FITNESS";
		synchronized (guests) {
			for(Person guest : guests) {
				if(guest.getId() == guestId) {
					guest.currentRoute.clear();
					guest.setStatus(status);
					guest.setFitnessTickAmount(hte);
					guest.getRoute(findClosestArea("Fitness", guestId));
												
				}
	  		}
		}
	}
	
	/**
     * method that finds the requested Area, if there are multiple available it calls getClosestObject().
     */
	public synchronized static Area findClosestArea(String name, int guestId)
	{
		ArrayList<Area> objectList = new ArrayList<Area>();
		{
			for (Area object: Area.getAreaList()) 
			{
				if(object.areaType.equals(name)) 
				{
					objectList.add(object);
				}
			}
			
			if (objectList.size() == 1)
			{
				return objectList.get(0);
			}
			
			else if (objectList.size() > 1)
			{
				return getClosestObject(objectList, guestId);
			}
		}
		return null;
	}
	
	/**
     * method that returns the closest Area to the guest provided.
     */
	private synchronized static Area getClosestObject(ArrayList<Area> list, int guestId)
	{
		Area closestArea = null;
		int closestDistance = 100;
		Person currentGuest = null;
		
		for(Person guest : guests) 
		{
			if(guest.getId() == guestId) 
			{
				currentGuest = guest;
			}
  		}
		
		for (int i= 0; i<list.size(); i++) 
		{	
			if(currentGuest.checkDistanceArea(list.get(i)) < closestDistance) 
			{
				closestArea = list.get(i);
				closestDistance = currentGuest.checkDistanceArea(list.get(i));
			}
		}
		return closestArea;
	}
	
	private synchronized void sendGuestToRestaurant(int guestId) 
	{
		String status = "NEED_FOOD";
		synchronized (guests) 
		{
			for(Person guest : guests) {
				if(guest.getId() == guestId) 
				{
					guest.currentRoute.clear();
					guest.setStatus(status);
					guest.getRoute(findClosestArea("Restaurant", guestId));
				}
	  		}
		}

	}
	
	private void getPeopleBackInTheBuilding()
	{
		if (reimportPeople)
		{
			synchronized (guests) {
		  		for(Person guest : guests) 
		  		{
					guest.setVisible();
					guest.setStatus("REENTERED");
		  		}  			
			}
			
			synchronized (cleaners) {
		  		for(Person cleaner : cleaners) 
		  		{
		  			cleaner.setVisible();
		  			cleaner.setStatus("INACTIVE");
		  		}  			
			}
		}
		reimportPeople = false;
	}
	
	private void removeGuest(int guestId, String tempEvent) 
	{
		synchronized (guests) {
			for(Person guest : guests) {
				if(guestId == guest.getId()) {
					guest.setStatus(tempEvent);
					guest.setAvailability(false);
				}
			}	
		}
	}	
	
	//add room to clean to queue
	private void addRoomToClean(int guestId) 
	{
		synchronized (guests) 
		{
			for(Person guest : guests) 
			{
				if(guestId == guest.getId()) 
				{
					Cleaner.getRoomCleaningList().add(getRoomNodeAfterCheckIn(guest.getSelectedRoom()));
				}
			}		
		}
	}
	
	//add room to clean to emergency clean queue
	private void addEmergencyRoomToClean(int guestId) 
	{
		synchronized (guests) 
		{
			for(Person guest : guests) 
			{
				if(guestId == guest.getId()) 
				{
					Cleaner.getEmergencyRoomCleaningList().add(getRoomNodeAfterCheckIn(guest.getSelectedRoom()));
				}
			}	
		}
	}
	
	public void checkIfInRestaurant(int guestId)
	{
		synchronized (guests) 
		{
			for(Person guest : guests) 
			{
				if(guestId == guest.getId()) 
				{
					if(guest.getStatus() == "IN_RESTAURANT") 
					{
						guest.getCurrentPosition().capacity++;
					}
				}
			}	
		}	
	}
	
	/**
     * method that receives the events and handles them accordingly based on the eventType.
     */
	@Override
	public synchronized void Notify(HotelEvent event) 
	{
		String tempEvent = event.Type.toString();
		String hashmapContent = event.Data.toString();
		
		//Check in: Create guest, assign room, add to array, get route to room
		if (tempEvent == "CHECK_IN")
		{
			String guestId;
			int setGuestIdValue;
			String prefStars;
			
			String[] splitArray = hashmapContent.split("\\s");
			String[] splitArray2 = splitArray[1].split("=");
			
			//Set GuestID
			guestId = splitArray2[0];
			setGuestIdValue = Integer.parseInt(guestId);
			
			//Set prefStars
			prefStars = splitArray2[1];
			int prefStarsInt = Integer.parseInt(prefStars);
			
			//Because this command is not running from Java FX I've added this to update UI from a different thread.
			Platform.runLater(
					  () -> {

						  selectedRoomId = getRoom(prefStarsInt);
						  
						  if (selectedRoomId != 0)
						  {
							  Person xx = PersonFactory.createPerson("Guest","GO_BACK_TO_ROOM", setGuestIdValue,true,2,GridBuilder.getMaxY()+1);
							  xx.setRoomId(selectedRoomId);
							  guests.add(xx);
							  xx.getRoute(getRoomNodeAfterCheckIn(selectedRoomId));
						  }
						  else
						  {
							 //
						  }
					   }
					);
			guestCounter++;
		} 
		
		//Check out for guest, send cleaner to room, remove the guest

		else if (tempEvent == "CHECK_OUT")
		{
			int guestId = splitEventCheckOut(event);

			checkIfInRestaurant(guestId);
			addRoomToClean(guestId);
			removeGuest(guestId, tempEvent);
		}
		
		//Send guest to fitness
		else if (tempEvent == "GOTO_FITNESS")
		{
			String guestId;
			int setGuestIdValue;
			int HTE;
			
			String[] splitArray = hashmapContent.split("\\s");
			String[] splitArray2 = splitArray[1].split("=");
			
			//Set GuestID
			guestId = splitArray2[0];
			setGuestIdValue = Integer.parseInt(guestId);
			HTE = Integer.parseInt(splitArray2[1]);
			
			checkIfInRestaurant(setGuestIdValue);
			sendGuestToFitness(setGuestIdValue, HTE);
		}
		
		//Send guest to restaurant
		else if (tempEvent == "NEED_FOOD")
		{
			int guestId = splitEventNeedFood(event);
			sendGuestToRestaurant(guestId);
		}
	
		//Send person to the Cinema
		else if (tempEvent == "GOTO_CINEMA")
		{
			int guestId = splitEventGoToCinema(event);
			sendGuestToCinema(guestId);
		}
		
		else if (tempEvent == "START_CINEMA")
		{
			int cinemaId = splitEventStartCinema(event);
						
			for (Area object: Area.getAreaList()) 
			{
				if(object instanceof Cinema) 
				{
					if (object.id == cinemaId)
					{
						((Cinema) object).startMovie();
					}
				}
			}
		}
		
		//Add room to clean to the queue.
		else if (tempEvent == "CLEANING_EMERGENCY"){
			int guestId = splitEventCleaningEmergency(event);
			addEmergencyRoomToClean(guestId);
		}
		
		else if (tempEvent == "EVACUATE")
		{
			evacuatePeople();
			reimportPeople = true;
		}
				
		else if (tempEvent == "GODZILLA")
		{
			System.out.println("Godzilla event, message: " + hashmapContent);
		}		
	}
	
	private int splitEventCheckOut(HotelEvent event)
	{
		int guestId = 0;
		String hashmapContent = event.Data.toString();
		String[] splitArray = hashmapContent.split("=");
		
		if (splitArray[1].contains("}")) {
			String[] splitArray2 = splitArray[1].split("}");
			guestId = Integer.parseInt(splitArray2[0]);
		}		
		else {
			guestId = Integer.parseInt(splitArray[1]);
		}
		
		return guestId;
	}
	
	private int splitEventNeedFood(HotelEvent event)
	{
		int guestId = 0;
		String hashmapContent = event.Data.toString();
		String[] splitArray = hashmapContent.split("=");
		
		if (splitArray[1].contains("}")){
			String[] splitArray2 = splitArray[1].split("}");
			guestId = Integer.parseInt(splitArray2[0]);
		} else {
			guestId = Integer.parseInt(splitArray[1]);
		}
	
		return guestId;
	}
	
	private int splitEventGoToCinema(HotelEvent event)
	{
		int guestId;
		String hashmapContent = event.Data.toString();
		String[] splitArray = hashmapContent.split("=");
		
		if (splitArray[1].contains("}"))
		{
			String[] splitArray2 = splitArray[1].split("}");
			guestId = Integer.parseInt(splitArray2[0]);
		}
		
		else 
		{
			guestId = Integer.parseInt(splitArray[1]);
		}
		return guestId;
	}
	
	private int splitEventStartCinema(HotelEvent event)
	{
		int cinemaId = 0;
		String hashmapContent = event.Data.toString();
		String[] splitArray = hashmapContent.split("=");
		
		if (splitArray[1].contains("}"))
		{
			String[] splitArray2 = splitArray[1].split("}");
			cinemaId = Integer.parseInt(splitArray2[0]);
		}
		
		else 
		{
			cinemaId = Integer.parseInt(splitArray[1]);
		}
		return cinemaId;
	}
	
	private int splitEventCleaningEmergency(HotelEvent event)
	{
		int guestId = 0;
		String hashmapContent = event.Data.toString();
		
		String[] splitArray = hashmapContent.split("=");
		
		if (splitArray[1].contains("}"))
		{
			String[] splitArray2 = splitArray[1].split("}");
			guestId = Integer.parseInt(splitArray2[0]);
		}
		
		else 
		{
			guestId = Integer.parseInt(splitArray[1]);
		}
		return guestId;
	}
	
	private void checkEvacuateMode()
	{
		if(evacuateGuestMode)
		{
			synchronized (guests) 
			{
		  		for(Person guest : guests) 
		  		{
					if (guest.getStatus() == "GO_OUTSIDE" && guest.getAliveStatus() == true)
					{
						evacuateGuestMode = true;
						break;
						//we are still evacuateMode
					}
					evacuateGuestMode = false;
		  		}  			
			}
		}
		if(evacuateCleanerMode)
		{
			synchronized (cleaners) 
			{
				for(Person cleaner : cleaners) 
				{
					if (cleaner.getStatus() == "GO_OUTSIDE")
					{
						evacuateCleanerMode = true;
						break;
						//we are still evacuateMode
					}
					evacuateCleanerMode = false;
				} 			
			}
		}
		if(!evacuateCleanerMode && !evacuateGuestMode)
		{
			getPeopleBackInTheBuilding();
		}
	}
	
	@Override
	public synchronized void update(Observable o, Object arg) 
	{
		moveCharacters();
		personsPerformActions();
		setRealtimeStatistics();
		checkMovie();
		checkEvacuateMode();	
	}
}