package Scenes;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Managers.SettingBuilder;
import Scenes.MainMenuScene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * This class holds the logic that is used for the settings scene.
 *
 */

public class SettingsScene 
{
	
	public SettingsScene() {
		
	}
	
	public void buildScene() {
		
		JFrame parent = new JFrame();
		
		GridPane settingsGrid = new GridPane();
		Scene settingsScene = new Scene(settingsGrid, 400, 400);
		MainMenuScene.mainMenuStage.setScene(settingsScene);
		
		
		
		settingsGrid.setHgap(10);
		settingsGrid.setVgap(10);
		
		Label generalSettingMenuLabel = new Label("Setting Menu:");
		
		Label setting1Label = new Label("HTE speed: ");
		TextField setting1EnterValueField = new TextField (Integer.toString(SettingBuilder.getTickSpeed()));
		Label setting1InfoLabel = new Label(" (In miliseconds)");
		
		Label setting2Label = new Label("Movie takes: ");
		TextField setting2EnterValueField = new TextField (Integer.toString(SettingBuilder.getMovieTime()));
		Label setting2InfoLabel = new Label(" (HTE)");
		
		Label setting3Label = new Label("Cleaning takes: ");
		TextField setting3EnterValueField = new TextField (Integer.toString(SettingBuilder.getCleaningTime()));
		Label setting3InfoLabel = new Label(" (HTE)");
		
		Label setting4Label = new Label("Stairs takes: ");
		TextField setting4EnterValueField = new TextField (Integer.toString(SettingBuilder.getStairTime()));
		Label setting4InfoLabel = new Label(" (HTE)");
		
		
		settingsGrid.add(generalSettingMenuLabel, 3, 1, 1, 1);
		
		settingsGrid.add(setting1Label, 3, 4, 1, 1);
		settingsGrid.add(setting1EnterValueField, 4, 4, 1, 1);
		settingsGrid.add(setting1InfoLabel, 5, 4, 1, 1);
		
		settingsGrid.add(setting2Label, 3, 5, 1, 1);
		settingsGrid.add(setting2EnterValueField, 4, 5, 1, 1);
		settingsGrid.add(setting2InfoLabel, 5, 5, 1, 1);
		
		settingsGrid.add(setting3Label, 3, 6, 1, 1);
		settingsGrid.add(setting3EnterValueField, 4, 6, 1, 1);
		settingsGrid.add(setting3InfoLabel, 5, 6, 1, 1);
		
		settingsGrid.add(setting4Label, 3, 7, 1, 1);
		settingsGrid.add(setting4EnterValueField, 4, 7, 1, 1);
		settingsGrid.add(setting4InfoLabel, 5, 7, 1, 1);
		
				
		Button cancelButton = new Button("Back");
		settingsGrid.add(cancelButton, 3, 9, 1, 1);
		
		Button saveButton = new Button("Save");
		settingsGrid.add(saveButton, 4, 9, 1, 1);
			
		cancelButton.setOnAction(new EventHandler<ActionEvent>() 
		{
			public void handle(ActionEvent e) {
				MainMenuScene.mainMenuStage.setScene(MainMenuScene.mainMenuScene);
			}
		});
		
		saveButton.setOnAction(new EventHandler<ActionEvent>() 
		{
			public void handle(ActionEvent e) {
				
				boolean settingComplete = true;
				int setting1IntValue = 1;
				int setting2IntValue = 1;
				int setting3IntValue = 1;
				int setting4IntValue = 1;
				
				String setting1Value = setting1EnterValueField.getText();
				String setting2Value = setting2EnterValueField.getText();
				String setting3Value = setting3EnterValueField.getText();
				String setting4Value = setting4EnterValueField.getText();
				
				try {
					setting1IntValue = Integer.parseInt(setting1Value);
					setting2IntValue = Integer.parseInt(setting2Value);
					setting3IntValue = Integer.parseInt(setting3Value);
					setting4IntValue = Integer.parseInt(setting4Value);
					
			    } catch (NumberFormatException f) {
			    	settingComplete = false;
			    	JOptionPane.showMessageDialog(parent, "Only numbers allowed");;
			    }
				
				//HTE speed
				if (setting1IntValue < 0)
				{
					JOptionPane.showMessageDialog(parent, "Value can't be lower then 250");
					settingComplete = false;
				}
				else if (setting1IntValue > 2000)
				{
					JOptionPane.showMessageDialog(parent, "Value can't be higher then 2000");
					settingComplete = false;
				}
				
				//Movie
				if (setting2IntValue <= 0)
				{
					JOptionPane.showMessageDialog(parent, "Value can't be 0 or lower");
					settingComplete = false;
				}
				
				//Cleaning
				if (setting3IntValue <= 0)
				{
					JOptionPane.showMessageDialog(parent, "Value can't be 0 or lower");
					settingComplete = false;
				}
				else if (setting3IntValue >= 5)
				{
					JOptionPane.showMessageDialog(parent, "Value can't be 5 or higher");
					settingComplete = false;
				}
				
				//Stairs
				if (setting4IntValue <= 0)
				{
					JOptionPane.showMessageDialog(parent, "Value can't be 0 or lower");
					settingComplete = false;
				}
				else if (setting4IntValue >= 5)
				{
					JOptionPane.showMessageDialog(parent, "Value can't be 5 or higher");
					settingComplete = false;
				}
				
				if (settingComplete)
				{
					SettingBuilder.tickSpeed = setting1IntValue;
					SettingBuilder.movieTime = setting2IntValue;
					SettingBuilder.cleaningTime = setting3IntValue;
					SettingBuilder.stairTime = setting4IntValue;
					
					JOptionPane.showMessageDialog(parent, "The settings are saved!");
				}
				else if(!settingComplete) {
					JOptionPane.showMessageDialog(parent, "The settings are not saved!");
					settingComplete = true;
				}
			}
		});
	}
}
