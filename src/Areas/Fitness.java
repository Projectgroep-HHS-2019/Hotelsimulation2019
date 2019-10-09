package Areas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import Managers.GridBuilder;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

/**
 * This is the Fitness area.
 *
 */

public class Fitness extends Area {

	//Constructor
	public Fitness(int id, int dimensionW, int dimensionH, int x, int y, String areaType)
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
			createSprite(new FileInputStream("src/Images/door_fitness.png"));
			
        } catch (FileNotFoundException e) {
            //
            e.printStackTrace();
        }
		
		
		HBox fitnessBg = new HBox();		
		fitnessBg.setAlignment(Pos.BOTTOM_LEFT);	
		fitnessBg.getChildren().addAll(roomImageView);
		
		// Paint the room on the grid
		GridBuilder.grid.add(fitnessBg,x,y, dimensionW, dimensionH);
	}
}
