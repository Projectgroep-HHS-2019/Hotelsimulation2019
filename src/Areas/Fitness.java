package Areas;

import Managers.GridBuilder;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import java.util.HashMap;

/**
 * This is the Fitness area.
 *
 */

public class Fitness extends Area {

	//Constructor
	public Fitness(int id, int dimensionW, int dimensionH, int x, int y, String areaType)
	{
		super(id, dimensionW, dimensionH, x, y, areaType);
		setSprite("src/Images/door_fitness.png");

		HBox fitnessBg = new HBox();
		fitnessBg.setAlignment(Pos.BOTTOM_LEFT);
		fitnessBg.getChildren().addAll(roomImageView);
		
		// Paint the room on the grid
		GridBuilder.grid.add(fitnessBg,x,y, dimensionW, dimensionH);
	}
}
