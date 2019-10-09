package Persons;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import Areas.Area;
import Managers.GridBuilder;
import Managers.HotelManager;
import Managers.SettingBuilder;

/**
 * This class holds the logic that is only used by Cleaner.
 *
 */

public class Cleaner extends Person{

	//Variables
	private int cleaningTime = SettingBuilder.cleaningTime;
	private int cleaningTimeRemaining;
	private int waitInFrontOfDoor = 0;
	private Area currentRoomToClean;
	public static ArrayList<Area> roomCleaningList = new ArrayList<Area>();
	public static ArrayList<Area> EmergencyRoomCleaningList = new ArrayList<Area>();
	
	/**
	 * Constructor that builds a Cleaner.
	 * @param status Is his current activate status.
	 * @param visibility Is his current activate visiblity status.
	 * @param x Is his current value for his position on the grid (x).
	 * @param y Is his current value for his position on the grid (y).
	 */
	public Cleaner(String status, boolean visibility, int x, int y){
		
		this.setStatus(status);
		this.setVisibility(visibility);	
		this.setX(x);
		this.setY(y);
		this.setCleaningTime();

		// Get the right image depending on dimensions
		try {
			setSprite(new FileInputStream("src/Images/cleaner.png"));	
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
		
		// Paint the guest on the grid
		GridBuilder.grid.add(this.personImageView,x,y);
	}
			
	
	//Check if there is an emergency call open, otherwise check normal cleaning
	private void checkCleaningList() {
		if (!EmergencyRoomCleaningList.isEmpty()){
			assignEmergencyRoomToClean(EmergencyRoomCleaningList.get(0));
		}
		else if(roomCleaningList.isEmpty()) {
			//	
			} else {
			assignRoomToClean(roomCleaningList.get(0));
		}
	}
	
	private void assignEmergencyRoomToClean(Area roomToClean) {
		currentRoomToClean = roomToClean;
		status = "EMERGENCY";
		getRoute(roomToClean);
		EmergencyRoomCleaningList.remove(0);
	}
	
	private void assignRoomToClean(Area roomToClean) {
		if(!HotelManager.evacuateCleanerMode){
			currentRoomToClean = roomToClean;
			status = "GOTODIRTYROOM";
			getRoute(roomToClean);
			roomCleaningList.remove(0);
		}
	}
	
	//Check the status and based on that perform the corresponding action
	@Override
	public void performAction(){
		if(status.equals("EVACUATED"))
		{
			setInvisible();
		}
		
		if(status.equals("GO_OUTSIDE"))
			//
			if(currentRoute.isEmpty()) 
			{
				setStatus("EVACUATED");
			}
		
		if(status.equals("INACTIVE")){
			checkCleaningList();
		} 
		
		if(status.equals("INACTIVE") && currentRoute.isEmpty())
		{
			checkCleaningList();
		}
		
		else if(status.equals("GOTODIRTYROOM") && currentRoute.isEmpty()){
			cleanRoom();
		}
		
		else if(status.equals("EMERGENCY") && currentRoute.isEmpty()){
			cleanRoom();
		}
	}

	//Functions
	private void cleanRoom(){
		if(visibility){
			if (waitInFrontOfDoor == 0){
				waitInFrontOfDoor++;
			}
			else if (waitInFrontOfDoor == 1){
				if(getCurrentPosition() == HotelManager.findClosestArea("Lobby", id)) {
					
				} else {
					setInvisible();
					cleaningTimeRemaining = cleaningTime;
				}
			}
		}
		else if (!visibility){
			if(cleaningTimeRemaining == 0){
				currentRoomToClean.setAvailability(true);
				setVisible();
				getRoute(Person.getLobby());
				setStatus("INACTIVE");
			}
			else {
				cleaningTimeRemaining -= 1;
			}
		}
	}
	
	public static ArrayList<Area> getRoomCleaningList() {
		return roomCleaningList;
	}

	public void setRoomCleaningList(ArrayList<Area> roomCleaningList) 
	{
		Cleaner.roomCleaningList = roomCleaningList;
	}

	public static ArrayList<Area> getEmergencyRoomCleaningList() {
		return EmergencyRoomCleaningList;
	}

	public static void setEmergencyRoomCleaningList(ArrayList<Area> roomEmergencyCleaningList) {
		Cleaner.EmergencyRoomCleaningList = roomEmergencyCleaningList;
	}
	
	private void setCleaningTime(){
		cleaningTime = SettingBuilder.getCleaningTime();
	}
	
}
