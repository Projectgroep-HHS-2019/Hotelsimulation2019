package Persons;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import Areas.Area;
import Areas.Restaurant;
import Managers.GridBuilder;
import Managers.HotelManager;

/**
 * This class holds the logic that is only used by Guest.
 *
 */

public class Guest extends Person{

	//Variables
	public int roomId;
	private boolean alive = true;
	private int queueTime = 3;
	public ArrayList<Area> restaurantsToCheck = new ArrayList<Area>();
	
	/**
	 * Constructor that builds a Guest.
	 * @param status Is his current activate status.
	 * @param id Is his unique identificationNumber.
	 * @param visibility Is his current activate visibility status.
	 * @param x Is his current value for his position on the grid (x).
	 * @param y Is his current value for his position on the grid (y).
	 */
	public Guest(String status, int id, boolean visibility, int x, int y)
	{
		this.id = id;
		this.setStatus(status);
		this.setVisibility(visibility);
		this.setVisible();	
		this.setX(x);
		this.setY(y);

		// Get the right image depending on dimensions
		try {
			setSprite(new FileInputStream("src/Images/guest.png"));	
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
		
		// Paint the guest on the grid
		GridBuilder.grid.add(this.personImageView,x,y);
	}
	
	//Functions
	
	public int checkDistanceArea(Area destinationArea){	
		ShortestPath.Dijkstra _ds = new ShortestPath.Dijkstra();
		getCurrentPosition().distance = 0;	
	    _ds.Dijkstra(getCurrentPosition(), destinationArea);
	    int foundDistance = destinationArea.distance;
	    clearDistances();		
	    return foundDistance;
	}	
	
	//Check the current status and based on the perform the corresponding action
	@Override
	public void performAction() 
	{
		alive = getAliveStatus();
		if(alive)
		{
			if(status.equals(String.valueOf(Status.GO_OUTSIDE)))
			{
				performActionGoOutside();
			}
			
			if(status.equals(String.valueOf(Status.GOTO_CINEMA)))
			{
				performActionGoToCinema();
			}
			
			if(status.equals(String.valueOf(Status.INSIDE_CINEMA)))
			{
				performActionInsideCinema();
			}
			
			if(status.equals(String.valueOf(Status.GOTO_FITNESS)))
			{
				performActionGoToFitness();
			}
			if(status.equals(String.valueOf(Status.INSIDE_FITNESS)))
			{
				performActionInsideFitness();
			}
			
			if (status.equals(String.valueOf(Status.REENTERED)) && currentRoute.isEmpty())
			{
				performActionReenteredNoRoute();
			}
			
			if(status.equals(String.valueOf(Status.GO_BACK_TO_ROOM)) && currentRoute.isEmpty())
			{
				performActionGoBackToRoomNoRoute();
			} 
			if(status.equals(String.valueOf(Status.CHECK_OUT)))
			{		
				performActionCheckOut();
			}			
			if(status.equals(String.valueOf(Status.LEAVE_HOTEL)) && currentRoute.isEmpty())
			{
				performActionLeaveHotelNoRoute();
			}	
			if(status.equals(String.valueOf(Status.NEED_FOOD)))
			{
				performActionNeedFood();
			}
			if(status.equals(String.valueOf(Status.CHECK_RESTAURANT_QUEUE)))
			{
				performActionCheckRestaurantQueue();
			}
			if(status.equals(String.valueOf(Status.IN_RESTAURANT)))
			{
				performActionInRestaurant();
			}
			if(status.equals(String.valueOf(Status.IN_QUEUE)))
			{
				performActionInQueue();
			}
		}
	}
	
	private void performActionGoOutside()
	{
		if(currentRoute.isEmpty()) 
		{
			setInvisible();
			setStatus(String.valueOf(Status.EVACUATED));
		}
	}
	
	private void performActionGoToCinema()
	{
		setVisible();
		if(currentRoute.isEmpty()) 
		{
			setStatus(String.valueOf(Status.INSIDE_CINEMA));
			setInvisible();
		}
	}
	
	private void performActionInsideCinema()
	{
		if(HotelManager.movieTimeRemaining == 0)
		{
			getRoute(HotelManager.getRoomNode(roomId));
			setStatus(String.valueOf(Status.GO_BACK_TO_ROOM));
			setVisible();
		}
	}
	
	private void performActionGoToFitness()
	{
		setVisible();
		if(currentRoute.isEmpty()) 
		{
			setStatus(String.valueOf(Status.INSIDE_FITNESS));
		}
	}
	
	private void performActionInsideFitness()
	{
		if(fitnessTickAmount == 0) 
		{
			getRoute(HotelManager.getRoomNode(roomId));
			setStatus(String.valueOf(Status.GO_BACK_TO_ROOM));
			setVisible();
		} 
		else 
		{
			setInvisible();
			fitnessTickAmount--;
		}
	}
	
	private void performActionReenteredNoRoute()
	{
		getRoute(HotelManager.getRoomNode(roomId));
		setStatus(String.valueOf(Status.GO_BACK_TO_ROOM));
	}
	
	private void performActionGoBackToRoomNoRoute()
	{
		setInvisible();
	}
	
	private void performActionCheckOut()
	{
		setVisible();
		getRoute(Person.getLobby());
		setStatus(String.valueOf(Status.LEAVE_HOTEL));
		HotelManager.guestCounter--;
	}
	
	private void performActionLeaveHotelNoRoute()
	{
		setStatus(String.valueOf(Status.LEFT_HOTEL));
		setInvisible();
	}
	
	private void performActionNeedFood()
	{
		setVisible();
		if(status.equals(String.valueOf(Status.NEED_FOOD))  && currentRoute.isEmpty())
		{
			setStatus(String.valueOf(Status.CHECK_RESTAURANT_QUEUE));
		}
	}
	
	private void performActionInRestaurant()
	{
		if(restaurantTickAmount > 0)
		{
			restaurantTickAmount--;
		}
		else
		{
			getRoute(HotelManager.getRoomNode(roomId));
			setStatus(String.valueOf(Status.GO_BACK_TO_ROOM));
			setVisible();
		}
	}
	
	private void performActionInQueue()
	{
		performActionCheckRestaurantQueue();
		queueTime--;
		if(queueTime < 0) 
		{
			die();
		}
	}
		
	private void die() 
	{
		setInvisible();
		setDead();
	}

	private void performActionCheckRestaurantQueue() {
		if(((Restaurant) getCurrentPosition()).capacity > 0) {
			setStatus(String.valueOf(Status.IN_RESTAURANT));
			setInvisible();
			((Restaurant)getCurrentPosition()).capacity--;
		} 
		else 
		{
			setStatus(String.valueOf(Status.IN_QUEUE));
		}
	}
	
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	
	public int getSelectedRoom(){
		return roomId;
	}
	
}
