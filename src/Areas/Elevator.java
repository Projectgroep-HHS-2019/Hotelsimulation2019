package Areas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

import Managers.GridBuilder;

/**
 * This is the Elevator area.
 *
 */

public class Elevator extends Area {

	//Constructor
	public Elevator(int id, int dimensionW, int dimensionH, int x, int y, String areaType)
	{
		this.dimensionW = dimensionW;
		this.dimensionH = dimensionH;
		this.x = x;
		this.y = y;
		this.areaType = areaType;
		
        neighbours = new HashMap<>();
        distance = Integer.MAX_VALUE;
        latest = null;
        this.id = id;
		
		
		// Get the right image depending on dimensions
		try {
			createSprite(new FileInputStream("src/Images/elevator_cabin.png"));
			
        } catch (FileNotFoundException e) {
            //
            e.printStackTrace();
        }
		
		// Paint the room on the grid
		GridBuilder.grid.add(roomImageView,x,y, dimensionW, dimensionH);
		roomImageView.setTranslateX(13);
        roomImageView.setFitWidth(24);
        roomImageView.setFitHeight(48);
		
	}

}
