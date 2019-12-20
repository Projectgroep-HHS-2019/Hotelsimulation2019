package Areas;

import Managers.GridBuilder;
import Managers.HotelManager;
import Managers.SettingBuilder;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import java.util.HashMap;


/**
 * This is the Cinema area.
 *
 */

public class Cinema extends Area {

	//Constructor
	public Cinema(int id, int dimensionW, int dimensionH, int x, int y, String areaType)
	{
 		super(id, dimensionW, dimensionH, x, y, areaType);
 		setSprite("src/Images/door_cinema.png");

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
