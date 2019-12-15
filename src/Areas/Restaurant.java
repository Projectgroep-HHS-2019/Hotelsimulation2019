package Areas;

import Managers.GridBuilder;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * This is the Restaurant area.
 *
 */

public class Restaurant extends Area {

	public long capacity;

	//Constructor
	public Restaurant(int id, int dimensionW, int dimensionH, long capacity, int x, int y, String areaType)
	{

		this.dimensionW = dimensionW;
		this.dimensionH = dimensionH;
		this.x = x;
		this.y = y;
		this.areaType = areaType;
		this.id = id;

		this.capacity = capacity;

		neighbours = new HashMap<>();
        distance = Integer.MAX_VALUE;
        latest = null;

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
