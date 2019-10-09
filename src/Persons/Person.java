package Persons;

import java.io.FileInputStream;
import java.util.ArrayList;

import Areas.Area;
import Areas.Lobby;
import Managers.GridBuilder;
import Managers.SettingBuilder;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * The abstract class Person holds the logic that is used by cleaner/guest.
 *
 */
public abstract class Person
{	
	ShortestPath.Dijkstra _ds = new ShortestPath.Dijkstra();
	public ArrayList<Area> currentRoute = new ArrayList<Area>();
	public static ArrayList<Area> roomCleaningList = new ArrayList<Area>();
	public ArrayList<Area> restaurantsToCheck = new ArrayList<Area>();
	protected ImageView personImageView;
	protected boolean visibility = true; // Hide or shows the person visually
	protected int id;
	private boolean alive = true;
	public int roomId;
	protected int stairsWaitTime = 0;
	protected int x = 10; // x coordinate
	protected int y = 9; // y coordinate
	protected int translateXVal;
	protected int translateYVal;
	protected int fitnessTickAmount;
	protected int restaurantTickAmount = 10;
	protected boolean moveAllowed;
	protected String status;
	
	public void setSprite(FileInputStream sprite)
	{
        Image personImage = new Image(sprite);
        personImageView = new ImageView();
        personImageView.setFitHeight(21);
        personImageView.setFitWidth(16); 
        personImageView.setImage(personImage);
        GridPane.setHalignment(this.personImageView, HPos.CENTER);
		GridPane.setValignment(this.personImageView, VPos.BOTTOM);
    }
	
	public synchronized void getRoute(Area destinationArea)
	{	
		currentRoute.clear();
		ShortestPath.Dijkstra _ds = new ShortestPath.Dijkstra();
		getCurrentPosition().distance = 0;	
	    currentRoute = _ds.Dijkstra(getCurrentPosition(), destinationArea);
	    currentRoute.remove(currentRoute.size() - 1);
	    clearDistances();		
	}

	protected void clearDistances() 
	{
		for (Area a : Area.getAreaList()) {
			a.distance = Integer.MAX_VALUE;;
			a.latest = null;
		}		
	}
	
	public int checkDistanceArea(Area destinationArea)
	{
		return 0;
	}
	
	public Area getCurrentPosition() 
	{
		for (Area object: Area.getAreaList()) 
		{
			if(object.getX() == x && object.getRealY() == y) 
			{
				return object;
			}
			else if(object.getRealY() == y) {
				for(int i = object.dimensionW; i > 0; i--) {
					if(i + object.getX() - 1 == x) {
						return object;
					}
				}
			}
		}
//		System.out.println("Je bent er voorbij!!!!!!!!!!!!!!!!!!!!!!!!!");
//		System.out.println("person x: " + x + "  y: " + y);
		return null;
	}
	
	public Area getLastArea() 
	{
		if(currentRoute.size() == 0) 
		{
			return null;
		}
		else 
		{
			return currentRoute.get(currentRoute.size() - 1) ;
		}
	}
	
	public void moveToArea()
	{
		moveAllowed();
		
		if(!moveAllowed){
			stairsWaitTime++;
		} else if (moveAllowed){

			if(getLastArea() == null) {
				//Reached end of route
			} 
			else if (getLastArea().getXEnd() == x || getLastArea().getX() == x) {
				if(currentRoute.size() == 1 && getLastArea().getX() != x) {
					moveLeft();
				} else {
					currentRoute.remove(getLastArea());
				}
			}
			if(getLastArea() == null) {
				//Reached end of route
			} 
			else if(getLastArea().getX() > x && getLastArea().getRealY() == y) {
				moveRight();
			} 
			else if(getLastArea().getX() < x && getLastArea().getRealY() == y) {
				moveLeft();
			} 
			else if(getLastArea().getRealY() < y) {
				moveUp();
			} 
			else if(getLastArea().getRealY() > y) {
				moveDown();
			}
		}
	}	
	
	private void moveRight() {
		x++;
		translateXVal += GridBuilder.colSize;
		personImageView.setTranslateX(translateXVal);	
	}
	
	private void moveLeft() {
		x--;
		translateXVal -= GridBuilder.colSize;
		personImageView.setTranslateX(translateXVal);
	}
	
	private void moveDown() {
		y++;
		translateYVal += GridBuilder.rowSize;
		personImageView.setTranslateY(translateYVal);		
	}
	
	private void moveUp() {
		y--;
		translateYVal -= GridBuilder.rowSize;
		personImageView.setTranslateY(translateYVal);	
	}	
	
	public void setInvisible(){
		Platform.runLater(
		  () -> {
			personImageView.setVisible(false);
			visibility = false;
		  });
	}
	
	public void setDead()
	{
		alive = false;
	}
	
	public boolean getAliveStatus()
	{
		return alive;
	}
	
	public void moveAllowed()
	{
		if(moveAllowed)
		for (Area object: Area.getAreaList()) {
			if(object.getX() == x && object.getRealY() == y) {
				if(object.areaType == "Stairway"){
					moveAllowed = false;
				}
			}
		}
		else if(!moveAllowed)
		{
			if(stairsWaitTime == SettingBuilder.getStairTime())
			{
				moveAllowed = true;
				stairsWaitTime = 0;
			}
		}
	}	
	
	public int getId()
	{
		return id;
	}
	
	public int getX() 
	{
		return x;
	}

	public void setX(int x) 
	{
		this.x = x;
	}

	public int getY() 
	{
		return y;
	}
	
	public void setY(int y) 
	{
		this.y = y;
	}
	
	public void clearRoute()
	{
		currentRoute.clear();
	}
	
	public void setId(int guestId)
	{
		this.id = guestId;
	}
	
	public void setStatus(String status) 
	{
		this.status = status;
	}
	
	public String getStatus() 
	{
		return status;
	}
	
	public int getFitnessTickAmount() 
	{
		return fitnessTickAmount;
	}

	public void setFitnessTickAmount(int fitnessTickAmount) 
	{
		this.fitnessTickAmount = fitnessTickAmount;
	}
	
	public boolean isVisibility() 
	{
		return visibility;
	}

	public void setVisibility(boolean visibility) 
	{
		this.visibility = visibility;
	}
	
	public void performAction() 
	{
		
	}

	public void setRoomId(int roomId) 
	{
	
	}
	
	public int getSelectedRoom()
	{
		return 0;
	}
	
	public static Area getLobby()
	{
		for (Area object: Area.getAreaList()) {
			if(object instanceof Lobby) {
				return object;
			}
		}
		return null;
	}
		

	public boolean getAvailability() 
	{
		return false;
	}

	public void setAvailability(boolean b) 
	{
		// 
	}

	public void setVisible()
	{
		if(alive)
		{
			Platform.runLater(() -> 
			{
			  	personImageView.setVisible(true);
				visibility = true;
			});
		}
	}
}
