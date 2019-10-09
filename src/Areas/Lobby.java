package Areas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Managers.GridBuilder;
import Managers.HotelManager;
import Scenes.MainMenuScene;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * This is the Lobby area.
 *
 */

public class Lobby extends Area {

	//Constructor
	public Lobby(int id, int dimensionW, int dimensionH, int x, int y, String areaType)
	{
		JFrame parent = new JFrame();
		
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
			createSprite(new FileInputStream("src/Images/floor_bg_lobby.png"));
			
        } catch (FileNotFoundException e) {
            //
            e.printStackTrace();
        }
		
		HBox lobbyBackground = new HBox();
		lobbyBackground.setMinHeight(48);
		
		lobbyBackground.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent mouseEvent) {
		    	MainMenuScene.eventManager.pause();
		    	HotelManager.timer.pause();
		        System.out.println("mouse click detected! " + mouseEvent.getSource());
		        
		        JOptionPane.showMessageDialog(parent, 
		        		"Guests:"+
		        		"\nin hotel: "+HotelManager.currentGuestAmount+
		        		"\nin own room: "+HotelManager.currentGuestAmountInRoom+
		        		"\nin restaurant: "+HotelManager.currentGuestAmountInRestaurant+
		        		"\nin fitness: "+HotelManager.currentGuestAmountInFitness+
		        		"\nin cinema: "+HotelManager.currentGuestAmountInCinema+
		        		"\n"+
		        		"\nCleaners:"+
		        		"\nin hotel: "+HotelManager.currentCleanerAmount+
		        		"\nIn emergency: "+HotelManager.currentCleanerAmountInEmergencyCleaning+
		        		"\nIn Checkout cleaning: "+HotelManager.currentCleanerAmountInCleaning
		        		);
		        
		        MainMenuScene.eventManager.pause();
		        HotelManager.timer.pause();
		        
		    }
		});
		
		lobbyBackground.getChildren().addAll(roomImageView);
		
		// Paint the room on the grid
		GridBuilder.grid.add(lobbyBackground,x,y, dimensionW, dimensionH);
        roomImageView.setFitHeight(96);
        roomImageView.setFitWidth((GridBuilder.getMaxX() - 1) * 48);
	}

}
