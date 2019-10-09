package Areas;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * This is the abstract class Area it holds the logic for all area types.=
 *
 */

public abstract class Area 
{
	// Variables for Dijkstra

    public HashMap<Area, Integer> neighbours;
    public int distance;
    public Area latest;
    public int id;
    public static List<Area> areaList =  Collections.synchronizedList(new ArrayList<Area>());
    
	// Variables
	protected int x = 0;
	protected int y = 0;
	public int dimensionW;
	protected int dimensionH;	
	public boolean available = true;
	public int stars = 1;
	protected ImageView roomImageView;
	protected Image roomImage;
	protected String imageLocation;
	public String areaType;
	public long capacity;
		
	//Functions
	protected void createSprite(FileInputStream sprite){
		Image roomImage = new Image(sprite);
        roomImageView = new ImageView();
        roomImageView.setFitWidth(16);
        roomImageView.setFitHeight(37);
        roomImageView.setImage(roomImage);	
		GridPane.setHalignment(roomImageView, HPos.LEFT);
		GridPane.setValignment(roomImageView, VPos.BOTTOM);
	}
	
    public static List<Area> getAreaList()
    {
    	return areaList;
    }
    
	public int getDistance() 
	{
		return dimensionW;
	}	   
    
	public int getX() 
	{
		return x;
	}

	public int getXEnd() 
	{
		return x + dimensionW -1;
	}
	
	public int getRealY() 
	{
		return (y - 1) + dimensionH;
	}

	public int getY() 
	{
		return y;
	}

	public void setAvailability(boolean b) 
	{
		this.available = b;
	}
	
}
