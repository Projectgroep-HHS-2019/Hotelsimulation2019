package Areas;

import Managers.GridBuilder;
import java.util.HashMap;

/**
 * This is the Elevator area.
 *
 */

public class Elevator extends Area {

	//Constructor
	public Elevator(int id, int dimensionW, int dimensionH, int x, int y, String areaType)
	{
		super(id, dimensionW, dimensionH, x, y, areaType);
		setSprite( "src/Images/elevator_cabin.png");

		// Paint the room on the grid
		GridBuilder.grid.add(roomImageView,x,y, dimensionW, dimensionH);
		roomImageView.setTranslateX(13);
        roomImageView.setFitWidth(24);
        roomImageView.setFitHeight(48);
		
	}

}
