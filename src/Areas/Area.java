package Areas;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
	protected int x;
	protected int y;
	protected int dimensionW;
	protected int dimensionH;
	protected boolean available = true;
	protected ImageView roomImageView;
	protected Image roomImage;
	protected String areaType;

	public Area(int id, int dimensionW, int dimensionH, int x, int y, String areaType) {
		this.dimensionW = dimensionW;
		this.dimensionH = dimensionH;
		this.x = x;
		this.y = y;
		this.areaType = areaType;
		this.id = id;

		neighbours = new HashMap<>();
		distance = Integer.MAX_VALUE;
		latest = null;
	}

	//Functions
	private void createSprite(FileInputStream sprite){
		Image roomImage = new Image(sprite);
		roomImageView = new ImageView();
		roomImageView.setFitWidth(16);
		roomImageView.setFitHeight(37);
		roomImageView.setImage(roomImage);
		GridPane.setHalignment(roomImageView, HPos.LEFT);
		GridPane.setValignment(roomImageView, VPos.BOTTOM);
	}

	protected void setSprite(String spriteFile){
		// Get the right image depending on dimensions
		try {
			createSprite(new FileInputStream(spriteFile));

		} catch (FileNotFoundException e) {
			//
			e.printStackTrace();
		}
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
