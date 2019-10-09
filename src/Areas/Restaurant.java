package Areas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

import Managers.GridBuilder;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

/**
 * This is the Restaurant area.
 *
 */

public class Restaurant extends Area {

	//Constructor
	public Restaurant(int id, int dimensionW, int dimensionH, long capacity, int x, int y, String areaType)
	{
		this.dimensionW = dimensionW;
		this.dimensionH = dimensionH;
		this.x = x;
		this.y = y;
		this.capacity = 2; //capacity
		this.areaType = areaType;
		
        neighbours = new HashMap<>();
        distance = Integer.MAX_VALUE;
        latest = null;
        this.id = id;
		
		// Get the right image depending on dimensions
		try {
			createSprite(new FileInputStream("src/Images/door_restaurant.png"));
			
        } catch (FileNotFoundException e) {
            //
            e.printStackTrace();
        }
		
		HBox restaurantBg = new HBox();
		restaurantBg.setAlignment(Pos.BOTTOM_LEFT);
		restaurantBg.getChildren().addAll(roomImageView);
		
		// Paint the room on the grid
		GridBuilder.grid.add(restaurantBg,x,y, dimensionW, dimensionH);
	}
	
}
