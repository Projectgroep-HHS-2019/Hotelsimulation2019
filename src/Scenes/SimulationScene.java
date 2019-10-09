package Scenes;

import javafx.scene.layout.BorderPane;
import Managers.GridBuilder;
import javafx.scene.Scene;

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
		bPane.setMaxSize(600, 600);
	}
	
	/**
	 * method die een scene opbouwd voor het spel
	 */	
	public void setSimulationScene(){
		// TO DO auto - resize 
		simulationScene = new Scene(bPane, 800, 600);
		MainMenuScene.mainMenuStage.setScene(simulationScene);
	}

}
