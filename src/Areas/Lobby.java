package Areas;

import Managers.GridBuilder;
import Managers.HotelManager;
import Scenes.MainMenuScene;
import javafx.scene.layout.HBox;

import javax.swing.*;
import java.util.HashMap;

/**
 * This is the Lobby area.
 *
 */

public class Lobby extends Area {

	//Constructor
	public Lobby(int id, int dimensionW, int dimensionH, int x, int y, String areaType)
	{
		super(id, dimensionW, dimensionH, x, y, areaType);
		JFrame parent = new JFrame();
		setSprite("src/Images/floor_bg_lobby.png");
		
		HBox lobbyBackground = new HBox();
		lobbyBackground.setMinHeight(48);
		
		lobbyBackground.setOnMouseClicked(mouseEvent -> {
			MainMenuScene.eventManager.pause();
			HotelManager.timer.pause();
			System.out.println("mouse click detected! " + mouseEvent.getSource());

			JOptionPane.showMessageDialog(parent,
					"Guests:"+
					"\nin hotel: "+ HotelManager.currentGuestAmount+
					"\nin own room: "+ HotelManager.currentGuestAmountInRoom+
					"\nin restaurant: "+ HotelManager.currentGuestAmountInRestaurant+
					"\nin fitness: "+ HotelManager.currentGuestAmountInFitness+
					"\nin cinema: "+ HotelManager.currentGuestAmountInCinema+
					"\n"+
					"\nCleaners:"+
					"\nin hotel: "+ HotelManager.currentCleanerAmount+
					"\nIn emergency: "+ HotelManager.currentCleanerAmountInEmergencyCleaning+
					"\nIn Checkout cleaning: "+ HotelManager.currentCleanerAmountInCleaning
					);

			MainMenuScene.eventManager.pause();
			HotelManager.timer.pause();

		});
		
		lobbyBackground.getChildren().addAll(roomImageView);
		
		// Paint the room on the grid
		GridBuilder.grid.add(lobbyBackground,x,y, dimensionW, dimensionH);
        roomImageView.setFitHeight(96);
        roomImageView.setFitWidth((GridBuilder.getMaxX() - 1) * 48);
	}

}
