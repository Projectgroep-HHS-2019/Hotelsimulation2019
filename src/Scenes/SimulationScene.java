package Scenes;

import Managers.HotelManager;
import javafx.scene.layout.BorderPane;
import Managers.GridBuilder;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class holds the logic that is used building the scene of the simulation.
 *
 */

public class SimulationScene 
{

	private BorderPane bPane;
	private Scene simulationScene;

	public SimulationScene(){
		setBorderPane();
		setSimulationScene();
	}
	
	public void setBorderPane(){
		bPane = new BorderPane();
		bPane.setCenter(GridBuilder.grid);
		bPane.setMaxSize(100, 100);
	}
	
	/**
	 * method die een scene opbouwd voor het spel
	 */	
	public void setSimulationScene(){
		// TO DO auto - resize
		simulationScene = new Scene(bPane, 700, 600);

		Stage stage = new Stage();
		stage.setScene(simulationScene);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setOnCloseRequest( e -> {
			MainMenuScene.eventManager.pause();
			HotelManager.timer.pause();
		});
		stage.show();
	}

}
