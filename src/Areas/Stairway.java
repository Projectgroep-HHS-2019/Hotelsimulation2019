package Areas;

import java.util.HashMap;

import Managers.GridBuilder;
import javafx.scene.layout.HBox;

/**
 * This is the Stairway area.
 *
 */

public class Stairway extends Area 
{
	public Stairway(int id, int dimensionW, int dimensionH, int x, int y, String areaType)
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

		HBox stairwayBg = new HBox();		
		// Paint the room on the grid
		GridBuilder.grid.add(stairwayBg,x,y, dimensionW, dimensionH);			
	}
}
