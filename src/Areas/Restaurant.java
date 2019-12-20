package Areas;

import Managers.GridBuilder;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
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
		super(id, dimensionW, dimensionH, x, y, areaType);
		this.capacity = capacity;
		setSprite("src/Images/door_restaurant.png");

		HBox restaurantBg = new HBox();
		restaurantBg.setAlignment(Pos.BOTTOM_LEFT);
		restaurantBg.getChildren().addAll(roomImageView);
		
		// Paint the room on the grid
		GridBuilder.grid.add(restaurantBg,x,y, dimensionW, dimensionH);
	}
	
}
