package Managers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Constructor that builds the SettingBuilder.
 */

public class SettingBuilder {

	//Variables
	private ArrayList<String> settingList;
	public static int defaultTickSpeed = 1000;
	public static int tickSpeed = 100; //In miliseconds
	public static int cleaningTime = 3;
	public static int dieInLineTime = 5;
	public static int movieTime = 20;
	public static int stairTime = 2;

	//Functions
	@SuppressWarnings("unused")
	private void getSettings()
	{					
		String pathBase = System.getProperty("user.dir");
		String pathFile = "\\src\\settings\\settings.txt";
		String fullPath = pathBase+pathFile;
		System.out.println(fullPath);
		
		File file = new File(fullPath);
		Scanner input = null;
		try 
		{
			input = new Scanner(file);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		settingList = new ArrayList<String>();

		while (input.hasNextLine()) 
		{
		    settingList.add(input.nextLine());
		}
	}
		
	public static int getTickSpeed() {
		return tickSpeed;
	}

	public static void setTickSpeed(int TickSpeed) {
		tickSpeed = TickSpeed;
	}

	public static int getMovieTime() {
		return movieTime;
	}

	public static void setMovieTime(int MovieTime) {
		movieTime = MovieTime;
	}
	
	public static int getstairTime() {
		return stairTime;
	}

	public static void setStairTime(int StairTime) {
		stairTime = StairTime;
	}
	
	public static int getCleaningTime() {
		return cleaningTime;
	}
	
 	public static int getStairTime() {
		return stairTime;
	}
}
