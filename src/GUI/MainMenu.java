package GUI;

import Scenes.MainMenuScene;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The MainMenu will call the method that starts the mainmenu and is the entry point of the program.
 *
 */

public class MainMenu extends Application
{
		
	public static void main(String[]args) 
	{
	    launch(args);
	}
	
	//Method die hoofdmenu start.
	
	public void start(Stage stage)
	{
		new MainMenuScene(stage);
	}
}
