package Areas;

import Managers.GridBuilder;
import Managers.HotelManager;
import Managers.SettingBuilder;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;


/**
 * This is the Cinema area.
 *
 */

public class Cinema extends Area {

	//Constructor
	public Cinema(int id, int dimensionW, int dimensionH, int x, int y, String areaType) 
	{

		this.dimensionW = dimensionW;
		this.dimensionH = dimensionH;
		this.x = x;
		this.y = y;
		this.areaType = areaType;
        this.id = id;

        neighbours = new HashMap<>();
        distance = Integer.MAX_VALUE;
        latest = null;

		// Get the right image depending on dimensions
		try 
		{
			createSprite(new FileInputStream("src/Images/door_cinema.png"));
        } 
		catch (FileNotFoundException e) 
		{
            e.printStackTrace();
        }
		
		HBox cinemaBg = new HBox();
		cinemaBg.setAlignment(Pos.BOTTOM_LEFT);
		cinemaBg.getChildren().addAll(roomImageView);
		
		// Paint the room on the grid
		GridBuilder.grid.add(cinemaBg,x,y, dimensionW, dimensionH);
	}
	
	//Functions
	public void startMovie(){
		HotelManager.moviePlaying = true;
	}
	
	public void stopMovie(){
		HotelManager.moviePlaying = false;
		HotelManager.movieTimeRemaining = SettingBuilder.movieTime;
	}
}
