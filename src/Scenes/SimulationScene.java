package Scenes;

import Managers.HotelManager;
import javafx.scene.layout.BorderPane;
import Managers.GridBuilder;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;

/**
 * This class holds the logic that is used building the scene of the simulation.
 *
 */

public class SimulationScene 
{
	private BorderPane bPane;

	public SimulationScene(){
		setBorderPane();
		setSimulationScene();
	}
	
	private void setBorderPane(){
		bPane = new BorderPane();
		bPane.setCenter(GridBuilder.grid);
		bPane.setMaxSize(100, 100);

		if(!GridBuilder.correctLayout){
			JFrame alertWindow = new JFrame();
			JOptionPane.showMessageDialog(alertWindow, "There's something wrong with the layout file.\nThis might cause the simulation not be able to run correctly!");
		}
	}
	
	/**
	 * method die een scene opbouwd voor het spel
	 */	
	private void setSimulationScene(){
		// TO DO auto - resize
		Scene simulationScene = new Scene(bPane, 700, 600);

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
