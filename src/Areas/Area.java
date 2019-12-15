package Areas;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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
    private static List<Area> areaList =  Collections.synchronizedList(new ArrayList<>());
    
	// Variables
	protected int x = 0;
	protected int y = 0;
	protected int dimensionW;
	protected int dimensionH;	
	protected boolean available = true;
	protected ImageView roomImageView;
	protected Image roomImage;
	protected String imageLocation;
	protected String areaType;

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

	public boolean isAvailable()
	{
		return available;
	}

	public String getAreaType()
	{
		return areaType;
	}

	public void setAvailability(boolean b) 
	{
		this.available = b;
	}
	
}
