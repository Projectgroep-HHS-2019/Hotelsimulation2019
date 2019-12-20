package Areas;

import Managers.GridBuilder;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

/**
 * This is the HotelRoom area.
 *
 */

public class HotelRoom extends Area {

	private int stars;
	private String image;

	//Constructor
	public HotelRoom(int id, int dimensionW, int dimensionH, int stars, int x, int y, String areaType)
	{
		super(id, dimensionW, dimensionH, x, y, areaType);
		this.stars = stars;
		getImageForStars();
		setSprite("src/Images/"+image);

		HBox roomBg = new HBox();
		roomBg.setAlignment(Pos.BOTTOM_LEFT);
		roomBg.getChildren().addAll(roomImageView);
		
		// Paint the room on the grid
		GridBuilder.grid.add(roomBg,x,y, dimensionW, dimensionH);
	}	
	
	private void getImageForStars(){
		if (stars == 1){
			image = "door_1.png";
		} else if (stars == 2){
			image = "door_2.png";
		} else if (stars == 3){
			image = "door_3.png";
		} else if (stars == 4){
			image = "door_4.png";
		} else if (stars == 5){
			image = "door_5.png";
		}
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}
	
}
