package Areas;

import Managers.GridBuilder;
import javafx.scene.layout.HBox;

import java.util.HashMap;

/**
 * This is the Stairway area.
 *
 */

public class Stairway extends Area
{
	public Stairway(int id, int dimensionW, int dimensionH, int x, int y, String areaType)
	{
        super(id, dimensionW, dimensionH, x, y, areaType);

		HBox stairwayBg = new HBox();
		// Paint the room on the grid
		GridBuilder.grid.add(stairwayBg,x,y, dimensionW, dimensionH);
	}
}
