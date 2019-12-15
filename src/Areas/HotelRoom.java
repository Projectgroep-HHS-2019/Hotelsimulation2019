package Areas;

import Managers.GridBuilder;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * This is the HotelRoom area.
 *
 */

public class HotelRoom extends Area {

	private int stars;

	//Constructor
	public HotelRoom(int id, int dimensionW, int dimensionH, int stars, int x, int y, String areaType)
	{
		this.x = x;
		this.y = y;		
		this.dimensionW = dimensionW;
		this.dimensionH = dimensionH;
		this.stars = stars;
		this.areaType = areaType;
		this.id = id;

		neighbours = new HashMap<>();
        distance = Integer.MAX_VALUE;
        latest = null;

		// Get the right image depending on dimensions
		try {
			getImageForStars();
			createSprite(new FileInputStream("src/Images/"+imageLocation));
			
        } catch (FileNotFoundException e) {
            //
            e.printStackTrace();
        }
		
		HBox roomBg = new HBox();
		roomBg.setAlignment(Pos.BOTTOM_LEFT);
		roomBg.getChildren().addAll(roomImageView);
		
		// Paint the room on the grid
		GridBuilder.grid.add(roomBg,x,y, dimensionW, dimensionH);
	}	
	
	private void getImageForStars(){
		if (stars == 1){
			imageLocation = "door_1.png";
		} else if (stars == 2){
			imageLocation = "door_2.png";
		} else if (stars == 3){
			imageLocation = "door_3.png";
		} else if (stars == 4){
			imageLocation = "door_4.png";
		} else if (stars == 5){
			imageLocation = "door_5.png";
		}
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}
	
}
